package com.example.pineberry.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pineberry.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    private FirebaseAuth auth;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        pref=getSharedPreferences("Login",MODE_PRIVATE);
        auth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        Button signin=findViewById(R.id.forgotpasswordbutton);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin(signin);
            }
        });
        TextView signup=findViewById(R.id.signuptv);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
        TextView forgotpassword=findViewById(R.id.forgotpassword);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }
    void signin(View view){

        String useremail=email.getText().toString();
        String userpassword=password.getText().toString();

        if(TextUtils.isEmpty(useremail)){
            email.setError("Enter Email");
            return;
        }

        if(TextUtils.isEmpty(userpassword)){
            password.setError("Enter Password");
            return;
        }
        if(!validEmail(useremail)){
            email.setError("Not a valid email");
            return;
        }
        if(userpassword.length()<6){
            password.setError("Password is too small");
            return;
        }
        auth.signInWithEmailAndPassword(useremail,userpassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful()){
                                   SharedPreferences.Editor editor=pref.edit();
                                   editor.putBoolean("isLoggedIn",true);
                                   editor.apply();
                                   Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                               }else{
                                   Toast.makeText(LoginActivity.this,"Login Unsuccessful"+task.getException(),Toast.LENGTH_LONG).show();
                               }
                            }
                        });

    }
    private boolean validEmail(String _email){
        int a=_email.indexOf("@");
        if(a>0) {
            String _s1=_email.substring(a);
            int b=_s1.indexOf(".");
            if(b>0) {
                if(_s1.length()>b+1) {
                    return true;
                }
                return false;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
    }
}