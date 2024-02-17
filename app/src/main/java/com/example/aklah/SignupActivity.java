package com.example.aklah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    ConstraintLayout back;
    EditText editText;
    EditText passEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        back=findViewById(R.id.back);
      //  back.getBackground().setAlpha(200);
         editText = findViewById(R.id.editTextText);
         passEdit = findViewById(R.id.editTextTextPassword);
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =editText.getText().toString();
                String password = passEdit.getText().toString();
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password);
                //FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password);
                    Intent intent = new Intent(SignupActivity.this, Signin.class);
                    startActivity(intent);
                    finish();
            }
        });
    }
}