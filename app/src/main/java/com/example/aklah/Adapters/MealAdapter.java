package com.example.aklah.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aklah.Home.View.HomeFragmentDirections;
import com.example.aklah.MealSearch.View.MealSearchFragmentDirections;
import com.example.aklah.Model.Ingredient;
import com.example.aklah.Model.Meal;
import com.example.aklah.R;
import com.example.aklah.Search.View.SearchFragment;
import com.example.aklah.Search.View.SearchFragmentDirections;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_MEAL_SEARCH=1;

    public static final int FRAGMENT_SEARCH=2;

    private int fragment;

    Context context;

    ArrayList<Meal> meals;

    public MealAdapter(Context context, ArrayList<Meal> meals, int fragment) {
        this.context=context;
        this.meals = meals;
        this.fragment =fragment;
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
        Meal curr = meals.get(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment==FRAGMENT_MEAL_SEARCH){
                    MealSearchFragmentDirections.ActionMealSearchFragment2ToMealInfoFragment action = MealSearchFragmentDirections.actionMealSearchFragment2ToMealInfoFragment();
                    action.setMealID(curr.getIdMeal());
                    Navigation.findNavController(v).navigate(action);
                } else if (fragment==FRAGMENT_HOME) {
                    HomeFragmentDirections.ActionHomeFragmentToMealInfoFragment action = HomeFragmentDirections.actionHomeFragmentToMealInfoFragment();
                    action.setMealID(curr.getIdMeal());
                    Navigation.findNavController(v).navigate(action);
                } else if (fragment==FRAGMENT_SEARCH) {
                    SearchFragmentDirections.ActionSearchFragmentToMealInfoFragment action = SearchFragmentDirections.actionSearchFragmentToMealInfoFragment();
                    action.setMealID(curr.getIdMeal());
                    Navigation.findNavController(v).navigate(action);
                }
            }
        });
        holder.nameView.setText(meals.get(position).getStrMeal());


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


        public ConstraintLayout constraintLayout;

        public CardView cardView;
      //  public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // layout=itemView;
            nameView = itemView.findViewById(R.id.meal_name);
            imageView = itemView.findViewById(R.id.meal_img);
            constraintLayout= itemView.findViewById(R.id.country_view);
            cardView=itemView.findViewById(R.id.card_view);
            }
    }
}

