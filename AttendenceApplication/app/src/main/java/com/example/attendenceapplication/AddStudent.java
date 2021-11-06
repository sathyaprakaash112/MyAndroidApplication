package com.example.attendenceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddStudent extends AppCompatActivity {

    String mymessage;
    Button next,back,finish;
    FirebaseFirestore firestore;
    TextInputEditText studentName,regNo,phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String data = prefs.getString("message","no_id");


        System.out.println(mymessage);




        back = findViewById(R.id.backbutton);
        finish = findViewById(R.id.finishbutton);
        next = findViewById(R.id.nextbutton);
        studentName = findViewById(R.id.Sname);
        regNo = findViewById(R.id.regno);
        phoneNo = findViewById(R.id.phonenumber);

        SharedPreferences preffs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preffs.edit();
        editor.putString("registernumber", regNo.getText().toString()); //InputString: from the EditText
        editor.commit();

        String name = studentName.getText().toString();
        String reg = regNo.getText().toString();
        String phone = phoneNo.getText().toString();


        firestore = FirebaseFirestore.getInstance();
        //studentName.setText(message);


        next.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View view) {
                String noWhiteSpace = "[a-zA-Z][a-zA-Z ]*";
                String name = studentName.getText().toString();
                String reg = regNo.getText().toString();
                String phone = phoneNo.getText().toString();
                if (name.isEmpty()){
                    studentName.setError("Name required!");
                    studentName.requestFocus();
                    return;
                }
                else if (!name.matches(noWhiteSpace)) {
                    studentName.setError("Student name must contain 3 to 20 characters without any Special Characters");
                    studentName.requestFocus();
                    return;
                }
                if(reg.isEmpty()){
                    regNo.setError("Register number required");
                    regNo.requestFocus();
                    return;
                }
                else if(!reg.matches("^[0-9]*$")){
                    regNo.setError("Register numbers must contain only numbers");
                    regNo.requestFocus();
                    return;
                }
                if(phone.isEmpty()){
                    phoneNo.setError("Phone number is required");
                    phoneNo.requestFocus();
                    return;
                }
                else if(!phone.matches("[0-9]{10}")){
                    phoneNo.setError("Phone number must contain only 10 digits");
                    phoneNo.requestFocus();
                    return;
                }

                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                HashMap<String,String> hm = new HashMap<>();
                hm.put("studentName",studentName.getText().toString());
                hm.put("registernumber",regNo.getText().toString());
                hm.put("phoneNo",phoneNo.getText().toString());
                hm.put("present","true");
                hm.put("absent","false");
                DocumentReference documentReference = firestore.collection("users").document(userid).collection("classes").document(data).collection("Students").document(regNo.getText().toString());
                documentReference.set(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddStudent.this, "Student added successfully. Please add the next Student.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AddStudent.class);
                        intent.putExtra("mymessage", mymessage);
                        intent.putExtra("registernumber",reg);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddStudent.this, "Database error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String noWhiteSpace = "[a-zA-Z][a-zA-Z ]*";
                String name = studentName.getText().toString();
                String reg = regNo.getText().toString();
                String phone = phoneNo.getText().toString();
                if (name.isEmpty()){
                    studentName.setError("Name required!");
                    studentName.requestFocus();
                    return;
                }
                if (!name.matches(noWhiteSpace)) {
                    studentName.setError("Student name must contain 3 to 20 characters without any Special Characters");
                    studentName.requestFocus();
                    return;
                }
                if(reg.isEmpty()){
                    regNo.setError("Register number required");
                    regNo.requestFocus();
                    return;
                }
                if(!reg.matches("^[0-9]*$")){
                    regNo.setError("Register numbers must contain only numbers");
                    regNo.requestFocus();
                    return;
                }
                if(phone.isEmpty()){
                    phoneNo.setError("Phone number is required");
                    phoneNo.requestFocus();
                    return;
                }
                if(!phone.matches("[0-9]{10}")){
                    phoneNo.setError("Phone number must contain only 10 digits");
                    phoneNo.requestFocus();
                    return;
                }

                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                HashMap<String,String> hm = new HashMap<>();
                hm.put("studentName",studentName.getText().toString());
                hm.put("registernumber",regNo.getText().toString());
                hm.put("phoneNo",phoneNo.getText().toString());
                hm.put("present","true");
                hm.put("absent","false");
                DocumentReference documentReference = firestore.collection("users").document(userid).collection("classes").document(data).collection("Students").document(regNo.getText().toString());
                documentReference.set(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddStudent.this, "Student added  successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AttendancePage.class);
                        intent.putExtra("mymessage", mymessage);
                        intent.putExtra("registernumber",reg);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddStudent.this, "Database error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
            }
        });
    }

    public void validator() {
        String noWhiteSpace = "\\A\\w{3,20}\\z";
        String name = studentName.getText().toString();
        String reg = regNo.getText().toString();
        String phone = phoneNo.getText().toString();
        if (name.isEmpty()){
            studentName.setError("Name required!");
            studentName.requestFocus();
            return;
        }
        if (!name.matches(noWhiteSpace)) {
            studentName.setError("Student name must contain 3 to 20 characters without any Special Characters");
            studentName.requestFocus();
            return;
        }
        if(reg.isEmpty()){
            regNo.setError("Register number required");
            regNo.requestFocus();
            return;
        }
        if(!reg.matches("[\\p{Digit}]")){
            regNo.setError("Register numbers must contain only numbers");
            regNo.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            phoneNo.setError("Phone number is required");
            phoneNo.requestFocus();
            return;
        }
        if(!phone.matches("[0-9]{10}")){
            phoneNo.setError("Phone number must contain only 10 digits");
            phoneNo.requestFocus();
            return;
        }
    }
}