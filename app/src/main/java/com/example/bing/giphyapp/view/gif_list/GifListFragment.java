package com.example.bing.giphyapp.view.gif_list;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bing.giphyapp.R;
import com.example.bing.giphyapp.data.DataBase;
import com.example.bing.giphyapp.data.EntityDbHelper;
import com.example.bing.giphyapp.giphy.Giphy;
import com.example.bing.giphyapp.model.Entity;
import com.example.bing.giphyapp.view.MainActivity;
import com.example.bing.giphyapp.view.base.SpaceItemDecoration;
import com.example.bing.giphyapp.view.gif_fav.GifFavView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bing on 2017/6/23.
 */

public class GifListFragment extends Fragment implements GifListView,
		MainActivity.FragmentResetListener,
		GifListAdapter.LoadMoreListener,
		SearchView.OnQueryTextListener,
		GifListAdapter.Listener {


	//region Views
	@BindView(R.id.recycler_view) RecyclerView recyclerView;
	@BindView(R.id.search_gif) SearchView searchView;
	//endregion

	//region Variables
	private GifListAdapter listAdapter;
	private GifListPresenter presenter;
	//endregion

	//region Instance
	public static GifListFragment newInstance() {
		GifListFragment gifListFragment = new GifListFragment();
		return gifListFragment;
	}
	//endregion

	//region Lifecycle
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_recycler, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		presenter = new GifListPresenter(this);

		recyclerView.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.spacing_medium)));
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		EntityDbHelper dbHelper = new EntityDbHelper(getActivity());

		presenter.loadDataFromDataBase(dbHelper);

		if (listAdapter != null) {
			listAdapter.setLoadMoreListener(this);
			listAdapter.setLoadMoreType(Giphy.TRENDING, null);

			recyclerView.setAdapter(listAdapter);
		}

		searchView.setSubmitButtonEnabled(true);
		searchView.setOnQueryTextListener(this);



		((MainActivity) getActivity()).setFragmentResetListener(this);

	}
	//endregion

	//region Implementations
	@Override
	public void appendListAdapter(List<Entity> entityList) {
		listAdapter.append(entityList);
	}

	@Override
	public void makeToast(String toast) {
		Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
	}

	@Override
	public void updateDatabase(SQLiteDatabase mDb) {
		listAdapter = new GifListAdapter(new ArrayList<Entity>(), mDb, this);
	}
	//endregion


	//region Listeners
	@Override
	public void onReset() {
		listAdapter.resetData();
		listAdapter.setLoadMoreType(Giphy.TRENDING, null);
	}

	@Override
	public void onLoadMore(String loadType, String query) {
		presenter.onLoadMore(listAdapter.getDataCount(), loadType, query);
	}

	@Override
	public boolean onQueryTextSubmit(final String query) {
		((MainActivity) getActivity()).getSupportActionBar().setTitle(query);
		listAdapter.resetData();
		listAdapter.setLoadMoreType(Giphy.SEARCH, query);

		searchView.clearFocus();
		searchView.onActionViewCollapsed();
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
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
					(getActivity()).runOnUiThread(new Runnable() {
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
