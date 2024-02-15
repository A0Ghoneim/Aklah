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
import com.example.aklah.Model.Ingredient;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<
        IngredientAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Ingredient> ingredients;



    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.ingredient_list_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameView.setText(ingredients.get(position).getStrIngredient());
        String link = "https://www.themealdb.com/images/ingredients/"+ingredients.get(position).getStrIngredient()+".png";
        Glide.with(context).load(link).apply(new RequestOptions().override(100, 100)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameView;
        public ImageView imageView;
        public ConstraintLayout constraintLayout;
        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            nameView = itemView.findViewById(R.id.ingredient_name);
            imageView = itemView.findViewById(R.id.ingredient_img);
            constraintLayout= itemView.findViewById(R.id.ingredient_view);

        }
    }
}

