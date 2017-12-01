package smart.android.example.com.smartgarbage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText et_name,et_address,et_pincode,et_contact,et_quantity;
    private CheckBox chk_paper,chk_plastic,chk_metal;
    private Spinner spn_locality;


    private String name,address,pincode,contact,quantity,locality,paper,plastic,metal,code,username;
    private int pap,pla,met;
    private int unique;

    ArrayAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        et_name= (EditText) findViewById(R.id.et_name);
        et_address= (EditText) findViewById(R.id.et_address);
        et_pincode= (EditText) findViewById(R.id.et_pincode);
        et_contact= (EditText) findViewById(R.id.et_contact);
        et_quantity= (EditText) findViewById(R.id.et_quantity);
        spn_locality= (Spinner) findViewById(R.id.spn_locality);
        chk_paper= (CheckBox) findViewById(R.id.chk_paper);
        chk_plastic= (CheckBox) findViewById(R.id.chk_plastic);
        chk_metal= (CheckBox) findViewById(R.id.chk_metal);


        //setting adapter
        adapter=ArrayAdapter.createFromResource(this,R.array.list_locality,R.layout.support_simple_spinner_dropdown_item);
        spn_locality.setAdapter(adapter);
        spn_locality.setOnItemSelectedListener(Main2Activity.this);

        //getting value from the intent

        Intent i=getIntent();
        username=i.getStringExtra("message");


        //generating unique code
        double randomDouble = Math.random();
        unique=(int) (randomDouble*1000000);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        TextView seltdView= (TextView) view;
        locality=seltdView.getText().toString();
       // Toast.makeText(this,locality,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }






    public void onSubmit(View view){

        //generating unique code
        double randomDouble = Math.random();
        unique=(int) (randomDouble*10000);

        if ((et_name.getText().toString().trim().equals("")) ||(et_address.getText().toString().trim().equals(""))||(et_pincode.getText().toString().trim().equals(""))|| (et_contact.getText().toString().trim().equals(""))|| (et_quantity.getText().toString().trim().equals(""))){

            Toast.makeText(this,"please fill the details",Toast.LENGTH_SHORT).show();
        }else {

            name=et_name.getText().toString();
            address=et_address.getText().toString();
            pincode=et_pincode.getText().toString();
            contact=et_contact.getText().toString();
            quantity=et_quantity.getText().toString();
            code=Integer.toString(unique);

            if (chk_paper.isChecked()){
                paper="yes";
            }
            else
                paper="no";

            if (chk_plastic.isChecked()){
                plastic="yes";
            }
            else
                plastic="no";
            if (chk_metal.isChecked()){
                metal="yes";
            }
            else {
                metal = "no";
            }

            System.out.println(name +" "+address+" "+pincode+" "+locality+" "+contact+" "+quantity+" "+code+" "+paper+" "+plastic+" "+metal);
            String method="postadd";
            BackgroundTask backgroundTask=new BackgroundTask(this);
            backgroundTask.execute(method,name,address,locality,pincode,contact,plastic,paper,metal,quantity,code,username);


        }


        /*Intent i=new Intent(this,SubmitActivity.class);
        i.putExtra("ucode",code);
        startActivity(i);
*/

    }





}


























/* public void post(View view){

        Intent intent = new Intent(this, SubmitActivity.class);
        startActivity(intent);
    }*/
