package com.tecbeast.hdwallpapers.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.adapter.GalleryAdapter;
import com.tecbeast.hdwallpapers.imagepicker.helper.Constants;
import com.tecbeast.hdwallpapers.imagepicker.model.Image;
import com.tecbeast.hdwallpapers.preference.Preferences;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class GalleryActivity extends AppCompatActivity {


    private ArrayList<Image> images = new ArrayList<>();

    private int REQUEST_CODE_PICKER = 2000;
    RecyclerView recyclerView;
    GalleryAdapter imageAdapter;
    List<File> files;
    private GridLayoutManager layoutManager;
    private int imageColumns;
    Intent intent;
    String directory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("directoryPath")) {
                directory = intent.getStringExtra("directoryPath");
            }
        } else {
            directory = Constants.ImageFolder;
        }


        refreshView();

    }


    private void refreshView() {
        files = getListFiles(new File(directory));

        imageColumns = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 3 : 5;

        int columns = imageColumns;
        layoutManager = new GridLayoutManager(GalleryActivity.this, columns);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        imageAdapter = new GalleryAdapter(GalleryActivity.this, files);
        recyclerView.setAdapter(imageAdapter);
        if (files.size() == 0) {
            finish();
        }
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.add(file);
            } else {
                inFiles.add(file);
            }
        }
        return inFiles;
    }


    public void copyDirectoryOneLocationToAnotherLocation(String sourceLocation, String targetLocation) {

        File source = new File(sourceLocation);
        Log.e("SourceImage", "" + sourceLocation);

        File destination = new File(targetLocation);
        Log.e("destinationImage", "" + targetLocation);
        try {
            FileUtils.copyFile(source, destination);
            FileUtils.forceDelete(source);


            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(destination)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}