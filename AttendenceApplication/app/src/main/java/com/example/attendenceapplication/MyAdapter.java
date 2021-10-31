package com.example.attendenceapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Classes> classesArrayList;

    public MyAdapter(Context context, ArrayList<Classes> classesArrayList) {
        this.context = context;
        this.classesArrayList = classesArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        Classes classes = classesArrayList.get(position);
        holder.departmentName.setText(classes.DepartmentName);
        holder.Batch.setText(classes.Batch);
        holder.currentYear.setText(classes.currentYear);
        holder.currentSemester.setText(classes.currentSemester);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String documentname = holder.departmentName.getText().toString()+holder.Batch.getText().toString()+ holder.currentYear.getText().toString()+holder.currentSemester.getText().toString()+holder.departmentName.getText().toString();
                Intent intent = new Intent(view.getContext(), AttendancePage.class);
               // intent.putExtra("message", documentname);
                context.startActivity(intent);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("message", documentname); //InputString: from the EditText
                editor.commit();
                SharedPreferences preff = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                SharedPreferences.Editor editor1 = prefs.edit();
                editor.putString("message2", documentname); //InputString: from the EditText
                editor.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return classesArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView departmentName,currentSemester,currentYear,Batch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            departmentName = itemView.findViewById(R.id.department);
            currentSemester = itemView.findViewById(R.id.sem);
            currentYear = itemView.findViewById(R.id.year);
            Batch = itemView.findViewById(R.id.batch);
        }
    }



}
