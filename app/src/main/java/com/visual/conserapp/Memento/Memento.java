package com.visual.conserapp.Memento;

import com.visual.conserapp.Model.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 10/06/2018.
 */

public class Memento {
    private ArrayList<String> listIngredients;

    public Memento(ArrayList<String> listIngredients) {
        this.listIngredients = listIngredients;
    }

    public Memento(){}

    public ArrayList<String> getState(){
        return listIngredients;
    }



}
