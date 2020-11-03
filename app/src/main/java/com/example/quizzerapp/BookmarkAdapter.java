package com.example.quizzerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.viewHolder> {
    private List<questionModel>list;

    public BookmarkAdapter(List<questionModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_item,parent,false);
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.setData(list.get(position).getQuestion(),list.get(position).getCorrectAns(),position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        private TextView question,answer;
        private ImageButton deleteBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            question =itemView.findViewById(R.id.questionn);
            answer=itemView.findViewById(R.id.answers);
            deleteBtn=itemView.findViewById(R.id.deleteBtn);
        }

        private void setData(String question,String answer,final int position){
            this.question.setText(question);
            this.answer.setText(answer);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    notifyItemRemoved(position);

                }
            });


        }
    }
}
