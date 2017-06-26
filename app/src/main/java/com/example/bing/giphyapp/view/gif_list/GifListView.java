package com.example.bing.giphyapp.view.gif_list;

import android.database.sqlite.SQLiteDatabase;

import com.example.bing.giphyapp.model.Entity;

import java.util.List;

/**
 * Created by Bing on 2017/6/25.
 */

public interface GifListView {

	void appendListAdapter(List<Entity> entityList);

	void makeToast(String toast);

	void updateDatabase(SQLiteDatabase mDb);
}
