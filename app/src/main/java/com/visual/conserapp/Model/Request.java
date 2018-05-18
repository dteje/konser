package com.visual.conserapp.Model;

import java.util.List;

/**
 * Created by daniel on 09/05/2018.
 */

public class Request {
    private String clientname;
    private String total;
    private List<Order> foods;

    public String getClientName() {
        return clientname;
    }

    public void setClientName(String name) {
        this.clientname = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }



    public Request(String name, String total, List<Order> foods) {
        this.clientname = name;
        this.total = total;
        this.foods = foods;
    }


}
