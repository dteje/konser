package com.visual.conserapp.Views.CRUD.Modify;

import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.visual.conserapp.Model.Food;
import com.visual.conserapp.R;

/**
 * Created by daniel on 06/06/2018.
 */

public class CrudEditFood extends CrudEdit {

    private String toolbarTitle = "Plato";
    private EditText txt_name, txt_price, txt_ings, txt_img, txt_desc;
    private Food food;


    @Override
    protected void getItemsById() {
        txt_id = findViewById(R.id.crud_txt_id);
        txt_name = findViewById(R.id.crud_et_name);
        txt_price = findViewById(R.id.crud_et_price);
        txt_ings = findViewById(R.id.crud_et_ings);
        txt_img = findViewById(R.id.crud_et_img);
        txt_desc = findViewById(R.id.crud_et_desc);

    }

    @Override
    protected DatabaseReference getReferenceForDatabase() {
        return database.getReference("Food");
    }

    @Override
    protected void collectData(DataSnapshot dataSnapshot) {
        food = dataSnapshot.child(id).getValue(Food.class);

    }

    @Override
    protected void displayDataOnScreen() {
        txt_id.setText("ID " + id);
        txt_name.setText(food.getName());
        txt_price.setText(food.getPrice());
        txt_ings.setText(food.getIngredientes());
        txt_img.setText(food.getImage());
        txt_desc.setText(food.getDescription());

    }

    @Override
    protected void createNewObject() {
        map.put("ID",newid);
        map.put("Name",txt_name.getText().toString());
        map.put("Price",txt_price.getText().toString());
        map.put("Ingredients",txt_ings.getText().toString());
        map.put("Description",txt_desc.getText().toString());
        map.put("Image",txt_img.getText().toString());
        map.put("CategoryID","");
        map.put("Discount","");
    }

    @Override
    protected void updateObject() {
        //TODO CHECK FIELDS ARE OK
        food.setName(txt_name.getText().toString());
        food.setPrice(txt_price.getText().toString());
        food.setIngredientes(txt_price.getText().toString());
        food.setDescription(txt_desc.getText().toString());
        food.setImage(txt_img.getText().toString());

        map.put(id+"/Name",txt_name.getText().toString());
        map.put(id+"/Price",txt_price.getText().toString());
        map.put(id+"/Ingredients",txt_ings.getText().toString());
        map.put(id+"/Description",txt_desc.getText().toString());
        map.put(id+"/Image",txt_img.getText().toString());
    }

    @Override
    protected void setViewLayout() {
        setContentView(R.layout.crud_food);
    }

    @Override
    protected String getToolbarTitle() {
        return this.toolbarTitle;
    }




}
