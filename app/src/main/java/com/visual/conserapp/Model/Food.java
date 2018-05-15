package com.visual.conserapp.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by daniel on 30/04/2018.
 */

public class Food {

    private String Name;
    private String Price;
    private String Discount;
    private String Ingredientes;
    private String Description;
    private String Image;
    private String CategoryID;

    public Food(String CategoryID, String Description, String Discount, String Ingredientes, String Image, String Name, String Price) {
        this.Name = Name;
        this.Price = Price;
        this.Discount = Discount;
        this.Description = Description;
        this.Image = Image;
        this.CategoryID = CategoryID;
        this.Ingredientes = Ingredientes;
        //this.Ingredientes = new ArrayList<>();
    }

    public Food() {
    }

    public Food(String name) {
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        this.Discount = discount;
    }

    public String getIngredientes() {
        return this.Ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.Ingredientes = ingredientes;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public List<String> getListOfIngredients() {
        List<String> listaIds = Arrays.asList(this.Ingredientes.split(","));
        return listaIds;
    }

}