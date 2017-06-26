package com.example.bing.giphyapp.utils;

import com.example.bing.giphyapp.model.Entity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Bing on 2017/6/23.
 */

public class ModelUtils {
	private static Gson gson = new Gson();

	public static <T> T toObject(String json, TypeToken<T> typeToken) {
		return gson.fromJson(json, typeToken.getType());
	}

	public static <T> String toString(T object, TypeToken<T> typeToken) {
		return gson.toJson(object, typeToken.getType());
	}
}