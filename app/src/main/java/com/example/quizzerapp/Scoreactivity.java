package com.example.quizzerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Scoreactivity extends AppCompatActivity {
    private TextView scored,totalText;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreactivity);
        scored=findViewById(R.id.scored);
        totalText=findViewById(R.id.totalText);
        doneButton=findViewById(R.id.doneButtton);
        Log.i("km","this is the stating of the score activity");
        scored.setText(String.valueOf(getIntent().getIntExtra("score",0)));
       totalText.setText("OUT OF"+String.valueOf(getIntent().getIntExtra("total",0)));
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}