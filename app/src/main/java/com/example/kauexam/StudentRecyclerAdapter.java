package com.example.kauexam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.MyViewHolder> {

    private ArrayList<StudentClass> userlist;

    public StudentRecyclerAdapter(ArrayList<StudentClass> userlist){
        this.userlist = userlist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView studentName;
        private TextView sectionName;
        private TextView studentId;

        public MyViewHolder(final View view){
            super(view);

            studentName = view.findViewById(R.id.student_name);
            sectionName = view.findViewById(R.id.section);
            studentId =  view.findViewById(R.id.student_id);
        }

    }

    @NonNull
    @Override
    public StudentRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_items,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentRecyclerAdapter.MyViewHolder holder, int position) {

        String studentFullName = userlist.get(position).getFullName();
        String studentSection = userlist.get(position).getSection();
        String studentID = userlist.get(position).getId();

        holder.studentName.append(studentFullName);
        holder.sectionName.append(studentSection);
        holder.studentId.append(""+studentID);


    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }
}
