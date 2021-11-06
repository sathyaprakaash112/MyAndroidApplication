package com.example.attendenceapplication;

import static com.example.attendenceapplication.R.*;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Classes> classesArrayList;

    MyAdapter myAdapter;
    Button logout;
    FirebaseFirestore fstore;
    Integer i;
    ProgressDialog progressDialog;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        textView = findViewById(id.staffnameheader);

        toolbar = findViewById(R.id.toolbar);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        recyclerView = findViewById(id.RView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fstore = FirebaseFirestore.getInstance();


        classesArrayList = new ArrayList<Classes>();
        myAdapter = new MyAdapter(MainActivity.this, classesArrayList);

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        navigationView.setCheckedItem(R.id.nav_home);

        //defining id to variables
        logout = findViewById(id.logoutbtn);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });

    }

    private void EventChangeListener() {
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fstore.collection("users").document(userid).collection("classes").orderBy("departmentName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                classesArrayList.add(dc.getDocument().toObject(Classes.class));

                            }

                            myAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                        }
                        if (classesArrayList.size() == 0) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }


                    }
                });
    }


    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case id.createclass:
                Intent intent = new Intent(MainActivity.this, createClass.class);
                startActivity(intent);
                break;
            case id.forgotpassword:
                Intent intent1 = new Intent(MainActivity.this, forgotpassword.class);
                startActivity(intent1);
                break;

            case id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(getApplicationContext(), login.class);
                startActivity(intent2);
                break;
            case id.addstud:
                Toast.makeText(getApplicationContext(), "Please select the class and add students", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent3);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}