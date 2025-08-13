package com.example.pineberry.fragments;

import static android.content.Intent.getIntent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pineberry.R;
import com.example.pineberry.adapters.ShowAllAdapter;
import com.example.pineberry.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAllFragment extends Fragment {
    RecyclerView recyclerView;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel> list;
    FirebaseFirestore firestore;
    public ShowAllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_show_all, container, false);

        firestore=FirebaseFirestore.getInstance();

//        recyclerView= recyclerView.findViewById(R.id.show_all_recycler);
        recyclerView=root.findViewById(R.id.show_all_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        list=new ArrayList<>();
        showAllAdapter=new ShowAllAdapter(getActivity(),list,requireActivity().getSupportFragmentManager());
        recyclerView.setAdapter(showAllAdapter);
        String type=null;
        // Inside your Fragment class

        // Check if arguments are not null
        if (getArguments() != null) {
            // Get the value associated with the key "type" from the arguments
             type = getArguments().getString("type");

            // Now you can use the 'type' variable as needed
        }
        if(type==null){
            firestore.collection("ShowAll")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc: task.getResult().getDocuments()){
                                    ShowAllModel showAllModel=doc.toObject(ShowAllModel.class);
                                    list.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });
        }
        if(type!=null&&type.equalsIgnoreCase("men")){
            firestore.collection("ShowAll").whereEqualTo("type","men")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc: task.getResult().getDocuments()){
                                    ShowAllModel showAllModel=doc.toObject(ShowAllModel.class);
                                    list.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });
        }
        if(type!=null&&type.equalsIgnoreCase("woman")){
            firestore.collection("ShowAll").whereEqualTo("type","woman")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc: task.getResult().getDocuments()){
                                    ShowAllModel showAllModel=doc.toObject(ShowAllModel.class);
                                    list.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });
        }
        if(type!=null&&type.equalsIgnoreCase("watch")){
            firestore.collection("ShowAll").whereEqualTo("type","watch")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc: task.getResult().getDocuments()){
                                    ShowAllModel showAllModel=doc.toObject(ShowAllModel.class);
                                    list.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });
        }
        if(type!=null&&type.equalsIgnoreCase("shoes")){
            firestore.collection("ShowAll").whereEqualTo("type","shoes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc: task.getResult().getDocuments()){
                                    ShowAllModel showAllModel=doc.toObject(ShowAllModel.class);
                                    list.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });
        }
        if(type!=null&&type.equalsIgnoreCase("kids")){
            firestore.collection("ShowAll").whereEqualTo("type","kids")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc: task.getResult().getDocuments()){
                                    ShowAllModel showAllModel=doc.toObject(ShowAllModel.class);
                                    list.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });
        }
        if(type!=null&&type.equalsIgnoreCase("camera")){
            firestore.collection("ShowAll").whereEqualTo("type","camera")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc: task.getResult().getDocuments()){
                                    ShowAllModel showAllModel=doc.toObject(ShowAllModel.class);
                                    list.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
        return root;
    }
}