package com.example.quizzerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<categorymodel>list;
    private Dialog loadingDialogue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        loadingDialogue=new Dialog(this);
        loadingDialogue.setContentView(R.layout.loading);
        loadingDialogue.getWindow().setBackgroundDrawable(getDrawable(R.drawable.roundedcorners));
        loadingDialogue.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialogue.setCancelable(false);

       //IT IS TO TAKE THE  EMAIL AND THE PASSWORD AS THE AUTHENTICATION


//auth=FirebaseAuth.getInstance();
//it is used to get the refernce to accept the registration
//auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(RegisterActivity.this,"Registration Sucessful",Toast.LENGTH_LONG).show();
//                }
//                else {
//                    Toast.makeText(RegisterActivity.this,"Registration failed",Toast.LENGTH_LONG).show();
//                }
//            }
//        });


        //THIS IS ADDING OF THE DATA

// FirebaseDatabase.getInstance().getReference().child("program").push().child("Name").setValue(txt_MainName);
//this is the usually used to add the details in the firebasedatabase

          //THIS IS THE RETRIVING OF THE DATA WHICH IS ADDED ABOOVE,THAT MEANS A SINGLE VALUE




//DatabaseReference reference =FirebaseDatabase.getInstance().getReference().child("Languages");
//        final ArrayList<String> list=new ArrayList<>();
//        final ArrayAdapter adapter =new ArrayAdapter<String>(this,R.layout.listitems,list);
//        listView.setAdapter(adapter);
//        DatabaseReference reference =FirebaseDatabase.getInstance().getReference().child("Languages");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//                list.clear();
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Information info=snapshot.getValue(Information.class);
//                    String txt=info.getEmail()+":"+info.getName();
//                    list.add(txt);
//                }
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    list.add(snapshot.getValue().toString());
//                }
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        //this is the code used to get the data from the location


        //THIS IS RETRIVING THE DATA ,MULTIPLE VALUE AT A TIME USING THE LISTVIEW

//final ArrayList<String> list=new ArrayList<>();
//        final ArrayAdapter adapter =new ArrayAdapter<String>(this,R.layout.listitems,list);
//        listView.setAdapter(adapter);
//        DatabaseReference reference =FirebaseDatabase.getInstance().getReference().child("Information");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



        Toolbar toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        Log.i("mytag","this is before passing of the arraylist to the categorymodel");
        list=new ArrayList<>();


        final categoryadapter adapter =new categoryadapter(list);
        recyclerView.setAdapter(adapter);
        loadingDialogue.show();

        Log.i("this is the problem","sdkhfashdfliusaaf8wyef");
        myRef.child("category").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("this is the problem","sdkhfashdfliusaaf8wyef");
                for(DataSnapshot datasnapshot1 :dataSnapshot.getChildren()) {

                    list.add(datasnapshot1.getValue(categorymodel.class));



                }
                adapter.notifyDataSetChanged();
                loadingDialogue.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CategoriesActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                loadingDialogue.dismiss();
                finish();



            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return  super.onOptionsItemSelected(item);
    }
}