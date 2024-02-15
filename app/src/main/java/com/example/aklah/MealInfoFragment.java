package com.example.aklah;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.aklah.Model.Meal;

import java.util.ArrayList;


public class MealInfoFragment extends Fragment {

    Meal meal;
    private WebView webView;
    private int savedVideoPosition;
    String videolink;

    ArrayList<String> recipeIngredients;
    ArrayList<String> recipeMeasures;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        meal =MealInfoFragmentArgs.fromBundle(getArguments()).getMealData();
        recipeIngredients =new ArrayList<>();
        recipeMeasures = new ArrayList<>();
        ingredientsToList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView mealTextView = view.findViewById(R.id.mealName);
        TextView describtionTextView = view.findViewById(R.id.describtionView);
        mealTextView.setText(meal.getStrMeal());
        describtionTextView.setText(meal.getStrInstructions());

        RecyclerView recyclerView = view.findViewById(R.id.recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecipeAdapter recipeAdapter = new RecipeAdapter(getActivity(),recipeIngredients,recipeMeasures);
        recyclerView.setAdapter(recipeAdapter);
         videolink = meal.getStrYoutube().replace("watch?v=","embed/");
        webView = view.findViewById(R.id.videoView);

        initializeWebView();

        if (savedInstanceState != null) {
            savedVideoPosition = savedInstanceState.getInt("savedVideoPosition");
        }

        loadVideo(videolink); // Replace with your video URL
    }
       /* if (savedInstanceState != null) {
            Bundle webViewBundle = savedInstanceState.getBundle("webViewState");
            if (webViewBundle != null) {
                webView.restoreState(webViewBundle);
            }
        } else {
            // Load your initial URL or content if needed
            String videolink = meal.getStrYoutube().replace("watch?v=","embed/");
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true); // Enable JavaScript

            // Enable video support
            webSettings.setMediaPlaybackRequiresUserGesture(false);

            webView.setWebChromeClient(new WebChromeClient());
            webView.loadData("<iframe width=\"100%\" height=\"100%\" src=\"" + videolink + "\" frameborder=\"0\" allowfullscreen></iframe>", "text/html", "utf-8");
        }
*/

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("savedVideoPosition", savedVideoPosition);
    }

    private void initializeWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
    }
    private void loadVideo(String videoUrl) {
        // Load your video URL into the WebView
        webView.loadData("<iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe>", "text/html", "utf-8");

        // Execute JavaScript to seek to the saved position after the page has finished loading
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.evaluateJavascript("document.querySelector('iframe').contentWindow.postMessage('seekTo=" + savedVideoPosition / 1000.0 + "', '*');", null);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webView.loadData("", "text/html", "utf-8");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        loadVideo(videolink);

    }

    private void ingredientsToList(){
        for (int i = 0; i < 1; i++) {
            if (!meal.getStrIngredient1().equals("")) {
                recipeIngredients.add(meal.getStrIngredient1());
                recipeMeasures.add(meal.getStrMeasure1());
            }
            else {
                break;
            }
            if (!meal.getStrIngredient2().equals("")) {
                recipeIngredients.add(meal.getStrIngredient2());
                recipeMeasures.add(meal.getStrMeasure2());            }
            else {
                break;
            }
            if (!meal.getStrIngredient3().equals("")) {
                recipeIngredients.add(meal.getStrIngredient3());
                recipeMeasures.add(meal.getStrMeasure3());
            }
            else {
                break;
            }
            if (!meal.getStrIngredient4().equals("")) {
                recipeIngredients.add(meal.getStrIngredient4());
                recipeMeasures.add(meal.getStrMeasure4());            }
            else {
                break;
            }
            if (!meal.getStrIngredient5().equals("")) {
                recipeIngredients.add(meal.getStrIngredient5());
                recipeMeasures.add(meal.getStrMeasure5());            }
            else {
                break;
            }
            if (!meal.getStrIngredient6().equals("")) {
                recipeIngredients.add(meal.getStrIngredient6());
                recipeMeasures.add(meal.getStrMeasure6());            }
            else {
                break;
            }
            // coo
            if (!meal.getStrIngredient7().equals("")) {
                recipeIngredients.add(meal.getStrIngredient7());
                recipeMeasures.add(meal.getStrMeasure7());            }
            else {
                break;
            }
            if (!meal.getStrIngredient8().equals("")) {
                recipeIngredients.add(meal.getStrIngredient8());
                recipeMeasures.add(meal.getStrMeasure8());            }
            else {
                break;
            }
            if (!meal.getStrIngredient9().equals("")) {
                recipeIngredients.add(meal.getStrIngredient9());
                recipeMeasures.add(meal.getStrMeasure9());            }
            else {
                break;
            }
            if (!meal.getStrIngredient10().equals("")) {
                recipeIngredients.add(meal.getStrIngredient10());
                recipeMeasures.add(meal.getStrMeasure10());            }
            else {
                break;
            }
            if (!meal.getStrIngredient11().equals("")) {
                recipeIngredients.add(meal.getStrIngredient11());
                recipeMeasures.add(meal.getStrMeasure11());            }
            else {
                break;
            }
            if (!meal.getStrIngredient12().equals("")) {
                recipeIngredients.add(meal.getStrIngredient12());
                recipeMeasures.add(meal.getStrMeasure12());            }
            else {
                break;
            }
            if (!meal.getStrIngredient13().equals("")) {
                recipeIngredients.add(meal.getStrIngredient13());
                recipeMeasures.add(meal.getStrMeasure13());            }
            else {
                break;
            }
            if (!meal.getStrIngredient14().equals("")) {
                recipeIngredients.add(meal.getStrIngredient14());
                recipeMeasures.add(meal.getStrMeasure14());            }
            else {
                break;
            }
            if (!meal.getStrIngredient15().equals("")) {
                recipeIngredients.add(meal.getStrIngredient15());
                recipeMeasures.add(meal.getStrMeasure15());            }
            else {
                break;
            }
            if (!meal.getStrIngredient16().equals("")) {
                recipeIngredients.add(meal.getStrIngredient16());
                recipeMeasures.add(meal.getStrMeasure16());            }
            else {
                break;
            }
            if (!meal.getStrIngredient17().equals("")) {
                recipeIngredients.add(meal.getStrIngredient17());
                recipeMeasures.add(meal.getStrMeasure17());            }
            else {
                break;
            }
            if (!meal.getStrIngredient18().equals("")) {
                recipeIngredients.add(meal.getStrIngredient18());
                recipeMeasures.add(meal.getStrMeasure18());            }
            else {
                break;
            }
            if (!meal.getStrIngredient19().equals("")) {
                recipeIngredients.add(meal.getStrIngredient19());
                recipeMeasures.add(meal.getStrMeasure19());            }
            else {
                break;
            }
            if (!meal.getStrIngredient20().equals("")) {
                recipeIngredients.add(meal.getStrIngredient20());
                recipeMeasures.add(meal.getStrMeasure20());            }
            else {
                break;
            }
        }

    }
}