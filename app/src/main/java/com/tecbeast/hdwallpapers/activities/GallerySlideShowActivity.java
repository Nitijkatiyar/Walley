package com.tecbeast.hdwallpapers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.adapter.GalleryImageAdapter;
import com.tecbeast.hdwallpapers.utils.ScrollingViewPager;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by nitij on 05-09-2016.
 */

public class GallerySlideShowActivity extends AppCompatActivity {

    ArrayList<File> photos;
    ScrollingViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);

        pager = (ScrollingViewPager) findViewById(R.id.pager);

        ImageView imageView = (ImageView) findViewById(R.id.close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        photos = (ArrayList<File>) getIntent().getSerializableExtra("images");


        GalleryImageAdapter fullScreenImageAdapter = new GalleryImageAdapter(GallerySlideShowActivity.this, photos);
        pager.setAdapter(fullScreenImageAdapter);

        pager.setCurrentItem(getIntent().getIntExtra("position", 0), true);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
