package com.visual.conserapp;

import android.app.ProgressDialog;
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

public class SignIn extends AppCompatActivity {
    EditText edtEmail, edtPassword, edtName;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtEmail = (MaterialEditText) findViewById(R.id.edtEmail);
        edtName = (MaterialEditText) findViewById(R.id.edtName);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);

        btnSignIn = (Button) findViewById(R.id.btn_confirmsignin);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pd = new ProgressDialog(SignIn.this);
                pd.setMessage("Espere por favor...");
                pd.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    //mDialog.setMessage("Please wait");


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(edtEmail.getText().toString()).exists()) {

                            User user = dataSnapshot.child(edtEmail.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                Toast.makeText(SignIn.this, "Acceso correcto", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignIn.this, "Password incorrecto", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SignIn.this,"Usuario no registrado", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
