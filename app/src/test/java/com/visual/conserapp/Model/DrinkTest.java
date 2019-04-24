package com.visual.conserapp.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DrinkTest {

    Drink drink;

    @Before
    public void init() {
        String name = "Agua";
        String price = "1";
        String image = "link image";
        String ID = "305";
        drink = new Drink(name, price, image, ID);
    }

    @Test
    public void correctName() {
        assertEquals("Agua", drink.getName());
    }

    @Test
    public void correctPrice() {
        assertEquals("1", drink.getPrice());
    }

    @Test
    public void setDescription() {
        drink.setID("306");
        assertEquals("306", drink.getID());
    }

    @Test
    public void setName() {
        drink.setName("Agua con gas");
        assertEquals("Agua con gas", drink.getName());
    }


}