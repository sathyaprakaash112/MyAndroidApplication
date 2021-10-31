package com.example.attendenceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {

    Button loginbtn;
    TextView register, forgotpassword;
    TextInputEditText username, password;
    //progressbar
    //private int currentProgress = 0;
   // private ProgressBar progressBar;
    String First;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        First = preferences.getString("FirstTimeInstall","");




        //find view declerations
        loginbtn=findViewById(R.id.loginbtn);
        register=findViewById(R.id.registerbtn);
        forgotpassword=findViewById(R.id.forgot);
        //progressBar = findViewById(R.id.progressBar);
        username = findViewById(R.id.staffname);
        password = findViewById(R.id.staffpassword);
        firebaseAuth = FirebaseAuth.getInstance();

        //progress bar

//        currentProgress = (10 + currentProgress);
//        progressBar.setProgress(currentProgress);
//        progressBar.setMax(100);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extract //validate
                String username1 = username.getText().toString();
                String password1 = password.getText().toString();
                if (username1.isEmpty()) {
                    username.setError("Email id missing");
                    return;
                }
                if (password1.isEmpty()) {
                    password.setError("Password missing");
                    return;
                }




                //login
                firebaseAuth.signInWithEmailAndPassword(username1, password1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
//                        progressBar.setVisibility(View.VISIBLE);
                        //login success action
                        Toast.makeText(login.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(getApplicationContext(), classIntro.class);
                        startActivity(intent2,ActivityOptions.makeSceneTransitionAnimation(login.this).toBundle());
//                        progressBar.setVisibility(View.GONE);
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);
                Intent intent1 = new Intent(getApplicationContext(), register.class);
                startActivity(intent1,ActivityOptions.makeSceneTransitionAnimation(login.this).toBundle());
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
                Intent intent1 = new Intent(getApplicationContext(), forgotpassword.class);
                startActivity(intent1,ActivityOptions.makeSceneTransitionAnimation(login.this).toBundle());
            }
        });

        if(First.equals("Yes")){
            //Toast.makeText(login.this, "Login using your email and password", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,login.class);
               // startActivity(intent);
        }else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall","Yes");
            editor.apply();
        }





    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null && First.equals("Yes")) {
//            progressBar.setVisibility(View.VISIBLE);

            Intent intent1 = new Intent(getApplicationContext(), classIntro.class);
            startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(login.this).toBundle());
            finish();
        }
    }

}