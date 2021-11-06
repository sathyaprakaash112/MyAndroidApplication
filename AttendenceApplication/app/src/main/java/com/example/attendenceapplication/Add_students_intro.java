package com.example.attendenceapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class Add_students_intro extends AppCompatActivity {

    Button createsheet,importsheet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students_intro);

        createsheet = findViewById(R.id.createsheetbtn);
        importsheet = findViewById(R.id.importsheetbtn);

        importsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //File file = new File(getApplicationContext().getExternalFilesDir(null),exc);


            }
        });

    }
}