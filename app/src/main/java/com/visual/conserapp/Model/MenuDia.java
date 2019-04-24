package com.visual.conserapp.Model;

import android.support.annotation.NonNull;
import android.view.Menu;

import java.util.Arrays;
import java.util.List;

/**
 * Created by daniel on 30/04/2018.
 */

public class MenuDia implements Comparable{

    private String Name;
    private Double Price;
    private List<Food> listaPlatos;
    private String IdsPlato1;
    private String IdsPlato2;
    private String IdsPlato3;

    public MenuDia(String name, Double price, List<Food> listaPlatos, String idsPlato1, String idsPlato2, String idsPlato3) {
        Name = name;
        Price = price;
        this.listaPlatos = listaPlatos;
        IdsPlato1 = idsPlato1;
        IdsPlato2 = idsPlato2;
        IdsPlato3 = idsPlato3;
    }


    public MenuDia(String name) {
        Name = name;
    }

    public MenuDia(){}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public List<Food> getListaPlatos() {
        return listaPlatos;
    }

    public void setListaPlatos(List<Food> listaPlatos) {
        this.listaPlatos = listaPlatos;
    }

    public String getIdsPlato1() {
        return IdsPlato1;
    }

    public void setIdsPlato1(String idsPlato1) {
        IdsPlato1 = idsPlato1;
    }

    public String getIdsPlato2() {
        return IdsPlato2;
    }

    public void setIdsPlato2(String idsPlato2) {
        IdsPlato2 = idsPlato2;
    }

    public String getIdsPlato3() {
        return IdsPlato3;
    }

    public void setIdsPlato3(String idsPlato3) {
        IdsPlato3 = idsPlato3;
    }

    public String getPlatosOrdenados(){
        String res1 = listaPlatos.get(0).getName();
        String res2 = listaPlatos.get(1).getName();
        String res3 = listaPlatos.get(2).getName();

        String res = res1+", "+res2+", "+res3;

        return res;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        MenuDia m = (MenuDia) o;
        return this.Name.toLowerCase().compareTo(m.getName().toLowerCase());
    }
}