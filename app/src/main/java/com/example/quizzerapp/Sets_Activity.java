package com.example.quizzerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListAdapter;

public class Sets_Activity extends AppCompatActivity {
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets_);
       Log .i("nytag","this is the calling of setsactivity");



        Toolbar tooolbar=findViewById(R.id.tooolbar);
        setSupportActionBar(tooolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //it willk give the back arrow on the tool bar
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        //it will set the title


        Log .i("nytag","this is the calling of setsactivity");


        gridView =findViewById(R.id.gridview);
        GridAdapter adapter=new GridAdapter(getIntent().getByteExtra("sets", (byte) 1),getIntent().getStringExtra("title"));
        gridView.setAdapter(adapter);
    }
//it will set the  functionality whhen we click the backbutton
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}