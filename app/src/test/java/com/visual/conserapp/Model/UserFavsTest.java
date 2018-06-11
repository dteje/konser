package com.visual.conserapp.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserFavsTest {
    UserFavs userFavs;
    ArrayList<Favs> listFavs;

    @Before
    public void init() {
        User user = new User("Ferran", "1234", "email@gmail.com", true);
        Favs fav1 = new Favs("Name1", "NameUser1", "[Bacon, Bacon]", 2.0);
        Favs fav2 = new Favs("Name2", "NameUser2", "[Tomate, Bacon]", 2.0);
        Favs fav3 = new Favs("Name3", "NameUser3", "[Patatas Fritas, Bacon]", 2.4);
        listFavs = new ArrayList<Favs>();
        listFavs.add(fav1);
        listFavs.add(fav2);
        listFavs.add(fav3);
        userFavs = new UserFavs(user.getName(), listFavs, user.getEmailAsId());
    }

    @Test
    public void correctName() {
        assertEquals("Ferran", userFavs.getUsername());
    }

    @Test
    public void correctId() {
        assertEquals("email@gmail,com", userFavs.getId());
    }

    @Test
    public void correctList() {
        assertEquals("Name1", userFavs.getListFavs().get(0).getNameSandwichOfficial());
    }

    @Test
    public void setName(){
        userFavs.setUsername("Pablo");
        assertEquals("Pablo", userFavs.getUsername());
    }

    @Test
    public void setList(){
        Favs fav1 = new Favs("NewName1", "NewNameUser1", "[Bacon, Bacon]", 2.0);
        Favs fav2 = new Favs("NewName2", "NewNameUser2", "[Tomate, Bacon]", 2.0);
        Favs fav3 = new Favs("NewName3", "NewNameUser3", "[Patatas Fritas, Bacon]", 2.4);
        listFavs = new ArrayList<Favs>();
        listFavs.add(fav1);
        listFavs.add(fav2);
        listFavs.add(fav3);
        assertEquals("[Tomate, Bacon]", userFavs.getListFavs().get(1).getIngredientes());
    }

}