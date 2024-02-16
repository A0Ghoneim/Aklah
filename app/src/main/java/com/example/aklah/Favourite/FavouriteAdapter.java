package com.example.aklah.Favourite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aklah.Adapters.OnFavouriteClickListener;
import com.example.aklah.Model.Meal;
import com.example.aklah.R;
import com.example.aklah.Schedule.ScheduleFragmentDirections;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    public static final int FRAGMENT_PLAN=0;
    public static final int FRAGMENT_FAV=1;

    Context context;
 OnFavouriteClickListener listener;
    ArrayList<Meal> meals;

    int fragment;

    public FavouriteAdapter(Context context, ArrayList<Meal> meals,OnFavouriteClickListener listener) {
        this.context=context;
        this.meals = meals;
        this.listener=listener;
    }
    public FavouriteAdapter(Context context, ArrayList<Meal> meals,OnFavouriteClickListener listener,int fragment) {
        this.context=context;
        this.meals = meals;
        this.listener=listener;
        this.fragment=fragment;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.favourite_list_row,parent,false);
        FavouriteAdapter.ViewHolder viewHolder = new FavouriteAdapter.ViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
        Meal curr = meals.get(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment==FRAGMENT_FAV) {
                    FavouriteFragmentDirections.ActionFavouriteFragmentToMealInfoFragment action = FavouriteFragmentDirections.actionFavouriteFragmentToMealInfoFragment();
                    action.setMealID(curr.getIdMeal());
                    Navigation.findNavController(v).navigate(action);
                }
                else if (fragment==FRAGMENT_PLAN){
                    ScheduleFragmentDirections.ActionScheduleFragmentToMealInfoFragment2 action = ScheduleFragmentDirections.actionScheduleFragmentToMealInfoFragment2();
                    action.setMealID(curr.getIdMeal());
                    Navigation.findNavController(v).navigate(action);
                }
            }
        });
        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFavMealClick(curr);
            }
        });
        holder.nameView.setText(meals.get(position).getStrMeal());

        Glide.with(context).load(meals.get(position).getStrMealThumb()).apply(new RequestOptions().override(400,100)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameView;
        public ImageView imageView;

        public ImageView deleteIcon;

        public ConstraintLayout constraintLayout;

        public CardView cardView;
          public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             layout=itemView;
            nameView = itemView.findViewById(R.id.meal_name);
            imageView = itemView.findViewById(R.id.meal_img);
            constraintLayout= itemView.findViewById(R.id.country_view);
            cardView=itemView.findViewById(R.id.card_view);
            deleteIcon=itemView.findViewById(R.id.deleteicon);
        }
    }
}

