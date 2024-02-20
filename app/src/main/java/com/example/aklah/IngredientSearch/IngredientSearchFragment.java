package com.example.aklah.IngredientSearch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.aklah.Adapters.CategoryAdapter;
import com.example.aklah.Adapters.CountryAdapter;
import com.example.aklah.Adapters.IngredientAdapter;
import com.example.aklah.CountrySearch.CountrySearchFragmentArgs;
import com.example.aklah.Model.Category;
import com.example.aklah.Model.Country;
import com.example.aklah.Model.Ingredient;
import com.example.aklah.R;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class IngredientSearchFragment extends Fragment {

    ArrayList<Ingredient> ingredients;
    Observable<Ingredient> observable;
    RecyclerView recyclerView;
    IngredientAdapter ingredientAdapter;
    SearchView searchView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Ingredient> cat = IngredientSearchFragmentArgs.fromBundle(getArguments()).getAllIngredients().getIngredients();
        if (cat != null) {
            ingredients = new ArrayList<>();
            observable = Observable.fromIterable(cat);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredient_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.all_ingredient_recycle);
        searchView=view.findViewById(R.id.ingredient_search);
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                observable.filter(ingredient -> ingredient.getStrIngredient().toLowerCase().contains(newText.toLowerCase())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Ingredient>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        ingredients = new ArrayList<>();
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Ingredient ingredient) {
                        ingredients.add(ingredient);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        ingredientAdapter.setIngredients(ingredients);
                        ingredientAdapter.notifyDataSetChanged();
                    }
                });
                return true;
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Ingredient>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                ingredients=new ArrayList<>();
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Ingredient ingredient) {
                ingredients.add(ingredient);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                ingredientAdapter = new IngredientAdapter(getActivity(),ingredients,R.layout.all_ingredient_list_row,IngredientAdapter.FRAGMENT_INGREDIENT_SEARCH);
                recyclerView.setAdapter(ingredientAdapter);
            }
        });
    }
}