package com.andela.app.animationchallenge.fragment;


import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.andela.app.animationchallenge.R;
import com.andela.app.animationchallenge.adapter.PhotoAdapter;
import com.andela.app.animationchallenge.model.Photo;
import com.andela.app.animationchallenge.util.LoadingStatus;
import com.andela.app.animationchallenge.viewmodel.PhotoViewModel;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements PhotoAdapter.PhotoClickListener {

    private PhotoViewModel photoViewModel;
    private RecyclerView recyclerView;
    private MaterialProgressBar progressBar;
    private View noItemView;
    private PhotoAdapter photoAdapter;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initViews(view);

        photoViewModel.getLoadingStatus().observe(this, this::setupProgressBarVisibility);

        photoViewModel.getPhoto().observe(this, photos -> {

            photoAdapter.submitList(photos);
            setupNoItemViewVisibility(photos.size());
        });
    }



    private void setupProgressBarVisibility(LoadingStatus loadingState) {

        if (loadingState == LoadingStatus.RUNNING) {
            progressBar.setVisibility(View.VISIBLE);
            Log.d("HomeFragment", "Loading");
        } else if (loadingState == LoadingStatus.SUCCESS) {
            Log.d("HomeFragment", "LOADED");
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            Log.d("HomeFragment", "INVISIBLE");
        }
    }

    private void setupNoItemViewVisibility(int size) {
        int visibility = size == 0 ? View.VISIBLE : View.INVISIBLE;
        noItemView.setVisibility(visibility);
    }

    private void initViews(View view) {

        progressBar = view.findViewById(R.id.progressBarPhoto);
        recyclerView = view.findViewById(R.id.rvPhotos);
        noItemView = view.findViewById(R.id.no_photo);
        photoAdapter = new PhotoAdapter(this);
        recyclerView.setAdapter(photoAdapter);
    }

    @Override
    public void onPhotoClick(Photo photo, View view) {
        // Todo: Show detail detail view

        Toast.makeText(getContext(), photo.getTitle(), Toast.LENGTH_LONG).show();

    }


}
