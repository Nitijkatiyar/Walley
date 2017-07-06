package com.tecbeast.hdwallpapers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kc.unsplash.Unsplash;
import com.kc.unsplash.api.Order;
import com.kc.unsplash.models.Collection;
import com.kc.unsplash.models.Photo;
import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.activities.MainActivity;

import java.util.List;

import static com.tecbeast.hdwallpapers.fragments.HomeFragment.unsplash;


public class CategoryFragment extends BaseFragment {

    public static CategoryFragment fragment;

    public static CategoryFragment getInstance() {
        if (fragment == null) {
            fragment = new CategoryFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        unsplash.getCuratedCollections(1, 20, new Unsplash.OnCollectionsLoadedListener() {
            @Override
            public void onComplete(List<Collection> collections) {

                int photoCount = collections.size();
            }

            @Override
            public void onError(String error) {

            }

        });
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
