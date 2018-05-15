package com.visual.conserapp.Model;

/**
 * Created by daniel on 30/04/2018.
 */

public class Ingredient {

    private String Name;
    private String type;
    private double pricebuy;
    private double pricesell;

    public String getCategoryID() {
        return type;
    }

    public void setCategoryID(String type) {
        type = this.type;
    }



    public Ingredient(){}
    public Ingredient(String name, String type) {
        this.Name = name; this.type = type;
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
