package com.example.bing.giphyapp.view.gif_fav;

import android.database.sqlite.SQLiteDatabase;

import com.example.bing.giphyapp.model.Entity;

import java.util.List;

/**
 * Created by Bing on 2017/6/25.
 */

public interface GifFavView {

	void updateDataBase(List<Entity> data, SQLiteDatabase mDb);

	void onPreUI();

	void onPostUI();
}
