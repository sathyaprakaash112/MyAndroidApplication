package com.example.attendenceapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class classIntro extends AppCompatActivity {

    Button createclass,existingclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_intro);

        createclass = findViewById(R.id.create);
        existingclass = findViewById(R.id.existing);

        createclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), createClass.class);
                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(classIntro.this).toBundle());
            }
        });
        existingclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(classIntro.this).toBundle());
            }
        });

    }
}