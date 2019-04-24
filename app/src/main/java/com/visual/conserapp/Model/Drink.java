package com.visual.conserapp.Model;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

/**
 * Created by daniel on 30/04/2018.
 */

public class Drink implements Comparable{

    @Override
    public String toString() {
        return "Drink{" +
                "Name='" + Name + '\'' +
                ", Price='" + Price + '\'' +
                ", Image='" + Image + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }

    private String Name;
    private String Price;
    private String Image;
    private String ID;

    public Drink(String name, String price, String image, String ID) {
        Name = name;
        Price = price;
        Image = image;
        this.ID = ID;
    }

    public Drink(String name) {
        Name = name;
    }

    public Drink() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Drink d = (Drink) o;
        return this.Name.toLowerCase().compareTo(d.getName().toLowerCase());
    }
}