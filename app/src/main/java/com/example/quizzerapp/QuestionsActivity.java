package com.example.quizzerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {
    public static  final String FILE_NAME="QUIZZER";
    public static  final String KEY_NAME="QUESTIONS";


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    private TextView questions,numIndicator;
    private FloatingActionButton bookmarkBtn;
    private LinearLayout optionsContainers;
    private Button shareBtn,nextBtn;
    private int count=0;
    private int score=0;
    private List<questionModel>list;
    private int position=0;
    private String category;
    private int setNo;
    Dialog loadingDialogue;
    private List<questionModel> bookmarksList;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private int matchedQuestionPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        questions = findViewById(R.id.question);
        numIndicator = findViewById(R.id.num_Indicator);
        bookmarkBtn = findViewById(R.id.bookmarksBtn);
        optionsContainers = findViewById(R.id.optionsContainer);
        shareBtn = findViewById(R.id.shareButtton);
        nextBtn = findViewById(R.id.nextButton);





        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();



        Toolbar toolbar = findViewById(R.id.tooollbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //<!--we had set the the next button disable ,at the first ,and we will lightly light the button in order convey the-->
        //<!--    user it will only enable after when you select the option-->

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


        preferences=getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();
        gson=new Gson();



        loadingDialogue=new Dialog(this);
        loadingDialogue.setContentView(R.layout.loading);
        loadingDialogue.getWindow().setBackgroundDrawable(getDrawable(R.drawable.roundedcorners));
        loadingDialogue.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialogue.setCancelable(false);


        getBookmarks();
        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if(modelMatch()){
                    bookmarksList.remove(matchedQuestionPosition);
                    bookmarkBtn.setImageDrawable(getDrawable(R.drawable.bookmark_border));



                }
                else{
                    bookmarksList.add(list.get(position));
                    bookmarkBtn.setImageDrawable(getDrawable(R.drawable.bookmark));

                }
            }
        });



        category=getIntent().getStringExtra("category");
        setNo=getIntent().getIntExtra("setNo",1);


        list=new ArrayList<>();
        loadingDialogue.show();
        myRef.child("SETS").child(category).child("questions").orderByChild("setNo").equalTo(setNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    list.add(snapshot.getValue(questionModel.class));

                }
                if(list.size()>0){

                    for (int i=0;i<4;i++){
                        //we are settng the onclick functionality to the each and every option when we click the option,we have check wheather the
                        //option is true or not if not we have to display the correct option to the user
                        optionsContainers.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                checkAnswer((Button)view);
                                //here we are passed the button at the check answer we will get the text and check the answer

                            }
                        });

                    }
                    playAnim(questions,0,list.get(position).getQuestion());
                    nextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            nextBtn.setEnabled(false);
                            nextBtn.setAlpha(0.7f);
                            enableOption(true);
                            position++;
                            if(position==list.size()){
                                Intent scoreIntent=new Intent(QuestionsActivity.this,Scoreactivity.class);
                                scoreIntent.putExtra("score",score);
                                scoreIntent.putExtra("total",list.size());
                                startActivity(scoreIntent);
                                finish();
                                //score activity
                                return;
                            }
                            count=0;
                            playAnim(questions,0,list.get(position).getQuestion());

                            //here we are setting the question in that place
                            //fron here only the animationn of the question starts

                        }
                    });
                    shareBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String body=list.get(position).getQuestion()+" "+
                                    list.get(position).getOptionA()+
                                    list.get(position).getOptionB()+
                                    list.get(position).getOptionC()+
                                    list.get(position).getOptionD();


                            Intent shareIntent=new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("plain/text");

                            shareIntent.putExtra(Intent.EXTRA_SUBJECT,"QUIZ CHALLANGE");
                            shareIntent.putExtra(Intent.EXTRA_TEXT,body);
                            startActivity(Intent.createChooser(shareIntent,"share Via"));


                            startActivity(shareIntent);
                        }
                    });


                }
                else {
                    finish();
                    Toast.makeText(QuestionsActivity.this,"there is no questions available here",Toast.LENGTH_LONG).show();

                }
                loadingDialogue.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(QuestionsActivity.this,"database error",Toast.LENGTH_LONG).show();
                loadingDialogue.dismiss();
                finish();
            }
        });





    }

    @Override
    protected void onPause() {
        super.onPause();
        storeBookmarks();
    }

    private void playAnim(final View view , final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                //from here the animation of the options starts
                //count is the number of the button in the container

                if(value==0&&count<4){

                    String option="";
                    //here we are setting the optionns of the question according to the positions

                    if (count == 0) {
                        option=list.get(position).getOptionA();
                    }
                    else if (count == 1) {
                        option=list.get(position).getOptionB();

                    }
                    else if (count == 2) {
                        option=list.get(position).getOptionC();
                    }
                    else if (count == 3) {
                        option=list.get(position).getOptionD();
                    }




                    //from here the animation of the options starts
                    //count is the number of the button in the container
                    playAnim(optionsContainers.getChildAt(count),0,option);
                    //value equals to zero means there is no text in the button
                    //when you do this it will close the button
                    //if you did not use the onAnimationnEnd youwill not see the button
                    count++;
                }

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //when the animation start you will set the value to the 0.
                //it will close the button by setting the zero
                //here in the onanimation end by checking the condition if value is 0 ,
                // we set back to original state of the button which is 1

                if(value==0){
                    //here we used the try catch bcz here the we have the question
                    // is in the text type and the button is in the button to avoid the exception we had created the try catch block
                    try{
                        ((TextView)view).setText(data);
                        numIndicator.setText(position+1+"/"+list.size());
                        if(modelMatch()){
                            bookmarkBtn.setImageDrawable(getDrawable(R.drawable.bookmark));



                        }
                        else{
                            bookmarkBtn.setImageDrawable(getDrawable(R.drawable.bookmark_border));


                        }

                    }
                    catch (ClassCastException ex){
                        ((Button)view ).setText(data);
                    }

                    view.setTag(data);
                    //we had created this in order to show the user the correct answer when the user tapped on the incorrect answer

                    playAnim(view, 1,data);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }



    private void checkAnswer(Button selectedOption){
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        enableOption(false);


        if(selectedOption.getText().toString().equals(list.get(position).getCorrectAns())){
              //correct
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4caf50")));
            score++;
        }
        else{

            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctoption=(Button) optionsContainers.findViewWithTag(list.get(position).getCorrectAns());
            correctoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4caf50")));
        }


    }



    private void enableOption(boolean trueOrFalse){
        //we will use this when the user press the option,we have to disable the user to press other buttons
        for(int i=0;i<4;i++){
            optionsContainers.getChildAt(i).setEnabled(trueOrFalse);
            if(trueOrFalse){
                optionsContainers.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));

            }
        }

    }



    private  void getBookmarks(){
        // we had used this in the top of the programm ,because the bookmark has to show wheather it is bookmarked or not by chcecking in the bookmark list
        String json=preferences.getString(KEY_NAME,"");
        Type type=new TypeToken<List<questionModel>>(){}.getType();
        bookmarksList=gson.fromJson(json,type);

        if(bookmarksList==null){
            bookmarksList=new ArrayList<>();

        }
    }


//if we want to know where we used certain method then change the name by deleting one char ,then it will show an error .

    private boolean modelMatch(){

        boolean matched =false;
        int i=0;
        //here we are checkiing that the element in the bookmark list is matched with the question list,if bookmarked it ,it will be true
        for (questionModel model: bookmarksList){
            if(model.getQuestion().equals(list.get(position).getQuestion())&&model.getCorrectAns().equals(list.get(position).getCorrectAns())&&model.getSetNo()==list.get(position).getSetNo()){
                 matched=true;
                 matchedQuestionPosition=i;

            }
            i++;


        }

        return matched;
    }

    private void  storeBookmarks(){
        String json=gson.toJson(bookmarksList);
        editor.putString(KEY_NAME,json);
        editor.commit();


    }



}