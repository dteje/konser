package com.visual.conserapp.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IngredientTest {

    Ingredient ing;

    @Before
    public void init() {
        ing = new Ingredient("Tomate", "Verdura");
    }

    @Test
    public void correctName() {
        assertEquals("Tomate", ing.getName());
    }

    @Test
    public void correctType() {
        assertEquals("Verdura", ing.getType());
    }

    @Test
    public void setName(){
        ing.setName("Pollo");
        assertEquals("Pollo", ing.getName());
    }

    @Test
    public void setType(){
        ing.setType("Carne_Pescado");
        assertEquals("Carne_Pescado", ing.getType());
    }

}