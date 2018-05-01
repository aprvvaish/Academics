package com.portal.academics.studentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    String userName;
    TextView text_user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        Intent intent =new Intent();
        userName = intent.getStringExtra(MainActivity.USER_NAME);
        text_user = findViewById(R.id.textView);
        text_user.setText("Hi,"+userName);

    }
}
