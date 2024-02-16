package com.example.aklah.MealSearch.View;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.aklah.Adapters.MealAdapter;
import com.example.aklah.MealSearch.Presenter.MealSearchPresenter;
import com.example.aklah.MealSearch.Presenter.MealSearchPresenterImp;
import com.example.aklah.Model.Category;
import com.example.aklah.Model.Country;
import com.example.aklah.Model.Database.MealLocalDataSourceImp;
import com.example.aklah.Model.Ingredient;
import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepositoryImp;
import com.example.aklah.Network.MealRemoteDataSourceImp;
import com.example.aklah.R;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealSearchFragment extends Fragment implements MealSearchView {
    private static final String KEY_OBJECT_LIST = "objectList";


    MealSearchPresenter presenter;

RecyclerView recyclerView;
    MealAdapter mealAdapter;
SearchView searchView;
ArrayList<Meal> meals;
Observable<Meal> observable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meals=new ArrayList<>();
        observable=Observable.fromIterable(meals);
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
        mealAdapter = new MealAdapter(getActivity(),meals,MealAdapter.FRAGMENT_MEAL_SEARCH);
        recyclerView.setAdapter(mealAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals(""))return false ;
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
         mealAdapter.setMeals(meals);
         mealAdapter.notifyDataSetChanged();
        observable = Observable.fromIterable(meals);
    }

    @Override
    public void showErrorMsg(String error) {

    }

    @Override
    public void onSaveInstanceState(@androidx.annotation.NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("TAG x", "onSaveInstanceState: ");
        outState.putSerializable(KEY_OBJECT_LIST, meals);

    }

    @Override
    public void onDestroy() {
        Log.i("TAG x", "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState!=null){
            meals= (ArrayList<Meal>) savedInstanceState.getSerializable(KEY_OBJECT_LIST);
        }
       // Log.i("TAG x", "onViewStateRestored: "+meals.size());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("TAG x", "onPause: ");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("TAG x", "onStop: ");


    }

    @Override
    public void onResume() {
        super.onResume();
        if (mealAdapter!=null) {
            Log.i("TAG", "onResume: insudeeeee"+meals.size());
            mealAdapter.notifyDataSetChanged();
        }
        Log.i("TAG x", "onResume: ");
    }
}