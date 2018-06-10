package com.visual.conserapp.Memento;

import android.util.Log;

import com.visual.conserapp.Model.Ingredient;

import java.util.ArrayList;

public class CareTaker {

    private ArrayList<Memento> savedIngredients = new ArrayList<Memento>();
    private  ArrayList<Memento> good = new ArrayList<Memento>();

    public void addMemento(Memento memento){
        savedIngredients.add(memento);
        good.add(memento);
    }

    public Memento getMemento(int index){
       return good.get(index);
    }

    public ArrayList<Memento> getMementos(){
        return good;
    }


}
