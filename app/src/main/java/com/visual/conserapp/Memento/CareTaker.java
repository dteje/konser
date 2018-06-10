package com.visual.conserapp.Memento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 10/06/2018.
 */

public class CareTaker {
    private List<Memento> mementoList = new ArrayList<Memento>();

    public void add(Memento state){
        mementoList.add(state);
        state = new Memento();
    }


    public Memento get(int index){
        return mementoList.get(index);
    }

    public int getSize(){
        return mementoList.size();
    }
}