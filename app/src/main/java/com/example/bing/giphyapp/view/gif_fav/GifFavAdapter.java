package com.example.bing.giphyapp.view.gif_fav;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bing.giphyapp.R;
import com.example.bing.giphyapp.data.DataBase;
import com.example.bing.giphyapp.model.Entity;
import com.example.bing.giphyapp.view.MainActivity;

import java.util.List;

/**
 * Created by Bing on 2017/6/25.
 */

public class GifFavAdapter extends RecyclerView.Adapter {

	//region Interface
	interface Listener{
		void onFavouriteClick(final Entity entity, final ImageButton button, final SQLiteDatabase mDb);
	}
	//endregion

	//region Constants
	public static final int TYPE_FULL = 0;
	public static final int TYPE_QUARTER = 1;
	//endregion

	private Context context;

	//region Variables
	Listener listener;
	List<Entity> data;
	SQLiteDatabase mDb;
	//endregion

	//region Constructor
	public GifFavAdapter(Context context, List<Entity> data, SQLiteDatabase mDb, Listener listener) {
		this.context = context;
		this.data = data;
		this.mDb = mDb;
		this.listener = listener;
	}
	//endregion

	//region ViewHolder
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
		final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_gif, parent, false);

//		view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//			@Override
//			public boolean onPreDraw() {
//				StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
//				switch (viewType) {
//					case TYPE_FULL:
//						layoutParams.setFullSpan(false);
//						break;
//					case TYPE_QUARTER:
//						layoutParams.setFullSpan(true);
//						layoutParams.height = view.getHeight() / 2;
//						break;
//				}
//				view.setLayoutParams(layoutParams);
//				StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) ((RecyclerView) parent).getLayoutManager();
//				staggeredGridLayoutManager.invalidateSpanAssignments();
//				view.getViewTreeObserver().removeOnPreDrawListener(this);
//				return true;
//			}
//		});
		return new GifFavViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		final Entity entity = data.get(position);
		final GifFavViewHolder gifFavViewHolder = (GifFavViewHolder) holder;


		gifFavViewHolder.userName.setText(!entity.username.equals("") ? entity.username : "GIPHY");
		gifFavViewHolder.gifLike.setImageResource(R.drawable.ic_favorite_blue_a400_18dp);
		if (!"".equals(entity.image_url)) {
			Glide.with(gifFavViewHolder.itemView.getContext()).load(entity.image_url).apply(new RequestOptions().placeholder(R.drawable.gif_placeholder)).into(gifFavViewHolder.shotImage);
		} else {
			gifFavViewHolder.shotImage.setImageResource(R.drawable.gif_placeholder);
		}

		gifFavViewHolder.gifLike.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onFavouriteClick(entity, gifFavViewHolder.gifLike, mDb);
			}
		});
	}
	//endregion

	//region Item Operations
	@Override
	public int getItemViewType(int position) {
		int modeFive = position % 5;
		if (modeFive == 0) {
				return TYPE_FULL;
		} else {
			return TYPE_QUARTER;
		}
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	public void resetData() {
		data.clear();
		notifyDataSetChanged();
	}
	//endregion
}
