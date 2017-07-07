package com.tecbeast.hdwallpapers.picasawallpapers;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.activities.BaseActivity;
import com.tecbeast.hdwallpapers.picasawallpapers.app.AppController;
import com.tecbeast.hdwallpapers.picasawallpapers.helper.NavDrawerListAdapter;
import com.tecbeast.hdwallpapers.picasawallpapers.picasa.model.Category;

public class MainActivity extends BaseActivity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// Navigation drawer title
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private List<Category> albumsList;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_picasa);

		mTitle = mDrawerTitle = getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// Getting the albums from shared preferences
		albumsList = AppController.getInstance().getPrefManger().getCategories();

//		// Insert "Recently Added" in navigation drawer first position
//		Category recentAlbum = new Category(null,
//				getString(R.string.nav_drawer_recently_added));
//
//		albumsList.add(0, recentAlbum);

		// Loop through albums in add them to navigation drawer adapter
		for (Category a : albumsList) {
			navDrawerItems.add(new NavDrawerItem(a.getId(), a.getTitle()));
		}

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// Setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

//		// Enabling action bar app icon and behaving it as toggle button
//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		getSupportActionBar().setHomeButtonEnabled(true);
//		getSupportActionBar().setIcon(
//				new ColorDrawable(getResources().getColor(
//						android.R.color.transparent)));

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.mipmap.ic_launcher_round, R.string.app_name, R.string.app_name) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Navigation drawer menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}







	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {

		default:
			// selected wallpaper category
			// send album id to home fragment to list all the wallpapers
			String albumId = albumsList.get(0).getId();
			fragment = GridFragment.newInstance(albumId);
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(albumsList.get(position).getTitle());
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e(TAG, "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}
