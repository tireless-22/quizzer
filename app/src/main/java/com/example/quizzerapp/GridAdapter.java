package com.example.quizzerapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
    private int sets=0;
    private String category;

    public GridAdapter(int sets,String category) {
        this.sets = sets;
        this.category=category;
    }

    @Override
    public int getCount() {
        return sets;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        View v;
        if(view==null){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_item,viewGroup, false);

        }
        else{
            v=view;

        }

        //view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_item, viewGroup, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questionIntent=new Intent(viewGroup.getContext(),QuestionsActivity.class);
                questionIntent.putExtra("category",category);
                questionIntent.putExtra("setNo",i+1);

                viewGroup.getContext().startActivity(questionIntent);
            }
        });
        ((TextView)v.findViewById(R.id.textviewgrid)).setText(String.valueOf(i+1));


        return v;
    }
}
