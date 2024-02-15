package com.example.aklah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aklah.Model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<
        CategoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Category> categories;



    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.category_list_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameView.setText(categories.get(position).getStrCategory());
        Glide.with(context).load(categories.get(position).getStrCategoryThumb()).apply(new RequestOptions().override(100,100)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameView;
        public ImageView imageView;
        public ConstraintLayout constraintLayout;
        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            nameView = itemView.findViewById(R.id.category_name);
            imageView = itemView.findViewById(R.id.category_img);
            constraintLayout= itemView.findViewById(R.id.category_view);

        }
    }
}

