package com.tecbeast.hdwallpapers.activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kc.unsplash.models.Photo;
import com.tecbeast.hdwallpapers.MyApplication;
import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.model.Wallpaper;
import com.tecbeast.hdwallpapers.utils.WallpapersUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FullScreenViewActivity extends Activity implements OnClickListener {
	private static final String TAG = FullScreenViewActivity.class
			.getSimpleName();
	public static final String TAG_SEL_IMAGE = "selectedImage_picase";
	public static final String TAG_SEL_IMAGE_UNSPLASH = "selectedImage_unsplash";
	private Wallpaper selectedPhoto;
	private Photo selectedPhotoUnsplash;
	private ImageView fullImageView;
	private LinearLayout llSetWallpaper, llDownloadWallpaper;
	private WallpapersUtils utils;
	private ProgressBar pbLoader;

	// Picasa JSON response node keys
	private static final String TAG_ENTRY = "entry",
			TAG_MEDIA_GROUP = "media$group",
			TAG_MEDIA_CONTENT = "media$content", TAG_IMG_URL = "url",
			TAG_IMG_WIDTH = "width", TAG_IMG_HEIGHT = "height";
	ImageLoader imageLoader = MyApplication
			.getInstance().getImageLoader();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_image);

		fullImageView = (ImageView) findViewById(R.id.imgFullscreen);
		llSetWallpaper = (LinearLayout) findViewById(R.id.llSetWallpaper);
		llDownloadWallpaper = (LinearLayout) findViewById(R.id.llDownloadWallpaper);
		pbLoader = (ProgressBar) findViewById(R.id.pbLoader);


		utils = new WallpapersUtils(getApplicationContext());

		// layout click listeners
		llSetWallpaper.setOnClickListener(this);
		llDownloadWallpaper.setOnClickListener(this);

		// setting layout buttons alpha/opacity
		llSetWallpaper.getBackground().setAlpha(70);
		llDownloadWallpaper.getBackground().setAlpha(70);

		Intent i = getIntent();
		selectedPhoto = (Wallpaper) i.getSerializableExtra(TAG_SEL_IMAGE);
		selectedPhotoUnsplash = (Photo) i.getParcelableExtra(TAG_SEL_IMAGE_UNSPLASH);

		if (selectedPhoto != null) {
			fetchFullResolutionImage();
		} else if (selectedPhotoUnsplash != null) {
			// show loader before making request
			pbLoader.setVisibility(View.VISIBLE);
			llSetWallpaper.setVisibility(View.GONE);
			llDownloadWallpaper.setVisibility(View.GONE);
			imageLoader.get(selectedPhotoUnsplash.getUrls().getRegular(),
					new ImageLoader.ImageListener() {

						@Override
						public void onErrorResponse(
								VolleyError arg0) {
							Toast.makeText(
									getApplicationContext(),
									getString(R.string.msg_wall_fetch_error),
									Toast.LENGTH_LONG).show();
						}

						@Override
						public void onResponse(
								ImageLoader.ImageContainer response,
								boolean arg1) {
							if (response.getBitmap() != null) {
								// load bitmap into imageview
								fullImageView
										.setImageBitmap(response
												.getBitmap());

								// hide loader and show set &
								// download buttons
								pbLoader.setVisibility(View.GONE);
								llSetWallpaper
										.setVisibility(View.VISIBLE);
								llDownloadWallpaper
										.setVisibility(View.VISIBLE);

								}
						}
					});
		} else {
			Toast.makeText(getApplicationContext(),
					getString(R.string.msg_unknown_error), Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * Fetching image fullresolution json
	 * */
	private void fetchFullResolutionImage() {
		String url = selectedPhoto.getPhotoJson();

		// show loader before making request
		pbLoader.setVisibility(View.VISIBLE);
		llSetWallpaper.setVisibility(View.GONE);
		llDownloadWallpaper.setVisibility(View.GONE);

		// volley's json obj request
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(  url,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG,
								"Image full resolution json: "
										+ response.toString());
						try {
							// Parsing the json response
							JSONObject entry = response
									.getJSONObject(TAG_ENTRY);

							JSONArray mediacontentArry = entry.getJSONObject(
									TAG_MEDIA_GROUP).getJSONArray(
									TAG_MEDIA_CONTENT);

							JSONObject mediaObj = (JSONObject) mediacontentArry
									.get(0);

							String fullResolutionUrl = mediaObj
									.getString(TAG_IMG_URL);

							// image full resolution widht and height
							final int width = mediaObj.getInt(TAG_IMG_WIDTH);
							final int height = mediaObj.getInt(TAG_IMG_HEIGHT);

							Log.d(TAG, "Full resolution image. url: "
									+ fullResolutionUrl + ", w: " + width
									+ ", h: " + height);



							// We download image into ImageView instead of
							// NetworkImageView to have callback methods
							// Currently NetworkImageView doesn't have callback
							// methods

							imageLoader.get(fullResolutionUrl,
									new ImageLoader.ImageListener() {

										@Override
										public void onErrorResponse(
												VolleyError arg0) {
											Toast.makeText(
													getApplicationContext(),
													getString(R.string.msg_wall_fetch_error),
													Toast.LENGTH_LONG).show();
										}

										@Override
										public void onResponse(
												ImageLoader.ImageContainer response,
												boolean arg1) {
											if (response.getBitmap() != null) {
												// load bitmap into imageview
												fullImageView
														.setImageBitmap(response
																.getBitmap());
												adjustImageAspect(width, height);

												// hide loader and show set &
												// download buttons
												pbLoader.setVisibility(View.GONE);
												llSetWallpaper
														.setVisibility(View.VISIBLE);
												llDownloadWallpaper
														.setVisibility(View.VISIBLE);
											}
										}
									});

						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(),
									getString(R.string.msg_unknown_error),
									Toast.LENGTH_LONG).show();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, "Error: " + error.getMessage());
						// unable to fetch wallpapers
						// either google username is wrong or
						// devices doesn't have internet connection
						Toast.makeText(getApplicationContext(),
								getString(R.string.msg_wall_fetch_error),
								Toast.LENGTH_LONG).show();

					}
				});

		// Remove the url from cache
		MyApplication.getInstance().getRequestQueue().getCache().remove(url);

		// Disable the cache for this url, so that it always fetches updated
		// json
		jsonObjReq.setShouldCache(false);

		// Adding request to request queue
		MyApplication.getInstance().addToRequestQueue(jsonObjReq);
	}

	/**
	 * Adjusting the image aspect ration to scroll horizontally, Image height
	 * will be screen height, width will be calculated respected to height
	 * */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void adjustImageAspect(int bWidth, int bHeight) {
		LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		if (bWidth == 0 || bHeight == 0)
			return;

		int sHeight = 0;

		if (android.os.Build.VERSION.SDK_INT >= 13) {
			Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			sHeight = size.y;
		} else {
			Display display = getWindowManager().getDefaultDisplay();
			sHeight = display.getHeight();
		}

		int new_width = (int) Math.floor((double) bWidth * (double) sHeight
				/ (double) bHeight);
		params.width = new_width;
		params.height = sHeight;

		Log.d(TAG, "Fullscreen image new dimensions: w = " + new_width
				+ ", h = " + sHeight);

		fullImageView.setLayoutParams(params);
	}

	/**
	 * View click listener
	 * */
	@Override
	public void onClick(View v) {
		Bitmap bitmap = ((BitmapDrawable) fullImageView.getDrawable())
				.getBitmap();
		switch (v.getId()) {
		// button Download Wallpaper tapped
		case R.id.llDownloadWallpaper:
			utils.saveImageToSDCard(bitmap);
			break;
		// button Set As Wallpaper tapped
		case R.id.llSetWallpaper:
			utils.setAsWallpaper(bitmap);
			break;
		default:
			break;
		}

	}
}