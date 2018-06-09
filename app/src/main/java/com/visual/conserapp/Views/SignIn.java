package com.visual.conserapp.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.visual.conserapp.Common.Common;
import com.visual.conserapp.Views.Home;
import com.visual.conserapp.Model.User;

import io.paperdb.Paper;

import com.visual.conserapp.R;

public class SignIn extends AppCompatActivity {
    EditText edtEmail, edtPassword, edtName;
    Button btnSignIn;
    com.rey.material.widget.CheckBox cb_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtEmail = (MaterialEditText) findViewById(R.id.edtEmail);
        edtName = (MaterialEditText) findViewById(R.id.edtName);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        btnSignIn = (Button) findViewById(R.id.btn_confirmsignin);
        cb_remember = (com.rey.material.widget.CheckBox) findViewById(R.id.chbRemember);

        Paper.init(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cb_remember.isChecked()) {
                    Paper.book().write(Common.USER_KEY, encodeUserEmail(edtEmail.getText().toString()));
                    Paper.book().write(Common.PWD_KEY, edtPassword.getText().toString());
                }


                final ProgressDialog pd = new ProgressDialog(SignIn.this);
                pd.setMessage("Espere por favor...");
                pd.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(encodeUserEmail(edtEmail.getText().toString())).exists()) {
                            pd.dismiss();
                            User user = dataSnapshot.child(encodeUserEmail(edtEmail.getText().toString())).getValue(User.class);
                            user.setEmail(encodeUserEmail(edtEmail.getText().toString()));
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                Intent intent = new Intent(SignIn.this,Home.class);
                                Common.currentUser = user;
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SignIn.this, "Password incorrecto", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            System.out.println("ELSE");
                            Toast.makeText(SignIn.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Cancelled");
                    }
                });
            }
        });
    }

    private static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    private static String decodeUserEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }
}
