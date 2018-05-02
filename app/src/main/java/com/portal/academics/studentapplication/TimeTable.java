package com.portal.academics.studentapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class TimeTable extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    ImageView imageView;
    String course;
    String[] semesters = {"I SEM","II SEM","III SEM","IV SEM","V SEM","VI SEM"};
    Spinner semester;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity);
        SharedPreferences settings = getSharedPreferences("MYPREFS",MODE_PRIVATE);
        course=settings.getString("course",null);
        imageView= findViewById(R.id.imageView2);
        semester =findViewById(R.id.spinner);
        ArrayAdapter aa = new ArrayAdapter(TimeTable.this,android.R.layout.simple_spinner_item,semesters);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semester.setAdapter(aa);
        semester.setOnItemSelectedListener(this);


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        new GetImage().execute(semester.getSelectedItemPosition()+1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class SendHttpRequestTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            }catch (Exception e){
                Log.println(Log.ERROR,"Error",e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {

            imageView.setImageBitmap(result);
        }
    }

    public class GetImage extends AsyncTask<Integer,Void,Void>
    {
        String imageLink="";

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
                ResultSet reset = stmt.executeQuery(" select image from timetable where LEFT(sem,1)= " + num[0] +" and course= '" + course + "'");

                if(reset.next()) {
                    imageLink=reset.getString(1);
                }
                Log.println(Log.INFO,"Info", imageLink );
                con.close();


            } catch (Exception e) {
                Log.w("Error connection", "Message: " + e.getMessage());
                Log.println(Log.ERROR,"Error",e.toString());

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new SendHttpRequestTask().execute(imageLink);

            super.onPostExecute(aVoid);
        }
    }
}
