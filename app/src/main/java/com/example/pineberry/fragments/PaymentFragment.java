package com.example.pineberry.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pineberry.R;

public class PaymentFragment extends Fragment {
    TextView subTotal,discount,shipping,total;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_payment, container, false);
        double amount=0.0;
       // amount=getIntent().getDoubleExtra("amount",0.0);
        if (getArguments() != null) {
            // Get the Serializable object associated with the key "detailed" from the arguments
            amount =  getArguments().getDouble("amount",30.0);
            // Now you can use the 'obj' variable as needed
        }

        subTotal = root.findViewById(R.id.sub_total);
        discount = root.findViewById(R.id.textView17);
        shipping = root.findViewById(R.id.textView18);
        total = root.findViewById(R.id.total_amt);
        double subTotalamount=(amount*1.2);
        shipping.setText(String.valueOf(2.0)+"$");
        subTotal.setText(String.valueOf(subTotalamount)+"$");
        discount.setText(String.valueOf(amount*0.2)+"$");
        total.setText(String.valueOf(amount+2.0)+"$");


        return root;
    }
}