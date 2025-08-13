package com.example.pineberry.fragments;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pineberry.R;
import com.example.pineberry.activities.AddAddressActivity;
import com.example.pineberry.activities.AddressActivity;
import com.example.pineberry.activities.PaymentActivity;
import com.example.pineberry.adapters.AddressAdapter;
import com.example.pineberry.models.AddressModel;
import com.example.pineberry.models.NewProductsModel;
import com.example.pineberry.models.PopularProductsModel;
import com.example.pineberry.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
public class AddressFragment extends Fragment implements AddressAdapter.SelectedAddress {
    RecyclerView recyclerView;
    private List<AddressModel> addressModelList;
    private AddressAdapter addressAdapter;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String mAddress="";

    public AddressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_address, container, false);

//        //get data from detailed activity
//        Object obj=getIntent().getSerializableExtra("item");

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        recyclerView=root.findViewById(R.id.address_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addressModelList=new ArrayList<>();
        addressAdapter=new AddressAdapter(getContext(),addressModelList,this);
        recyclerView.setAdapter(addressAdapter);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                AddressModel addressModel = doc.toObject(AddressModel.class);
                                addressModelList.add(addressModel);
                                addressAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        Button addAddress=root.findViewById(R.id.add_address_btn);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddressFragment fragment = new AddAddressFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.home_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        Object obj=null;
        // Inside your Fragment class

        // Check if arguments are not null
        if (getArguments() != null) {
            // Get the Serializable object associated with the key "detailed" from the arguments
            obj = getArguments().getSerializable("detailed");

            // Now you can use the 'obj' variable as needed
        }
        Button paymentButton=root.findViewById(R.id.payment_btn);
        Object finalObj = obj;
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount=0.0;
                if(finalObj instanceof NewProductsModel){
                    NewProductsModel newProductsModel=(NewProductsModel) finalObj;
                    amount = newProductsModel.getPrice();
                }
                if(finalObj instanceof PopularProductsModel){
                    PopularProductsModel popularProductsModel=(PopularProductsModel) finalObj;
                    amount = popularProductsModel.getPrice();
                }
                if(finalObj instanceof ShowAllModel){
                    ShowAllModel showAllModel=(ShowAllModel) finalObj;
                    amount = showAllModel.getPrice();
                }
                PaymentFragment fragment = new PaymentFragment();
                Bundle args = new Bundle();
                args.putDouble("amount", amount);
                fragment.setArguments(args);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.home_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

//                Intent intent=new Intent(getActivity(), PaymentActivity.class);
//                intent.putExtra("amount",amount);
//                startActivity(intent);

            }
        });
        return root;
    }

    @Override
    public void setAddress(String address) {
        mAddress=address;
    }
}