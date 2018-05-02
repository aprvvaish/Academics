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
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Attendance extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    String[] semesters = {"I SEM","II SEM","III SEM","IV SEM","V SEM","VI SEM"};
    TextView subj,subj1,subj2,sh,sh1,sh2,at,at1,at2;
    Spinner sem;

    int sem_pos;
    String userName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_activity);
        SharedPreferences settings = getSharedPreferences("MYPREFS",MODE_PRIVATE);
        userName=settings.getString("username",null);

        subj =findViewById(R.id.textView5);
        subj1 =findViewById(R.id.textView8);
        subj2 =findViewById(R.id.textView11);
        at =findViewById(R.id.textView6);
        at1 =findViewById(R.id.textView9);
        at2 =findViewById(R.id.textView12);
        sh =findViewById(R.id.textView7);
        sh1 =findViewById(R.id.textView10);
        sh2 =findViewById(R.id.textView13);

        sem = findViewById(R.id.semester);

        ArrayAdapter aa = new ArrayAdapter(Attendance.this,android.R.layout.simple_spinner_item,semesters);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(aa);
        sem.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        new GetAttendance().execute(pos+1);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class GetAttendance extends AsyncTask<Integer,Void,Void>
    {
        private String attendance,attendance1,attendance2="";
        private String shortage,shortage1,shortage2="";
        private String subject,subject1,subject2="";
        //boolean success = false;

        @Override
        protected Void doInBackground(Integer... num) {

            try {

                String DB_username = "l3wxuwmb_dev";
                String DB_password = "dev360";
                String url = "jdbc:mysql://mysql7002.site4now.net:3306/l3wxuwmb_academics";
                // SET CONNECTIONSTRING
                Class.forName("com.mysql.jdbc.Driver");
                Connection con =  DriverManager.getConnection(url, DB_username, DB_password);

                Log.w("Connection", "open");
                Log.println(Log.INFO, "INFO", "Open");
                //int num=Integer.getInteger(str[1]);
                Log.println(Log.INFO, "INFO", num[0].toString());
                Statement stmt =  con.createStatement();
                ResultSet reset = stmt.executeQuery("select sub, attendence, shortage from addattendence where month in( select month from addattendence where usn= '"+ userName +"' and LEFT(sem,1)= "+ num[0]+" )");

                if(reset.next()) {
                    attendance=reset.getString("attendence");
                    shortage = reset.getString("shortage");
                    subject = reset.getString("sub");
                    reset.next();
                    attendance1=reset.getString("attendence");
                    shortage1 = reset.getString("shortage");
                    subject1 = reset.getString("sub");
                    reset.next();
                    attendance2=reset.getString("attendence");
                    shortage2 = reset.getString("shortage");
                    subject2 = reset.getString("sub");
                }
                //name+=reset.getString(2);
                Log.println(Log.INFO,"INFO",attendance+shortage+subject);

                con.close();


            } catch (Exception e) {
                Log.w("Error connection", "" + e.getMessage());
                Log.println(Log.ERROR,"Error",e.toString());

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            subj.setText(subject);
            subj1.setText(subject1);
            subj2.setText(subject2);
            at.setText(attendance);
            at1.setText(attendance1);
            at2.setText(attendance2);
            sh.setText(shortage);
            sh1.setText(shortage2);
            sh2.setText(shortage2);

            super.onPostExecute(aVoid);
        }
    }

}
