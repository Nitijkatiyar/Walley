package com.tecbeast.hdwallpapers.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tecbeast.hdwallpapers.MyApplication;
import com.tecbeast.hdwallpapers.R;
import com.tecbeast.hdwallpapers.model.Category;
import com.tecbeast.hdwallpapers.utils.AppConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final String TAG_FEED = "feed",
            TAG_GPHOTO_ID = "gphoto$id",
            TAG_ALBUM_TITLE = "title", TAG_ENTRY = "entry",
            TAG_MEDIA_GROUP = "media$group",
            TAG_MEDIA_CONTENT = "media$content", TAG_IMG_URL = "url",
            TAG_IMG_WIDTH = "width", TAG_IMG_HEIGHT = "height", TAG_ID = "id",
            TAG_T = "$t";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//		getActionBar().hide();
        setContentView(R.layout.activity_splash);

        // Picasa request to get list of albums
        String url = AppConst.URL_PICASA_ALBUMS;

        Log.d(TAG, "Albums request url: " + url);

        // Preparing volley's json object request
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Albums Response: " + response.toString());
                List<Category> albums = new ArrayList<Category>();

                try {
                    // Parsing the json response
                    JSONArray entry = response.getJSONObject(TAG_FEED)
                            .getJSONArray(TAG_ENTRY);

                    // loop through albums nodes and add them to album
                    // list
                    for (int i = 0; i < entry.length(); i++) {
                        JSONObject albumObj = (JSONObject) entry.get(i);
                        // album id
                        String albumId = albumObj.getJSONObject(
                                TAG_GPHOTO_ID).getString(TAG_T);

                        // album title
                        String albumTitle = albumObj.getJSONObject(
                                TAG_ALBUM_TITLE).getString(TAG_T);

                        Category album = new Category();
                        album.setId(albumId);
                        album.setTitle(albumTitle);

                        // add album to list
                        albums.add(album);

                        Log.d(TAG, "Album Id: " + albumId
                                + ", Album Title: " + albumTitle);
                    }

                    // Store albums in shared pref
                    MyApplication.getInstance().getPrefManger()
                            .storeCategories(albums);

                    // String the main activity
                    Intent intent = new Intent(getApplicationContext(),
                            com.tecbeast.hdwallpapers.activities.MainActivity.class);
                    startActivity(intent);
                    // closing spalsh activity
                    finish();

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
                Log.e(TAG, "Volley Error: " + error.getMessage());

                // show error toast
                Toast.makeText(getApplicationContext(),
                        getString(R.string.splash_error),
                        Toast.LENGTH_LONG).show();

                // Unable to fetch albums
                // check for existing Albums data in Shared Preferences
                if (MyApplication.getInstance().getPrefManger()
                        .getCategories() != null && MyApplication.getInstance().getPrefManger()
                        .getCategories().size() > 0) {
                    // String the main activity
                    Intent intent = new Intent(getApplicationContext(),
                            com.tecbeast.hdwallpapers.activities.MainActivity.class);
                    startActivity(intent);
                    // closing spalsh activity
                    finish();
                }

            }
        });

        // disable the cache for this request, so that it always fetches updated
        // json
        jsonObjReq.setShouldCache(false);

        // Making the request
        MyApplication.getInstance().addToRequestQueue(jsonObjReq);

    }

}
