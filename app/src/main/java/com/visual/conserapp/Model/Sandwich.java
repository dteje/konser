package com.visual.conserapp.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 30/04/2018.
 */

public class Sandwich {

    String name;
    Double price;
    Double discount;
    List<Ingredient> ingredientes;

    public Sandwich(){
        this.ingredientes = new ArrayList<>();
    }
    public Sandwich(String name) {
        this.name = name;
        this.ingredientes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public List<Ingredient> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingredient> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
