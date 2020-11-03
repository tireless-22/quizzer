package com.example.quizzerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.quizzerapp.QuestionsActivity.FILE_NAME;
import static com.example.quizzerapp.QuestionsActivity.KEY_NAME;

public class Bookmarks extends AppCompatActivity {
    private RecyclerView recyclerView;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    private List<questionModel> bookmarksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        Toolbar toolbar = findViewById(R.id.tb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BOOKMARKS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();

        getBookmarks();


        recyclerView = findViewById(R.id.rv_bookmarks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        BookmarkAdapter adapter = new BookmarkAdapter(bookmarksList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        storeBookmarks();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    private void getBookmarks() {
        // we had used this in the top of the programm ,because the bookmark has to show wheather it is bookmarked or not by chcecking in the bookmark list
        String json = preferences.getString(KEY_NAME, "");
        Type type = new TypeToken<List<questionModel>>() {
        }.getType();
        bookmarksList = gson.fromJson(json, type);

        if (bookmarksList == null) {
            bookmarksList = new ArrayList<>();

        }
    }


//if we want to know where we used certain method then change the name by deleting one char ,then it will show an error .


    private void storeBookmarks() {
        String json = gson.toJson(bookmarksList);
        editor.putString(KEY_NAME, json);
        editor.commit();


    }
}