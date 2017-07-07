package com.tecbeast.hdwallpapers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.adapter.CategoryAdapter;
import com.tecbeast.hdwallpapers.picasawallpapers.app.AppController;


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
        CategoryAdapter gridViewAdapter = new CategoryAdapter(getActivity(), AppController.getInstance().getPrefManger().getCategories());
        listView.setAdapter(gridViewAdapter);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
