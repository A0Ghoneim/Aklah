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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aklah.CountrySearch.CountrySearchFragmentDirections;
import com.example.aklah.IngredientSearch.IngredientSearchFragmentDirections;
import com.example.aklah.Model.Category;
import com.example.aklah.Model.Ingredient;
import com.example.aklah.R;
import com.example.aklah.Search.View.SearchFragmentDirections;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<
        IngredientAdapter.ViewHolder> {



    public static final int FRAGMENT_SEARCH = 0;

    public static final int FRAGMENT_INGREDIENT_SEARCH = 1;

    int layout;
    int fragment;
    private Context context;
    private ArrayList<Ingredient> ingredients;

    private ArrayList<Ingredient> all;



    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients,int layout,int fragment) {
        this.context = context;
        this.ingredients = ingredients;
        this.layout=layout;
        this.fragment=fragment;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient curr = ingredients.get(position);
        holder.nameView.setText(ingredients.get(position).getStrIngredient());
        String link = "https://www.themealdb.com/images/ingredients/"+ingredients.get(position).getStrIngredient()+".png";

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment==FRAGMENT_SEARCH) {
                    SearchFragmentDirections.ActionSearchFragmentToMealSearchFragment22 actionSearchFragmentToMealSearchFragment22 = SearchFragmentDirections.actionSearchFragmentToMealSearchFragment22();
                    actionSearchFragmentToMealSearchFragment22.setIngredirnt(curr);
                    Navigation.findNavController(v).navigate(actionSearchFragmentToMealSearchFragment22);
                }
                else if (fragment==FRAGMENT_INGREDIENT_SEARCH){
                    IngredientSearchFragmentDirections.ActionIngredientSearchFragmentToMealSearchFragment2 action = IngredientSearchFragmentDirections.actionIngredientSearchFragmentToMealSearchFragment2();
                    action.setIngredirnt(curr);
                    Navigation.findNavController(v).navigate(action);
                }
            }
        });
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
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            nameView = itemView.findViewById(R.id.ingredient_name);
            imageView = itemView.findViewById(R.id.ingredient_img);
            constraintLayout= itemView.findViewById(R.id.ingredient_view);
            cardView=itemView.findViewById(R.id.ingredient_card);

        }
    }
}

