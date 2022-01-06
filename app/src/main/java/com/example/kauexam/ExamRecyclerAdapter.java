package com.example.kauexam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ExamRecyclerAdapter extends RecyclerView.Adapter<ExamRecyclerAdapter.MyViewHolder> {

    private ArrayList<ExamHistory> examList;
    private RecyclerViewClickInterface recyclerViewClickInterface;


    public ExamRecyclerAdapter(ArrayList<ExamHistory> examList,RecyclerViewClickInterface recyclerViewClickInterface){

        this.examList = examList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    // inner class
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView courseCode;
        private TextView examDate;
        private TextView examTime;
        private TextView location;


        public MyViewHolder(final View view){

            super(view);

            courseCode = view.findViewById(R.id.exam_history_course_code);
            location = view.findViewById(R.id.exam_history_exam_location);
            examDate =  view.findViewById(R.id.exam_history_date);
            examTime = view.findViewById(R.id.exam_history_time);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }

    }

    @NonNull
    @Override
    public ExamRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_history_items,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamRecyclerAdapter.MyViewHolder holder, int position) {

        String courseCode = examList.get(position).getCourseCode();
        String examLocation = examList.get(position).getLocation();
        String examDate = examList.get(position).getExamDate();
        String examTime = examList.get(position).getExamTime();

        holder.courseCode.append(courseCode);
        holder.location.append(""+examLocation);
        holder.examDate.append(examDate);
        holder.examTime.append(examTime);


    }

    @Override
    public int getItemCount() {
        return examList.size();
    }


}
