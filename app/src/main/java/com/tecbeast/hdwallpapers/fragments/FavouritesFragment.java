package com.tecbeast.hdwallpapers.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.activities.MainActivity;


public class FavouritesFragment extends BaseFragment{

    public static FavouritesFragment fragment;

    public static FavouritesFragment getInstance() {
        if (fragment == null) {
            fragment = new FavouritesFragment();
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

        View view = inflater.inflate(R.layout.fragment_favourite, container, false);



        ( (MainActivity)getActivity()).updateToolbarTitle("Favourites");


        return view;
    }



}
