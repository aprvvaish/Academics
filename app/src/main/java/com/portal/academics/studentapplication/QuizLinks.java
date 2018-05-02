package com.portal.academics.studentapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class QuizLinks extends AppCompatActivity {
    public ArrayList<String> list = new ArrayList<String>();
    ArrayAdapter<String> itemsAdapter;
    String course;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);


            SharedPreferences settings = getSharedPreferences("MYPREFS",MODE_PRIVATE);
            course=settings.getString("course",null);
            listView = findViewById(R.id.quiz_list);
            new GetQuiz().execute();


        }

        public class GetQuiz extends AsyncTask<Void,Void,Void>
        {


            @Override
            protected Void doInBackground(Void... voids) {

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
                    ResultSet reset = stmt.executeQuery(" select link from quiz where course= '" + course + "'");

                    while(reset.next()) {
                        list.add(reset.getString("link"));
                    }
                    Log.println(Log.INFO,"Info", list.toString() );
                    con.close();


                } catch (Exception e) {
                    Log.w("Error connection", "" + e.getMessage());
                    Log.println(Log.ERROR,"Error",e.toString());

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                itemsAdapter =new ArrayAdapter<String>(QuizLinks.this, android.R.layout.simple_list_item_1, list);

                listView.setAdapter(itemsAdapter);
                listView.setClickable(true);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Get the selected item text from ListView
                        String selectedItem = (String) parent.getItemAtPosition(position);
                        Intent intent=new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse( selectedItem));
                        startActivity(intent);


                    }
                });
                super.onPostExecute(aVoid);
            }
        }
    }

