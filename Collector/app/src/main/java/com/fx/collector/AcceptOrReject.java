package com.fx.collector;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
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
import java.util.ArrayList;

public class AcceptOrReject extends Activity {
    String name="";
    String message="";
    String quantity="";
    String address="";
    String area="";
    String pincode="";
    String username="";
    String contact="";
    String code="";
    String check_plastic="";
    String  check_paper="";
    String check_metal="";
    String waste_type="";
    String json_string="";
    String j_string="";
    String JSON_STRING="";
    JSONObject jsonObject;
    JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_or_reject);
        code=getIntent().getExtras().getString("code");

        new BackGround().execute();
    }
        class BackGround extends AsyncTask<Void,Void,String>{
            String j_url="http://rahul68.16mb.com/android/all_user_data.php?code="+code;
        public BackGround() {
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url=new URL(j_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                while((j_string=bufferedReader.readLine())!=null){
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
            JSON_STRING= result;
            try {
                jsonObject = new JSONObject(JSON_STRING);
                int count = 0;
                jsonArray = jsonObject.getJSONArray("server_response");

                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    name=JO.getString("name");
                    address=JO.getString("address");
                    pincode=JO.getString("pincode");
                    area=JO.getString("location");
                    quantity=JO.getString("quantity");
                    username=JO.getString("username");
                    contact=JO.getString("contact");
                    check_plastic=JO.getString("plastic");
                    check_paper=JO.getString("paper");
                    check_metal=JO.getString("metal");
                    if((check_plastic+check_paper+check_metal).equals("100")) {
                        waste_type="plastic";
                    }
                    else if((check_plastic+check_paper+check_metal).equals("010")) {
                        waste_type="paper";
                    }
                    else if((check_plastic+check_paper+check_metal).equals("001")) {
                        waste_type="metal";
                    }
                    else if((check_plastic+check_paper+check_metal).equals("110")) {
                        waste_type="plastic paper";
                    }
                    else if((check_plastic+check_paper+check_metal).equals("101")) {
                        waste_type="plastic metal";
                    }
                    else if((check_plastic+check_paper+check_metal).equals("011")) {
                        waste_type="paper metal";
                    }
                    else{
                        waste_type="plastic paper metal";
                    }
                    count++;
                }
            }
                catch(JSONException e){
                    e.printStackTrace();
                }
          //  Toast.makeText(AcceptOrReject.this,check_plastic+check_paper+check_metal,Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_accept_or_reject);
            TextView namee =(TextView)findViewById(R.id.name);
            TextView addresss =(TextView)findViewById(R.id.address);
            TextView quantityy=(TextView)findViewById(R.id.quantity);
            TextView areaa=(TextView)findViewById(R.id.area);
            TextView pincodee=(TextView)findViewById(R.id.pincode);
            TextView waste_typee=(TextView)findViewById(R.id.waste_type);
            namee.setText(name);
            addresss.setText(address);
            quantityy.setText(quantity);
            areaa.setText(area);
            pincodee.setText(pincode);
            waste_typee.setText(waste_type);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
    public void accept(View view){
        Intent i=new Intent(AcceptOrReject.this,Contact.class);
        i.putExtra("code",code);
        i.putExtra("number",contact);
        startActivity(i);
    }
    public void reject(View view){
        Toast.makeText(AcceptOrReject.this,"Rejected",Toast.LENGTH_LONG).show();
        Intent i=new Intent(AcceptOrReject.this,Select_Area.class);
        startActivity(i);
    }
}
