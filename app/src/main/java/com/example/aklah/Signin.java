package com.example.aklah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
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
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;


public class Signin extends AppCompatActivity {

    ConstraintLayout back;
    FirebaseAuth auth;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleImage;
   // ImageView facevookImage;

    TextView signup;

    TextView guest;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                //FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password);
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password);
                int i=0;
                while (i<100) {
                    if (i==99){
                        Toast.makeText(Signin.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                    }
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                       navigateToOtherActivity(0);
                        break;
                    }
                    i++;
                }
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