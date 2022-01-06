package com.example.kauexam;

import static com.example.kauexam.StudentsListExams.examInfo;
import static com.example.kauexam.camera.studentInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class AttendenceConfirmation extends AppCompatActivity {

    public static final String ANSI_GREEN = "\u001B[32m";
    FirebaseDatabase database;
    DatabaseReference ref;
    TextView course_code,exam_location,exam_date,exam_time,student_name,student_id;
    EditText section;
    Button confirmBtn;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_confirmation);

        Date date = new Date();

        course_code = findViewById(R.id.course_code);
        exam_date = findViewById(R.id.exam_date);
        exam_time = findViewById(R.id.exam_time);
        student_name = findViewById(R.id.student_name);
        student_id = findViewById(R.id.student_id);
        exam_location = findViewById(R.id.exam_location);
        section = findViewById(R.id.section);
        confirmBtn = findViewById(R.id.confirmBtn);

        dialog = new Dialog(AttendenceConfirmation.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_back));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        course_code.append(""+examInfo.get(0));
        exam_location.append(""+examInfo.get(1));
        exam_date.append(""+examInfo.get(2));
        exam_time.append(""+examInfo.get(3));

        student_id.append(""+studentInfo.get(0));
        student_name.append(""+studentInfo.get(1));


        Button btOk = dialog.findViewById(R.id.btOk);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(AttendenceConfirmation.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueEventListener roomsValueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot userDataSnapshot1 : userDataSnapshot.getChildren()) {
                                for (DataSnapshot userDataSnapshot2 : userDataSnapshot1.getChildren()) {
                                    if (userDataSnapshot2.getKey().equalsIgnoreCase(examInfo.get(0))) { // course Code
                                        System.out.println(userDataSnapshot2.getKey());
                                        userDataSnapshot2.getRef().child("students").child("" + date.getTime()).child("fullName").setValue(studentInfo.get(1));
                                        userDataSnapshot2.getRef().child("students").child("" + date.getTime()).child("id").setValue(studentInfo.get(0));
                                        userDataSnapshot2.getRef().child("students").child("" + date.getTime()).child("section").setValue(section.getText().toString());
                                    }
                                }

                            }

                        }

                        dialog.show();

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
                ref.addListenerForSingleValueEvent(roomsValueEventListener);
            }
        });
            }
}