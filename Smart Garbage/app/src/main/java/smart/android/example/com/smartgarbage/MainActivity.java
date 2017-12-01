package smart.android.example.com.smartgarbage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
    private EditText et_sgn_user, et_sgn_pass;
    private String sgn_user,sgn_password;
    private Button signIn;


    String username="";
    ProgressDialog progressDialog;
    String password="";
    String message="";
    String login_status="";
    String j_string="";
    String J_STRING="";
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_sgn_user= (EditText) findViewById(R.id.et_sgn_user);
        et_sgn_pass= (EditText) findViewById(R.id.et_sgn_pass);
        signIn= (Button) findViewById(R.id.sing_in);
    }

    public void signIn(View view){
        sgn_user=et_sgn_user.getText().toString().trim();
        sgn_password=et_sgn_pass.getText().toString().trim();
        String method="login";
        System.out.println(sgn_user+"  "+sgn_password);

        new Login().execute();
        //BackgroundSignUp login =new BackgroundSignUp(this);
       // login.execute(method,sgn_user,sgn_password);
    }

    public void onSignUp(View view){

        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);

    }

    class Login extends AsyncTask<Void,Void,String>{
        String j_url="http://rahul68.16mb.com/customer/login.php?username="+sgn_user+"&password="+sgn_password;
        public Login() {
            super();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url =new URL(j_url);
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
            progressDialog.dismiss();
            J_STRING=result;
            try {
                jsonObject=new JSONObject(J_STRING);
                int count=0 ;
                jsonArray=jsonObject.getJSONArray("server_response");
                while (count<jsonArray.length()){
                    JSONObject JO=jsonArray.getJSONObject(count);
                    message=JO.getString("message");
                    login_status=JO.getString("login_status");
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(login_status.equals("true")){
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("message", message);
                startActivity(intent);
            }
            else{
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
            }
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Connecting to server");
            progressDialog.show();
        }
    }









    }







