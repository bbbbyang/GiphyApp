package com.example.bing.giphyapp.view.gif_fav;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;

import com.example.bing.giphyapp.data.DataBase;
import com.example.bing.giphyapp.data.EntityDbHelper;
import com.example.bing.giphyapp.model.Entity;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by Bing on 2017/6/25.
 */

public class GifFavPresenter {

	//region Variable
	private GifFavView gifFavView;
	private SQLiteDatabase mDb;
	private List<Entity> data;
	//endregion

	//region Constructor
	GifFavPresenter(GifFavView gifFavView) {
		this.gifFavView = gifFavView;
	}
	//endregion

	//region LifeCycle
	public void loadDataFromDataBase(EntityDbHelper dbHelper) {
		data = new ArrayList<>();
		mDb = dbHelper.getWritableDatabase();
		freshDataFromDataBase();
		gifFavView.updateDataBase(data, mDb);
	}

	public void freshDataFromDataBase() {
		AsyncTaskCompat.executeParallel(new GetDataFromDataBaseAsyncTask());
	}
	//endregion

	//region AsyncTask
	class GetDataFromDataBaseAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			Cursor cursor = DataBase.queryALlData(mDb);
			if(cursor.moveToFirst()) {
				while(!cursor.isAfterLast()) {
					Entity entity = new Entity();
					entity._id = cursor.getLong(0);
					entity.username = cursor.getString(1);
					entity.image_url = cursor.getString(2);
					data.add(entity);
					cursor.moveToNext();
				}
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			gifFavView.onPreUI();
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			gifFavView.onPostUI();
		}
	}
	//endregion

	//region Database Operation
	public void insertData(Entity entity) {
		entity._id = DataBase.insertData(mDb, entity);
	}

	public void deleteData(Entity entity) {

		if(DataBase.deleteData(mDb, entity._id)) {
			entity._id = -1;
		}
	}
	//endregion
}
