package com.example.aklah.Adapters;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aklah.CategorySearch.CategorySearchFragmentDirections;
import com.example.aklah.Model.Category;
import com.example.aklah.Model.PojoCategory;
import com.example.aklah.R;
import com.example.aklah.Search.View.SearchFragmentDirections;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<
        CategoryAdapter.ViewHolder> {
    public static final int SAMPLE = 1;
    public static final int ALL = 0;

    public static final int FRAGMENT_SEARCH = 0;

    public static final int FRAGMENT_CATEGORY_SEARCH = 1;

    int fragment;

    private Context context;
    private ArrayList<Category> categories;

    private ArrayList<Category> all;

    int layout;




    public CategoryAdapter(Context context, ArrayList<Category> categories,int size,int layout,int fragment) {
        this.context = context;
        this.categories = categories;
        this.layout=layout;
        this.all=categories;
        this.fragment=fragment;

        if (size==SAMPLE){
             ArrayList<Category> categoriesSample = new ArrayList<>(categories.subList(0,8));
            categoriesSample.add(new Category("99","View More","aaa"));
            this.categories=categoriesSample;
        }
        else if (size==ALL){
            this.categories = categories;        }
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
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
        Category curr = categories.get(position);
        holder.nameView.setText(categories.get(position).getStrCategory());
        if (categories.get(position).getStrCategoryThumb().equals("aaa")){
            holder.imageView.setImageResource(R.drawable.manyyfood_removebg_preview);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        SearchFragmentDirections.ActionSearchFragmentToCategorySearchFragment action = SearchFragmentDirections.actionSearchFragmentToCategorySearchFragment();
                        PojoCategory pojoCategory = new PojoCategory(all);
                        action.setAllCategories(pojoCategory);
                        Navigation.findNavController(v).navigate(action);

                }
            });
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
            float elevationInDp = 12f; // Set your desired elevation value
            float elevationInPixels = context.getResources().getDisplayMetrics().density * elevationInDp;
            holder.cardView.setElevation(elevationInPixels);
            holder.cardView.setRadius(32f);


        }
        else {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fragment==FRAGMENT_SEARCH) {
                        SearchFragmentDirections.ActionSearchFragmentToMealSearchFragment22 actionSearchFragmentToMealSearchFragment22 = SearchFragmentDirections.actionSearchFragmentToMealSearchFragment22();
                   actionSearchFragmentToMealSearchFragment22.setCatrgory(curr);
                    Navigation.findNavController(v).navigate(actionSearchFragmentToMealSearchFragment22);
                    }
                    else if (fragment==FRAGMENT_CATEGORY_SEARCH){
                        CategorySearchFragmentDirections.ActionCategorySearchFragmentToMealSearchFragment2 action = CategorySearchFragmentDirections.actionCategorySearchFragmentToMealSearchFragment2();
                        action.setCatrgory(curr);
                        Navigation.findNavController(v).navigate(action);
                    }
                }
            });
            Glide.with(context).load(categories.get(position).getStrCategoryThumb()).apply(new RequestOptions().override(100, 100)).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameView;
        public ImageView imageView;
        public ConstraintLayout constraintLayout;
        public CardView cardView;
        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            nameView = itemView.findViewById(R.id.meal_name);
            imageView = itemView.findViewById(R.id.meal_img);
            constraintLayout= itemView.findViewById(R.id.category_view);
            cardView=itemView.findViewById(R.id.card_view);

        }
    }
}

