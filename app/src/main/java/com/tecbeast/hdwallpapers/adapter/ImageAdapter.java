package com.tecbeast.hdwallpapers.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.activities.GalleryActivity;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nitij  on 7/31/16.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Activity context;
    private LayoutInflater inflater;
    private List<File> images;
    HashMap<Integer, Boolean> isSelected = new HashMap<>();

    public ImageAdapter(Activity context, List<File> images) {
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
        viewHolder.checkBox.setVisibility(View.GONE);
        String imagepath = image.getAbsolutePath();
        if (image.isDirectory()) {
            File[] files = image.listFiles();
            imagepath = files[0].getAbsolutePath();
            if (image.getAbsolutePath().contains("img")) {
                viewHolder.textView.setText(image.getAbsolutePath().split("img/")[1]);
            } else if (image.getAbsolutePath().contains("vds")) {
                viewHolder.textView.setText(image.getAbsolutePath().split("vds/")[1]);
            }
            viewHolder.textView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.textView.setVisibility(View.GONE);
        }


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
            if (images.get(getPosition()).isDirectory()) {
                String title = "";
                if (images.get(getPosition()).getAbsolutePath().contains("img")) {
                    title = images.get(getPosition()).getAbsolutePath().split("img/")[1];
                } else if (images.get(getPosition()).getAbsolutePath().contains("vds")) {
                    title = images.get(getPosition()).getAbsolutePath().split("vds/")[1];
                }
                context.startActivity(new Intent(context, GalleryActivity.class).putExtra("directoryPath", images.get(getPosition()).getAbsolutePath()).putExtra("title", title));
            } else {

            }
        }
    }


}
