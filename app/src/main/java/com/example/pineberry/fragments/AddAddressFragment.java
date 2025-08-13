package com.example.pineberry.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pineberry.R;
import com.example.pineberry.activities.AddAddressActivity;
import com.example.pineberry.activities.AddressActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressFragment extends Fragment {
    EditText name,address, city,postalCode,phoneNumber;
    Button addAddressBtn;
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    public AddAddressFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_add_address, container, false);

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        name=root.findViewById(R.id.ad_name);
        address=root.findViewById(R.id.ad_address);
        city=root.findViewById(R.id.ad_city);
        phoneNumber =root.findViewById(R.id.ad_phone);
        postalCode=root.findViewById(R.id.ad_code);
        addAddressBtn=root.findViewById(R.id.ad_add_address);
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=name.getText().toString();
                String userCity=city.getText().toString();
                String userAddress=address.getText().toString();
                String userCode=postalCode.getText().toString();
                String userNumber=phoneNumber.getText().toString();

                String final_address="";

                if(!userName.isEmpty()){
                    final_address+=userName;
                    final_address+=", ";
                }

                if(!userCity.isEmpty()){
                    final_address+=userCity;
                    final_address+=", ";
                }

                if(!userAddress.isEmpty()){
                    final_address+=userAddress;
                    final_address+=", ";
                }

                if(!userCode.isEmpty()){
                    final_address+=userCode;
                    final_address+=", ";
                }

                if(!userNumber.isEmpty()){
                    final_address+=userNumber;
                    final_address+=", ";
                }

                if(!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userCode.isEmpty() && !userNumber.isEmpty()){

                    Map<String,String> map=new HashMap<>();
                    map.put("userAddress",final_address);

                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText( getContext(),"Address Added", Toast.LENGTH_SHORT).show();
                                     //   startActivity(new Intent(getContext(), AddressActivity.class));
                                        AddressFragment fragment = new AddressFragment();
                                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                                        transaction.replace(R.id.home_container, fragment);
                                        transaction.addToBackStack(null);
                                        transaction.commit();
                                    }
                                }
                            });

                }else{
                    Toast.makeText(getActivity(), "Kindly Fill Out All the Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return root;
    }
}