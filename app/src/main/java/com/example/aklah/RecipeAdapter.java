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

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<
        RecipeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> ingredients;
    private ArrayList<String> measures;



    public RecipeAdapter(Context context, ArrayList<String> ingredients,ArrayList<String> measures) {
        this.context = context;
        this.ingredients = ingredients;
        this.measures=measures;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setMeasures(ArrayList<String> measures) {
        this.measures = measures;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.recipe_list_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameView.setText(measures.get(position)+" "+ingredients.get(position));
        String link = "https://www.themealdb.com/images/ingredients/"+ingredients.get(position)+".png";
        Glide.with(context).load(link).apply(new RequestOptions().override(100,100)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameView;
        public TextView descriptionView;
        public ImageView imageView;
        public ConstraintLayout constraintLayout;
        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            nameView = itemView.findViewById(R.id.nameText);
            imageView = itemView.findViewById(R.id.imageView);
            constraintLayout= itemView.findViewById(R.id.constraintView);

        }
    }
}
