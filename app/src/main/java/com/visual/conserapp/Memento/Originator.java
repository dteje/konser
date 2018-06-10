package com.visual.conserapp.Memento;

import com.visual.conserapp.Model.Food;

import java.util.List;

/**
 * Created by daniel on 10/06/2018.
 */

public class Originator {

    private List<Food> state;

    public void setState(List<Food> state){
        this.state = state;
    }

    public List<Food> getState(){
        return state;
    }

    public Memento saveStateToMemento(){
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento){
        state = memento.getState();
    }
}