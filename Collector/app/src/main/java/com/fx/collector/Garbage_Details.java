package com.fx.collector;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.ArrayList;

/**
 * Created by singh on 4/30/2017.
 */

public class Garbage_Details extends Activity {
    String area="";
    String j_string="";
    String check_plastic="";
    String  check_paper="";
    String check_metal="";
    String JSON_STRING="";
    ArrayList<String> quantity=new ArrayList<>();
    ArrayList<String> code=new ArrayList<>();
    ArrayList<String> waste_type=new ArrayList<>();
    ArrayList<String> name=new ArrayList<>();
    JSONObject jsonObject;
    JSONArray jsonArray;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garbage_details);
        area=getIntent().getExtras().getString("area");
        new CustomerDetails().execute();

    }

    class CustomerDetails extends AsyncTask<Void,Void,String>{
        String j_url="http://rahul68.16mb.com/android/get_details.php?area="+area;

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
        public CustomerDetails(){
            super();
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            JSON_STRING=result;
            try {
                jsonObject=new JSONObject(JSON_STRING);
                int count=0 ;
                jsonArray=jsonObject.getJSONArray("server_response");
                while (count<jsonArray.length()){
                    JSONObject JO=jsonArray.getJSONObject(count);
                    quantity.add(JO.getString("quantity"));
                    code.add(JO.getString("code"));
                    name.add(JO.getString("name"));
                    check_plastic=JO.getString("plastic");
                    check_paper=JO.getString("paper");
                    check_metal=JO.getString("metal");
                    if((check_plastic+check_paper+check_metal).equals("100")) {
                        waste_type.add("plastic");
                    }
                    else if((check_plastic+check_paper+check_metal).equals("010")) {
                        waste_type.add("paper");
                    }
                    else if((check_plastic+check_paper+check_metal).equals("001")) {
                        waste_type.add("metal");
                    }
                    else if((check_plastic+check_paper+check_metal).equals("110")) {
                        waste_type.add("plastic paper");
                    }
                    else if((check_plastic+check_paper+check_metal).equals("101")) {
                        waste_type.add("plastic metal");
                    }
                    else if((check_plastic+check_paper+check_metal).equals("011")) {
                        waste_type.add("paper metal");
                    }
                    else{
                        waste_type.add("plastic paper metal");
                    }
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ListAdapter mylistadapter=new CustomAdapter(Garbage_Details.this,quantity,code,name,waste_type);
            ListView mylistview=(ListView) findViewById(R.id.mylistview);
            mylistview.setAdapter(mylistadapter);
            mylistview.setOnItemClickListener(
                    new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String code=String.valueOf(parent.getItemAtPosition(position));
                            Intent i =new Intent(Garbage_Details.this,AcceptOrReject.class);
                            i.putExtra("code",code);
                            startActivity(i);
                        }
                    }
            );
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

   /* public void show(View view){
        Button accept=(Button)findViewById(R.id.accept);
        Button reject=(Button)findViewById(R.id.reject);
        accept.setVisibility(View.VISIBLE);
        reject.setVisibility(View.VISIBLE);
    }
    public void accept(View view){
        Intent i=new Intent(Garbage_Details.this,Contact.class);
        startActivity(i);
    } */
}
