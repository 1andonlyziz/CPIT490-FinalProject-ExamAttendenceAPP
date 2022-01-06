package com.example.kauexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity  implements RecyclerViewClickInterface {

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private ArrayList<StudentClass> studentlist;

    private ArrayList<StudentClass> studentList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_list);
        recyclerView = findViewById(R.id.studentRecyclerView);
        studentList = new ArrayList<>();

        //firebase database
        database = FirebaseDatabase.getInstance();
        ref = database.getReference(LoginActivity.userId+"/exams");
        ValueEventListener roomsValueEventListener = new ValueEventListener() {
            String id;
            String fullName;
            String section;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for ( DataSnapshot userDataSnapshot : dataSnapshot.getChildren() ) {
                    for (DataSnapshot userDataSnapshot1 : userDataSnapshot.child("students").getChildren()) {

                        id = userDataSnapshot1.child("id").getValue().toString();
                        fullName = userDataSnapshot1.child("fullName").getValue().toString();
                        section = userDataSnapshot1.child("section").getValue().toString();
                        studentList.add(new StudentClass(id, fullName, section));
                        System.out.println(studentList.toString());

                    }
                }
                setAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.addListenerForSingleValueEvent(roomsValueEventListener);
    }

    private void setAdapter() {

        StudentRecyclerAdapter adapter = new StudentRecyclerAdapter(studentList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, (CharSequence) studentList.get(position), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onLongItemClick(int position) {

    }
}