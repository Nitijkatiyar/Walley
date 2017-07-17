package com.tecbeast.hdwallpapers.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kc.unsplash.models.Photo;
import com.tecbeast.hdwallpapers.MyApplication;
import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.model.Wallpaper;

import java.util.ArrayList;
import java.util.List;
 
public class UnSplashWallpapersAdapter extends BaseAdapter {

    private Activity _activity;
    private LayoutInflater inflater;
    private List<Photo> wallpapersList = new ArrayList<Photo>();
    private int imageWidth;
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    public UnSplashWallpapersAdapter(Activity activity, List<Photo> wallpapersList,
                                     int imageWidth) {
        this._activity = activity;
        this.wallpapersList = wallpapersList;
        this.imageWidth = imageWidth;
    }

    @Override
    public int getCount() {
        return this.wallpapersList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.wallpapersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) _activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.grid_item_photo, null);

        if (imageLoader == null)
            imageLoader = MyApplication.getInstance().getImageLoader();

        // Grid thumbnail image view
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);

        Photo p = wallpapersList.get(position);

        thumbNail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbNail.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth,
                imageWidth));
        thumbNail.setImageUrl(p.getUrls().getThumb(), imageLoader);

        return convertView;
    }

}