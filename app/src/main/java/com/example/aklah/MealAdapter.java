package com.example.aklah;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aklah.Model.Meal;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    Context context;

    ArrayList<Meal> meals;

    public MealAdapter(Context context,ArrayList<Meal> meals) {
        this.context=context;
        this.meals = meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.meal_list_row,parent,false);
        MealAdapter.ViewHolder viewHolder = new MealAdapter.ViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull MealAdapter.ViewHolder holder, int position) {
        holder.nameView.setText(meals.get(position).getStrMeal());

        holder.icon.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               ImageView im = (ImageView) v;
                                               Drawable drawable = im.getDrawable();
                                               Drawable drawable1 = context.getDrawable(R.drawable.favorite_fill0_wght400_grad0_opsz24);
                                               if (drawable.equals(drawable1)) {
                                                   im.setImageResource(R.drawable.favorite_fill1_wght400_grad0_opsz24);
                                               }
                                           }
                                       });
        //String link = "https://flagsapi.com/"+countries.get(position)+"/flat/64.png";
       // String link = "https://flagcdn.com/w160/"+countries.get(position).getCode()+".png";
        Glide.with(context).load(meals.get(position).getStrMealThumb()).apply(new RequestOptions().override(400,100)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameView;
        public ImageView imageView;

        public ImageView icon;
        public ConstraintLayout constraintLayout;

        public CardView cardView;
        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            nameView = itemView.findViewById(R.id.meal_name);
            imageView = itemView.findViewById(R.id.meal_img);
            icon=itemView.findViewById(R.id.like_img);
            constraintLayout= itemView.findViewById(R.id.country_view);
            cardView=itemView.findViewById(R.id.card_view);


        }
    }
}

