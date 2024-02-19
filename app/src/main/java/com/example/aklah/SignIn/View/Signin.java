package com.example.aklah.SignIn.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aklah.MainActivity;
import com.example.aklah.Model.Database.MealLocalDataSourceImp;
import com.example.aklah.Model.Meal;
import com.example.aklah.Model.MealRepositoryImp;
import com.example.aklah.Network.MealRemoteDataSourceImp;
import com.example.aklah.R;
import com.example.aklah.SignIn.Presenter.SignInPresenter;
import com.example.aklah.SignIn.Presenter.SignInPresenterImp;
import com.example.aklah.SignupActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Signin extends AppCompatActivity {

    SignInPresenter presenter;

    ConstraintLayout back;
    FirebaseAuth auth;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleImage;

    // ImageView facevookImage;
    DatabaseReference dbRefrence;


    FirebaseDatabase dbInstance;

    TextView signup;

    TextView guest;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SignInPresenterImp(MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(this)));


       dbInstance=FirebaseDatabase.getInstance("https://aklah-3ba8e-default-rtdb.europe-west1.firebasedatabase.app/");

        setContentView(R.layout.activity_signin);
        back=findViewById(R.id.back);
       // back.getBackground().setAlpha(200);
        EditText editText = findViewById(R.id.editTextText);
        EditText passEdit = findViewById(R.id.editTextTextPassword);
        googleImage=findViewById(R.id.imageView2);
       // facevookImage=findViewById(R.id.facebookimg);
        signup=findViewById(R.id.sign_up);
        guest=findViewById(R.id.guestText);
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToOtherActivity(1);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signin.this, SignupActivity.class);
                startActivity(intent);
            }
        });



       /* ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder().build();
        FirebaseAuth.getInstance().sendSignInLinkToEmail(email,actionCodeSettings);

        if (FirebaseAuth.getInstance().isSignInWithEmailLink(email)){
            FirebaseAuth.getInstance().signInWithEmailLink()
        }
        */

       gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
               .requestEmail().build();
       gsc= GoogleSignIn.getClient(this,gso);

        auth=FirebaseAuth.getInstance();



       /* BeginSignInRequest signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build());*/

      /*  facevookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookSignIn();
            }
        });*/

       googleImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               signIn();
           }
       });




        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =editText.getText().toString();
                String password = passEdit.getText().toString();
                if (email.equals("")||password.equals("")){
                    Log.i("NULLLL", "onClick: ");
                    Toast.makeText(Signin.this, "Enter a valid email or passord", Toast.LENGTH_SHORT).show();
                    return;
                }
                //FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password);
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            dbRefrence = dbInstance.getReference("Users");
                            dbRefrence.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    //    ArrayList<Meal> meallist = new ArrayList<>();

                                    for (DataSnapshot mealSnapshot : snapshot.getChildren()) {
                                        // For each person object in the list

                                        Meal meal = mealSnapshot.getValue(Meal.class);
                                        Log.i("TAG", "onDataChange: "+meal.getStrMeal());
                                        presenter.insert(meal).subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new CompletableObserver() {
                                                    @Override
                                                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                                    }

                                                    @Override
                                                    public void onComplete() {

                                                    }

                                                    @Override
                                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                                                    }
                                                });
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            navigateToOtherActivity(0);
                        }
                        else {
                            Toast.makeText(Signin.this, "Failed to sign in\n"+"check email and password", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseauth(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, "failed to login", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onActivityResult: ",e );
            }
        } else if (requestCode==2000) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseauth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            dbRefrence = dbInstance.getReference("Users");
                            dbRefrence.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    //    ArrayList<Meal> meallist = new ArrayList<>();

                                    for (DataSnapshot mealSnapshot : snapshot.getChildren()) {
                                        // For each person object in the list

                                        Meal meal = mealSnapshot.getValue(Meal.class);
                                        Log.i("TAG", "onDataChange: "+meal.getStrMeal());
                                        presenter.insert(meal).subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new CompletableObserver() {
                                                    @Override
                                                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                                    }

                                                    @Override
                                                    public void onComplete() {

                                                    }

                                                    @Override
                                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                                                    }
                                                });
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            navigateToOtherActivity(0);
                        }
            }
        });
    }

    void navigateToOtherActivity(int x){
        Intent intent = new Intent(Signin.this, MainActivity.class);
        intent.putExtra("guest",x);
        startActivity(intent);
        finish();
    }

    void facebookSignIn(){

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            navigateToOtherActivity(0);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Signin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}