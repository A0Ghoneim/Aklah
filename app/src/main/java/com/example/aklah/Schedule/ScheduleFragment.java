package com.example.aklah.Schedule;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aklah.Adapters.OnFavouriteClickListener;
import com.example.aklah.Adapters.FavouriteAdapter;
import com.example.aklah.Model.Database.MealLocalDataSourceImp;
import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepositoryImp;
import com.example.aklah.Network.MealRemoteDataSourceImp;
import com.example.aklah.R;
import com.example.aklah.Schedule.Presenter.SchedulePresenter;
import com.example.aklah.Schedule.Presenter.SchedulePresenterImp;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ScheduleFragment extends Fragment implements ScheduleView, OnFavouriteClickListener {

    SchedulePresenter presenter;

    DatabaseReference dbRefrence;

    FirebaseDatabase dbInstance;

    Group groupMoskala;
    Group groupTamam;

    int guest;
    ArrayList<Meal> satmeals;
    ArrayList<Meal> sunmeals;
    ArrayList<Meal> monmeals;
    ArrayList<Meal> tuemeals;
    ArrayList<Meal> wedmeals;
    ArrayList<Meal> thumeals;
    ArrayList<Meal> frimeals;

    RecyclerView satrecycle;
    RecyclerView sunrecycle;
    RecyclerView monrecycle;
    RecyclerView tuerecycle;
    RecyclerView wedrecycle;
    RecyclerView thurecycle;
    RecyclerView frirecycle;

    FavouriteAdapter satAdapter;
    FavouriteAdapter sunAdapter;
    FavouriteAdapter monAdapter;
    FavouriteAdapter tueAdapter;
    FavouriteAdapter wedAdapter;
    FavouriteAdapter thuAdapter;
    FavouriteAdapter friAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myapp",0);
        guest=sharedPreferences.getInt("guest",0);
        dbInstance= FirebaseDatabase.getInstance("https://aklah-3ba8e-default-rtdb.europe-west1.firebasedatabase.app/");
        presenter=new SchedulePresenterImp(this, MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getActivity())));
        satmeals=new ArrayList<>();
        sunmeals=new ArrayList<>();
        monmeals=new ArrayList<>();
        tuemeals=new ArrayList<>();
        wedmeals=new ArrayList<>();
        thumeals=new ArrayList<>();
        frimeals=new ArrayList<>();

        presenter.getSatdayMeals();
        presenter.getSundayMeals();
        presenter.getMondayMeals();
        presenter.getTuesdayMeals();
        presenter.getWednesdayMeals();
        presenter.getThursdayMeals();
        presenter.getFridayMeals();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        groupTamam=view.findViewById(R.id.group_tmam);
        groupMoskala=view.findViewById(R.id.group_moshkla);
        if (guest==1) {
            groupTamam.setVisibility(View.GONE);
            groupMoskala.setVisibility(View.VISIBLE);
        }
        satrecycle=view.findViewById(R.id.sat_day_Recycler);
        sunrecycle=view.findViewById(R.id.sun_day_Recycler);
        monrecycle=view.findViewById(R.id.mon_day_Recycler);
        tuerecycle=view.findViewById(R.id.tue_day_Recycler);
        wedrecycle=view.findViewById(R.id.wed_day_Recycler);
        thurecycle=view.findViewById(R.id.thu_day_Recycler);
        frirecycle=view.findViewById(R.id.fri_day_Recycler);

        LinearLayoutManager satlinearLayoutManagers = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager sunlinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager monlinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager tuelinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager wedlinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager thulinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager frilinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        satrecycle.setLayoutManager(satlinearLayoutManagers);
        sunrecycle.setLayoutManager(sunlinearLayoutManager);
        monrecycle.setLayoutManager(monlinearLayoutManager);
        tuerecycle.setLayoutManager(tuelinearLayoutManager);
        wedrecycle.setLayoutManager(wedlinearLayoutManager);
        thurecycle.setLayoutManager(thulinearLayoutManager);
        frirecycle.setLayoutManager(frilinearLayoutManager);

         satAdapter = new FavouriteAdapter(getActivity(),satmeals,this);
         sunAdapter = new FavouriteAdapter(getActivity(),sunmeals,this);
         monAdapter = new FavouriteAdapter(getActivity(),monmeals,this);
         tueAdapter = new FavouriteAdapter(getActivity(),tuemeals,this);
         wedAdapter = new FavouriteAdapter(getActivity(),wedmeals,this);
         thuAdapter = new FavouriteAdapter(getActivity(),thumeals,this);
         friAdapter = new FavouriteAdapter(getActivity(),frimeals,this);

        satrecycle.setAdapter(satAdapter);
        sunrecycle.setAdapter(sunAdapter);
        monrecycle.setAdapter(monAdapter);
        tuerecycle.setAdapter(tueAdapter);
        wedrecycle.setAdapter(wedAdapter);
        thurecycle.setAdapter(thuAdapter);
        frirecycle.setAdapter(friAdapter);


    }

    @Override
    public void setSaturdayFlowable(Flowable<List<Meal>> flowable) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {satmeals=new ArrayList<>(meals); satAdapter.setMeals(satmeals); satAdapter.notifyDataSetChanged(); },throwable -> Log.e("TAG", "setSaturdayFlowable: ",throwable ));
    }

    @Override
    public void setSundayFlowable(Flowable<List<Meal>> flowable) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {sunmeals=new ArrayList<>(meals); sunAdapter.setMeals(sunmeals); sunAdapter.notifyDataSetChanged(); },throwable -> Log.e("TAG", "setSaturdayFlowable: ",throwable ));
    }

    @Override
    public void setMondayFlowable(Flowable<List<Meal>> flowable) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {monmeals=new ArrayList<>(meals); monAdapter.setMeals(monmeals); monAdapter.notifyDataSetChanged(); },throwable -> Log.e("TAG", "setSaturdayFlowable: ",throwable ));
    }

    @Override
    public void setTuesdayFlowable(Flowable<List<Meal>> flowable) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {tuemeals=new ArrayList<>(meals); tueAdapter.setMeals(tuemeals); tueAdapter.notifyDataSetChanged(); },throwable -> Log.e("TAG", "setSaturdayFlowable: ",throwable ));
    }

    @Override
    public void setWednesdayFlowable(Flowable<List<Meal>> flowable) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {wedmeals=new ArrayList<>(meals); wedAdapter.setMeals(wedmeals); wedAdapter.notifyDataSetChanged(); },throwable -> Log.e("TAG", "setSaturdayFlowable: ",throwable ));
    }

    @Override
    public void setThursdayFlowable(Flowable<List<Meal>> flowable) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {thumeals=new ArrayList<>(meals); thuAdapter.setMeals(thumeals); thuAdapter.notifyDataSetChanged(); },throwable -> Log.e("TAG", "setSaturdayFlowable: ",throwable ));
    }

    @Override
    public void setFridayFlowable(Flowable<List<Meal>> flowable) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {frimeals=new ArrayList<>(meals); friAdapter.setMeals(frimeals); friAdapter.notifyDataSetChanged(); },throwable -> Log.e("TAG", "setSaturdayFlowable: ",throwable ));
    }

    @Override
    public void onFavMealClick(Meal meal) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            dbRefrence = dbInstance.getReference("Users");
            dbRefrence.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Integer.toString(meal.getMyid())).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                  //  Toast.makeText(getActivity(), "firebase delete", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                 //   Toast.makeText(getActivity(), "firebase fail to delete", Toast.LENGTH_SHORT).show();
                }
            });
        }
       // presenter.deletesavedmeal(meal.getIdMeal(),meal.getDay());
        presenter.deleteMeal(meal);
    }
}