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

public class RegistrationActivity extends AppCompatActivity {
    EditText name,email,password,phone;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
        //getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        sharedPreferences=getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
        boolean isFirstTime=sharedPreferences.getBoolean("firstTime",true);
        if(isFirstTime){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();
            startActivity(new Intent(RegistrationActivity.this, OnBoardingActivity.class));
            finish();
        }
        Button signup=findViewById(R.id.signupbutton);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup(signup);
            }
        });
        TextView signin=findViewById(R.id.signintv);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

    }
    void signup(View view){
        String username=name.getText().toString();
        String useremail=email.getText().toString();
        String userpassword=password.getText().toString();
        String userphone=phone.getText().toString();
        if(TextUtils.isEmpty(username)){
           name.setError("Enter Name");
           return;
        }
        if(TextUtils.isEmpty(userphone)){
            phone.setError("Enter Phone");
            return;
        }
        if(TextUtils.isEmpty(useremail)){
            email.setError("Enter Email");
            return;
        }

        if(TextUtils.isEmpty(userpassword)){
            password.setError("Enter Password");
            return;
        }
        if(!validPassword(userpassword)){
            password.setError("Invalid Password");
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
        auth.createUserWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this,"Successfully Registered",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                }else{
                    Toast.makeText(RegistrationActivity.this,"Registration Unsuccessful"+task.getException(),Toast.LENGTH_SHORT).show();
                }
                //startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });
    }
    private boolean validPassword(String password){
        char arr[]={'_','@','#','*'};
        boolean isLowerChar=false,isDigit=false,isUpperChar=false,isSpecialChar=false;
        for(int i=0;i<password.length();i++){
            if(password.charAt(i)<=90&&password.charAt(i)>=65){
                isUpperChar=true;
            }
            if(password.charAt(i)<=122&&password.charAt(i)>=97){
                isLowerChar=true;
            }
            if(password.charAt(i)<=57&&password.charAt(i)>=48){
                isDigit=true;
            }
            for(int j=0;j<arr.length;j++){
                if(password.charAt(i)==arr[j]){
                    isSpecialChar=true;
                }
            }
        }
        return isLowerChar&&isDigit&&isSpecialChar&&isUpperChar;
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
}