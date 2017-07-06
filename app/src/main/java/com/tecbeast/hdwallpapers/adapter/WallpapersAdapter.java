package com.tecbeast.hdwallpapers.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kc.unsplash.models.Photo;
import com.tecbeast.hdwallpapers.R;

import java.util.List;
 
public class WallpapersAdapter extends RecyclerView.Adapter<WallpapersAdapter.MyViewHolder> {
 
    private List<Photo> photosList;
    private Context context;
 
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image_view);
        }
    }
 
 
    public WallpapersAdapter(List<Photo> photosList,Context context) {
        this.photosList = photosList;
        this.context = context;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wallpaper, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Photo photo = photosList.get(position);
        Glide.with(context)
                .load(photo.getUrls().getRegular())
                .placeholder(R.drawable.folder_placeholder)
                .error(R.drawable.folder_placeholder)
                .into(holder.image);
    }
 
    @Override
    public int getItemCount() {
        return photosList.size();
    }
}