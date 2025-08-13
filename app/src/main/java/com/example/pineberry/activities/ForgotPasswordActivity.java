package com.example.pineberry.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pineberry.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText email;
    Button btnback,resetbtn;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password);


        //Initialization

        email=findViewById(R.id.fpwdEmail);
        resetbtn=findViewById(R.id.Linkbutton);
        auth=FirebaseAuth.getInstance();

        //reset Button Functionalilty
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=email.getText().toString();
                if(!validEmail(useremail)){
                    email.setError("Not a valid email");
                    return;
                }
                if(TextUtils.isEmpty(useremail)){
                    email.setError("Email is required");
                    return;
                }
                resetPassword(useremail);
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));

            }
        });


        // Back to Login Button functionality

        btnback=findViewById(R.id.backToLoginButton);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
            }
        });

    }
    // This method is the method which has all the functionality of firebase connection and sending of email

    private void resetPassword(String useremail) {
        resetbtn.setVisibility(View.INVISIBLE);
        auth.sendPasswordResetEmail(useremail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Reset Password Link has been sent to above email",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"An error occurred. Couldn't send the email",Toast.LENGTH_SHORT).show();
                        resetbtn.setVisibility(View.VISIBLE);
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
}