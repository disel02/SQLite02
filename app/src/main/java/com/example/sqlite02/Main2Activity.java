package com.example.sqlite02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    DbHelper dbHelper;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor editor;
    EditText etfname,etlname;
    Button btnsubmit,btndelete,btnedit;
    LinearLayout llhide;
    Boolean textclick;
    String fname02,lname02,fname,lname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        textclick = prefs.getBoolean("textclick", false);

        dbHelper=new DbHelper(this);
        etfname=(EditText)findViewById(R.id.etfname);
        etlname=(EditText)findViewById(R.id.etlname);
        btnsubmit=(Button)findViewById(R.id.btnsubmit);
        llhide=(LinearLayout)findViewById(R.id.llhide);
        btndelete=(Button)findViewById(R.id.btndelete);
        btnedit=(Button)findViewById(R.id.btnedit);

        if (textclick) {
            llhide.setVisibility(View.VISIBLE);
            btnsubmit.setVisibility(View.GONE);
            Intent intent=getIntent();
            fname02=intent.getStringExtra("fnamekey");
            lname02 = intent.getStringExtra("lnamekey");
            etfname.setText(fname02);
            etlname.setText(lname02);
        }

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deletetask(fname02);
                Toast.makeText(Main2Activity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=etfname.getText().toString();
                lname=etlname.getText().toString();
                try {
                    dbHelper.updatetask(fname, lname, fname02, lname02);
                    Toast.makeText(Main2Activity.this, "update Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    Toast.makeText(Main2Activity.this, " "+e, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=etfname.getText().toString();
                lname=etlname.getText().toString();
                try {
                    dbHelper.insertNewTask(fname,lname);
                    Toast.makeText(Main2Activity.this, "successfully added", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    Toast.makeText(Main2Activity.this, " "+e, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
