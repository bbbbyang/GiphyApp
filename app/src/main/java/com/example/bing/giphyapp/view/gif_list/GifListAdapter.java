package com.example.bing.giphyapp.view.gif_list;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bing.giphyapp.R;
import com.example.bing.giphyapp.data.DataBase;
import com.example.bing.giphyapp.model.Entity;
import com.example.bing.giphyapp.view.MainActivity;

import java.util.List;

/**
 * Created by Bing on 2017/6/24.
 */

public class GifListAdapter extends RecyclerView.Adapter {

	//region Interface
	interface Listener {
		void onFavouriteClick(final Entity entity, final ImageButton button, final SQLiteDatabase mDb);
	}

	public interface LoadMoreListener {
		void onLoadMore(String loadType, String query);
	}
	//endregion

	//region Constants
	private static final int VIEW_TYPE_SHOT = 1;
	private static final int VIEW_TYPE_LOADING = 2;
	//endregion

	//region Variables
	private List<Entity> data;
	private LoadMoreListener loadMoreListener;
	private SQLiteDatabase mDb;
	private String loadMoreType;
	private String query;
	private Listener listener;
	//endregion

	//region Constructor
	public GifListAdapter(List<Entity> data, SQLiteDatabase mDb, Listener listener) {
		this.data = data;
		this.mDb = mDb;
		this.listener = listener;
	}
	//endregion

	//region Public Helper
	public void setLoadMoreType(String loadMoreType, String query) {
		this.loadMoreType = loadMoreType;
		this.query = query;
	}
	//endregion

	//region ViewHolder
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if(viewType == VIEW_TYPE_SHOT) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_gif, parent, false);
			return new GifListViewHolder(view);
		} else {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_loading, parent, false);
			return new RecyclerView.ViewHolder(view) {};
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		int viewType = getItemViewType(position);

		if (viewType == VIEW_TYPE_LOADING) {
			if(getLoadMoreListener() != null) {
				loadMoreListener.onLoadMore(loadMoreType, query);
			}
		} else {
			final Entity entity = data.get(position);

			final GifListViewHolder gifListViewHolder = (GifListViewHolder) holder;
			gifListViewHolder.userName.setText(!entity.username.equals("") ? entity.username : "GIPHY");
			gifListViewHolder.gifLike.setImageResource(R.drawable.ic_favorite_grey_18dp);

			String imageURL = entity.images.get(Entity.DOWNSIZED_LARGE).get(Entity.URL);
			entity.setImage_url(imageURL);

			entity._id = DataBase.checkDataBaseRecord(mDb, entity);
			if(entity._id != -1) {
				gifListViewHolder.gifLike.setImageResource(R.drawable.ic_favorite_blue_a400_18dp);
			}

			if (!"".equals(imageURL)) {
				Glide.with(gifListViewHolder.itemView.getContext()).load(imageURL).apply(new RequestOptions().placeholder(R.drawable.gif_placeholder)).into(gifListViewHolder.shotImage);
			} else {
				gifListViewHolder.shotImage.setImageResource(R.drawable.gif_placeholder);
			}

			gifListViewHolder.gifLike.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onFavouriteClick(entity, gifListViewHolder.gifLike, mDb);
				}
			});
		}
	}
	//endregion

	//region Data Item Operations
	@Override
	public int getItemCount() {
		return data.size() + 1;
	}

	@Override
	public int getItemViewType(int position) {
		return position < data.size() ? VIEW_TYPE_SHOT : VIEW_TYPE_LOADING;
	}

	public void append(@NonNull List<Entity> moreShots) {
		data.addAll(moreShots);
		notifyDataSetChanged();
	}
	//endregion

	public int getDataCount() {
		return data.size();
	}

	public void resetData() {
		data.clear();
		notifyDataSetChanged();
	}

	//region Setters and Getters
	public LoadMoreListener getLoadMoreListener() {
		return loadMoreListener;
	}

	public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
		this.loadMoreListener = loadMoreListener;
	}
	//endregion
}
