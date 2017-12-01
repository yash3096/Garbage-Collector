package com.fx.collector;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import android.widget.Toast;

public class Register extends Activity {

    String reg_url = "";


    ProgressDialog progressDialog;
    Activity activity;
    Context ctx;
    AlertDialog.Builder builder;

    private EditText et_fullname, et_username, et_password, et_email, et_contact;
    String reg_fullname = "";
    String reg_user = "";
    String reg_password = "";
    String reg_email = "";
    String reg_contact = "";
    String reg_gender = "";

    RadioButton rb_male, rb_female;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_fullname = (EditText) findViewById(R.id.et_fullname);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_email = (EditText) findViewById(R.id.et_email);
        et_contact = (EditText) findViewById(R.id.et_contact);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);


    }

    public void register(View view) {

        reg_fullname = et_fullname.getText().toString();
        reg_user = et_username.getText().toString();
        reg_password = et_password.getText().toString();
        reg_email = et_email.getText().toString();
        reg_contact = et_contact.getText().toString();
        reg_gender = null;

        if (rb_male.isChecked()) {
            reg_gender = "male";
        } else {
            reg_gender = "female";
        }

        String method = "signup";
        SignUp signUp=new SignUp(this);
        signUp.execute(method,reg_fullname,reg_user,reg_password,reg_email,reg_contact,reg_gender);
        finish();
        //Toast.makeText(this,method,Toast.LENGTH_LONG).show();

    }

    class SignUp extends AsyncTask<String, Void, String> {
        Context ctx;
        SignUp(Context ctx){
            this.ctx=ctx;
        }
        @Override
        protected String doInBackground(String... strings) {
            String method=strings[0];
            String reg_url="http://rahul68.16mb.com/android/register.php";
            if(method.equals("register")){
                String reg_fullname = strings[1];
                String reg_user = strings[2];
                String reg_password = strings[3];
                String reg_mail = strings[4];
                String reg_contact = strings[5];
                String reg_gender = strings[6];
                try {
                    URL url=new URL(reg_url);
                    HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream os=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8")); //entering data into database;
                    String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(reg_fullname, "UTF-8") + "&" +
                            URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(reg_user, "UTF-8") + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(reg_password, "UTF-8") + "&" +
                            URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(reg_gender, "UTF-8") + "&" +
                            URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(reg_mail, "UTF-8") + "&" +
                            URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(reg_contact, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    InputStream IS=httpURLConnection.getInputStream();
                    IS.close();
                    return "Registration Successfull";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

            builder = new AlertDialog.Builder(activity);

            progressDialog = new ProgressDialog(ctx);
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Connecting to server...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
            Intent i=new Intent(Register.this,MainActivity.class);
            startActivity(i);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
