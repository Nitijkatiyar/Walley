package com.tecbeast.hdwallpapers.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.kc.unsplash.Unsplash;
import com.kc.unsplash.api.Order;
import com.kc.unsplash.models.Photo;
import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.activities.MainActivity;
import com.tecbeast.hdwallpapers.adapter.UnSplashWallpapersAdapter;
import com.tecbeast.hdwallpapers.activities.FullScreenViewActivity;
import com.tecbeast.hdwallpapers.utils.AppConst;
import com.tecbeast.hdwallpapers.utils.WallpapersUtils;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment {

    public static HomeFragment fragment;
    public static Unsplash unsplash;
    private List<Photo> photoList = new ArrayList<>();
    UnSplashWallpapersAdapter mAdapter;
    private WallpapersUtils utils;
    private GridView gridView;
    private int columnWidth;
    private ProgressBar pbLoader;

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

        utils = new WallpapersUtils(getActivity());

        gridView = (GridView) view.findViewById(R.id.grid_view);
        gridView.setVisibility(View.GONE);
        pbLoader = (ProgressBar) view.findViewById(R.id.pbLoader);
        pbLoader.setVisibility(View.VISIBLE);
        InitilizeGridLayout();

        unsplash.getPhotos(1, 50, Order.LATEST, new Unsplash.OnPhotosLoadedListener() {
            @Override
            public void onComplete(List<Photo> photos) {
                photoList = photos;

                // Hide the loader, make grid visible
                pbLoader.setVisibility(View.GONE);
                gridView.setVisibility(View.VISIBLE);
                mAdapter = new UnSplashWallpapersAdapter(getActivity(), photos, columnWidth);
                gridView.setAdapter(mAdapter);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // On selecting the grid image, we launch fullscreen activity
                        Photo photo = photoList.get(i);
                        Intent intent = new Intent(getActivity(),
                                FullScreenViewActivity.class);
                        intent.putExtra(FullScreenViewActivity.TAG_SEL_IMAGE_UNSPLASH, photo);
                        startActivity(intent);
                    }
                });


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

    private void InitilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                AppConst.GRID_PADDING, r.getDisplayMetrics());

        // Column width
        columnWidth = (int) ((utils.getScreenWidth() - ((2 + 1) * padding)) / 2);
        gridView.setNumColumns(2);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);

        // Setting horizontal and vertical padding
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
