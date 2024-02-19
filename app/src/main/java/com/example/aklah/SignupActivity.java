package com.example.aklah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aklah.SignIn.View.Signin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
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
                if (email.equals("")||password.equals("")){
                    Log.i("NULLLL", "onClick: ");
                    Toast.makeText(SignupActivity.this, "Enter a valid email or passord", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(SignupActivity.this, "signed up successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, Signin.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "onFailure: ", e);
                        Toast.makeText(SignupActivity.this, "Failed to register\n"+"Check your internet connectivity and make sure you enter a valid email account", Toast.LENGTH_LONG).show();
                    }
                });
                //FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password);

            }
        });
    }
}