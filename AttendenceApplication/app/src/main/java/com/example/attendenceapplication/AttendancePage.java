package com.example.attendenceapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AttendancePage extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Student> studentArrayList;
    FirebaseFirestore fstore;
    Button addstudentbtn;
    MyStudentAdapter myStudentAdapter;
    String mymessage;
    String registernumber;
    String data;
    ProgressDialog progressDialog;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    private CheckBox absentcheckbox1, presentcheckbox1;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendancepage);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        data = prefs.getString("message", "no_id");

        System.out.println(registernumber);
        System.out.println(data);


        drawerLayout = findViewById(R.id.drawer_layout2);
        navigationView = findViewById(R.id.nav_view1);
        toolbar = findViewById(R.id.toolbar21);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        navigationView.setCheckedItem(R.id.nav_home);

        addstudentbtn = findViewById(R.id.addstudent);
        recyclerView = findViewById(R.id.RecView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Please wait...");
//        progressDialog.show();

        fstore = FirebaseFirestore.getInstance();


        studentArrayList = new ArrayList<Student>();
        myStudentAdapter = new MyStudentAdapter(AttendancePage.this, studentArrayList);

        recyclerView.setAdapter(myStudentAdapter);
        changeListener();

        addstudentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddStudent.class));
            }
        });

        presentcheckbox1 = findViewById(R.id.presentcheckBox);
        absentcheckbox1 = findViewById(R.id.absentcheckbox);


//        presentcheckbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    // do something
//                    absentcheckbox1.setChecked(false);
//                }else {
//
//                }
//            }
//        });


    }


    private void changeListener() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fstore.collection("users").document(uid).collection("classes").document(data).collection("Students").orderBy("registernumber", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                        if (error != null) {

//                            if (progressDialog.isShowing()) {
//                                progressDialog.dismiss();
//                            }

                            Log.e("Firestore error", error.getMessage());
                            return;
                        }


                        for (DocumentChange dc : value.getDocumentChanges()) {



//                            if (dc.getType() == null) {
//                                if (progressDialog.isShowing()) {
//                                    progressDialog.dismiss();
//                                    return;
//                                }

                            //}
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                studentArrayList.add(dc.getDocument().toObject(Student.class));
                            }
                            myStudentAdapter.notifyDataSetChanged();
//                            if (progressDialog.isShowing()) {
//                                progressDialog.dismiss();
//                            }
//                            else if (studentArrayList.size() == 0) {
//                                if (progressDialog.isShowing()) {
//                                    progressDialog.dismiss();
//                                    return;
//                                }
                            }

                        }
                    //}
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
            case R.id.createclass:
                Intent intent = new Intent(AttendancePage.this, createClass.class);
                startActivity(intent);
                break;
            case R.id.forgotpassword:
                Intent intent1 = new Intent(AttendancePage.this, forgotpassword.class);
                startActivity(intent1);
                break;

            case R.id.logout:
                menu.findItem(R.id.logout);
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(getApplicationContext(), login.class);
                startActivity(intent2);
                break;
            case R.id.addstud:
                Intent intent3 = new Intent(AttendancePage.this, AddStudent.class);
                startActivity(intent3);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}

