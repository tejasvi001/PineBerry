package com.example.pineberry.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.pineberry.R;
import com.example.pineberry.adapters.CategoryAdapter;
import com.example.pineberry.adapters.NewProductsAdapter;
import com.example.pineberry.adapters.PopularProductsAdapter;
import com.example.pineberry.models.CategoryModel;
import com.example.pineberry.models.NewProductsModel;
import com.example.pineberry.models.PopularProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    TextView catShowAll,popularShowAll,newProductShowAll;
    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    RecyclerView catRecyclerView,newProductRecyclerView,popularRecyclerView;

    //category recyclerview
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;
    //new product recycler view
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;
    //popular product recycler view
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;
    FirebaseFirestore db ;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_home, container, false);

        db=FirebaseFirestore.getInstance();
        catRecyclerView=root.findViewById(R.id.rec_category);
        newProductRecyclerView=root.findViewById(R.id.new_product_rec);
        popularRecyclerView=root.findViewById(R.id.popular_rec);


        catShowAll = root.findViewById(R.id.category_see_all);
        popularShowAll = root.findViewById(R.id.popular_see_all);
        newProductShowAll = root.findViewById(R.id.newProducts_see_all);

        progressDialog=new ProgressDialog(getActivity());

        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ShowAllActivity.class);
//                startActivity(intent);
                ShowAllFragment fragment = new ShowAllFragment();
                Bundle args = new Bundle();
                args.putString("type", null);
                fragment.setArguments(args);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.home_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        });

        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ShowAllActivity.class);
//                startActivity(intent);
                ShowAllFragment fragment = new ShowAllFragment();
                Bundle args = new Bundle();
                args.putString("type", null);
                fragment.setArguments(args);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.home_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        linearLayout=root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);

        //image slider
        ImageSlider imageSlider=root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1,"Discount On Shoes Items", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2,"Discount On Perfume ", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3," 70% OFF", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        //category
        catRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        categoryModelList=new ArrayList<>();
        categoryAdapter=new CategoryAdapter(getActivity(),categoryModelList,requireActivity().getSupportFragmentManager());
        catRecyclerView.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                                CategoryModel categoryModel=document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), "Can not load", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //New Products
        newProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductsModelList=new ArrayList<>();
        newProductsAdapter=new NewProductsAdapter(getContext(),newProductsModelList,requireActivity().getSupportFragmentManager());
        newProductRecyclerView.setAdapter(newProductsAdapter);
        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                               NewProductsModel newProductsModel=document.toObject(NewProductsModel.class);
                                newProductsModelList.add(newProductsModel);
                                newProductsAdapter.notifyDataSetChanged();;
                            }
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), "Can not load", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //popular products
        popularRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularProductsModelList=new ArrayList<>();
        popularProductsAdapter=new PopularProductsAdapter(getContext(),popularProductsModelList,requireActivity().getSupportFragmentManager());
        popularRecyclerView.setAdapter(popularProductsAdapter);

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                                PopularProductsModel popularProductsModel=document.toObject(PopularProductsModel.class);
                                popularProductsModelList.add(popularProductsModel);
                                popularProductsAdapter.notifyDataSetChanged();;
                            }
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), "Can not load", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return root;
    }

}