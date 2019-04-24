package com.visual.conserapp.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MenuDiaTest {

    MenuDia menuDia;

    @Before
    public void init() {

        String categoryID = "0001";
        String description = "description";
        String discount = "0";
        String ingredients = "[Patata, Queso, Bacon]";
        String image = "link image";
        String price = "2.5";
        String ID = "111";
        Food food1 = new Food(categoryID, description, discount, ingredients, image, price, ID);

        String categoryID2 = "0002";
        String description2 = "description";
        String discount2 = "0";
        String ingredients2 = "[Lechuga, Tomate, Pollo, Queso Freso]";
        String image2 = "link image2";
        String price2 = "2.8";
        String ID2 = "118";
        Food food2 = new Food(categoryID2, description2, discount2, ingredients2, image2, price2, ID2);

        ArrayList<Food> foodList = new ArrayList<Food>();
        foodList.add(food1);
        foodList.add(food2);

        String name = "Menu Especial";
        double priceMenu = 5;
        String idsPlato1 = "400";
        String idsPlato2 = "405";
        String idsPlato3 = "415";
        menuDia = new MenuDia(name, priceMenu, foodList, idsPlato1, idsPlato2, idsPlato3);
    }

    @Test
    public void correctName() {
        assertEquals("Menu Especial", menuDia.getName());
    }

    @Test
    public void correctList() {
        assertEquals("[Lechuga, Tomate, Pollo, Queso Freso]", menuDia.getListaPlatos().get(1).getIngredients());
    }

    @Test
    public void correctIDPlato3() {
        assertEquals("415", menuDia.getIdsPlato3());
    }

    @Test
    public void setPrice() {
        menuDia.setPrice(5.5);
        assertEquals("5.5", String.valueOf(menuDia.getPrice()));
    }

    @Test
    public void setIDPlato1() {
        menuDia.setIdsPlato1("440");
        assertEquals("440", menuDia.getIdsPlato1());
    }


}