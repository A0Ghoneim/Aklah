package com.example.aklah;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.aklah.Model.Category;
import com.example.aklah.Model.Country;
import com.example.aklah.Model.Database.MealLocalDataSourceImp;
import com.example.aklah.Model.Ingredient;
import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepositoryImp;
import com.example.aklah.Network.MealRemoteDataSourceImp;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealSearchFragment extends Fragment implements MealSearchView {

MealSearchPresenter presenter;

RecyclerView recyclerView;
    MealAdapter mealAdapter;
SearchView searchView;
ArrayList<Meal> meals;
Observable<Meal> observable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MealSearchPresenterImp(this, MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getActivity())));
        Category category = MealSearchFragmentArgs.fromBundle(getArguments()).getCatrgory();
        Country country = MealSearchFragmentArgs.fromBundle(getArguments()).getCountry();
        Ingredient ingredient = MealSearchFragmentArgs.fromBundle(getArguments()).getIngredirnt();
        if (category!=null){
               presenter.getMealsInCategory(category.getStrCategory());
        } else if (country!=null) {
            presenter.getMealsInCountry(country.getStrArea());
        } else if (ingredient!=null) {
            presenter.getMealsByIngredient(ingredient.getStrIngredient());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_search, container, false);
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.meal_recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        searchView=view.findViewById(R.id.search_view_meal);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                observable.filter(meal -> meal.getStrMeal().toLowerCase().contains(newText.toLowerCase()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Meal>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                    meals=new ArrayList<>();
                            }

                            @Override
                            public void onNext(@NonNull Meal meal) {
                                    meals.add(meal);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                    mealAdapter.setMeals(meals);
                                    mealAdapter.notifyDataSetChanged();
                            }
                        });

                return true;
            }
        });

    }

    @Override
    public void showAllMeals(ArrayList<Meal> meals) {
        this.meals = meals;
         mealAdapter = new MealAdapter(getActivity(),meals);
        recyclerView.setAdapter(mealAdapter);
        observable = Observable.fromIterable(meals);
    }

    @Override
    public void showErrorMsg(String error) {

    }
}