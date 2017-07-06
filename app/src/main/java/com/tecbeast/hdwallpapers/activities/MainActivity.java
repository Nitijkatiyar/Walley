package com.tecbeast.hdwallpapers.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.fragments.CategoryFragment;
import com.tecbeast.hdwallpapers.fragments.FavouritesFragment;
import com.tecbeast.hdwallpapers.fragments.HomeFragment;
import com.tecbeast.hdwallpapers.fragments.MyAlbumFragment;
import com.tecbeast.hdwallpapers.utils.BottomNavigationViewHelper;


public class MainActivity extends BaseActivity {

    private TextView mTextMessage;
    BottomNavigationView navigation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setSelected(true);

        updateToolbarTitle(getResources().getString(R.string.title_home));
        navigation.setItemBackgroundResource(R.color.tab_home);
        updateToolbarColor(getResources().getColor(R.color.tab_home));
        replaceFragment(HomeFragment.getInstance());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tab_home:
                    updateToolbarTitle(getResources().getString(R.string.title_home));
                    navigation.setItemBackgroundResource(R.color.tab_home);
                    updateToolbarColor(getResources().getColor(R.color.tab_home));
                    replaceFragment(HomeFragment.getInstance());
                    return true;
                case R.id.tab_category:
                    updateToolbarTitle(getResources().getString(R.string.title_category));
                    navigation.setItemBackgroundResource(R.color.tab_category);
                    updateToolbarColor(getResources().getColor(R.color.tab_category));
                    replaceFragment(CategoryFragment.getInstance());
                    return true;
                case R.id.tab_favourite:
                    updateToolbarTitle(getResources().getString(R.string.title_favourite));
                    navigation.setItemBackgroundResource(R.color.tab_favourites);
                    updateToolbarColor(getResources().getColor(R.color.tab_favourites));
                    replaceFragment(FavouritesFragment.getInstance());
                    return true;
                case R.id.tab_myalbum:
                    updateToolbarTitle(getResources().getString(R.string.title_myalbum));
                    navigation.setItemBackgroundResource(R.color.tab_myalbum);
                    updateToolbarColor(getResources().getColor(R.color.tab_myalbum));
                    replaceFragment(MyAlbumFragment.getInstance());
                    return true;
            }
            return false;
        }

    };

}

