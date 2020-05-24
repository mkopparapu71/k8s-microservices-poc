package com.mypoc.ws.frontend;

import java.util.Date;

public class Order {
    private int id;
    private String name;
    private Date date;
    private float total;
 
    public Order(int id) {
        this.id = id;
    }
 
    public Order() {
    }
 
    public Order(int id, String name, Date date, float total) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.total = total;
    }
 
    @Override
    public int hashCode() {
        final int prime = 41;
        int result = 1;
        result = prime * result + id;
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (id != other.id)
            return false;
        return true;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
}