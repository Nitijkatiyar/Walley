package com.tecbeast.hdwallpapers.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.utils.ScrollingViewPager;

import java.io.File;
import java.util.ArrayList;

//import android.support.v4.view.ViewPager;

public class GalleryImageAdapter extends PagerAdapter {

    private Activity _activity;
    private ArrayList<File> imagePaths;
    private LayoutInflater inflater;

    // constructor
    public GalleryImageAdapter(Activity activity,
                               ArrayList<File> imagePaths) {
        this._activity = activity;
        this.imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this.imagePaths
                .size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;
        Button btnClose;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);

        Glide.with(_activity)
                .load(imagePaths.get(position).getAbsolutePath())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(imgDisplay);


        ((ScrollingViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ScrollingViewPager) container).removeView((RelativeLayout) object);

    }
}