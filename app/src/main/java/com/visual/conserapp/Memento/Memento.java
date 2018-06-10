package com.visual.conserapp.Memento;

import com.visual.conserapp.Model.Food;

import java.util.List;

/**
 * Created by daniel on 10/06/2018.
 */

public class Memento {
    private List<Food> state;

    public Memento(List<Food> state) {
        this.state = state;
    }

    public List<Food> getState() {
        return state;
    }

}
