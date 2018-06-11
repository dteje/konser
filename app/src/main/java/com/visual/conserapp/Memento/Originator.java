package com.visual.conserapp.Memento;

        import com.visual.conserapp.Model.Food;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by daniel on 10/06/2018.
 */

public class Originator {

    private ArrayList<String> state;

    public void setState(ArrayList<String> state){
        this.state = state;
    }

    public ArrayList<String> getState(){
        return state;
    }

    public Memento saveStateToMemento(){
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento){
        state = memento.getState();
    }
}