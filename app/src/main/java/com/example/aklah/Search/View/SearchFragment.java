package com.example.aklah.Search.View;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.aklah.Adapters.CategoryAdapter;
import com.example.aklah.Adapters.CountryAdapter;
import com.example.aklah.Adapters.IngredientAdapter;
import com.example.aklah.Adapters.MealAdapter;
import com.example.aklah.InternetCheckTask;
import com.example.aklah.Model.Category;
import com.example.aklah.Model.Country;
import com.example.aklah.Model.Database.MealLocalDataSourceImp;
import com.example.aklah.Model.Ingredient;
import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepositoryImp;
import com.example.aklah.Model.PojoCountry;
import com.example.aklah.Model.PojoIng;
import com.example.aklah.Network.MealRemoteDataSourceImp;
import com.example.aklah.R;
import com.example.aklah.Search.Presenter.SearchPresenter;
import com.example.aklah.Search.Presenter.SearchPresenterImp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class SearchFragment extends Fragment implements MySearchView {
    ImageView sticker;
    TextView network;

    ScrollView scrollView;

    SearchView searchView;

    SearchPresenter searchPresenter;

    RecyclerView categoryRecyclerView;

    RecyclerView countryRecyclerView;
    RecyclerView allMealsRecycle;
    MealAdapter mealAdapter;
    ArrayList<Meal> allMealslist;

    PojoCountry pojoCountry;
    PojoIng pojoIng;

    RecyclerView ingredientRecyclerView;

    TextView allCountriesText;
    TextView allIngredientsText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allMealslist=new ArrayList<>();
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
        sticker=view.findViewById(R.id.imageView3);
        network=view.findViewById(R.id.textView5);
        scrollView=view.findViewById(R.id.scrollView2);
        searchView=view.findViewById(R.id.searchView);
        allMealsRecycle=view.findViewById(R.id.allmeals_recycler);
        categoryRecyclerView=view.findViewById(R.id.recyclerView);
        countryRecyclerView=view.findViewById(R.id.recycler_countries);
        ingredientRecyclerView =view.findViewById(R.id.ingredients_recycler);
        allCountriesText=view.findViewById(R.id.allCountriesText);
        allIngredientsText=view.findViewById(R.id.allIngredientsText);
        InternetCheckTask internetCheckTask = new InternetCheckTask(new InternetCheckTask.InternetCheckListener() {
            @Override
            public void onInternetCheckResult(boolean isInternetAvailable) {
                if (!isInternetAvailable) {
                    scrollView.setVisibility(View.GONE);
                    searchView.setVisibility(View.GONE);
                    sticker.setVisibility(View.VISIBLE);
                    network.setVisibility(View.VISIBLE);
                }
            }
        });

        internetCheckTask.execute();


        allCountriesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragmentDirections.ActionSearchFragmentToCountrySearchFragment action = SearchFragmentDirections.actionSearchFragmentToCountrySearchFragment();
                action.setAllCountries(pojoCountry);
                Navigation.findNavController(v).navigate(action);
            }
        });

        allIngredientsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragmentDirections.ActionSearchFragmentToIngredientSearchFragment action = SearchFragmentDirections.actionSearchFragmentToIngredientSearchFragment();
                action.setAllIngredients(pojoIng);
                Navigation.findNavController(v).navigate(action);
            }
        });


//        if (!connectionCheck()){
//            scrollView.setVisibility(View.GONE);
//            searchView.setVisibility(View.GONE);
//            sticker.setVisibility(View.VISIBLE);
//            network.setVisibility(View.VISIBLE);
//        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")){
                    searchEnd();
                }
                else {
                    searchStart();
                    searchPresenter.getMealsThatContain(newText);
                }
                return false;
            }
        });
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
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);


        //  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
      //  linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(gridLayoutManager);
        countryRecyclerView.setLayoutManager(gridLayoutManager2);
        ingredientRecyclerView.setLayoutManager(linearLayoutManager);
        allMealsRecycle.setLayoutManager(gridLayoutManager3);
        mealAdapter = new MealAdapter(getActivity(),allMealslist,MealAdapter.FRAGMENT_SEARCH);
        allMealsRecycle.setAdapter(mealAdapter);

    }

    private void searchStart() {
        scrollView.setVisibility(View.GONE);
        allMealsRecycle.setVisibility(View.VISIBLE);
    }

    private void searchEnd() {
        scrollView.setVisibility(View.VISIBLE);
        allMealsRecycle.setVisibility(View.GONE);
    }

    @Override
    public void getCategories(ArrayList<Category> categories) {
       // ArrayList<Category> categoriesSample = new ArrayList<>(categories.subList(0,8));
        //categoriesSample.add(new Category("99","View More","aaa"));
       /* final float scale = getContext().getResources().getDisplayMetrics().density;
        int hpixels = (int) (50*categories.size() * scale + 0.5f);
        int wpixels = (int) (390 * scale + 0.5f);*/
       // categoryRecyclerView.setLayoutParams(new RecyclerView.LayoutParams(wpixels,hpixels));
        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(),categories,CategoryAdapter.SAMPLE,R.layout.category_list_row,CategoryAdapter.FRAGMENT_SEARCH);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void getCountries(ArrayList<Country> countries) {
        pojoCountry=new PojoCountry(countries);
        ArrayList<Country> codedCountries = addcodes(countries);
        codedCountries.removeIf(country -> country.getStrArea().equals("Unknown"));
        ArrayList<Country> countrySample = new ArrayList<>(codedCountries.subList(0,10));
        CountryAdapter countryAdapter = new CountryAdapter(getActivity(),countrySample,R.layout.country_list_row,CountryAdapter.FRAGMENT_SEARCH);
        countryRecyclerView.setAdapter(countryAdapter);
        allCountriesText.setVisibility(View.VISIBLE);
    }

    @Override
    public void getIngredients(ArrayList<Ingredient> ingredients) {
        pojoIng=new PojoIng(ingredients);
        ArrayList<Ingredient> ingredientsSample = new ArrayList<>(ingredients.subList(0,10));
        IngredientAdapter ingredientAdapter = new IngredientAdapter(getActivity(),ingredientsSample,R.layout.ingredient_list_row,IngredientAdapter.FRAGMENT_SEARCH);
        ingredientRecyclerView.setAdapter(ingredientAdapter);
        allIngredientsText.setVisibility(View.VISIBLE);
    }

    @Override
    public void setAllMeals(ArrayList<Meal> meals) {
        if (meals==null){
            allMealslist=new ArrayList<>();
        }
        else {
            allMealslist=meals;
        }
        mealAdapter.setMeals(allMealslist);
        mealAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMsg(String error) {

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

//    boolean connectionCheck(){
//        if (NetworkIsConnected()&&InternetIsConnected()){
//            return true;
//        }
//        return false;
//    }
    private boolean NetworkIsConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    private boolean InternetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}
