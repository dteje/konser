package com.visual.conserapp.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.AlertFactory.AlertEnum;
import com.visual.conserapp.AlertFactory.AlertFactory;
import com.visual.conserapp.AlertFactory.AlertParent;
import com.visual.conserapp.Common.Common;
import com.visual.conserapp.Model.Favs;
import com.visual.conserapp.Model.User;
import com.visual.conserapp.Model.UserFavs;
import com.visual.conserapp.R;

import java.util.ArrayList;

public class FavsPopWindow extends Activity {

    Favs favs;
    UserFavs userFavs;
    DatabaseReference favs_table;
    DatabaseReference userfavs_table;
    FirebaseDatabase database;

    Intent intent;

    EditText textInputLayout;

    ArrayList<Favs> listFavs;

    String nameOfficial;
    double price;
    String listIngredients;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_favs_popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        listFavs = new ArrayList<Favs>();

        getWindow().setLayout((int) (width), (int) (height * 0.3));

        obtainDataFirebase();

        intent = getIntent();

        getDataFromSandwichCreator(intent);

        textInputLayout = (EditText) findViewById(R.id.textInputNombre);

        User user = Common.getInstance().currentUser;
        String id = user.getEmailAsId();
        String username = user.getName();
        userFavs = new UserFavs(username, listFavs, id);

    }

    public void getDataFromSandwichCreator(Intent in) {
        Bundle extras = in.getExtras();
        nameOfficial = extras.getString("nameSandwichOfficial");
        price = extras.getDouble("price");
        listIngredients = extras.getString("listIngredients");
    }

    public void generateFavs(View view) {

        String userName = textInputLayout.getText().toString();

        AlertFactory alertFactory = new AlertFactory();
        AlertParent alertParent = alertFactory.generateAlert(AlertEnum.EMPTY_SANDWICH);

        if (userName.equals("")) alertParent.printAlert(this);
        else {
            favs = new Favs(nameOfficial, userName, listIngredients, price);
            listFavs.add(favs);
            userFavs.setListFavs(listFavs);

            userfavs_table.child(userFavs.getId()).setValue(userFavs);


            Toast toast = Toast.makeText(this, "Añadido a favoritos!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            finish();
        }

    }

    public void declareDatabase() {
        database = FirebaseDatabase.getInstance();
        favs_table = database.getReference("Favs");
        userfavs_table = database.getReference("UserFavs");
    }

    public void obtainDataFirebase() {
        declareDatabase();

        userfavs_table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                obtainFavs(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void obtainFavs(DataSnapshot dataSnapshot) {
        listFavs.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            if(ds.getKey().equals(Common.getInstance().currentUser.getEmailAsId())){
                listFavs = ds.getValue(UserFavs.class).getListFavs();
            }
        }
    }
}
