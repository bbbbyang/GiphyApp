package com.example.bing.giphyapp.data;

import android.provider.BaseColumns;

/**
 * Created by Bing on 2017/6/25.
 */

public class EntityContract {
	public static final class Entity implements BaseColumns {

		public static final String TABLE_NAME = "favourite";
		public static final String COLUMN_NAME = "username";
		public static final String COLUMN_IMAGE = "image";
	}
}
