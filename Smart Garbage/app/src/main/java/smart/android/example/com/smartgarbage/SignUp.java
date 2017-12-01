package smart.android.example.com.smartgarbage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private EditText et_fullname,et_username,et_password,et_email,et_contact;

    private RadioButton rb_male,rb_female;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_fullname= (EditText) findViewById(R.id.et_fullname);
        et_username= (EditText) findViewById(R.id.et_username);
        et_password= (EditText) findViewById(R.id.et_password);
        et_email= (EditText) findViewById(R.id.et_email);
        et_contact= (EditText) findViewById(R.id.et_contact);
        rb_male= (RadioButton) findViewById(R.id.rb_male);
        rb_female= (RadioButton) findViewById(R.id.rb_female);



    }

    public void onRegister(View view){

        if ((et_fullname.getText().toString().trim().equals(""))||(et_username.getText().toString().trim().equals(""))||(et_password.getText().toString().trim().equals(""))||(et_email.getText().toString().trim().equals(""))||(et_contact.getText().toString().trim().equals(""))){

            Toast.makeText(this,"Please fill the details",Toast.LENGTH_SHORT).show();
        }

        else {
            String reg_fullname = et_fullname.getText().toString();
            String reg_user = et_username.getText().toString();
            String reg_password = et_password.getText().toString();
            String reg_email = et_email.getText().toString();
            String reg_contact = et_contact.getText().toString();
            String reg_gender = null;

            if (rb_male.isChecked()) {
                reg_gender = "male";
            } else {
                reg_gender = "female";
            }

            String method = "signup";

            BackgroundSignUp signup = new BackgroundSignUp(this);
            signup.execute(method, reg_fullname, reg_user, reg_password, reg_email, reg_contact, reg_gender);

            //Toast.makeText(this,method,Toast.LENGTH_LONG).show();


        }

    }
}
