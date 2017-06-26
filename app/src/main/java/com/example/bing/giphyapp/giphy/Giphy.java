package com.example.bing.giphyapp.giphy;

import android.util.Log;
import android.widget.Toast;

import com.example.bing.giphyapp.model.Entity;
import com.example.bing.giphyapp.utils.ModelUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.bing.giphyapp.utils.ModelUtils.toObject;

/**
 * Created by Bing on 2017/6/24.
 */

public class Giphy {

	private static final String TAG = "GiphyTAG";

	private static final String API_KEY_CODE = "c84e3c066a5147f9a66996b7efca266e";
	private static final String API_URL = "https://api.giphy.com/v1/gifs/";

	public static final String TRENDING = "trending";
	public static final String SEARCH = "search";

	private static final String KEY_LIMITE = "limit";
	private static final String KEY_API = "api_key";
	private static final String KEY_QUERY = "q";
	private static final String KEY_OFFSET = "offset";
	private static final int KEY_NUM = 10;

	public interface GiphyService {
		@GET(TRENDING)
		Call<ResponseBody> getTrendingGif(@Query(KEY_API) String codeAPI,
		                                  @Query(KEY_LIMITE) int count,
		                                  @Query(KEY_OFFSET) int offset);

		@GET(SEARCH)
		Call<ResponseBody> getSearchGif(@Query(KEY_API) String codeAPI,
		                                @Query(KEY_QUERY) String query,
		                                @Query(KEY_LIMITE) int count,
		                                @Query(KEY_OFFSET) int offset);
	}

	public GiphyService getGiphyService() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(API_URL)
				.build();
		GiphyService giphyService = retrofit.create(GiphyService.class);
		return giphyService;
	}

	public List<Entity> getGiphy(String KEY_ACTION, String query, int offset) {

		Call<ResponseBody> call = null;

		switch (KEY_ACTION) {
			case TRENDING:
				call = getGiphyService().getTrendingGif(API_KEY_CODE, KEY_NUM, offset);
				break;
			case SEARCH:
				call = getGiphyService().getSearchGif(API_KEY_CODE, query, KEY_NUM, offset);
		}

		try {
			Response<ResponseBody> response = call.execute();
			String responseString = response.body().string();
			JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
			JsonArray jsonArray = jsonObject.getAsJsonArray("data");
			List<Entity> entities = ModelUtils.toObject(jsonArray.toString(), new TypeToken<List<Entity>>() {});

			return entities;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
