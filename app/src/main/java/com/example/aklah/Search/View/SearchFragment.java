package com.example.aklah.Search.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aklah.Adapters.CategoryAdapter;
import com.example.aklah.Adapters.CountryAdapter;
import com.example.aklah.Adapters.IngredientAdapter;
import com.example.aklah.Model.Category;
import com.example.aklah.Model.Country;
import com.example.aklah.Model.Database.MealLocalDataSourceImp;
import com.example.aklah.Model.Ingredient;
import com.example.aklah.Model.MealRepositoryImp;
import com.example.aklah.Network.MealRemoteDataSourceImp;
import com.example.aklah.R;
import com.example.aklah.Search.Presenter.SearchPresenter;
import com.example.aklah.Search.Presenter.SearchPresenterImp;

import java.util.ArrayList;


public class SearchFragment extends Fragment implements MySearchView {

    SearchPresenter searchPresenter;

    RecyclerView categoryRecyclerView;

    RecyclerView countryRecyclerView;

    RecyclerView ingredientRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        searchPresenter = new SearchPresenterImp(this, MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getActivity())));
        searchPresenter.getCategories();
        searchPresenter.getCountries();
        searchPresenter.getIngredients();
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryRecyclerView=view.findViewById(R.id.recyclerView);
        countryRecyclerView=view.findViewById(R.id.recycler_countries);
        ingredientRecyclerView =view.findViewById(R.id.ingredients_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.HORIZONTAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(),2,GridLayoutManager.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);


        //  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
      //  linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(gridLayoutManager);
        countryRecyclerView.setLayoutManager(gridLayoutManager2);
        ingredientRecyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void getCategories(ArrayList<Category> categories) {
       // ArrayList<Category> categoriesSample = new ArrayList<>(categories.subList(0,8));
        //categoriesSample.add(new Category("99","View More","aaa"));
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int hpixels = (int) (50*categories.size() * scale + 0.5f);
        int wpixels = (int) (390 * scale + 0.5f);
       // categoryRecyclerView.setLayoutParams(new RecyclerView.LayoutParams(wpixels,hpixels));
        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(),categories,CategoryAdapter.SAMPLE,R.layout.category_list_row,CategoryAdapter.FRAGMENT_SEARCH);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void getCountries(ArrayList<Country> countries) {
        ArrayList<Country> codedCountries = addcodes(countries);
        codedCountries.removeIf(country -> country.getStrArea().equals("Unknown"));
        ArrayList<Country> countrySample = new ArrayList<>(codedCountries.subList(0,10));
        CountryAdapter countryAdapter = new CountryAdapter(getActivity(),countrySample);
        countryRecyclerView.setAdapter(countryAdapter);
    }

    @Override
    public void getIngredients(ArrayList<Ingredient> ingredients) {
        ArrayList<Ingredient> ingredientsSample = new ArrayList<>(ingredients.subList(0,10));
        IngredientAdapter ingredientAdapter = new IngredientAdapter(getActivity(),ingredientsSample);
        ingredientRecyclerView.setAdapter(ingredientAdapter);

    }

    private ArrayList<Country> addcodes(ArrayList<Country> countries) {
        for (int i = 0; i < countries.size(); i++) {
            Country curr = countries.get(i);
            switch (curr.getStrArea()){
                case "American":
                    curr.setCode("us");
                    break;
                case "British":
                    curr.setCode("gb");
                    break;
                case "Canadian":
                    curr.setCode("ca");
                    break;
                case "Chinese":
                    curr.setCode("cn");
                    break;
                case "Croatian":
                    curr.setCode("hr");
                    break;
                case "Dutch":
                    curr.setCode("de");
                    break;
                case "Egyptian":
                    curr.setCode("eg");
                    break;
                case "Filipino":
                    curr.setCode("ph");
                    break;
                case "French":
                    curr.setCode("fr");
                    break;
                case "Greek":
                    curr.setCode("gr");
                    break;
                case "Indian":
                    curr.setCode("in");
                    break;
                case "Irish":
                    curr.setCode("ie");
                    break;
                case "Italian":
                    curr.setCode("it");
                    break;
                case "Jamaican":
                    curr.setCode("jm");
                    break;
                case "Japanese":
                    curr.setCode("jp");
                    break;
                case "Kenyan":
                    curr.setCode("ke");
                    break;
                case "Malaysian":
                    curr.setCode("my");
                    break;
                case "Mexican":
                    curr.setCode("mx");
                    break;
                case "Moroccan":
                    curr.setCode("ma");
                    break;
                case "Polish":
                    curr.setCode("pl");
                    break;
                case "Portuguese":
                    curr.setCode("pt");
                    break;
                case "Russian":
                    curr.setCode("ru");
                    break;
                case "Spanish":
                    curr.setCode("es");
                    break;
                case "Thai":
                    curr.setCode("th");
                    break;
                case "Tunisian":
                    curr.setCode("tn");
                    break;
                case "Turkish":
                    curr.setCode("tr");
                    break;
                case "Vietnamese":
                    curr.setCode("vn");
            }
        }
        return countries;
    }

    @Override
    public void showErrorMsg(String error) {

    }
}