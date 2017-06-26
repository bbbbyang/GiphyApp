package com.example.bing.giphyapp.view.gif_fav;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.bing.giphyapp.R;
import com.example.bing.giphyapp.data.DataBase;
import com.example.bing.giphyapp.data.EntityDbHelper;
import com.example.bing.giphyapp.model.Entity;
import com.example.bing.giphyapp.view.MainActivity;
import com.example.bing.giphyapp.view.base.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bing on 2017/6/25.
 */

public class GifFavFragment extends Fragment implements MainActivity.FavouriteRefreshListener,
		GifFavView,
		GifFavAdapter.Listener{

	//region Views
	@BindView(R.id.recycler_view_fav) RecyclerView recyclerViewFav;
	@BindView(R.id.progress_bar) ProgressBar progressBar;
	//endregion

	//region Variables
	private EntityDbHelper dbHelper;
	private GifFavAdapter favAdapter;
	private GifFavPresenter presenter;
	//endregion

	//region Instance
	public static GifFavFragment newInstance() {
		return new GifFavFragment();
	}
	//endregion

	//region Lifecycle
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recycler_view, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		presenter = new GifFavPresenter(this);

		recyclerViewFav.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.spacing_medium)));
		recyclerViewFav.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

		dbHelper = new EntityDbHelper(getActivity());

		presenter.loadDataFromDataBase(dbHelper);

		if(favAdapter != null) {
			recyclerViewFav.setAdapter(favAdapter);
		}

		((MainActivity) getActivity()).setFavouriteRefreshListener(this);

	}
	//endregion

	//region Listeners
	@Override
	public void onRefresh() {
		favAdapter.resetData();
		presenter.freshDataFromDataBase();
	}
	//endregion

	//region Implementations
	@Override
	public void updateDataBase(List<Entity> data, SQLiteDatabase mDb) {
		favAdapter = new GifFavAdapter(getContext(), data, mDb, this);
	}

	@Override
	public void onPreUI() {
		progressBar.setVisibility(View.VISIBLE);
		recyclerViewFav.setVisibility(View.GONE);
	}

	@Override
	public void onPostUI() {
		recyclerViewFav.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
	}

	@Override
	public void onFavouriteClick(final Entity entity, final ImageButton button, final SQLiteDatabase mDb) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(entity._id == -1) {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							button.setImageResource(R.drawable.ic_favorite_blue_a400_18dp);
						}
					});
					presenter.insertData(entity);
				} else {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							button.setImageResource(R.drawable.ic_favorite_grey_18dp);
						}
					});
					presenter.deleteData(entity);
				}
			}
		}).start();
	}

	//endregion
}
