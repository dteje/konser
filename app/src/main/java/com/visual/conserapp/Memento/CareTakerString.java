package com.visual.conserapp.Memento;

import android.util.Log;

import com.visual.conserapp.Model.Ingredient;

import java.util.ArrayList;

public class CareTakerString {

    private ArrayList<MementoString> mementos = new ArrayList<MementoString>();
    private  ArrayList<Memento> good = new ArrayList<Memento>();

    public void addMemento(MementoString memento){
        mementos.add(memento);
    }

    public MementoString getMemento(int index){
        return mementos.get(index);
    }

    public ArrayList<MementoString> getMementos(){
        return mementos;
    }


}
