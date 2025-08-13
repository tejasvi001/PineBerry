package com.example.pineberry.activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pineberry.R;
import com.example.pineberry.fragments.HomeFragment;
import com.example.pineberry.fragments.ShowAllFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Fragment homeFragment;
    FirebaseAuth auth;
    Toolbar toolbar;
    public static final String CHANNEL_ID="MESSAGE CHANNEL";
    public static final int NOTIFICATION_ID=100;
    Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();

        //notification
        Drawable dr= ResourcesCompat.getDrawable(getResources(),R.drawable.logo,null);
        BitmapDrawable bm=(BitmapDrawable) dr;
        Bitmap largeicon=bm.getBitmap();

        NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notification=new Notification.Builder(this)
                        .setLargeIcon(largeicon)
                        .setSmallIcon(R.drawable.logo)
                        .setContentText("Welcome To PineBerry, We hope you have a good time here")
                        .setSubText("PineBerry says")
                        .setChannelId(CHANNEL_ID)
                        .build();
                        nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"Notification",NotificationManager.IMPORTANCE_HIGH));
                        
            }else{
            notification=new Notification.Builder(this)
                    .setLargeIcon(largeicon)
                    .setSmallIcon(R.drawable.logo)
                    .setContentText("Welcome To PineBerry, We hope you have a good time here")
                    .setSubText("PineBerry says")
                    .build();
         }

        nm.notify(NOTIFICATION_ID,notification);

        toolbar=findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        //fragmenthome
        homeFragment =new HomeFragment();
        loadFragment(homeFragment);

    }

    private void loadFragment(Fragment homeFragment) {
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homeFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id== R.id.menu_logout){
            auth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
       else if(id== R.id.menu_my_cart){
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        }
       return true;
    }

}