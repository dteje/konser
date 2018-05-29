package com.visual.conserapp.Views.CRUD;

import android.graphics.Color;
import android.os.Bundle;

import com.visual.conserapp.R;

/**
 * Created by daniel on 29/05/2018.
 */

public class Users extends Crud{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        retrieveDataFromFB();
    }




}
