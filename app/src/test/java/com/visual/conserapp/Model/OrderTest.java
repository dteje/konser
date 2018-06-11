package com.visual.conserapp.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by daniel on 11/06/2018.
 */
public class OrderTest {

    Order order;
    int orderid;
    String productid, productname, productquantity, productprice, productdiscount;

    @Before
    public void setUp() {
        orderid = 12345;
        productid = "100";
        productname = "Bocadillo de calamares";
        productquantity = "2";
        productprice = "2.00";
        productdiscount = "0";
        order = new Order(orderid, productid, productname, productquantity, productprice, productdiscount);
    }

    @Test
    public void getID() throws Exception {
        assertEquals(orderid, order.getID());
    }

    @Test
    public void setID() throws Exception {
        order.setID(1234);
        assertEquals(1234, order.getID());
    }

    @Test
    public void getProductID() throws Exception {
        assertEquals(productid, order.getProductID());
    }

    @Test
    public void setProductID() throws Exception {
        order.setProductID("101");
        assertEquals("101", order.getProductID());
    }

    @Test
    public void getProductName() throws Exception {
        assertEquals(productname, order.getProductName());
    }

    @Test
    public void setProductName() throws Exception {
        order.setProductName("Bocadillo irlandes");
        assertEquals("Bocadillo irlandes", order.getProductName());
    }

    @Test
    public void getQuantity() throws Exception {
        assertEquals(productquantity, order.getQuantity());
    }

    @Test
    public void setQuantity() throws Exception {
        order.setQuantity("10");
        assertEquals("10", order.getQuantity());
    }

    @Test
    public void getPrice() throws Exception {
        assertEquals(productprice, order.getPrice());
    }

    @Test
    public void setPrice() throws Exception {
        order.setPrice("5.45");
        assertEquals("5.45", order.getPrice());
    }

    @Test
    public void getDiscount() throws Exception {
        assertEquals(productdiscount, order.getDiscount());
    }

    @Test
    public void setDiscount() throws Exception {
        order.setDiscount("4");
        assertEquals("4", order.getDiscount());
    }

}