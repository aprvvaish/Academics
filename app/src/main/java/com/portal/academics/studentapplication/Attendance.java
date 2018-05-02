package com.portal.academics.studentapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Attendance extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    String[] semesters = {"I SEM","II SEM","III SEM","IV SEM","V SEM","VI SEM"};
    String[] months1 ={"Jul","Aug","Sep","Oct","Nov","Dec"};
    String[] months2 ={"Jan","Feb","Mar","Apr","May","Jun"};
    Spinner sem;
    Spinner month;
    int sem_pos;
    String userName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_activity);
        SharedPreferences settings = getSharedPreferences("MYPREFS",MODE_PRIVATE);
        userName=settings.getString("username",null);

        sem = findViewById(R.id.semester);
        month = findViewById(R.id.month);
        sem.setOnItemSelectedListener(this);
        month.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(Attendance.this,android.R.layout.simple_spinner_item,semesters);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(aa);
         sem_pos = sem.getSelectedItemPosition()+1;
        ArrayAdapter aa1;
        if(sem_pos/2 !=0)
        {
             aa1 = new ArrayAdapter(Attendance.this,android.R.layout.simple_spinner_item,months1);
        }
        else
        {
             aa1 = new ArrayAdapter(Attendance.this,android.R.layout.simple_spinner_item,months2);
        }
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(aa1);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        String str=month.getSelectedItem().toString();
        String sem_position=String.valueOf(sem_pos);
        new GetAttendance().execute(str,sem_position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class GetAttendance extends AsyncTask<String,Void,Void>
    {
        private String attendance="";
        private String shortage="";
        boolean success = false;

        @Override
        protected Void doInBackground(String... str) {

            try {

                String DB_username = "l3wxuwmb_dev";
                String DB_password = "dev360";
                String url = "jdbc:mysql://mysql7002.site4now.net:3306/l3wxuwmb_academics";
                // SET CONNECTIONSTRING
                Class.forName("com.mysql.jdbc.Driver");
                Connection con =  DriverManager.getConnection(url, DB_username, DB_password);

                Log.w("Connection", "open");
                Log.println(Log.INFO, "INFO", "Open");
                int num=Integer.getInteger(str[1]);
                Statement stmt =  con.createStatement();
                ResultSet reset = stmt.executeQuery(" select attendence, shortage from addattendence where usn= '"+ userName +"' and LEFT(month,3)= '" + str[0] +"' and LEFT(sem,1)= "+ num);

                if(reset.next()) {
                    attendance=reset.getString("attendence");
                    shortage = reset.getString("shortage");

                }
                //name+=reset.getString(2);
                Log.println(Log.INFO,"INFO",attendance+shortage);

                con.close();


            } catch (Exception e) {
                Log.w("Error connection", "" + e.getMessage());
                Log.println(Log.ERROR,"Error",e.toString());

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {



            super.onPostExecute(aVoid);
        }
    }

}
