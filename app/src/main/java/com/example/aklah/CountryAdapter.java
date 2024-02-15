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
import com.example.aklah.Model.Country;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    Context context;

    ArrayList<Country> countries;

    public CountryAdapter(Context context,ArrayList<Country> countries) {
        this.context=context;
        this.countries = countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.country_list_row,parent,false);
        CountryAdapter.ViewHolder viewHolder = new CountryAdapter.ViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        holder.nameView.setText(countries.get(position).getStrArea());
        //String link = "https://flagsapi.com/"+countries.get(position)+"/flat/64.png";
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            nameView = itemView.findViewById(R.id.country_name);
            imageView = itemView.findViewById(R.id.country_img);
            constraintLayout= itemView.findViewById(R.id.country_view);

        }
    }
}

