package com.example.kauexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

      EditText mLoginEmail;
      EditText mPassword;
      TextView missingPassword;
      Button mLoginBtn;
      // fetch userid from here
      FirebaseAuth auth;
      static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginEmail= findViewById(R.id.etEmail);
        mPassword = findViewById(R.id.etPass);
        missingPassword = findViewById(R.id.txt_forget);
        mLoginBtn = findViewById(R.id.btLogin);



        auth = FirebaseAuth.getInstance();


        // login button
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

    }
    private void loginUser(){

        String email = mLoginEmail.getText().toString();
        String password = mPassword.getText().toString();

        if(TextUtils.isEmpty(email)){

            mLoginEmail.setError("Email cannot be empty");
            mLoginEmail.requestFocus();

        }else if(TextUtils.isEmpty(password)){

            mLoginEmail.setError("Password cannot be empty");
            mPassword.requestFocus();

        }else {
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(LoginActivity.this, "User Logged In successfully", Toast.LENGTH_SHORT).show();
                        userId = auth.getUid();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));


                    }else{

                        Toast.makeText(LoginActivity.this,"Log in Error: " + task.getException(),Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }




    }

}