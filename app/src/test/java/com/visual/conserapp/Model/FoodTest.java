package com.visual.conserapp.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FoodTest {
    Food food;


    @Before
    public void init() {
        String categoryID = "0001";
        String description = "description";
        String discount = "0";
        String ingredients = "[Patata, Queso, Bacon]";
        String image = "link image";
        String price = "3";
        String ID = "101";
        food = new Food(categoryID, description, discount, ingredients, image, price, ID);
    }

    @Test
    public void correctCategoryID() {
        assertEquals("0001", food.getCategoryID());
    }

    @Test
    public void correctIngredients() {
        assertEquals("[Patata, Queso, Bacon]", food.getIngredients());
    }

    @Test
    public void correctImage() {
        assertEquals("link image", food.getImage());
    }

    @Test
    public void setDescription() {
        food.setDescription("New description");
        assertEquals("New description", food.getDescription());
    }

    @Test
    public void setID() {
        food.setID("102");
        assertEquals("102", food.getID());
    }


}