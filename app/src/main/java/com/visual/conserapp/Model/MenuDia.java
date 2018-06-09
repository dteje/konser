package com.visual.conserapp.Model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by daniel on 30/04/2018.
 */

public class MenuDia {

    private String Name;
    private Double Price;
    private String Plato1;
    private String Plato2;
    private String Plato3;
    private String IdsPlato1;
    private String IdsPlato2;
    private String IdsPlato3;


    public MenuDia(String name, Double price, String plato1, String plato2, String plato3, String idsPlato1, String idsPlato2, String idsPlato3) {
        Name = name;
        Price = price;
        Plato1 = plato1;
        Plato2 = plato2;
        Plato3 = plato3;
        IdsPlato1 = idsPlato1;
        IdsPlato2 = idsPlato2;
        IdsPlato3 = idsPlato3;
    }

    public MenuDia(String name, Double price, String plato1, String plato2, String plato3) {
        Name = name;
        Price = price;
        Plato1 = plato1;
        Plato2 = plato2;
        Plato3 = plato3;
    }

    public MenuDia() {
    }

    public MenuDia(String name) {
        this.Name = name;
    }

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

    public String getPlato1() {
        return Plato1;
    }

    public void setPlato1(String plato1) {
        Plato1 = plato1;
    }

    public String getPlato2() {
        return Plato2;
    }

    public void setPlato2(String plato2) {
        Plato2 = plato2;
    }

    public String getPlato3() {
        return Plato3;
    }

    public void setPlato3(String plato3) {
        Plato3 = plato3;
    }

    public String getIdsPlato1() {
        return IdsPlato1;
    }

    public void setIdsPlato1(String idsPlato1) {
        this.IdsPlato1 = idsPlato1;
    }

    public String getIdsPlato2() {
        return IdsPlato2;
    }

    public void setIdsPlato2(String idsPlato2) {
        this.IdsPlato2 = idsPlato2;
    }

    public String getIdsPlato3() {
        return IdsPlato3;
    }

    public void setIdsPlato3(String idsPlato3) {
        this.IdsPlato3 = idsPlato3;
    }


}