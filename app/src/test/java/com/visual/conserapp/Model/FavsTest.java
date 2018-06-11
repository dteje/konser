package com.visual.conserapp.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FavsTest {

    Favs favs;


    @Before
    public void init() {
        favs = new Favs("NameSandwich", "MySandwich", "[Patatas Fritas, Bacon]", 2.4);
    }

    @Test
    public void correctNameSandwich() {
        assertEquals("NameSandwich", favs.getNameSandwichOfficial());
    }

    @Test
    public void correctNameUser() {
        assertEquals("MySandwich", favs.getNameSandwichUser());
    }

    @Test
    public void correctIngredients() {
        assertEquals("[Patatas Fritas, Bacon]", favs.getIngredientes());
    }

    @Test
    public void setName() {
        favs.setNameSandwichUser("FavSandwich");
        assertEquals("FavSandwich", favs.getNameSandwichUser());
    }

    @Test
    public void setIngredients() {
        favs.setIngredientes("[Patatas Fritas, Bacon, Huevo Frito]");
        assertEquals("[Patatas Fritas, Bacon, Huevo Frito]", favs.getIngredientes());
    }


}