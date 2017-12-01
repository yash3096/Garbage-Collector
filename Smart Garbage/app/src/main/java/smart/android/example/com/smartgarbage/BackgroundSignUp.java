package smart.android.example.com.smartgarbage;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
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

/**
 * Created by Ajay on 6/10/2017.
 */

public class BackgroundSignUp extends AsyncTask<String,Void,String> {

    String reg_url = "http://rahul68.16mb.com/customer/register.php";


    ProgressDialog progressDialog;
    Activity activity;
    Context ctx;
    AlertDialog.Builder builder;

    BackgroundSignUp(Context ctx) {

        this.ctx = ctx;
        activity = (Activity) ctx;
    }

    @Override
    protected String doInBackground(String... strings) {

        String method = strings[0];
        if (method.equalsIgnoreCase("signup")) {

            String reg_fullname = strings[1];
            String reg_user = strings[2];
            String reg_password = strings[3];
            String reg_mail = strings[4];
            String reg_contact = strings[5];
            String reg_gender = strings[6];

            /****************MAKING HTTP CONNECTION ******************/

            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

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

                /*************TAKING INPUT STREAM (DATA FROM THE SERVER)**********************/

                InputStream inputStream = httpURLConnection.getInputStream();


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    stringBuilder.append(line + "\n");
                }

                //inputStream.close();
                httpURLConnection.disconnect();
                Thread.sleep(5000);
                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }/* else if (method.equalsIgnoreCase("login")) {




            String sgn_user = strings[1];
            String sgn_pass = strings[2];

            String log_url = "http://smartgarbage.16mb.com/android/login.php?username="+sgn_user+"&password="+sgn_pass;

            System.out.println(sgn_user+"  "+sgn_pass);

            try {
                URL url =new URL(log_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    stringBuilder.append(line + "\n");
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
        }*/
        return "try again";
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        // dialog= ProgressDialog.show(ctx,"please Wait",null,true);

        /*************CREATING DIALOG WHILE THE PROCESS IS GOING ON**********/
        builder = new AlertDialog.Builder(activity);

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Connecting to server...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }


    @Override
    protected void onPostExecute(String json) {

        //Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();


        /*************ON POST EXECUTION WE ARE CONVERTING THE COLLECTED DATA INTO JSON*****************/

        try {
            progressDialog.dismiss();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            JSONObject JO = jsonArray.getJSONObject(0);
            String code = JO.getString("code");
            String message = JO.getString("message");

            System.out.println("ajay bahi " + code + message);

            Toast.makeText(ctx, code + " " + message, Toast.LENGTH_LONG).show();

            if (code.equals("reg_true")) {

                showDialog("Registration Success", message, code);
            }
            else if (code.equals("reg_false")) {

                showDialog("Registration Failed", message, code);
            }
           /* else if (code.equals("login_true")) {

                Intent intent = new Intent(activity, Main2Activity.class);
                intent.putExtra("message", message);
                activity.startActivity(intent);
            }
            else if (code.equals("login_false")) {

                showDialog("Login Failed", message, code);

            }*/


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showDialog(String title, String message, String code) {
        System.out.println("outside bahi " + code);
        System.out.println("outside bahiya " + message);

        builder.setTitle(title);
        if (code.equals("reg_true") || code.equals("reg_false")) {

            System.out.println("inside bahi ");

            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                    activity.finish();
                }
            });


            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        /*else if (code.equals("login_false")) {

            System.out.println("andar");
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    EditText sgn_username, sgn_password;
                    sgn_username = (EditText) activity.findViewById(R.id.et_sgn_user);
                    sgn_password = (EditText) activity.findViewById(R.id.et_sgn_pass);
                    sgn_username.setText("");
                    sgn_password.setText("");
                    dialog.dismiss();

                }


            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }*/

    }
}