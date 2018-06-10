package com.visual.conserapp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.visual.conserapp.Adapter.RecyclerClickListener;
import com.visual.conserapp.Memento.CareTaker;
import com.visual.conserapp.Memento.CareTakerString;
import com.visual.conserapp.Memento.Memento;
import com.visual.conserapp.Memento.MementoString;
import com.visual.conserapp.Memento.Originator;
import com.visual.conserapp.Memento.OriginatorString;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.R;

import java.util.ArrayList;

public class TestMemento extends AppCompatActivity {


    ArrayList<String> strings;
    CareTakerString careTaker = new CareTakerString();
    OriginatorString originator = new OriginatorString();
    int savedFiles = 0, currentIngredient = 0;
    ArrayList<MementoString> testListMemento = careTaker.getMementos();


    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_memento);

        Ingredient ing1 = new Ingredient("name", "type");
        Ingredient ing2 = new Ingredient("name2", "type");
        Ingredient ing3 = new Ingredient("name3", "type");
        Ingredient ing4 = new Ingredient("name4", "type");

        strings = new ArrayList<String>();

        strings.add("one");
        strings.add("one");
        strings.add("one");
        strings.add("one");



        textView = findViewById(R.id.textView);
        textView.setText(strings.toString());
    }

    public void remove(View view) {
        strings.remove(0);
    }

    public void undo(View view) {

        if (currentIngredient >= 1) {
            currentIngredient--;

            ArrayList<String> listIng = originator.restoreFromMemento(careTaker.getMemento(currentIngredient));
            strings = listIng;

            for(int i = 0; i < careTaker.getMementos().size(); i++){
                Log.d("mementos> ", careTaker.getMemento(i).getList().toString());
            }

            textView.setText(strings.toString());

        }
    }

    public void saveState(View view) {

        originator.setList(strings);
        careTaker.addMemento(originator.storeInMemento());

        savedFiles++;
        currentIngredient++;

        for(int i = 0; i < careTaker.getMementos().size(); i++){
            Log.d("mementos> ", careTaker.getMemento(i).getList().toString());
        }

        textView.setText(strings.toString());
    }

    public void redo(View view) {

        if ((savedFiles - 1) > currentIngredient) {
            currentIngredient++;

            ArrayList<String> listIng = originator.restoreFromMemento(careTaker.getMemento(currentIngredient));
            strings = listIng;

            for(int i = 0; i < careTaker.getMementos().size(); i++){
                Log.d("mementos> ", careTaker.getMemento(i).getList().toString());
            }


            textView.setText(strings.toString());
        }


    }

}
