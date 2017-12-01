package smart.android.example.com.smartgarbage;

/**
 * Created by Ajay on 4/30/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class SubmitActivity extends AppCompatActivity {
    private TextView tv_code;
    private String uniquecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit);
        tv_code= (TextView) findViewById(R.id.tv_code);

        Intent i=getIntent();
        uniquecode=i.getStringExtra("ucode");

        tv_code.setText(uniquecode);

    }
   public void onPostAgain(View view){
       Intent intent = new Intent(this, Main2Activity.class);
       startActivity(intent);
   }

    public void onOldPost(View view){
        Intent intent = new Intent(this, Recents.class);
        startActivity(intent);

    }
}

