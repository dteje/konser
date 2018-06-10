package com.visual.conserapp.Memento;

import com.visual.conserapp.Model.Ingredient;

import java.util.ArrayList;

public class MementoString{


    private ArrayList<String> list;

    public MementoString(ArrayList<String> list) {
        this.list = list;
    }

    public ArrayList<String> getList() {
        return list;
    }
}
