package com.mypoc.ws.products;

import java.util.ArrayList;
import java.util.List;
 
public class ProductDAO {
    private static ProductDAO instance;
    private static List<Product> data = new ArrayList<>();
     
    static {
        data.add(new Product(1, "Samsung S20 Ultra", 1299.99f));
        data.add(new Product(2, "Samsung S20+", 999.99f));     
        data.add(new Product(3, "Samsung S10+", 799.99f));     
        data.add(new Product(4, "Samsung S10", 599.99f));     
        data.add(new Product(5, "Apple iPhone 11 Pro Max", 1399.99f));     
        data.add(new Product(6, "Apple iPhone 11 Pro", 1199.99f));     
        data.add(new Product(7, "Apple iPhone 11", 1099.99f));     
        data.add(new Product(8, "Apple iPhone X", 899.99f));     
    }
     
    private ProductDAO() {
         
    }
     
    public static ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
         
        return instance;               
    }
     
    public List<Product> listAll() {
        return new ArrayList<Product>(data);
    }
     
    public Product add(String name, float price) {
        int newId = data.size() + 1;
        Product newP = new Product();
        newP.setId(newId);
        newP.setName(name);
        newP.setPrice(price);
        
        data.add(newP);
         
        return newP;
    }
     
    public Product get(int id) {
        Product productToFind = new Product(id);
        int index = data.indexOf(productToFind);
        if (index >= 0) {
            return data.get(index);
        }
        return null;
    }
     
    public boolean delete(int id) {
        Product productToFind = new Product(id);
        int index = data.indexOf(productToFind);
        if (index >= 0) {
            data.remove(index);
            return true;
        }
         
        return false;
    }
     
    public boolean update(Product product) {
        int index = data.indexOf(product);
        if (index >= 0) {
            data.set(index, product);
            return true;
        }
        return false;
    }
}