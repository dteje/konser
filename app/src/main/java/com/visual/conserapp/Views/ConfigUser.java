package com.visual.conserapp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.visual.conserapp.Common.Common;
import com.visual.conserapp.Model.User;
import com.visual.conserapp.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daniel on 11/06/2018.
 */

public class ConfigUser extends AppCompatActivity{

    private Toolbar toolbar;
    private DatabaseReference table_user;
    private TextView txtname, txtmail, txtold, txtnew;
    private User user;
    private Map<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Mis datos");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        table_user = FirebaseDatabase.getInstance().getReference("User");

        txtname = (TextView) findViewById(R.id.txt_name);
        txtmail = (TextView) findViewById(R.id.txt_mail);
        txtold = (TextView) findViewById(R.id.txt_old);
        txtnew = (TextView) findViewById(R.id.txt_new);

        user = Common.currentUser;

        txtname.setText(user.getName());
        txtmail.setText(user.getEmail());

        map = new HashMap<>();
    }

    public void onUpdate(View view){
        user.setEmail(txtmail.getText().toString());
        user.setName(txtname.getText().toString());
        if(txtold.getText().toString().equals(user.getPassword())){
            if(!txtnew.getText().toString().isEmpty()){
                user.setPassword(txtnew.getText().toString());
            }

        }
        map.put(user.getEmailAsId(),user);
        table_user.updateChildren(map);
        Common.currentUser = user;
        finish();
    }

    public boolean onNavSuperior(MenuItem menuitem) {
        View view = menuitem.getActionView();
        int id = menuitem.getItemId();
        Intent intent;
        if (id == R.id.cart_id) intent = new Intent(this, Cart.class);
        else if (id == R.id.sandwitchCreator_id) intent = new Intent(this, SandwitchCreator.class);
        else intent = new Intent(this, Home.class);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

}
