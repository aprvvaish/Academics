package com.portal.academics.studentapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.JDBC4ResultSet;

public class DbConnector extends AsyncTask<Void,Void,Void>
{
    private String name="";

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            String username = "l3wxuwmb_dev";
            String password = "dev360";
            String url = "jdbc:mysql://mysql7002.site4now.net:3306/l3wxuwmb_academics";
            // SET CONNECTIONSTRING
            Class.forName("com.mysql.jdbc.Driver");
            Connection con =  DriverManager.getConnection(url, username, password);

            //Class.forName("com.mysql.jdbc.Driver").newInstance();

            //Connection DbConn = DriverManager.getConnection("jdbc:mysql://mysql7002.site4now.net:3306/l3wxuwmb_academics" , username , password);

            Log.w("Connection", "open");
            Log.println(Log.INFO, "INFO", "Open");
            Statement stmt =  con.createStatement();
            ResultSet reset = stmt.executeQuery(" select * from student ");
            if(reset.next()) {
                name = reset.getString(1);
            }
            name+=reset.getString(2);

            con.close();
            //return msgString;

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());
            name="no connection \n" + e.toString();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //text.setText(name);
        passData(name);
        super.onPostExecute(aVoid);
    }

    public String passData(String str)
    {
        return str;
    }
}