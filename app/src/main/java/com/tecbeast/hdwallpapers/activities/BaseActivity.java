package com.tecbeast.hdwallpapers.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kc.unsplash.Unsplash;
import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.fragments.CategoryFragment;
import com.tecbeast.hdwallpapers.fragments.FavouritesFragment;
import com.tecbeast.hdwallpapers.fragments.HomeFragment;
import com.tecbeast.hdwallpapers.fragments.MyAlbumFragment;
import com.tecbeast.hdwallpapers.imagepicker.helper.Constants;

import java.io.File;


public class BaseActivity extends AppCompatActivity {

    public Unsplash unsplash;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File imagefolder = new File(Constants.ImageFolder);
        if (!imagefolder.exists()) {
            imagefolder.mkdirs();
        }
        unsplash = new Unsplash("599c084ef1361b127420664a0a132945b4f083d21c9a4679d2e39c9497f31765");
    }

    private void updateToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    public void updateToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void updateToolbarColor(int color) {
        ColorDrawable colorDrawable = new ColorDrawable(color);
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
    }

    public void initToolbar(Toolbar toolbar, boolean isBackEnabled) {
        setSupportActionBar(toolbar);
        if (isBackEnabled) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
    }

    public void initToolbar(Toolbar toolbar, String title, boolean isBackEnabled) {
        setSupportActionBar(toolbar);
        if (isBackEnabled) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void replaceFragment(Fragment fragment) {
        Fragment newFragment = fragment;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content, newFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (manager.getFragments() != null && manager.getFragments().contains(newFragment)) {
            transaction.show(newFragment);
        }
        transaction.addToBackStack(newFragment.toString());
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getFragments() != null) {
            if (manager.getFragments().contains(HomeFragment.getInstance())
                || manager.getFragments().contains(CategoryFragment.getInstance())
                || manager.getFragments().contains(FavouritesFragment.getInstance())
                || manager.getFragments().contains(MyAlbumFragment.getInstance())
                ) {
                finish();
            } else if (manager.getBackStackEntryCount() > 0) {
                manager.popBackStack();
            }
        } else {
            super.onBackPressed();
        }
    }
}
