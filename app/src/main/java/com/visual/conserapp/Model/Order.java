package com.visual.conserapp.Model;

/**
 * Created by daniel on 27/04/2018.
 */

public class Order {


    private int ID;
    private String ProductID;
    private String ProductName;
    private String Quantity;
    private String Price;
    private String Discount;

    public Order(int ID, String productID, String productName, String quantity, String price, String discount) {
        this.ID = ID;
        ProductID = productID;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
    }

    public Order() {
    }

    public Order(String productID, String productName, String quantity, String price, String discount) {
        ProductID = productID;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
    }


    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getProductID() {

        return ProductID;
    }

    public void setProductID(String productID) {
        this.ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        this.ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        this.Quantity = quantity;
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



}