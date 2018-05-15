package com.visual.conserapp.Model;

import java.util.List;

/**
 * Created by daniel on 09/05/2018.
 */

public class Request {
    private String name;
    private String total;
    private List<Order> foods;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        this.name = name;
        this.total = total;
        this.foods = foods;
    }


}