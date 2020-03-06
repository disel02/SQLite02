package com.example.sqlite02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlite02.ListAdapter.ListAdapterClass;
import com.example.sqlite02.ListAdapter.subjects;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor editor;
    DbHelper dbHelper;
    ListView lsttask;
    List<subjects> subjectsList;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        lsttask = (ListView) findViewById(R.id.lsttask);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        loadTaskList();

        lsttask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String lvfname = ((TextView) findViewById(R.id.tvfname)).getText().toString();
                String lvlname = ((TextView) findViewById(R.id.tvlname)).getText().toString();

                editor.putBoolean("textclick", true);
                editor.apply();

                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("fnamekey",lvfname);
                intent.putExtra("lnamekey",lvlname);
                startActivity(intent);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("textclick", false);
                editor.apply();
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    private void loadTaskList() {
        subjectsList=dbHelper.getTodos();
        if (subjectsList != null) {
            ListAdapterClass adapter = new ListAdapterClass(subjectsList, this);
            lsttask.setAdapter(adapter);
        }
        else
            Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();
    }
}
