package com.portal.academics.studentapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;

import com.mysql.jdbc.JDBC4ResultSet;



public class MainActivity extends AppCompatActivity {

    public static final String USER_NAME =
            "com.portal.academics.academicsapp.extra.MESSAGE";
    String user,pass;
    boolean checkUser = false;
    Button login_button;
    EditText text_user,text_pswd;
    String userName,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_user = findViewById(R.id.uName);
        text_pswd = findViewById(R.id.uPswd);
        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = text_user.getText().toString();
                password = text_pswd.getText().toString();
                if(userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Incorrect Credentials",Toast.LENGTH_SHORT).show();
                }
                else {
                    new CheckUser().execute();
                }
            }
        });

    }

    public class CheckUser extends AsyncTask<Void,Void,Void>
    {
        private String name="";
        boolean success = false;

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
                ResultSet reset = stmt.executeQuery(" select * from login where usn= '" + userName + "' and pswrd= '" + password +"'");

                if(reset.next()) {
                    success=true;
                }
                //name+=reset.getString(2);

                con.close();
                //return msgString;

            } catch (Exception e) {
                Log.w("Error connection", "" + e.getMessage());

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //text.setText(name);
            if(success==true)
            {
                Toast.makeText(MainActivity.this,
                        "Login Successful",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Dashboard.class);
                intent.putExtra(USER_NAME,userName);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(MainActivity.this,
                        "Incorrect Credentials",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aVoid);
        }
    }
}
