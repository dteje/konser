package com.visual.conserapp.Model;

/**
 * Created by daniel on 30/04/2018.
 */

public class Ingredient {

    private String Name;
    private String Type;
    private double pricebuy;
    private double pricesell;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ingredient(){}
    public Ingredient(String name, String type) {
        this.Name = name; this.Type = type;
    }
    public Ingredient(String id, String name, String type) {
        this.id = id;
        this.Name = name;
        this.Type = type;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public double getPricebuy() {
        return pricebuy;
    }

    public void setPricebuy(double pricebuy) {
        this.pricebuy = pricebuy;
    }

    public double getPricesell() {
        return pricesell;
    }

    public void setPricesell(double pricesell) {
        this.pricesell = pricesell;
    }

}