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
import com.example.aklah.CategorySearch.CategorySearchFragmentDirections;
import com.example.aklah.CountrySearch.CountrySearchFragmentDirections;
import com.example.aklah.Model.Category;
import com.example.aklah.Model.Country;
import com.example.aklah.R;
import com.example.aklah.Search.View.SearchFragmentDirections;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {


    public static final int FRAGMENT_SEARCH = 0;

    public static final int FRAGMENT_COUNTRY_SEARCH = 1;

    int fragment;


    int layout;

    Context context;

    ArrayList<Country> countries;

    private ArrayList<Country> all;

    public CountryAdapter(Context context,ArrayList<Country> countries,int layout,int fragment) {
        this.context = context;
        this.countries = countries;
        this.layout=layout;
        this.fragment = fragment;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(layout,parent,false);
        CountryAdapter.ViewHolder viewHolder = new CountryAdapter.ViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        Country curr = countries.get(position);
        holder.nameView.setText(countries.get(position).getStrArea());
        //String link = "https://flagsapi.com/"+countries.get(position)+"/flat/64.png";
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment==FRAGMENT_SEARCH) {
                    SearchFragmentDirections.ActionSearchFragmentToMealSearchFragment22 actionSearchFragmentToMealSearchFragment22 = SearchFragmentDirections.actionSearchFragmentToMealSearchFragment22();
                    actionSearchFragmentToMealSearchFragment22.setCountry(curr);
                    Navigation.findNavController(v).navigate(actionSearchFragmentToMealSearchFragment22);
                }
                else if (fragment==FRAGMENT_COUNTRY_SEARCH){
                    CountrySearchFragmentDirections.ActionCountrySearchFragmentToMealSearchFragment2 action = CountrySearchFragmentDirections.actionCountrySearchFragmentToMealSearchFragment2();
                    action.setCountry(curr);
                    Navigation.findNavController(v).navigate(action);
                }
            }
        });


        String link = "https://flagcdn.com/w160/"+countries.get(position).getCode()+".png";
        Glide.with(context).load(link).apply(new RequestOptions().override(100,50)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return countries.size();
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
            nameView = itemView.findViewById(R.id.country_name);
            imageView = itemView.findViewById(R.id.country_img);
            constraintLayout= itemView.findViewById(R.id.country_view);
            cardView=itemView.findViewById(R.id.country_card);

        }
    }
}

