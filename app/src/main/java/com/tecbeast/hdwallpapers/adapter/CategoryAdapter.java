package com.tecbeast.hdwallpapers.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.picasawallpapers.app.AppConst;
import com.tecbeast.hdwallpapers.picasawallpapers.app.AppController;
import com.tecbeast.hdwallpapers.picasawallpapers.picasa.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private Activity _activity;
    private LayoutInflater inflater;
    private List<Category> categoriesList = new ArrayList<Category>();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CategoryAdapter(Activity activity, List<Category> categoriesList ) {
        this._activity = activity;
        this.categoriesList = categoriesList;
    }

    @Override
    public int getCount() {
        return this.categoriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.categoriesList.get(position);
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
            convertView = inflater.inflate(R.layout.categories_listitem, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        // Grid thumbnail image view
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);

        Category p = categoriesList.get(position);
        title.setText(categoriesList.get(position).getTitle());

        String url = AppConst.URL_ALBUM_PHOTOS.replace("_ALBUM_ID_",
                p.getId());

        thumbNail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbNail.setImageDrawable(_activity.getResources().getDrawable(R.drawable.ico_download));

        return convertView;
    }


}