package com.visual.conserapp.Memento;

import android.util.Log;

import com.visual.conserapp.Model.Ingredient;

import java.util.ArrayList;

public class OriginatorString {

    private ArrayList<String> list;

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public MementoString storeInMemento() {
        return new MementoString(list);
    }

    public ArrayList<String> restoreFromMemento(MementoString memento) {
        list = memento.getList();

        return list;
    }

}
