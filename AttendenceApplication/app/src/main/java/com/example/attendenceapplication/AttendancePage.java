package com.example.attendenceapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AttendancePage extends AppCompatActivity implements QuantityListener{
    RecyclerView recyclerView;
    ArrayList<Student> studentArrayList;
    FirebaseFirestore fstore;
    Button addstudentbtn,proceedbtn;
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
    ArrayList<String> absenteesPhoneNumbers,absentees;
    HashMap<String,String> absenteesDetails;


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

        proceedbtn = findViewById(R.id.proceedbtn);
        absenteesPhoneNumbers = new ArrayList<>();

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


        absenteesDetails = new HashMap<>();
        studentArrayList = new ArrayList<Student>();
        myStudentAdapter = new MyStudentAdapter(AttendancePage.this, studentArrayList,this);

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


        proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(AttendancePage.this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                        //when permission is granted send message.
                    Set<Map.Entry<String,String>> me = absenteesDetails.entrySet();
                    for(Map.Entry s : me){
                        sendMessage(s.getKey().toString(),s.getValue().toString());
                    }
                }else{
                    ActivityCompat.requestPermissions(AttendancePage.this,new String[]{Manifest.permission.SEND_SMS},100);
                }
            }

        });
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

    private void sendMessage(String studentName,String phoneNo) {
        int n1 = absenteesPhoneNumbers.size(),n2 = studentArrayList.size();
        String message = "Your ward " + studentName + " is absent for the class.";
        if(!message.equals("") && !phoneNo.equals("")){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo,null,message,null,null);
            Toast.makeText(getApplicationContext(), "Parents notified successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Phone number not available. Task failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Set<Map.Entry<String,String>> me = absenteesDetails.entrySet();
            for(Map.Entry s : me){
                sendMessage(s.getKey().toString(),s.getValue().toString());
            }
        }else{
            Toast.makeText(getApplicationContext(), "Permission denied!", Toast.LENGTH_SHORT).show();
        }
    }

    private static String getMessage(String s) {
        String  message = "Your ward " + s + " is absent for the class.";
        return message;
    }


    private void changeListener() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fstore.collection("users").document(uid).collection("classes").document(data).collection("Students").orderBy("registernumber", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                        if (error != null) {


                            Log.e("Firestore error", error.getMessage());
                            return;
                        }


                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                studentArrayList.add(dc.getDocument().toObject(Student.class));
                            }
                            myStudentAdapter.notifyDataSetChanged();

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


    @Override
    public void onQuantityChange(ArrayList<Student> arrayList) {
        Toast.makeText( this, arrayList.toString(), Toast.LENGTH_SHORT).show();
        for(Student s :arrayList){
            System.out.println(s.phoneNo);
//            absentees.add(s.studentName);
//            absenteesPhoneNumbers.add(s.phoneNo);
            absenteesDetails.put(s.studentName,s.phoneNo);

        }
    }
}

