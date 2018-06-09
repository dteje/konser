package com.visual.conserapp.Views.CRUD.Modify;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.visual.conserapp.Model.Ingredient;
import com.visual.conserapp.R;

/**
 * Created by daniel on 06/06/2018.
 */

public class CrudEditIngredients extends CrudEdit {

    private String toolbarTitle = "Plato";
    private TextView txt_id;
    private EditText txt_name;
    private RadioGroup radioGroup;
    private Ingredient ingredient;


    @Override
    protected void getItemsById() {
        txt_id = findViewById(R.id.crud_txt_id);
        txt_name = findViewById(R.id.crud_et_name);
        radioGroup = findViewById(R.id.radiobuttongroup);
        radioGroup.check(R.id.rbCarne);
    }

    @Override
    protected DatabaseReference getReferenceForDatabase() {
        return database.getReference("Ingredient");
    }

    @Override
    protected void collectData(DataSnapshot dataSnapshot) {
        ingredient = dataSnapshot.child(id).getValue(Ingredient.class);

    }

    @Override
    protected void displayDataOnScreen() {
        txt_id.setText("ID " + id);
        txt_name.setText(ingredient.getName());
    }

    @Override
    protected void modifyDisplayToNew() {
        newid = (String) getIntent().getExtras().get("newid");
        txt_id.setText(newid);
        btn_delete.setVisibility(View.INVISIBLE);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewObject();
                table.child(newid).setValue(map);
                finish();
            }
        });
    }

    @Override
    protected void createNewObject() {
        ingredient = new Ingredient(txt_name.getText().toString(),obtainType());
        map.put("id",newid);
        map.put("name",txt_name.getText().toString());
        map.put("type",obtainType());
        map.put("pricebuy",0);
        map.put("pricesell",0);
    }

    @Override
    protected void updateObject() {
        //TODO CHECK FIELDS ARE OK
        ingredient.setName(txt_name.getText().toString());
        ingredient.setType(obtainType());
        map.put(id+"/name",txt_name.getText().toString());
        map.put(id+"/type",obtainType());

    }

    @Override
    protected void setViewLayout() {
        setContentView(R.layout.crud_ingredient);
    }

    @Override
    protected String getToolbarTitle() {
        return this.toolbarTitle;
    }

    public String obtainType() {
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);
        RadioButton r = (RadioButton) radioGroup.getChildAt(idx);
        String type = r.getText().toString();
        return type;
    }
}
