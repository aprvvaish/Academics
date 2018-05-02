package com.portal.academics.studentapplication;

import android.content.Intent;
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
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewMarks extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    String mark1,mark2,mark3,mark4,sub1,sub2,sub3,sub4;
    TextView subject1,subject2,subject3,subject4,marks1,marks2,marks3,marks4;
    String[] semesters = {"I SEM","II SEM","III SEM","IV SEM","V SEM","VI SEM"};
    Spinner sem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marks_activity);
        subject1=findViewById(R.id.sub1);
        subject2=findViewById(R.id.sub2);
        subject3=findViewById(R.id.sub3);
        subject4=findViewById(R.id.sub4);
        marks1=findViewById(R.id.mark1);
        marks2=findViewById(R.id.mark2);
        marks3=findViewById(R.id.mark3);
        marks4=findViewById(R.id.mark4);

        sem = findViewById(R.id.semester);
        sem.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(ViewMarks.this,android.R.layout.simple_spinner_item,semesters);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(aa);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        new GetMarks().execute(position+1);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class GetMarks extends AsyncTask<Integer,Void,Void>
    {
        private String name="";
        boolean success = false;

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

                Statement stmt =  con.createStatement();
                ResultSet reset = stmt.executeQuery(" select mark, sub from addmark where LEFT(sem,1)= " + num[0]);

                if(reset.next()) {
                    mark1=reset.getString("mark");
                    sub1=reset.getString("sub");
                    reset.next();
                    mark2=reset.getString("mark");
                    sub2=reset.getString("sub");
                    reset.next();
                    mark3=reset.getString("mark");
                    sub3=reset.getString("sub");
                    reset.next();
                    mark4=reset.getString("mark");
                    sub4=reset.getString("sub");
                }
                //name+=reset.getString(2);
                Log.println(Log.INFO,"INFO",mark1+sub1);

                con.close();
                //return msgString;

            } catch (Exception e) {
                Log.w("Error connection", "" + e.getMessage());
                Log.println(Log.ERROR,"Error",e.toString());

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            marks1.setText(mark1);
            subject1.setText(sub1);
            marks2.setText(mark2);
            subject2.setText(sub2);
            marks3.setText(mark3);
            subject3.setText(sub3);
            marks4.setText(mark4);
            subject4.setText(sub4);


            super.onPostExecute(aVoid);
        }
    }
}
