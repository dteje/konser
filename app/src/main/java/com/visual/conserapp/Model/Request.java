package com.visual.conserapp.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 09/05/2018.
 */

public class Request {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    private String id;
    private String clientname;
    private String total;
    private List<Order> orders;
    private Boolean done;
    private Boolean payed;

    public String getPickupHour() {
        return pickupHour;
    }

    public void setPickupHour(String pickupHour) {
        this.pickupHour = pickupHour;
    }

    private String pickupHour;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> foods) {
        this.orders = foods;
    }

    public Request() {
        this.orders = new ArrayList<Order>();
    }

    public Request(String name, String total, List<Order> orders) {
        this.clientname = name;
        this.total = total;
        this.orders = orders;
        this.done = false;
        this.payed = false;
    }

    public Request(String id, String name, String total, List<Order> orders, String pickupHour) {
        this.id = id;
        this.clientname = name;
        this.total = total;
        this.orders = orders;
        this.done = false;
        this.payed = false;
        this.pickupHour = pickupHour;
    }

    public Boolean getDone() {
        return this.done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getPayed() {
        return this.payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    public String toString(){
        return ("ID: "+id+", Cliente: "+clientname+", Total: "+total+", Orders: "+orders.toString()+", DONE: "+done.toString());
    }
}
