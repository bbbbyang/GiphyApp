package com.example.bing.giphyapp.model;

import android.os.Parcelable;

import java.util.Map;

/**
 * Created by Bing on 2017/6/23.
 */

public class Entity {

	public static final String DOWNSIZED_LARGE = "downsized_large";
	public static final String URL = "url";

	public String username;
	public Map<String, Map<String, String>> images;

	public long _id;
	public String image_url;

	public Entity() {
		_id = -1;
	}

	public void setImage_url(String url) {
		image_url = url;
	}
}
