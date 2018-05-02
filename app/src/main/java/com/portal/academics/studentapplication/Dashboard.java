package com.portal.academics.studentapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;
import java.util.Set;

public class Dashboard extends AppCompatActivity {

    String userName;
    TextView text_user;
    Button marks,attendance,links,ttable,assignment,quiz;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
//        Intent intent =getIntent();
//        userName = intent.getStringExtra(MainActivity.USER_NAME);
        SharedPreferences settings = getSharedPreferences("MYPREFS",MODE_PRIVATE);
        userName=settings.getString("username",null);
        text_user = findViewById(R.id.textView);
        text_user.setText("Hi,"+ userName);

        marks = findViewById(R.id.marks_button);
        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this,ViewMarks.class);
                startActivity(intent);
            }
        });

        attendance = findViewById(R.id.attendance_button);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this,Attendance.class);
                startActivity(intent);
            }
        });

        links = findViewById(R.id.tutorial_button);
        links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this,TutorialLinks.class);
                startActivity(intent);
            }
        });

        quiz = findViewById(R.id.quiz_button);
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this,QuizLinks.class);
                startActivity(intent);
            }
        });

        assignment = findViewById(R.id.assignment_button);
        assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this,Assignment.class);
                startActivity(intent);
            }
        });

        ttable = findViewById(R.id.timetable_button);
        ttable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this,TimeTable.class);
                startActivity(intent);
            }
        });


    }


}
