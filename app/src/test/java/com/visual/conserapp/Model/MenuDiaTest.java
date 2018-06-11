package com.visual.conserapp.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MenuDiaTest {

    private MenuDia menuDia;
    private String name = "nombre";
    private Double price = 0.0;
    private List<Food> listaPlatos = new ArrayList<>();
    private String idsPlato1="ids1",idsPlato2="ids2",idsPlato3="ids3";


    @Before
    public void setUp(){
        menuDia = new MenuDia(name,price,listaPlatos,idsPlato1,idsPlato2,idsPlato3);
    }

    @Test
    public void testName() {
        //get
        assertEquals(name, menuDia.getName());
        //set
        menuDia.setName("menuPrueba");
        assertEquals("menuPrueba", menuDia.getName());
        menuDia.setName(name);
    }

    @Test
    public void testPrice() {
        //get
        assertEquals();
        //set
    }


    @Test
    public void getListaPlatos() {
    }

    @Test
    public void setListaPlatos() {
    }

    @Test
    public void getIdsPlato1() {
    }

    @Test
    public void setIdsPlato1() {
    }

    @Test
    public void getIdsPlato2() {
    }

    @Test
    public void setIdsPlato2() {
    }

    @Test
    public void getIdsPlato3() {
    }

    @Test
    public void setIdsPlato3() {
    }

    @Test
    public void getPlatosOrdenados() {
    }
}