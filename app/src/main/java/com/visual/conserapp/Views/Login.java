package com.visual.conserapp.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visual.conserapp.Common.Common;
import com.visual.conserapp.Model.User;
import com.visual.conserapp.R;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {


    Button btn_signup, btn_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_signup = (Button) findViewById(R.id.btn_signup);

        Paper.init(this);

        btn_signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignIn.class);
                startActivity(intent);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);
        if (user != null && pwd != null) {
            if (!user.isEmpty() && !pwd.isEmpty()) {
                login(user, pwd);
            }
        }

    }

    private void login(String emailp, final String pwd) {

        final String email = encodeUserEmail(emailp);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference users = database.getReference("User");

        users.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(email).exists()) {

                    User user = dataSnapshot.child(email).getValue(User.class);
                    if (user.getPassword().equals(pwd)) {
                        Intent intent = new Intent(Login.this, Home.class);
                        Common.getInstance().currentUser = user;
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Password incorrecto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
