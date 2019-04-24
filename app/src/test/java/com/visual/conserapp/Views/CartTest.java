package com.visual.conserapp.Views;

import com.visual.conserapp.Database.Database;
import com.visual.conserapp.Model.Order;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by daniel on 11/06/2018.
 */
public class CartTest {
    @Test
    public void updateCart() throws Exception {
        Cart cart = new Cart();
        //Database database = new Database();
        int quantity1 = 1;
        int quantity2 = 3;
        double price1 = 2.00;
        double price2 = 4.50;
        double total1, total2, total;
        Order order1 = new Order("productID1", "productName1", ""+quantity1, ""+price1, "0");
        Order order2 = new Order("productID2", "productName2", ""+quantity2, ""+price2, "0");


    }

}