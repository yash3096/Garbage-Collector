package com.fx.collector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

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
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;


public class Select_Area extends Activity {

       ArrayList<String> location= new ArrayList<>();

    ProgressDialog progressDialog;
    String Location="";
    String json_string="";
    String JSON_STRING="";
    JSONObject jsonObject;
    JSONArray jsonArray;
    Spinner mySpinner;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_area);
        mySpinner=(Spinner)findViewById(R.id.select_area);
            new FetchArea().execute();

    }

    class FetchArea extends AsyncTask<Void,Void,String>{
    String json_url="http://rahul68.16mb.com/android/get_area.php";



        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                while((json_string=bufferedReader.readLine())!=null){
                    stringBuilder.append(json_string+"\n");
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
        public FetchArea(){
            super();
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            JSON_STRING=result;
            try {
                jsonObject=new JSONObject(JSON_STRING);
                int count=0;
                jsonArray=jsonObject.getJSONArray("server_response");
                while (count<jsonArray.length()){
                    JSONObject JO=jsonArray.getJSONObject(count);
                    location.add(JO.getString("location"));
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Select_Area.this, android.R.layout.simple_spinner_item, location); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner.setAdapter(spinnerArrayAdapter);
        }

        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(Select_Area.this);
            progressDialog.setMessage("Connecting to server");
            progressDialog.show();
        }
    }

    public void find(View view){
        Location=mySpinner.getSelectedItem().toString();
        Intent i=new Intent(Select_Area.this,Garbage_Details.class);
        i.putExtra("area",Location);
        startActivity(i);
    }
}