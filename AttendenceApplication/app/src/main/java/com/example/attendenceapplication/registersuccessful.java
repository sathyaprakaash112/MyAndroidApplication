package com.example.attendenceapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class registersuccessful extends AppCompatActivity {

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registersuccessful);

        back=findViewById(R.id.backbtn);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent1 = new Intent(getApplicationContext(), classIntro.class);
                    startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(registersuccessful.this).toBundle());
                }
        });


    }
}