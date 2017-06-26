package com.example.bing.giphyapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.bing.giphyapp.model.Entity;

/**
 * Created by Bing on 2017/6/25.
 */

public class DataBase {

	public static long insertData(SQLiteDatabase db, Entity entity) {
		if (db == null) {
			return -1;
		}

		long _id = -1;

		ContentValues cv = new ContentValues();
		cv.put(EntityContract.Entity.COLUMN_NAME, entity.username);
		cv.put(EntityContract.Entity.COLUMN_IMAGE, entity.image_url);

		try {
			db.beginTransaction();
			_id = db.insert(EntityContract.Entity.TABLE_NAME, null, cv);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

		return _id;
	}

	public static boolean deleteData(SQLiteDatabase db, long _id) {
		return db.delete(EntityContract.Entity.TABLE_NAME, EntityContract.Entity._ID + "=" + _id, null) > 0;
	}

	public static long checkDataBaseRecord(SQLiteDatabase db, Entity entity) {

		String[] selectionArguments = new String[]{entity.image_url};
		String[] projection = new String[]{EntityContract.Entity._ID};

		Cursor cursor = db.query(
				EntityContract.Entity.TABLE_NAME,
				projection,
				EntityContract.Entity.COLUMN_IMAGE + " = ? ",
				selectionArguments,
				null,
				null,
				null);
		if(cursor.getCount() <= 0) {
			cursor.close();
			return -1;
		} else {
			cursor.moveToFirst();
			long _id = cursor.getLong(0);
			cursor.close();
			return _id;
		}

	}

	public static Cursor queryALlData(SQLiteDatabase db) {
		String[] projection = new String[]{EntityContract.Entity._ID, EntityContract.Entity.COLUMN_NAME, EntityContract.Entity.COLUMN_IMAGE};

		Cursor cursor = db.query(
				EntityContract.Entity.TABLE_NAME,
				projection,
				null,
				null,
				null,
				null,
				null);
		Cursor result = cursor;
//		cursor.close();
		return result;
	}


}
