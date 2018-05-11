package com.visual.conserapp.Model;

/**
 * Created by daniel on 30/04/2018.
 */

public class Ingredient {

    String Name;
    String CategoryID;
    double pricebuy;
    double pricesell;

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }



    public Ingredient(){}
    public Ingredient(String name, String CategoryID) {
        this.Name = name; this.CategoryID = CategoryID;
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
