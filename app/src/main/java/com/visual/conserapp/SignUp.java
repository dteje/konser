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
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    EditText edtEmail, edtName, edtPass1;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtEmail = (MaterialEditText) findViewById(R.id.edtEmail);
        edtName = (MaterialEditText) findViewById(R.id.edtName);
        edtPass1 = (MaterialEditText) findViewById(R.id.edtPassword1);

        btnSignUp = (Button) findViewById(R.id.btn_confirmsignup);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pd = new ProgressDialog(SignUp.this);
                pd.setMessage("Please wait");
                pd.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(edtEmail.getText().toString()).exists()){
                            pd.dismiss();
                            Toast.makeText(SignUp.this,"Email ya registrado",Toast.LENGTH_SHORT).show();
                        }

                    else{
                        pd.dismiss();
                        User user = new User(edtName.getText().toString(), edtPass1.getText().toString());
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
