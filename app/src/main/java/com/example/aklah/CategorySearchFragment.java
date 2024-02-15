package com.example.aklah;

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

import com.example.aklah.Model.Category;
import com.google.common.util.concurrent.AbstractScheduledService;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategorySearchFragment extends Fragment {

    ArrayList<Category> categories;
    Observable<Category> observable;
RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
SearchView searchView;
 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Category> cat = CategorySearchFragmentArgs.fromBundle(getArguments()).getAllCategories().getCategories();
        if (cat != null) {
          categories = new ArrayList<>();
            observable = Observable.fromIterable(cat);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.all_categories_recycle);
        searchView=view.findViewById(R.id.category_search);
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                observable.filter(category -> category.getStrCategory().toLowerCase().contains(newText.toLowerCase())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Category>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        categories = new ArrayList<>();
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Category category) {
                            categories.add(category);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        categoryAdapter.setCategories(categories);
                            categoryAdapter.notifyDataSetChanged();
                    }
                });
                return true;
                }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Category>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                categories=new ArrayList<>();
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Category category) {
                    categories.add(category);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                categoryAdapter = new CategoryAdapter(getActivity(),categories,0,R.layout.all_category_list_row);
                    recyclerView.setAdapter(categoryAdapter);
            }
        });
    }
}