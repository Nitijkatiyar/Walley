package com.tecbeast.hdwallpapers.picasawallpapers.util;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

public class JsonRequestActivity extends Activity {

	private String TAG = JsonRequestActivity.class.getSimpleName();
    private Button btnJsonObj, btnJsonArray;
	private TextView msgResponse;
	private ProgressDialog pDialog;

	// These tags will be used to cancel the requests
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	 
	}

	 

	/**
	 * Making json object request
	 * */
	private void makeJsonObjReq() {
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(
				"", null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, response.toString());
						msgResponse.setText(response.toString());
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
					}
				}) {

			/**
			 * Passing some request headers
			 * */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				return headers;
			}

			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("name", "Androidhive");
				params.put("email", "abc@androidhive.info");
				params.put("pass", "password123");

				return params;
			}

		};



		// Cancelling request
		// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);		
	}

	/**
	 * Making json array request
	 * */
	private void makeJsonArryReq() {

		JsonArrayRequest req = new JsonArrayRequest("",
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						msgResponse.setText(response.toString());

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());

					}
				});


	}



}
