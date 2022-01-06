package com.example.kauexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CreateExamActivity extends AppCompatActivity{

    final Calendar myCalendar = Calendar.getInstance();
    EditText eDate, eTime,eCourseCode,elocation;

    FirebaseDatabase database ;
    DatabaseReference ref ;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam);
        database = FirebaseDatabase.getInstance();

        ref = database.getReference(LoginActivity.userId+"/exams");
        //ref = database.getReference();

        eDate = findViewById(R.id.etDate);
        eTime= findViewById(R.id.etTime);
        eCourseCode = findViewById(R.id.etCourse);
        elocation = findViewById(R.id.etRoom);

        dialog = new Dialog(CreateExamActivity.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_back));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button btOk = dialog.findViewById(R.id.btOk);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.dismiss();
            }
        });

        findViewById(R.id.btExamCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String courseCode = eCourseCode.getText().toString();
                String time = eTime.getText().toString();
                String date = eDate.getText().toString();
                String location = elocation.getText().toString();

                //if Clause , { Missing Variables }

                // if one of the parameters is missing tell the user to enter all the data
                if(courseCode.isEmpty() || time.isEmpty() || date.isEmpty() || location.isEmpty()){

                    Toast.makeText(CreateExamActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }else {

                    // inserting logic should go here

                    try {

                        ref.child(courseCode).child("courseCode").setValue(courseCode);
                        ref.child(courseCode).child("examDate").setValue(date);
                        ref.child(courseCode).child("examTime").setValue(time);
                        ref.child(courseCode).child("location").setValue(location);

                        //reset the parameters

                        eCourseCode.setText("");
                        eTime.setText("");
                        eDate.setText("");
                        elocation.setText("");

                        Toast.makeText(CreateExamActivity.this, "Exam was created Successfully", Toast.LENGTH_SHORT).show();

                        dialog.show();

                    } catch (Exception e) {
                        Toast.makeText(CreateExamActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        eTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateExamActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        eTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(CreateExamActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        eDate.setText(sdf.format(myCalendar.getTime()));
    }

    // this method is used to fetch student information from firebase database


}