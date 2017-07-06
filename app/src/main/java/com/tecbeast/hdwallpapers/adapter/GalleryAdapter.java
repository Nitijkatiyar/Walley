package com.tecbeast.hdwallpapers.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.activities.GallerySlideShowActivity;
import com.tecbeast.hdwallpapers.preference.Preferences;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nitij on 7/31/16.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageViewHolder> {

    private Activity context;
    private LayoutInflater inflater;
    private List<File> images;
    HashMap<Integer, Boolean> isSelected = new HashMap<>();

    public GalleryAdapter(Activity context, List<File> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_gallery, parent, false);
        return new ImageViewHolder(itemView, images, context);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder viewHolder, final int position) {

        File image = images.get(position);
        String imagepath = image.getAbsolutePath();
        if (image.isDirectory()) {
            File[] files = image.listFiles();
            imagepath = files[0].getAbsolutePath();
            viewHolder.textView.setText(image.getAbsolutePath().substring(imagepath.lastIndexOf(File.separator), image.getAbsolutePath().length()));
            viewHolder.textView.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setVisibility(View.GONE);
        } else {
            viewHolder.textView.setVisibility(View.GONE);
            viewHolder.checkBox.setVisibility(View.VISIBLE);
        }

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isSelected.put(position, isChecked);
            }
        });

        Log.e("ActualPath", "" + Preferences.getString(context, imagepath));
        Glide.with(context)
                .load(imagepath)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(viewHolder.imageView);
    }


    @Override
    public int getItemCount() {
        return images.size();
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;
        private CheckBox checkBox;
        List<File> images;
        private Activity context;

        public ImageViewHolder(View itemView, List<File> images, Activity context) {
            super(itemView);
            this.images = images;
            this.context = context;
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            textView = (TextView) itemView.findViewById(R.id.name);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (images.get(getPosition()).getAbsolutePath().contains("img")) {
                Intent intent = new Intent(context, GallerySlideShowActivity.class);
                intent.putExtra("images", (Serializable) images);
                intent.putExtra("position", getPosition());
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(images.get(getPosition()).getAbsolutePath()));
                intent.setDataAndType(Uri.parse(images.get(getPosition()).getAbsolutePath()), "video/*");
                context.startActivity(intent);
            }
        }
    }

    public List<File> getSelectedData() {
        List<File> list = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            if (isSelected.get(i) != null && isSelected.get(i)) {
                list.add(images.get(i));
            }
        }
        return list;
    }

}
