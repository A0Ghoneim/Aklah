package com.example.aklah.MealInfo.View;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aklah.Adapters.RecipeAdapter;
import com.example.aklah.MealInfo.Presenter.MealInfoPresenter;
import com.example.aklah.MealInfo.Presenter.MealInfoPresenterImp;
import com.example.aklah.Model.Database.MealLocalDataSourceImp;
import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepositoryImp;
import com.example.aklah.Network.MealRemoteDataSourceImp;
import com.example.aklah.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.reactivestreams.Subscription;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealInfoFragment extends Fragment implements MealInfoView {
    private static final int CALENDAR_PERMISSION_REQUEST_CODE = 100;


    Meal meal;

    DatabaseReference dbRefrence;


    FirebaseDatabase dbInstance;

    private WebView webView;
    private int savedVideoPosition;
    String videolink;

    MealInfoPresenter presenter;

    ArrayList<String> recipeIngredients;
    ArrayList<String> recipeMeasures;

    TextView mealTextView;
    TextView describtionTextView;
    Button addbtn;
    ImageButton schedulebtn;
    ImageButton planbtn;

    RecyclerView recyclerView;
    private Object lock = new Object();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbInstance=FirebaseDatabase.getInstance("https://aklah-3ba8e-default-rtdb.europe-west1.firebasedatabase.app/");

        Log.i("info", "onCreate: ");
        presenter=new MealInfoPresenterImp(this, MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getActivity())));
        String mealid = MealInfoFragmentArgs.fromBundle(getArguments()).getMealID();
            presenter.getMeal(mealid);
      /*  meal =MealInfoFragmentArgs.fromBundle(getArguments()).getMealData();
        recipeIngredients =new ArrayList<>();
        recipeMeasures = new ArrayList<>();
        ingredientsToList();*/
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


         mealTextView = view.findViewById(R.id.mealName);
         describtionTextView = view.findViewById(R.id.describtionView);


         recyclerView = view.findViewById(R.id.recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        webView = view.findViewById(R.id.videoView);
         addbtn = view.findViewById(R.id.addtofavbtn);
         schedulebtn=view.findViewById(R.id.scheduleButton);
         planbtn=view.findViewById(R.id.planbtn);


    }

    @Override
    public void showMeal(Meal meal) {
        Log.i("TAG", "showMeal: "+meal.getStrMeal());
        this.meal=meal;
        recipeIngredients =new ArrayList<>();
        recipeMeasures = new ArrayList<>();
        ingredientsToList();
        mealTextView.setText(meal.getStrMeal());
        describtionTextView.setText(meal.getStrInstructions());
        planbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronized (lock) {
                    //meal=this.meal;
                    // Create a PopupMenu and inflate the menu resource
                    PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                    popupMenu.inflate(R.menu.overflow_menu);

                    // Set up a listener for menu item clicks
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int itemId = item.getItemId();

                            if (itemId == R.id.saturday_choice) {
                                meal.setDay(1);
                            } else if (itemId == R.id.sunday_choice) {
                                meal.setDay(2);
                            } else if (itemId == R.id.monday_choice) {
                                meal.setDay(3);
                            } else if (itemId == R.id.tuesday_choice) {
                                meal.setDay(4);
                            } else if (itemId == R.id.wednesday_choice) {
                                meal.setDay(5);
                            } else if (itemId == R.id.thursday_choice) {
                                meal.setDay(6);
                            } else if (itemId == R.id.friday_choice) {
                                meal.setDay(7);
                            } else {
                                return false;
                            }

                            String s = meal.getIdMeal() + "" + "0" + meal.getDay();
                            meal.setFavourite(false);
                            meal.setMyid(Integer.parseInt(s));

                            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                dbRefrence = dbInstance.getReference("Users");

                                dbRefrence.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Integer.toString(meal.getMyid())).setValue(meal).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getActivity(), "added to firebase", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            Completable completable = presenter.saveMeal(meal);
                            completable.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new CompletableObserver() {
                                        @Override
                                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                        }

                                        @Override
                                        public void onComplete() {
                                            Toast.makeText(getActivity(), "Added to Plan", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                            Toast.makeText(getActivity(), "Failed to add", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            return true;
                        }
                    });

                    // Show the PopupMenu
                    popupMenu.show();
                }
            }});

        schedulebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCalendarPermissions()) {
                    showDatePickerDialog(meal);
                } else {
                    // Request calendar permissions
                    requestCalendarPermissions();
                }
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronized (lock) {
                    meal.setFavourite(true);
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        dbRefrence = dbInstance.getReference("Users");

                        String s = meal.getIdMeal() + "" + "1" + "0";
                        meal.setMyid(Integer.parseInt(s));
                        dbRefrence.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Integer.toString(meal.getMyid())).setValue(meal).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "added to firebase", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    Completable completable = presenter.saveMeal(meal);
                    completable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                }

                                @Override
                                public void onComplete() {
                                    Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                    //   if (e.getLocalizedMessage())
                                    Single<Meal> flowable = presenter.getMealbyid(meal.getIdMeal());
                                    flowable.subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new SingleObserver<Meal>() {
                                                @Override
                                                public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                                }

                                                @Override
                                                public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Meal meal) {
                                                    Toast.makeText(getActivity(), "Already Added", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                                    Toast.makeText(getActivity(), "Failed to Add", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }
                            });
                }
            }});
        RecipeAdapter recipeAdapter = new RecipeAdapter(getActivity(),recipeIngredients,recipeMeasures);
        recyclerView.setAdapter(recipeAdapter);
        videolink = meal.getStrYoutube().replace("watch?v=","embed/");
        initializeWebView();
        loadVideo(videolink); // Replace with your video URL
    }

    @Override
    public void showerroe(String errormsg) {
        Toast.makeText(getActivity(),"eertyuiop;lkjhgfd",Toast.LENGTH_LONG).show();
    }

    private void showDatePickerDialog(Meal meal) {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog and set the date picker listener
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Handle the selected date
                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        addtocalender(meal,selectedDay,selectedMonth,selectedYear);
                        // You can use the selected date as needed (e.g., display it in a TextView)
                        // textViewSelectedDate.setText(selectedDate);
                        // Alternatively, you can pass the selected date to another method or class
                        // for further processing.
                    }
                },
                year,
                month,
                day
        );

        // Show the date picker dialog
        datePickerDialog.show();
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
private void addtocalender(Meal meal,int day,int month,int year){
  /*  String title = "Meeting with Client";
    String description = "Discuss project details";*/
   /* int year = 2024;  // Set the desired year
    int month = 2;    // Set the desired month (January is 0, February is 1, and so on)
    int day = 15;     // Set the desired day*/

    // Create a Calendar instance and set the year, month, and day
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month, day);

    // Get the time in milliseconds
    long startTimeMillis = calendar.getTimeInMillis();
    long endTimeMillis = startTimeMillis + 3600000; // End time in milliseconds (1 hour duration)

    // Create a new event using ContentValues
    ContentValues values = new ContentValues();
    values.put(CalendarContract.Events.CALENDAR_ID, 1); // Calendar ID (1 for the default calendar)
    values.put(CalendarContract.Events.TITLE, meal.getStrMeal());
    //values.put(CalendarContract.Events.DESCRIPTION, description);
    values.put(CalendarContract.Events.DTSTART, startTimeMillis);
    values.put(CalendarContract.Events.DTEND, endTimeMillis);
    values.put(CalendarContract.Events.EVENT_TIMEZONE, "EET"); // Set the timezone as needed

    // Insert the event using the content resolver
    ContentResolver contentResolver = getActivity().getContentResolver();
    contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);

    // Optionally, open the calendar app to view the newly created event
   // Intent intent = new Intent(Intent.ACTION_VIEW);
   // intent.setData(CalendarContract.Events.CONTENT_URI);
   // startActivity(intent);
    Toast.makeText(getActivity(), "Added to calender", Toast.LENGTH_SHORT).show();
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
    private boolean checkCalendarPermissions() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCalendarPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                CALENDAR_PERMISSION_REQUEST_CODE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CALENDAR_PERMISSION_REQUEST_CODE) {
            // Check if the permissions were granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted, proceed with your code
            } else {
                // Permissions denied, handle accordingly (e.g., show a message or disable features)
            }
        }
    }

}