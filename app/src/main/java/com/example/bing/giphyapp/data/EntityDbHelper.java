package com.example.bing.giphyapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.bing.giphyapp.data.EntityContract.*;

/**
 * Created by Bing on 2017/6/25.
 */

public class EntityDbHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "giphy.db";
	private static final int DATABASE_VERSION = 1;

	public EntityDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + Entity.TABLE_NAME + " (" +

				Entity._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT," +

				Entity.COLUMN_NAME      + " TEXT NOT NULL, "    +

				Entity.COLUMN_IMAGE     + " TEXT DEFAULT NULL" + "); ";

		db.execSQL(SQL_CREATE_WAITLIST_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Entity.TABLE_NAME);
		onCreate(db);
	}
}
