package com.tecbeast.hdwallpapers.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.adapter.ImageAdapter;
import com.tecbeast.hdwallpapers.imagepicker.activity.ImagePickerActivity;
import com.tecbeast.hdwallpapers.imagepicker.helper.Constants;
import com.tecbeast.hdwallpapers.imagepicker.model.Image;
import com.tecbeast.hdwallpapers.preference.Preferences;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MyAlbumFragment extends BaseFragment {

    public static MyAlbumFragment fragment;

    public static MyAlbumFragment getInstance() {
        if (fragment == null) {
            fragment = new MyAlbumFragment();
        }
        return fragment;
    }

    View rootView;
    LinearLayout _noImages;
    Button _addImage;
    private ArrayList<Image> images = new ArrayList<>();

    private int REQUEST_CODE_PICKER = 2000;
    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    List<File> files;
    Button _chooseImages;
    private GridLayoutManager layoutManager;
    private int imageColumns;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_myalbum, container, false);

        _noImages = (LinearLayout) rootView.findViewById(R.id.imageFragment_NoImage);
        _chooseImages = (Button) rootView.findViewById(R.id.imageFragment_choose);
        _addImage = (Button) rootView.findViewById(R.id.addImage);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);


        _addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImages();
            }
        });
        _chooseImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImages();
            }
        });


        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshView();

    }

    public void selectImages() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);

        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_MODE, true);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_MODE, ImagePickerActivity.MODE_MULTIPLE);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_LIMIT, 99);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SHOW_CAMERA, false);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SELECTION_MODE, "images");
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_TITLE, "Album");
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_TITLE, "Tap to select images");
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_DIRECTORY, "Camera");

        startActivityForResult(intent, REQUEST_CODE_PICKER);
    }

    private void refreshView() {
        files = getListFiles(new File(Constants.ImageFolder));
        if (files.size() > 0) {
            _noImages.setVisibility(View.GONE);
            _addImage.setVisibility(View.VISIBLE);
        } else {
            _noImages.setVisibility(View.VISIBLE);
            _addImage.setVisibility(View.GONE);
        }
        imageColumns = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 3 : 5;

        int columns = imageColumns;
        layoutManager = new GridLayoutManager(getActivity(), columns);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        imageAdapter = new ImageAdapter(getActivity(), files);
        recyclerView.setAdapter(imageAdapter);
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        if(parentDir.listFiles() == null){
            return new ArrayList<>();
        }
        File[] files = parentDir.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                File[] fileList = file.listFiles();
                if (fileList.length > 0) {
                    inFiles.add(file);
                }
            } else {
                inFiles.add(file);
            }
        }
        return inFiles;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && resultCode == Activity.RESULT_OK && data != null) {
            images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0, l = images.size(); i < l; i++) {
                copyDirectoryOneLocationToAnotherLocation(images.get(i).getPath(), images.get(i).getName(), images.get(i).getId());
            }
        }
        refreshView();
    }

    public void copyDirectoryOneLocationToAnotherLocation(String sourceLocation, String FileName, long id) {

        File source = new File(sourceLocation);
        Log.e("SourceImage", "" + sourceLocation);
        String str1 = sourceLocation.replace("/" + FileName, "");
        String destinaltionFolder = str1.substring(str1.lastIndexOf("/"), str1.length());


        File file = new File(Constants.ImageFolder + "" + destinaltionFolder);
        if (!file.exists()) {
            file.mkdir();
        }
        String target = (file.getAbsolutePath() + "/" + FileName);
        File destination = new File(target);
        Log.e("destinationImage", "" + target);
        try {
            FileUtils.copyFile(source, destination);
            Preferences.setString(getActivity(), target, sourceLocation);


            removeThumbnails(getActivity().getContentResolver(), id);
            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(source)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void removeThumbnails(ContentResolver contentResolver, long photoId) {
        Cursor thumbnails = contentResolver.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Thumbnails.IMAGE_ID + "=?", new String[]{String.valueOf(photoId)}, null);
        for (thumbnails.moveToFirst(); !thumbnails.isAfterLast(); thumbnails.moveToNext()) {

            long thumbnailId = thumbnails.getLong(thumbnails.getColumnIndex(MediaStore.Images.Thumbnails._ID));
            String path = thumbnails.getString(thumbnails.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
            File file = new File(path);
            if (file.delete()) {
                contentResolver.delete(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, MediaStore.Images.Thumbnails._ID + "=?", new String[]{String.valueOf(thumbnailId)});

            }

        }
    }
}
