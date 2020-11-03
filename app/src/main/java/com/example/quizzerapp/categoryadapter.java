package com.example.quizzerapp;

        import android.content.Intent;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.bumptech.glide.Glide;

        import java.util.List;

        import de.hdodenhof.circleimageview.CircleImageView;

public class categoryadapter extends RecyclerView.Adapter<categoryadapter.ViewHolder> {


    private List<categorymodel>categorymodelList;

    public categoryadapter(List<categorymodel> categorymodelList) {
        Log.i("mytag","this is in the constructor of the  category adapter");
        this.categorymodelList = categorymodelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryitem,parent,false);
        Log.i("mytag","this is in the category oncreateviewholder in the categoryadapter");
        //here we are inflatting the itemview
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("mytag","this is in the onbindviewholder in the categoryadaptor");
        holder.setData(categorymodelList.get(position).getUrl(),categorymodelList.get(position).getName(),categorymodelList.get(position).getSets());

    }

    @Override
    public int getItemCount() {
        return categorymodelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView imageView;
        private TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i("mytag","this is in views at the holder class");
            imageView=itemView.findViewById(R.id.image_view);
            title=itemView.findViewById(R.id.title);
        }
        public  void setData(String url, final String title, final int sets){
            Log.i("mytag","this is the calling of the setdata in the holder class");

            this.title.setText(title);

            Glide.with(itemView.getContext()).load(url).into(imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent setintent=new Intent(itemView.getContext(),Sets_Activity.class);
                    setintent.putExtra("title",title);
                    setintent.putExtra("sets",sets);
                    itemView.getContext().startActivity(setintent);

                }
            });

        }
    }
}
