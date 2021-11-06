package com.example.attendenceapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class register extends AppCompatActivity {

    Button registerbtn;
    EditText registerStaffId,registerEmailId,registerCollegeName,createpassword,confirmcreatepassword;
    TextView loginpass;
    String userid;
    //ProgressBar progressBar;
    ArrayAdapter adapter;
    AutoCompleteTextView collegeName;
    FirebaseAuth firebaseAuth;
    HashMap<String,String> hm = new HashMap<>();

    FirebaseFirestore fstore;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //defining id to variables
        registerStaffId = findViewById(R.id.regStaffID);
        registerEmailId = findViewById(R.id.regEmailId);
        registerCollegeName = findViewById(R.id.autocomplteText);
        createpassword = findViewById(R.id.createpassword);
        confirmcreatepassword = findViewById(R.id.confirmcreatepassword);
        loginpass = findViewById(R.id.regloginbtn);
        registerbtn = findViewById(R.id.registerbutton);
       // progressBar = findViewById(R.id.progressBar);
        //dropdown part
        collegeName = findViewById(R.id.autocomplteText);
        adapter = ArrayAdapter.createFromResource(this,R.array.colleges, android.R.layout.simple_selectable_list_item);
        collegeName.setAdapter(adapter);
        collegeName.setEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();

        fstore = FirebaseFirestore.getInstance();
        //register btn settings

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String staffid = registerStaffId.getText().toString();
                String emailid = registerEmailId.getText().toString();
                String collegename = registerCollegeName.getText().toString();
                String cpassword = createpassword.getText().toString();
                String cpassword2 = confirmcreatepassword.getText().toString();
                String emailPattern = "[A-a-zZ0-9._-]+@[a-z]+\\.+[a-z]+";
                String emailPattern1 = "/^[\\W.+\\-]+@drngpit+\\.ac+\\.in$/";
                String noWhiteSpace = "\\A\\w{4,20}\\z";
                String passwordPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";


//                String passwordVal = "^"+
//                        "(?=.*[0-9])" +         //at least 1 digit
//                        "(?=.*[a-z])" +         //at least 1 lower case letter
//                        "(?=.*[A-Z])" +         //at least 1 upper case letter
//                        "(?=.*[a-zA-Z])" +      //any letter
//                        "(?=.*[@#$%^&+=])" +    //at least 1 special character
//                        "(?=\\S+$)" +           //no white spaces
//                        ".{4,}" +               //at least 4 characters
//                        "$";
                //staff id validation
                if (staffid.isEmpty()){
                    registerStaffId.setError("Enter name and id number");
                    registerStaffId.requestFocus();
                    return;
                }else if (!staffid.matches(noWhiteSpace)) {
                    registerStaffId.setError("Staff ID must contain 4 to 20 characters without any Special Characters");
                    registerStaffId.requestFocus();
                    return;
                }
                // onClick of button perform this simplest code.
                //email validation
                if ((Patterns.EMAIL_ADDRESS.matcher(emailid).matches()||(emailid.matches(emailPattern1)))) {
                    registerEmailId.setError(null);

                    //Toast.makeText(getApplicationContext(),"valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    registerEmailId.setError("Invalid email id");
                    //Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                //college validation
                if (collegename.isEmpty()){
                    registerCollegeName.setError("Enter college name ");
                    return;
                }

                if(cpassword.length()==0){
                    Toast.makeText(getApplicationContext(),"Password not entered", Toast.LENGTH_SHORT).show();
                    createpassword.requestFocus();
                    return;
                }
                if(cpassword2.length()==0){
                    Toast.makeText(getApplicationContext(),"Please confirm password", Toast.LENGTH_SHORT).show();
                    confirmcreatepassword.requestFocus();
                    return;
                }
                if(!cpassword.matches(passwordPattern)){
                    createpassword.setError("Password must have minimum 8 characters and contain at least 1 UPPERCASE, 1 lower case, 1 number, 1 special character.");
                    createpassword.requestFocus();
                    return;
                }
                if(!cpassword.equals(confirmcreatepassword.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Password fields is not matching.Enter correct password.", Toast.LENGTH_SHORT).show();
                    confirmcreatepassword.requestFocus();
                    return;

                }
                if(cpassword.length()<8){
                    createpassword.setError("Weak Password");
                    createpassword.requestFocus();
                    return;
                }



                //pushing the data into the firebase database
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");



                UserHelperClass helperClass = new UserHelperClass(staffid,emailid,collegename,cpassword);

                reference.child(staffid).setValue(helperClass);

                reference.child("staffid").setValue(staffid);





                //passing parameters email,password,username
                firebaseAuth.createUserWithEmailAndPassword(emailid,cpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(getApplicationContext(),registersuccessful.class);
                        userid = firebaseAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fstore.collection("users").document(userid);
                        Map<String,Object> user = new HashMap<>();
                        user.put("fName",staffid);
                        user.put("email",emailid);
                        user.put("collegename",collegename);
                        user.put("password",cpassword);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG,"onSuccess:user created successfully for "+  userid);
                            }
                        });
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(register.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });



        loginpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(getApplicationContext(), login.class);
                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(register.this).toBundle());
            }
        });

    }


}


