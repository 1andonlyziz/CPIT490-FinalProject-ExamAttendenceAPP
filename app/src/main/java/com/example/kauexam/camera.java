package com.example.kauexam;

import static com.example.kauexam.AttendenceConfirmation.ANSI_GREEN;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;


import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class camera extends AppCompatActivity {


    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button CaptureImageBtn, detectTextBtn;
    private ImageView imageView;
    private TextView textView;
    Bitmap imageBitmap;
    private String currentPhotoPath;
    public static ArrayList<String> studentInfo;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        studentInfo = new ArrayList<String>();

        CaptureImageBtn = findViewById(R.id.capture_image);
        detectTextBtn = findViewById(R.id.detect_text_image_btn);
        imageView = findViewById(R.id.image_view);
        textView = findViewById(R.id.text_display);



            CaptureImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fileName = "photo";
                    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    File imageFile = null;
                    try {
                        imageFile = File.createTempFile(fileName, ".jpg", storageDir);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    currentPhotoPath = imageFile.getAbsolutePath();
                    Uri imageUri = FileProvider.getUriForFile(camera.this, "com.example.kauexam.fileprovider", imageFile);

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                    try {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    } catch (ActivityNotFoundException e) {

                    }
                    textView.setText("");
                }
            });

            detectTextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imageView.getDrawable() != null) {
                        detectTextFromImage();
                    }else {
                        Toast.makeText(camera.this,"Make Sure You took a picture of your id card",Toast.LENGTH_LONG);
                    }
                }
            });




        }

    public void move(){
        Intent intent = new Intent(camera.this,AttendenceConfirmation.class);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            imageView.setImageBitmap(imageBitmap);
        }
    }

    private void detectTextFromImage() {

        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionCloudTextRecognizerOptions options =
                new FirebaseVisionCloudTextRecognizerOptions.Builder()
                        .setLanguageHints(Arrays.asList("en", "ar", "الرقم الجامعي", "الأســــم"))
                        .build();
        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance()
                .getCloudTextRecognizer(options);

        textRecognizer.processImage(firebaseVisionImage)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText result) {
                        studentInfo =  displayTextFromImage(result);
                        move();
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(camera.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    private ArrayList<String> displayTextFromImage(FirebaseVisionText result) {

        ArrayList<String> student = new ArrayList<String>();
        int counter = 0;
        String resultText = result.getText();
        for (FirebaseVisionText.TextBlock block : result.getTextBlocks()) {
            for (FirebaseVisionText.Line line : block.getLines()) {
                if (counter < 2) {
                    String lineText = line.getText();
                    student.add(lineText);
                    counter++;
                } else {
                    break;
                }
            }
            }
        return student;
        }
    }

