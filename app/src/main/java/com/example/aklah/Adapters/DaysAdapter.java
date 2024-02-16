package com.example.aklah.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aklah.Model.Day;
import com.example.aklah.Model.Meal;
import com.example.aklah.R;

import java.util.ArrayList;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {
    Context context;
    OnAddClickListener listener;
    ArrayList<Day> days;

    LinearLayoutManager linearLayoutManager;

    public DaysAdapter(Context context, ArrayList<Day> days,OnAddClickListener listener) {
        this.context=context;
        this.days =days;
        this.listener=listener;
        linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
    }

    @NonNull
    @Override
    public DaysAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.day_list_row,parent,false);
        DaysAdapter.ViewHolder viewHolder = new DaysAdapter.ViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull DaysAdapter.ViewHolder holder, int position) {
        Day curr = days.get(position);
        ArrayList<Meal> meals = new ArrayList<>();
        ArrayList<String> ids = curr.getMealIDs();
        for (int i = 0; i <ids.size(); i++) {
            //meals.add(re)
        }



        holder.nameView.setText(days.get(position).getName());

       // MealAdapter mealAdapter = new MealAdapter(context,)

        //holder.recyclerView.setAdapter();

    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameView;


        public RecyclerView recyclerView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.dayText);
            recyclerView = itemView.findViewById(R.id.sat_day_Recycler);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }
}


