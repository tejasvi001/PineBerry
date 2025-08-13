package com.example.pineberry.fragments;//package com.example.pineberryy.fragments;
//
//// ImageSliderFragment.java
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.example.pineberryy.R;
//
//public class ImageSliderFragment extends Fragment {
//
//    //private int[] imageResources = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
//    private ImageView imageView;
//    private int currentPosition = 0;
//    private Handler handler;
//    private Runnable runnable;
//    private int delay = 2000; // 2 seconds delay between images
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        imageView = view.findViewById(R.id.imageView);
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        handler = new Handler();
//        startSlider();
//    }
//
//    private void startSlider() {
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (getActivity() != null) {
//                  //  if (currentPosition == imageResources.length) {
//                        currentPosition = 0;
//                    }
//                  //  imageView.setImageResource(imageResources[currentPosition]);
//                    currentPosition++;
//                    handler.postDelayed(this, delay);
//                }
//    //        }
//        };
//
//        //handler.postDelayed(runnable, delay);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        handler.removeCallbacks(runnable);
//    }
//}
