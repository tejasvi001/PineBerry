package com.example.pineberry.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pineberry.R;
import com.example.pineberry.activities.AddressActivity;
import com.example.pineberry.activities.DetailedActivity;
import com.example.pineberry.activities.PaymentActivity;
import com.example.pineberry.models.NewProductsModel;
import com.example.pineberry.models.PopularProductsModel;
import com.example.pineberry.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedFragment extends Fragment {
    ImageView detailedImg;
    TextView rating,name,description,price,quantity;
    Button addToCart,buyNow;
    ImageView addItems,removeItems;

    int totalQuantity=1;
    int totalPrice=0;
    NewProductsModel newProductsModel=null;
    PopularProductsModel popularProductsModel=null;
    //show all
    ShowAllModel showAllModel =null;
    FirebaseAuth auth;
    private FirebaseFirestore firestore;
    public DetailedFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_detailed, container, false);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
         Object obj=null;
        // Inside your Fragment class

        // Check if arguments are not null
        if (getArguments() != null) {
            // Get the Serializable object associated with the key "detailed" from the arguments
             obj = getArguments().getSerializable("detailed");

            // Now you can use the 'obj' variable as needed
        }
        if(obj instanceof NewProductsModel){
            newProductsModel=(NewProductsModel) obj;
        }else if(obj instanceof PopularProductsModel){
            popularProductsModel=(PopularProductsModel)  obj;
        }else if(obj instanceof ShowAllModel){
            showAllModel=(ShowAllModel)  obj;
        }
        detailedImg= root.findViewById(R.id.detailed_img);
        name=root.findViewById(R.id.detailed_name);
        quantity=root.findViewById(R.id.quantity);
        rating=root.findViewById(R.id.rating);
        description=root.findViewById(R.id.detailed_disc);
        price=root.findViewById(R.id.detailed_price);
        addToCart=root.findViewById(R.id.add_to_cart);
        buyNow=root.findViewById(R.id.buy_Now);
        addItems=root.findViewById(R.id.add_item);
        removeItems=root.findViewById(R.id.remove_item);

        //New Products
        if(newProductsModel!=null){
            Glide.with(getContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(String.valueOf(newProductsModel.getRating()));
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            totalPrice= newProductsModel.getPrice()*totalQuantity;
        }
        //New Products
        if(popularProductsModel!=null){
            Glide.with(getContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(String.valueOf(popularProductsModel.getRating()));
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));
            totalPrice= popularProductsModel.getPrice()*totalQuantity;
        }
        //show all
        if(showAllModel!=null){
            Glide.with(getContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(String.valueOf(showAllModel.getRating()));
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));
            totalPrice= showAllModel.getPrice()*totalQuantity;
        }
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressFragment fragment = new AddressFragment();
////                Bundle args = new Bundle();
////                args.putString("type", null);
//                fragment.setArguments(args);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.home_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    if(newProductsModel!=null){
                        totalPrice= newProductsModel.getPrice()*totalQuantity;
                    }
                    if(popularProductsModel!=null){
                        totalPrice= popularProductsModel.getPrice()*totalQuantity;
                    }
                    if(showAllModel!=null){
                        totalPrice= showAllModel.getPrice()*totalQuantity;
                    }
                }
            }
        });
        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });
        return root;
    }
    private void addToCart(){
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MM/dd/yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());
        final HashMap<String,Object> cartMap=new HashMap<>();
        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(getContext(),"Added To Cart",Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                });


    }
}