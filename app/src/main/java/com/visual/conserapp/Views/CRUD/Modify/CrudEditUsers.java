package com.visual.conserapp.Views.CRUD.Modify;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.visual.conserapp.Model.User;
import com.visual.conserapp.R;

/**
 * Created by daniel on 06/06/2018.
 */

public class CrudEditUsers extends CrudEdit {

    private String toolbarTitle = "Usuario";
    private TextView txt_id;
    private EditText txt_name, txt_email, txt_pwd_old, txt_pwd1, txt_pwd2;
    private CheckBox ckb_admin;
    private User user;


    @Override
    protected void getItemsById() {
        txt_id = findViewById(R.id.crud_txt_id);
        txt_name = findViewById(R.id.crud_et_name);
        txt_email = findViewById(R.id.crud_et_email);
        txt_pwd_old = findViewById(R.id.crud_et_pwd_old);
        txt_pwd1 = findViewById(R.id.crud_et_pwd1);
        txt_pwd2 = findViewById(R.id.crud_et_pwd2);
        ckb_admin = findViewById(R.id.crud_ckb_admin);
    }

    @Override
    protected DatabaseReference getReferenceForDatabase() {
        return database.getReference("User");
    }

    @Override
    protected void collectData(DataSnapshot dataSnapshot) {
        user = dataSnapshot.child(id).getValue(User.class);
    }

    @Override
    protected void displayDataOnScreen() {
        txt_id.setText(id);
        txt_name.setText(user.getName());
        txt_email.setText(user.getEmail());
        txt_pwd_old.setText("");
        txt_pwd1.setText("");
        txt_pwd2.setText("");
        ckb_admin.setChecked(user.getAdmin());
    }

    @Override
    protected void createNewObject() {
        user = new User(txt_name.getText().toString(), txt_pwd1.getText().toString(),
                txt_email.getText().toString(), ckb_admin.isChecked());
        map.put("Admin", user.getAdmin());
        map.put("Email", user.getEmail());
        map.put("Name", user.getName());
        map.put("Password", user.getPassword());
        newid = user.getEmailAsId();
    }

    @Override
    protected void updateObject() {
        //TODO CHECK FIELDS ARE OK
        user.setName(txt_name.getText().toString());
        if (user.getPassword().equals(txt_pwd_old.getText().toString())) {
            if (txt_pwd1.getText().toString().equals(txt_pwd2.getText().toString())) {
                user.setPassword(txt_pwd1.toString());
                map.put(id + "/Password", txt_pwd1.getText().toString());
            }
        }
        user.setEmail(txt_email.toString());
        user.setAdmin(ckb_admin.isChecked());

        map.put(id + "/Name", txt_name.getText().toString());
        map.put(id + "/Email", txt_email.getText().toString());
        map.put(id + "/Admin", ckb_admin.isChecked());
    }

    @Override
    protected void setViewLayout() {
        setContentView(R.layout.crud_user);
    }

    @Override
    protected String getToolbarTitle() {
        return this.toolbarTitle;
    }


}
