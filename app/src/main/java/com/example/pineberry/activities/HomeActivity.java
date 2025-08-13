package com.example.pineberry.activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pineberry.R;
import com.example.pineberry.fragments.AboutUsFragment;
import com.example.pineberry.fragments.CartFragment;
import com.example.pineberry.fragments.HomeFragment;
import com.example.pineberry.fragments.ProfileFragment;
import com.example.pineberry.fragments.ShowAllFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    Fragment homeFragment,showAllFragment,cartFragment,profileFragment,aboutUsFragment;
    FirebaseAuth auth;
    Toolbar toolbar;
    public static final String CHANNEL_ID="MESSAGE CHANNEL";
    public static final int NOTIFICATION_ID=100;
    Notification notification;
    ImageButton navigationDrawerButton;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    TextView userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
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
                    .setContentText("Welcome To Pine Berry, We hope you have a good time here")
                    .setSubText("Pine Berry says")
                    .setChannelId(CHANNEL_ID)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"Notification",NotificationManager.IMPORTANCE_HIGH));

        }else{
            notification=new Notification.Builder(this)
                    .setLargeIcon(largeicon)
                    .setSmallIcon(R.drawable.logo)
                    .setContentText("Welcome To Pine Berry, We hope you have a good time here")
                    .setSubText("Pine Berry says")
                    .build();
        }

        nm.notify(NOTIFICATION_ID,notification);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true /* handle first */) {
            @Override
            public void handleOnBackPressed() {
                // Show confirmation dialog here
               // showExitConfirmationDialog();
            }
        });
        toolbar=findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        homeFragment = new HomeFragment();
        showAllFragment = new ShowAllFragment();
        cartFragment=new CartFragment();
        profileFragment=new ProfileFragment();
        aboutUsFragment=new AboutUsFragment();

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()== R.id.home) {
                    loadFragment(homeFragment);
                    return true;
                }
                else if (item.getItemId()== R.id.explore){
                    loadFragment(showAllFragment);
                    return true;
                }
                else if(item.getItemId()==R.id.cart){
                    loadFragment(cartFragment);
                    return true;
                }
                else if(item.getItemId()==R.id.profile){
                    loadFragment(profileFragment);
                    return true;
                }

                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.home);
//        //fragmenthome
//        homeFragment =new HomeFragment();



      //  loadFragment(homeFragment);
        TextView toolbarTitle=findViewById(R.id.toolbarTitle);
        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.home);
            }
        });
        drawerLayout=findViewById(R.id.drawerLayout);
        navigationDrawerButton =findViewById(R.id.navigationDrawerButton);
        navigationDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        TextView titleToolbar=findViewById(R.id.toolbarTitle);
        titleToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.home);
            }
        });
        navigationView=findViewById(R.id.navigationView);
//        View headerView=navigationView.getHeaderView(0);
//        ImageView img=headerView.findViewById(R.id.);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.navHome) {
                   // Toast.makeText(HomeActivity.this, "Redirecting To Home", Toast.LENGTH_SHORT).show();
                    bottomNavigationView.setSelectedItemId(R.id.home);
                    drawerLayout.closeDrawer(GravityCompat.END);
                    return true;
                }
                else if(item.getItemId()==R.id.navCart) {
                       // Toast.makeText(HomeActivity.this, "Redirecting To Cart", Toast.LENGTH_SHORT).show();
                        bottomNavigationView.setSelectedItemId(R.id.cart);
                        drawerLayout.closeDrawer(GravityCompat.END);
                        return true;
                }else if(item.getItemId()==R.id.navAbout){
                    loadFragment(aboutUsFragment);
                    drawerLayout.closeDrawer(GravityCompat.END);
                    return true;
                }else if(item.getItemId()==R.id.navProfile){
                    bottomNavigationView.setSelectedItemId(R.id.profile);
                    drawerLayout.closeDrawer(GravityCompat.END);
                    return true;
                }
                drawerLayout.closeDrawer(GravityCompat.END);
                return false;
            }
        });
    }

    private void loadFragment(Fragment homeFragment) {
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homeFragment);
        transaction.commit();
    }

    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Exit")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> {
                    runOnUiThread(() -> finish()); // Call finish() on main thread
                })
                .setNegativeButton("No", (dialog, which) -> Toast.makeText(getApplicationContext(), "You clicked on No", Toast.LENGTH_SHORT).show())
                .setNeutralButton("Cancel", (dialog, which) -> Toast.makeText(getApplicationContext(), "You clicked on Cancel", Toast.LENGTH_SHORT).show())
                .create()
                .show();
    }

}