package com.arthur.ngaclient.adapter;

import com.arthur.ngaclient.util.ExtensionEmotionUtil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ReplyImagesCategoryAdapter extends BaseAdapter {

	private Context mContext;

	public ReplyImagesCategoryAdapter(Context c) {
		mContext = c;
	}

	// 获取图片的个数
	@Override
	public int getCount() {
		return ExtensionEmotionUtil.resCategory.length;
	}

	// 获取图片在库中的位置
	@Override
	public Object getItem(int position) {
		return position;
	}

	// 获取图片ID
	@Override
	public long getItemId(int position) {
		return ExtensionEmotionUtil.resCategory[position];
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

		imageView.setImageResource(ExtensionEmotionUtil.resCategory[position]);

		return imageView;
	}

}