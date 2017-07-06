package com.tecbeast.hdwallpapers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.unsplash.Unsplash;
import com.kc.unsplash.api.Order;
import com.kc.unsplash.models.Photo;
import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.activities.MainActivity;
import com.tecbeast.hdwallpapers.adapter.WallpapersAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment {

    public static HomeFragment fragment;
    public static Unsplash unsplash;
    private List<Photo> photoList = new ArrayList<>();
    private RecyclerView recyclerView;
    WallpapersAdapter mAdapter;

    public static HomeFragment getInstance() {
        if (fragment == null) {
            fragment = new HomeFragment();
            unsplash = new Unsplash("599c084ef1361b127420664a0a132945b4f083d21c9a4679d2e39c9497f31765");
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);



        unsplash.getPhotos(1, 20, Order.LATEST, new Unsplash.OnPhotosLoadedListener() {
            @Override
            public void onComplete(List<Photo> photos) {
                int photoCount = photos.size();
                mAdapter = new WallpapersAdapter(photos,getActivity());
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onError(String error) {
                Log.v("Error", error);
            }
        });
        unsplash.getRandomPhoto(null,null,null,null,null,null,null, new Unsplash.OnPhotoLoadedListener() {


            @Override
            public void onComplete(Photo photo) {
                int photoCount = 1;
            }

            @Override
            public void onError(String error) {
                Log.v("Error", error);
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((MainActivity) getActivity()).updateToolbarTitle("Home");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
