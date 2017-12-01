package com.fx.collector;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by singh on 4/30/2017.
 */

public class Contact extends Activity {
    String code="";
    String contact="";
    String j_url="";
    String message="";
    String j_string="";
    String JSON_STRING="";
    JSONObject jsonObject;
    JSONArray jsonArray;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        code=getIntent().getExtras().getString("code");
        contact=getIntent().getExtras().getString("number");
        TextView codee=(TextView)findViewById(R.id.unique_code);
        TextView contactt=(TextView)findViewById(R.id.contact);
        codee.setText(code);
        contactt.setText(contact);
        new Accept().execute();
    }
    class Accept extends AsyncTask<Void,Void,String>{
        String j_url="http://rahul68.16mb.com/android/accept_garbage.php?code="+code;
        public Accept() {
            super();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url=new URL(j_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                while ((j_string=bufferedReader.readLine())!=null){
                    stringBuilder.append(j_string+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            JSON_STRING=result;
            try {
                jsonObject = new JSONObject(JSON_STRING);
                int count = 0;
                jsonArray = jsonObject.getJSONArray("server_response");

                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    message=JO.getString("message");
                    count++;
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            Toast.makeText(Contact.this,message,Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
