package com.example.aklah;

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
import com.example.aklah.Model.Category;
import com.example.aklah.Model.PojoCategory;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<
        CategoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Category> categories;

    private ArrayList<Category> all;

    int layout;

    private int size;



    public CategoryAdapter(Context context, ArrayList<Category> categories,int size,int layout) {
        this.context = context;
        this.categories = categories;
        this.layout=layout;
        this.all=categories;

        if (size==1){
             ArrayList<Category> categoriesSample = new ArrayList<>(categories.subList(0,8));
            categoriesSample.add(new Category("99","View More","aaa"));
            this.categories=categoriesSample;
        }
        else {
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
                    PojoCategory pojoCategory=new PojoCategory(all);
                         action.setAllCategories(pojoCategory);
                    Navigation.findNavController(v).navigate(action);
                }
            });

        }
        else {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   SearchFragmentDirections.ActionSearchFragmentToMealSearchFragment22 actionSearchFragmentToMealSearchFragment22 = SearchFragmentDirections.actionSearchFragmentToMealSearchFragment22();
                   actionSearchFragmentToMealSearchFragment22.setCatrgory(curr);
                    Navigation.findNavController(v).navigate(actionSearchFragmentToMealSearchFragment22);
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

