package com.example.attendenceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {

    Button resetbtn;
    EditText resetmailid;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        //defining id to variables
        resetbtn = findViewById(R.id.resetbtn);
        resetmailid = findViewById(R.id.resetpassed);
        fAuth = FirebaseAuth.getInstance();
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String reset = resetmailid.getText().toString();
                fAuth.sendPasswordResetEmail(reset).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(forgotpassword.this, "Password reset link sent to your mail", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(getApplicationContext(),login.class);
                        startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(forgotpassword.this).toBundle());
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast toast=Toast.makeText(getApplicationContext(),"Unregistered Email ID",Toast.LENGTH_LONG);
                        View view=toast.getView();
                        view.setBackgroundResource(R.color.red);
                        toast.show();
                    }
                });
            }
        });


    }
}