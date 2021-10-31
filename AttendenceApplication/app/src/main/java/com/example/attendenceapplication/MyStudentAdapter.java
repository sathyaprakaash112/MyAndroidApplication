package com.example.attendenceapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class MyStudentAdapter extends RecyclerView.Adapter<MyStudentAdapter.MyViewHolder> {


    Context context;
    ArrayList<Student> studentArrayList;
    String present,absent;

    public MyStudentAdapter(Context context, ArrayList<Student> studentArrayList) {
        this.context = context;
        this.studentArrayList = studentArrayList;
    }

    @NonNull
    @Override
    public MyStudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.studentitem,parent,false);


        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyStudentAdapter.MyViewHolder holder, int position) {
        Student student = studentArrayList.get(position);
        holder.presentbox.setOnCheckedChangeListener(null);
        holder.absentbox.setOnCheckedChangeListener(null);

        holder.registernumber.setText(student.registernumber);


        getPresentAbsent();

        if(true){
            holder.presentbox.setChecked(true);
        }
        else if(false){
            holder.absentbox.setChecked(false);
        }
        holder.presentbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final boolean isChecked = holder.presentbox.isChecked();
                if(isChecked == true){
                    holder.absentbox.setChecked(false);
                }

            }
        });

        holder.absentbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final boolean isChecked = holder.absentbox.isChecked();
                if(isChecked == true){
                    holder.presentbox.setChecked(false);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView registernumber;
        CheckBox presentbox,absentbox;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            registernumber = itemView.findViewById(R.id.registernumber);
            presentbox = itemView.findViewById(R.id.presentcheckBox);
            absentbox = itemView.findViewById(R.id.absentcheckbox);
        }
    }
    private void getPresentAbsent(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String registernumber = prefs.getString("registernumber", "no_id");
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        SharedPreferences preffs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String data = preffs.getString("message2", "no_id");
        fstore.collection("users").document(uid).collection("classes").document("EEE2019-2023Third YearⅠEEE").collection("Students").document("710719105032").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                present = value.getString("present");
                absent = value.getString("absent");
            }
        });
    }
}
