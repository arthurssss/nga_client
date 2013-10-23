package com.arthur.ngaclient.adapter;

import com.arthur.ngaclient.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ReplyImagesCategoryAdapter extends BaseAdapter {
	// 定义Context
	private Context mContext;
	// 定义整型数组 即图片源
	private int[] mImageIds;

	public ReplyImagesCategoryAdapter(Context c) {
		mContext = c;
		mImageIds = new int[] { R.drawable.acniang, R.drawable.ali,
				R.drawable.baozou, R.drawable.bierde, R.drawable.dayanmao,
				R.drawable.luoxiaohei, R.drawable.yangcongtou,
				R.drawable.zhaiyin };
	}

	// 获取图片的个数
	@Override
	public int getCount() {
		return mImageIds.length;
	}

	// 获取图片在库中的位置
	@Override
	public Object getItem(int position) {
		return position;
	}

	// 获取图片ID
	@Override
	public long getItemId(int position) {
		return mImageIds[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			// 设置图片n×n显示
			imageView.setLayoutParams(new GridView.LayoutParams(160, 160));
			// 设置显示比例类型
			imageView.setScaleType(ImageView.ScaleType.CENTER);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(mImageIds[position]);

		return imageView;
	}

}