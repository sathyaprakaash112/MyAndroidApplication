package com.example.attendenceapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class createClass extends AppCompatActivity {


    EditText ed1, ed2, ed3, ed4;
    AutoCompleteTextView autodeptname, autoBatch, autoCurrentYear, autoCurrrentSem;
    ArrayList<Classes> array;
    ArrayList<String> stringArrayList;


    FirebaseFirestore fstore;
    public Integer count = 1;
    ArrayAdapter adapter1, adapter2, adapter3, adapter4;
    Button next;
    public static String staffid;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);


//        EventChangeListener2();
        array = new ArrayList<Classes>();

        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //fetching data from firestore
        fstore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = fstore.collection("users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                String staffid1 = snapshot.getString("fName");
                staffid = staffid1;


            }
        });

        //defining id
        next = findViewById(R.id.createclsNextbtn);


        //listView = findViewById(R.id.listView);
        ed1 = findViewById(R.id.autodeptname);
        ed2 = findViewById(R.id.autoBatch);
        ed3 = findViewById(R.id.autoCurrentYear);
        ed4 = findViewById(R.id.autoCurrrentSem);

        autodeptname = findViewById(R.id.autodeptname);
        autoBatch = findViewById(R.id.autoBatch);
        autoCurrentYear = findViewById(R.id.autoCurrentYear);
        autoCurrrentSem = findViewById(R.id.autoCurrrentSem);

        //dropdown for department name
        adapter1 = ArrayAdapter.createFromResource(this, R.array.departmentnames, android.R.layout.simple_list_item_1);
        autodeptname.setAdapter(adapter1);
        autodeptname.setFocusable(false);

        //dropdown for batch
        adapter2 = ArrayAdapter.createFromResource(this, R.array.batch, android.R.layout.simple_selectable_list_item);
        autoBatch.setAdapter(adapter2);
        autoBatch.setFocusable(false);
        //dropdown for current year

        adapter3 = ArrayAdapter.createFromResource(this, R.array.currentYear, android.R.layout.simple_list_item_1);
        autoCurrentYear.setAdapter(adapter3);
        autoCurrentYear.setFocusable(false);

        //dropdown for current semester
        adapter4 = ArrayAdapter.createFromResource(this, R.array.currentSemester, android.R.layout.simple_list_item_1);
        autoCurrrentSem.setAdapter(adapter4);
        autoCurrrentSem.setFocusable(false);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //initializing variable
                String departmentName = autodeptname.getText().toString();
                String batch = autoBatch.getText().toString();
                String currentYear = autoCurrentYear.getText().toString();
                String currentSemester = autoCurrrentSem.getText().toString();


                if (departmentName.isEmpty()) {
                    autodeptname.setError("Select Department");
                    return;
                }
                if (batch.isEmpty()) {
                    autoBatch.setError("Select Batch");
                    return;
                }
                if (currentYear.isEmpty()) {
                    autoCurrentYear.setError("Select Current Year");
                    return;
                }
                if (currentSemester.isEmpty()) {
                    autoCurrrentSem.setError("Select Current semester");
                    return;
                }
                //update

                firebaseAuth = FirebaseAuth.getInstance();
                HashMap<String, String> hm1 = new HashMap<>();
                hm1.put("departmentName", departmentName);
                hm1.put("Batch", batch);
                hm1.put("currentYear", currentYear);
                hm1.put("currentSemester", currentSemester);

                //creating a document name to store student details in database
                String documentname = departmentName +batch + currentYear + currentSemester + departmentName;


                DocumentReference documentReference = fstore.collection("users").document(userid).collection("classes").document(documentname);

                documentReference.set(hm1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess:user created successfully for " + userid);
                        count++;
                    }
                });
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(createClass.this).toBundle());
            }
        });

    }

    private void EventChangeListener2() {
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fstore.collection("users").document(userid).collection("classes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){

                            Log.e("Firestore error",error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                //classesArrayList.add(dc.getDocument().toObject(Classes.class));
                                stringArrayList.add(dc.getDocument().toString());
                            }


                        }
                    }
                });

    }
}