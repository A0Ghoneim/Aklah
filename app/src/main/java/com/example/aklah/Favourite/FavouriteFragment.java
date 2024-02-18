package com.example.aklah.Favourite;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aklah.Adapters.OnFavouriteClickListener;
import com.example.aklah.Favourite.Presenter.FavouritePresenter;
import com.example.aklah.Favourite.Presenter.FavouritePresenterImp;
import com.example.aklah.Model.Database.MealLocalDataSourceImp;
import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepository;
import com.example.aklah.Model.MealRepositoryImp;
import com.example.aklah.Network.MealRemoteDataSourceImp;
import com.example.aklah.R;
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


public class FavouriteFragment extends Fragment implements FavouriteView, OnFavouriteClickListener {

    FavouritePresenter presenter;
    DatabaseReference dbRefrence;


    FirebaseDatabase dbInstance;

    FavouriteAdapter favouriteAdapter;
    RecyclerView recyclerView;

    ArrayList<Meal> meals;
    Group groupMoskala;
    Group groupTamam;
    int guest;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbInstance=FirebaseDatabase.getInstance("https://aklah-3ba8e-default-rtdb.europe-west1.firebasedatabase.app/");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myapp",0);
        guest=sharedPreferences.getInt("guest",0);
        presenter= new FavouritePresenterImp(this, MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getActivity())));
        presenter.getFavourites();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        groupTamam=view.findViewById(R.id.group_tamam);
        groupMoskala=view.findViewById(R.id.group_moshkla);
        if (guest==1){
            groupTamam.setVisibility(View.GONE);
            groupMoskala.setVisibility(View.VISIBLE);
        }
        recyclerView=view.findViewById(R.id.favouritesRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        if (meals==null){
            meals=new ArrayList<>();
        }
        favouriteAdapter = new FavouriteAdapter(getActivity(),meals,FavouriteFragment.this,1);
        recyclerView.setAdapter(favouriteAdapter);

    }

    @Override
    public void setFavouritesFlowable(Flowable<List<Meal>> flowable) {
            flowable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(prolist -> {meals=new ArrayList<>(prolist); favouriteAdapter.setMeals(meals); favouriteAdapter.notifyDataSetChanged();});
    }

    @Override
    public void onFavMealClick(Meal meal) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            dbRefrence = dbInstance.getReference("Users");
            dbRefrence.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Integer.toString(meal.getMyid())).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getActivity(), "firebase delete", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "firebase fail to delete", Toast.LENGTH_SHORT).show();
                }
            });
        }
        presenter.deleteMeal(meal);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (favouriteAdapter!=null) {
            Log.i("TAG", "onResume: insudeeeee"+meals.size());
            favouriteAdapter.notifyDataSetChanged();
        }
    }

}