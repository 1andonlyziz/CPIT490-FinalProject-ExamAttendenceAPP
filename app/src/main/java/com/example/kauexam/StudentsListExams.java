package com.example.kauexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentsListExams extends AppCompatActivity implements RecyclerViewClickInterface{

    private ArrayList<ExamHistory> examList;
    private RecyclerView recyclerView;
    public static ArrayList<String> examInfo;


    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list_exams);

        recyclerView = findViewById(R.id.available_exams_recycler_review);
        examList = new ArrayList<>();

        //firebase database
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();


        ValueEventListener roomsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot i : dataSnapshot.getChildren()) {
                    for (DataSnapshot j : i.getChildren()) {
                        for (DataSnapshot k : j.getChildren()) {

                            String examDate = (String) k.child("examDate").getValue();
                            String location = (String) k.child("location").getValue();
                            String examTime = (String) k.child("examTime").getValue();
                            String courseCode = (String) k.child("courseCode").getValue();

                            examList.add(new ExamHistory(courseCode, location, examDate, examTime));
                            System.out.println(examDate);
                            System.out.println(location);
                            System.out.println(examTime);
                            System.out.println(courseCode);

                        }

                        //update the adapter
                        setAdapter();

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.addListenerForSingleValueEvent(roomsValueEventListener);

    }

    //method to update the adapter
    private void setAdapter() {

        ExamRecyclerAdapter adapter = new ExamRecyclerAdapter(examList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {

        examInfo = new ArrayList<String>();


        examInfo.add(""+examList.get(position).getCourseCode());
        examInfo.add(""+examList.get(position).getLocation());
        examInfo.add(""+examList.get(position).getExamDate());
        examInfo.add(""+examList.get(position).getExamTime());

        Intent intent = new Intent(this,camera.class);
        startActivity(intent);

    }

    @Override
    public void onLongItemClick(int position) {

    }


}

