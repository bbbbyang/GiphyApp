package com.example.bing.giphyapp.view.gif_list;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.widget.Toast;

import com.example.bing.giphyapp.data.DataBase;
import com.example.bing.giphyapp.data.EntityDbHelper;
import com.example.bing.giphyapp.giphy.Giphy;
import com.example.bing.giphyapp.model.Entity;

import java.util.List;

/**
 * Created by Bing on 2017/6/25.
 */

public class GifListPresenter {
	//region Variable
	private GifListView gifListView;
	private SQLiteDatabase mDb;
	//endregion

	//region Constructor
	GifListPresenter(GifListView gifListView) {
		this.gifListView = gifListView;
	}
	//endregion

	//region LifeCycle
	public void loadDataFromDataBase(EntityDbHelper dbHelper) {

		mDb = dbHelper.getWritableDatabase();
		gifListView.updateDatabase(mDb);
	}


	public void onLoadMore(int count, String loadType, String query) {

		if (loadType == Giphy.TRENDING) {
			AsyncTaskCompat.executeParallel(new GiphyParsingAsyncTask(count), Giphy.TRENDING, null);
		} else if (loadType == Giphy.SEARCH) {
			AsyncTaskCompat.executeParallel(new GiphyParsingAsyncTask(count), Giphy.SEARCH, query);
		}

	}
	//endregion

	//region AsyncTask
	public class GiphyParsingAsyncTask extends AsyncTask<String, String, List<Entity>> {

		int offset;

		GiphyParsingAsyncTask(int offset) {
			this.offset = offset;
		}

		@Override
		protected List<Entity> doInBackground(String... params) {

			String KEY_ACTION = params[0];
			String query = params[1];

			List<Entity> entityList;
			entityList = new Giphy().getGiphy(KEY_ACTION, query, offset);
			return entityList;
		}

		@Override
		protected void onPostExecute(List<Entity> entityList) {
			if(entityList != null) {
				gifListView.appendListAdapter(entityList);
			} else {
				gifListView.makeToast("No Gif Found");
			}
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
