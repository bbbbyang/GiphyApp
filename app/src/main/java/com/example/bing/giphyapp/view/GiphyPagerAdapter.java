package com.example.bing.giphyapp.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.example.bing.giphyapp.view.gif_fav.GifFavFragment;
import com.example.bing.giphyapp.view.gif_list.GifListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bing on 2017/6/23.
 */

public class GiphyPagerAdapter extends FragmentPagerAdapter {

	public final List<Fragment> mFragmentList = new ArrayList<>();

	public GiphyPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return mFragmentList.get(position);
	}

	@Override
	public int getCount() {
		return mFragmentList.size();
	}

	public void addFragment(Fragment fragment) {
		mFragmentList.add(fragment);
	}

	@Override
	public int getItemPosition(Object object) {
		return PagerAdapter.POSITION_NONE;
	}

}
