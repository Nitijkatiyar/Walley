package com.tecbeast.hdwallpapers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tecbeast.hdwallpapers.MyApplication;
import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.adapter.PicasaCategoryAdapter;
import com.tecbeast.hdwallpapers.activities.PicasaPhotosActivity;


public class CategoryFragment extends BaseFragment {

    public static CategoryFragment fragment;
    ListView listView;

    public static CategoryFragment getInstance() {
        if (fragment == null) {
            fragment = new CategoryFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        PicasaCategoryAdapter gridViewAdapter = new PicasaCategoryAdapter(getActivity(), MyApplication.getInstance().getPrefManger().getCategories());
        listView.setAdapter(gridViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), PicasaPhotosActivity.class);
                intent.putExtra("albumId", MyApplication.getInstance().getPrefManger().getCategories().get(i).getId());
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
