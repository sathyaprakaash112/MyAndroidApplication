package com.example.attendenceapplication;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class MyStudentAdapter extends RecyclerView.Adapter<MyStudentAdapter.MyViewHolder> implements AbsenteesListener {


    Context context;
    ArrayList<Student> studentArrayList;
//    String present,absent;
    ArrayList<Student> positions = new ArrayList<>();
    AbsenteesListener absenteesListener;
    public MyStudentAdapter(Context context, ArrayList<Student> studentArrayList, AbsenteesListener absenteesListener) {
        this.context = context;
        this.studentArrayList = studentArrayList;
        this.absenteesListener = absenteesListener;
    }

    @NonNull
    @Override
    public MyStudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.studentitem,parent,false);

        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyStudentAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Student student = studentArrayList.get(position);
        holder.presentbox.setOnCheckedChangeListener(null);
        holder.absentbox.setOnCheckedChangeListener(null);

        holder.registernumber.setText(student.registernumber);

        holder.presentbox.setChecked(true);

        //getPresentAbsent();

        holder.presentbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final boolean isChecked = holder.presentbox.isChecked();
                if (isChecked) {
                    holder.absentbox.setChecked(false);
                }else{
                    holder.absentbox.setChecked(true);
                }
            }
        });

        holder.absentbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.absentbox.isChecked()){
                    holder.presentbox.setChecked(false);
                }else{
                    holder.presentbox.setChecked(true);
                }
            }
        });


        holder.absentbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(holder.absentbox.isChecked()){
                    positions.add(studentArrayList.get(position));
                }else{
                    positions.remove(studentArrayList.get(position));
                }
                absenteesListener.onQuantityChange(positions);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

    @Override
    public void onQuantityChange(ArrayList<Student> arrayList) {

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
//    private void getPresentAbsent(){
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//        String registernumber = prefs.getString("registernumber", "no_id");
//        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        SharedPreferences preffs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//        String data = preffs.getString("message2", "no_id");
//
//        fstore.collection("users").document(uid).collection("classes").document("EEE2019-2023Third YearⅠEEE").collection("Students").document("710719105032").addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                present = value.getString("present");
//                absent = value.getString("absent");
//            }
//        });
//    }
}
