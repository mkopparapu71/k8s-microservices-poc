#!/bin/bash

AWS_REGION=us-east-1
CLUSTER_NAME=k8s-microservices-poc

eksctl create cluster --name $CLUSTER_NAME --region $AWS_REGION --fargate
kubectl get svc

eksctl utils associate-iam-oidc-provider --cluster $CLUSTER_NAME --approve

wget -O alb-ingress-iam-policy.json https://raw.githubusercontent.com/kubernetes-sigs/aws-alb-ingress-controller/master/docs/examples/iam-policy.json
aws iam create-policy --policy-name ALBIngressControllerIAMPolicy --policy-document file://alb-ingress-iam-policy.json

STACK_NAME=eksctl-$CLUSTER_NAME-cluster
VPC_ID=$(aws cloudformation describe-stacks --stack-name "$STACK_NAME" | jq -r '[.Stacks[0].Outputs[] | {key: .OutputKey, value: .OutputValue}] | from_entries' | jq -r '.VPC')
AWS_ACCOUNT_ID=$(aws sts get-caller-identity | jq -r '.Account')

cat > rbac-role.yaml <<-EOF
---
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  labels:
    app.kubernetes.io/name: alb-ingress-controller
  name: alb-ingress-controller
rules:
  - apiGroups:
      - ""
      - extensions
    resources:
      - configmaps
      - endpoints
      - events
      - ingresses
      - ingresses/status
      - services
    verbs:
      - create
      - get
      - list
      - update
      - watch
      - patch
  - apiGroups:
      - ""
      - extensions
    resources:
      - nodes
      - pods
      - secrets
      - services
      - namespaces
    verbs:
      - get
      - list
      - watch
---
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  labels:
    app.kubernetes.io/name: alb-ingress-controller
  name: alb-ingress-controller
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: alb-ingress-controller
subjects:
  - kind: ServiceAccount
    name: alb-ingress-controller
    namespace: kube-system
EOF
kubectl apply -f rbac-role.yaml

eksctl create iamserviceaccount --name alb-ingress-controller --namespace kube-system --cluster $CLUSTER_NAME --attach-policy-arn arn:aws:iam::$AWS_ACCOUNT_ID:policy/ALBIngressControllerIAMPolicy --approve

cat > alb-ingress-controller.yaml <<-EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app.kubernetes.io/name: alb-ingress-controller
  name: alb-ingress-controller
  namespace: kube-system
spec:
  selector:
    matchLabels:
      app.kubernetes.io/name: alb-ingress-controller
  template:
    metadata:
      labels:
        app.kubernetes.io/name: alb-ingress-controller
    spec:
      containers:
      - name: alb-ingress-controller
        args:
        - --ingress-class=alb
        - --cluster-name=$CLUSTER_NAME
        - --aws-vpc-id=$VPC_ID
        - --aws-region=$AWS_REGION
        image: docker.io/amazon/aws-alb-ingress-controller:v1.1.6
      serviceAccountName: alb-ingress-controller
EOF
kubectl apply -f alb-ingress-controller.yaml

cat > nginx-deployment.yaml <<-EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  name: "nginx-deployment"
  namespace: "default"
spec:
  replicas: 3
  selector:
    matchLabels:
      app: "nginx"
  template:
    metadata:
      labels:
        app: "nginx"
    spec:
      containers:
      - image: nginx:latest
        imagePullPolicy: Always
        name: "nginx"
        ports:
        - containerPort: 80
EOF
kubectl apply -f nginx-deployment.yaml

cat > nginx-service.yaml <<-EOF
apiVersion: v1
kind: Service
metadata:
  annotations:
    alb.ingress.kubernetes.io/target-type: ip
  name: "nginx-service"
  namespace: "default"
spec:
  ports:
  - port: 80
    targetPort: 80
    protocol: TCP
  type: NodePort
  selector:
    app: "nginx"
EOF
kubectl apply -f nginx-service.yaml

cat > nginx-ingress.yaml <<-EOF
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: "nginx-ingress"
  namespace: "default"
  annotations:
    kubernetes.io/ingress.class: alb
    alb.ingress.kubernetes.io/scheme: internet-facing
  labels:
    app: nginx-ingress
spec:
  rules:
  - http:
      paths:
      - path: /*
        backend:
          serviceName: "nginx-service"
          servicePort: 80
EOF
kubectl apply -f nginx-ingress.yaml

kubectl get ingress nginx-ingress

LOADBALANCER_PREFIX=$(kubectl get ingress nginx-ingress -o json | jq -r '.status.loadBalancer.ingress[0].hostname' | cut -d- -f1)
TARGETGROUP_ARN=$(aws elbv2 describe-target-groups | jq -r '.TargetGroups[].TargetGroupArn' | grep $LOADBALANCER_PREFIX)
aws elbv2 describe-target-health --target-group-arn $TARGETGROUP_ARN | jq -r '.TargetHealthDescriptions[].TargetHealth.State'

kubectl get pods -o wide

