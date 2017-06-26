package com.example.bing.giphyapp.view.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bing.giphyapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Bing on 2017/6/25.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
	public @BindView(R.id.shot_image) ImageView shotImage;
	public @BindView(R.id.user_name) TextView userName;
	public @BindView(R.id.gif_like) ImageButton gifLike;
	public BaseViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}
}
