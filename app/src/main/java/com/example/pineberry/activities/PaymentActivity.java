package com.example.pineberry.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.pineberry.R;

public class PaymentActivity extends AppCompatActivity {
    TextView subTotal,discount,shipping,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        double amount=0.0;
        amount=getIntent().getDoubleExtra("amount",0.0);

        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.textView17);
        shipping = findViewById(R.id.textView18);
        total = findViewById(R.id.total_amt);
        shipping.setText(String.valueOf(2.0)+"$");
        subTotal.setText(String.valueOf(amount*1.2)+"$");
        discount.setText(String.valueOf(amount*0.2)+"$");
        total.setText(String.valueOf(amount+2.0)+"$");


    }
}