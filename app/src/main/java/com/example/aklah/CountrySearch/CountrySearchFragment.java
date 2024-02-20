package com.example.aklah.CountrySearch;

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
import com.example.aklah.CategorySearch.CategorySearchFragmentArgs;
import com.example.aklah.Model.Category;
import com.example.aklah.Model.Country;
import com.example.aklah.R;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CountrySearchFragment extends Fragment {

    ArrayList<Country> countries;
    Observable<Country> observable;
    RecyclerView recyclerView;
    CountryAdapter countryAdapter;
    SearchView searchView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Country> cat = CountrySearchFragmentArgs.fromBundle(getArguments()).getAllCountries().getCountries();
        if (cat != null) {
            countries = new ArrayList<>();
            observable = Observable.fromIterable(cat);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.all_countries_recycle);
        searchView=view.findViewById(R.id.country_search);
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                observable.filter(country -> country.getStrArea().toLowerCase().contains(newText.toLowerCase())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Country>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        countries = new ArrayList<>();
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Country country) {
                        countries.add(country);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        countryAdapter.setCountries(countries);
                        countryAdapter.notifyDataSetChanged();
                    }
                });
                return true;
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Country>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                countries=new ArrayList<>();
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Country country) {
                countries.add(country);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                countryAdapter = new CountryAdapter(getActivity(),countries,R.layout.all_country_list_row,CountryAdapter.FRAGMENT_COUNTRY_SEARCH);
                recyclerView.setAdapter(countryAdapter);
            }
        });
    }
}