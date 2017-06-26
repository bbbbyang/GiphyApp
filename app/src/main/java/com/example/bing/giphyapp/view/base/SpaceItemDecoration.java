package com.example.bing.giphyapp.view.base;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Bing on 2017/6/24.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
	private int space;

	public SpaceItemDecoration(int space) {
		this.space = space;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		outRect.left = space;
		outRect.bottom = space;
		outRect.right = space;

		if(parent.getChildAdapterPosition(view) == 0) {
			outRect.top = space;
		}
	}
}
