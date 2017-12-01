package smart.android.example.com.smartgarbage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Ajay on 6/10/2017.
 */

public class BackgroundTask extends AsyncTask<String,Void,String> {

    ProgressDialog progressDialog;

    Activity activity;

    Context ctx;

    String code;

    BackgroundTask(Context ctx){
        this.ctx=ctx;
        activity=(Activity) ctx;

    }




    protected String doInBackground(String... strings) {

        String reg_url="http://rahul68.16mb.com/customer/postadd.php";
        String method=strings[0];
        if (method.equalsIgnoreCase("postadd")){

            String name     =strings[1];
            String address  =strings[2];
            String locality =strings[3];
            String pincode  =strings[4];
            String contact  =strings[5];
            String plastic    =strings[6];
            String paper    =strings[7];
            String metal    =strings[8];
            String quantity =strings[9];
                   code     =strings[10];
            String username =strings[11];




        try {
            URL url=new URL(reg_url);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

            String data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                    URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")+"&"+
                    URLEncoder.encode("locality","UTF-8")+"="+URLEncoder.encode(locality,"UTF-8")+"&"+
                    URLEncoder.encode("pincode","UTF-8")+"="+URLEncoder.encode(pincode,"UTF-8")+"&"+
                    URLEncoder.encode("contact","UTF-8")+"="+URLEncoder.encode(contact,"UTF-8")+"&"+
                    URLEncoder.encode("plastic","UTF-8")+"="+URLEncoder.encode(plastic,"UTF-8")+"&"+
                    URLEncoder.encode("paper","UTF-8")+"="+URLEncoder.encode(paper,"UTF-8")+"&"+
                    URLEncoder.encode("metal","UTF-8")+"="+URLEncoder.encode(metal,"UTF-8")+"&"+
                    URLEncoder.encode("quantity","UTF-8")+"="+URLEncoder.encode(quantity,"UTF-8")+"&"+
                    URLEncoder.encode("code","UTF-8")+"="+URLEncoder.encode(code,"UTF-8")+"&"+
                    URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();
            InputStream IS=httpURLConnection.getInputStream();
            IS.close();
            return "Add post successful";


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        return "try after some time";
    }

    @Override
    protected void onPreExecute() {

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Please wait");
        //progressDialog.setMessage("Connecting to server...");
       // progressDialog.setIndeterminate(true);
        // progressDialog.setCancelable(false);
        progressDialog.show();
    }



    @Override
    protected void onPostExecute(String result) {

        progressDialog.dismiss();

        Intent i=new Intent(activity,SubmitActivity.class);
        i.putExtra("ucode",code);
        activity.startActivity(i);


    }
}
