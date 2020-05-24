package com.mypoc.ws.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
 
public class OrderDAO {
    private static OrderDAO instance;
    private static List<Order> data = new ArrayList<>();
     
    static {
        data.add(new Order(1, "John Doe", new Date(System.currentTimeMillis() - 5000000000L), 1299.99f));
        data.add(new Order(2, "Jane Smith", new Date(System.currentTimeMillis() - 4000000000L), 999.99f));     
        data.add(new Order(3, "Andrew White", new Date(System.currentTimeMillis() - 3000000000L), 799.99f));     
        data.add(new Order(4, "Greg Smith", new Date(System.currentTimeMillis() - 2000000000L), 599.99f));     
    }
     
    private OrderDAO() {
         
    }
     
    public static OrderDAO getInstance() {
        if (instance == null) {
            instance = new OrderDAO();
        }
         
        return instance;               
    }
     
    public List<Order> listAll() {
        return new ArrayList<Order>(data);
    }
     
    public Order add(String name, float total) {
        int newId = data.size() + 1;
        Order newP = new Order();
        newP.setId(newId);
        newP.setName(name);
        newP.setDate(new Date());
        newP.setTotal(total);
        
        data.add(newP);
         
        return newP;
    }
     
    public Order get(int id) {
        Order orderToFind = new Order(id);
        int index = data.indexOf(orderToFind);
        if (index >= 0) {
            return data.get(index);
        }
        return null;
    }
     
    public boolean delete(int id) {
        Order orderToFind = new Order(id);
        int index = data.indexOf(orderToFind);
        if (index >= 0) {
            data.remove(index);
            return true;
        }
         
        return false;
    }
     
    public boolean update(Order order) {
        int index = data.indexOf(order);
        if (index >= 0) {
            data.set(index, order);
            return true;
        }
        return false;
    }
}