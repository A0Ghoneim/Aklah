package com.example.aklah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        EditText editText = findViewById(R.id.editTextText);
        EditText passEdit = findViewById(R.id.editTextTextPassword);

       /* ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder().build();
        FirebaseAuth.getInstance().sendSignInLinkToEmail(email,actionCodeSettings);

        if (FirebaseAuth.getInstance().isSignInWithEmailLink(email)){
            FirebaseAuth.getInstance().signInWithEmailLink()
        }
        */
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =editText.getText().toString();
                String password = passEdit.getText().toString();
                //FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password);
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password);
                if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                    Intent intent = new Intent(Signin.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}