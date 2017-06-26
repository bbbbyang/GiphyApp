package com.example.bing.giphyapp.view;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bing.giphyapp.R;
import com.example.bing.giphyapp.view.gif_fav.GifFavFragment;
import com.example.bing.giphyapp.view.gif_list.GifListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

	//region Interface
	public interface FragmentResetListener {
		void onReset();
	}
	public interface FavouriteRefreshListener {
		void onRefresh();
	}
	//endregion

	//region Constants
	public static final int KEY_HOME = 0;
	public static final int KEY_FAVORITE = 1;
	//endregion
	//region Views
	@BindView(R.id.view_pager) ViewPager viewPager;
	@BindView(R.id.view_pager_tab) TabLayout tabLayout;
	@BindView(R.id.toolbar) Toolbar toolbar;
	//endregion

	//region Variable
	GiphyPagerAdapter giphyPagerAdapter;
	private FragmentResetListener fragmentResetListener;
	private FavouriteRefreshListener favouriteRefreshListener;
	private int currentPage = -1;
	//endregion

	//region Lifecycle
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ButterKnife.bind(this);

		toolbar.setTitle(R.string.labbel_giphy);
		setSupportActionBar(toolbar);

		setupViewPager();

		viewPager.addOnPageChangeListener(this);
	}
	//endregion

	//region Private Helper
	private void setupViewPager() {
		giphyPagerAdapter = new GiphyPagerAdapter(getSupportFragmentManager());
		giphyPagerAdapter.addFragment(GifListFragment.newInstance());
		giphyPagerAdapter.addFragment(GifFavFragment.newInstance());
		viewPager.setAdapter(giphyPagerAdapter);
		tabLayout.setupWithViewPager(viewPager, false);

		TabLayout.Tab tab0 = tabLayout.getTabAt(0);
		TabLayout.Tab tab1 = tabLayout.getTabAt(1);

		if(tab0 != null) tab0.setIcon(R.drawable.ic_home_white_24dp);
		if(tab1 != null) tab1.setIcon(R.drawable.ic_favorite_white_24dp);
		tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

	}
	//endregion

	//region Menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.toolbar_reset) {
			toolbar.setTitle(R.string.labbel_giphy);
			switch (currentPage) {
				case KEY_HOME:
					if(getFragmentResetListener() != null) {
						fragmentResetListener.onReset();
					}
					break;
				case KEY_FAVORITE:
					if(getFavouriteRefreshListener() != null) {
						favouriteRefreshListener.onRefresh();
					}
			}
		}
		return super.onOptionsItemSelected(item);
	}
	//endregion

	//region Getters and Setters
	public FragmentResetListener getFragmentResetListener() {
		return fragmentResetListener;
	}

	public void setFragmentResetListener(FragmentResetListener fragmentResetListener) {
		this.fragmentResetListener = fragmentResetListener;
	}
	public FavouriteRefreshListener getFavouriteRefreshListener() {
		return favouriteRefreshListener;
	}

	public void setFavouriteRefreshListener(FavouriteRefreshListener favouriteRefreshListener) {
		this.favouriteRefreshListener = favouriteRefreshListener;
	}
	//endregion


	//region Listener
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		currentPage = position;
		if(position == 0) {
			toolbar.setTitle(R.string.labbel_giphy);
		} else {
			toolbar.setTitle(R.string.labbel_favourite);
		}
		giphyPagerAdapter.notifyDataSetChanged();
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
	//endregion
}
