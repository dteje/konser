package com.visual.conserapp.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by daniel on 30/04/2018.
 */

public class Food {

    @Override
    public String toString() {
        return "Food{" +
                "Name='" + Name + '\'' +
                ", Price='" + Price + '\'' +
                ", Discount='" + Discount + '\'' +
                ", Ingredients='" + Ingredients + '\'' +
                ", Description='" + Description + '\'' +
                ", Image='" + Image + '\'' +
                ", CategoryID='" + CategoryID + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }

    private String Name;
    private String Price;
    private String Discount;
    private String Ingredients;
    private String Description;
    private String Image;
    private String CategoryID;
    private String ID;

    public Food(String CategoryID, String Description, String Discount, String ID, String Ingredients, String Image, String Name, String Price) {
        this.Name = Name;
        this.Price = Price;
        this.Discount = Discount;
        this.Description = Description;
        this.Image = Image;
        this.CategoryID = CategoryID;
        this.Ingredients = Ingredients;
        this.ID = ID;
        //this.Ingredients = new ArrayList<>();
    }

    public Food(String CategoryID, String Description, String Discount, String Ingredients, String Image, String Name, String Price) {
        this.Name = Name;
        this.Price = Price;
        this.Discount = Discount;
        this.Description = Description;
        this.Image = Image;
        this.CategoryID = CategoryID;
        this.Ingredients = Ingredients;
        //this.Ingredients = new ArrayList<>();
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



    public String getIngredients() {
        return this.Ingredients;
    }

    public void setIngredients(String Ingredients) {
        this.Ingredients = Ingredients;
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



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public List<String> getListOfIngredientes() {
        List<String> listaIds = Arrays.asList(this.Ingredients.split(","));
        return listaIds;
    }

}