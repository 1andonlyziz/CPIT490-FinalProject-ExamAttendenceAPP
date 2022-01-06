package com.example.kauexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    private ArrayList<ExamHistory> examList;
    private RecyclerView recyclerView;

    private FirebaseDatabase database;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.examRecyclerView);
        examList = new ArrayList<>();

        //firebase database
        database = FirebaseDatabase.getInstance();
        ref = database.getReference(LoginActivity.userId+"/exams");


        ValueEventListener roomsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot userDataSnapshot : dataSnapshot.getChildren() ) {

                    String examDate = userDataSnapshot.child("examDate").getValue().toString();
                    String location = userDataSnapshot.child("location").getValue().toString();
                    String examTime = userDataSnapshot.child("examTime").getValue().toString();
                    String courseCode = userDataSnapshot.child("courseCode").getValue().toString();

                    examList.add(new ExamHistory(courseCode,location,examDate,examTime));

                }

                //update the adapter
                setAdapter();

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

    }

    @Override
    public void onLongItemClick(int position) {

    }
}