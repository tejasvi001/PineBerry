package com.example.pineberry.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pineberry.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_splash_screen);
        //hide status  bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    SharedPreferences sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
                    boolean flag=sharedPreferences.getBoolean("isLoggedIn",false);

                    if(flag==true){
                        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    }
                    startActivity(new Intent(SplashScreenActivity.this, OnBoardingActivity.class));
                    finish();
            }
        }, 2000);
    }
}