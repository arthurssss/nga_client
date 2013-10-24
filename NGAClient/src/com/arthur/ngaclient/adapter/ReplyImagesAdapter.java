package com.arthur.ngaclient.adapter;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.util.DensityUtil;
import com.arthur.ngaclient.util.ExtensionEmotionUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ReplyImagesAdapter extends BaseAdapter {

	private Context mContext = null;
	private int mCategoryPosition = -1;
	private LayoutInflater mInflater;

	private ImageLoader mImageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheInMemory(true).build();

	public ReplyImagesAdapter(Context context, int position) {
		mContext = context;
		mCategoryPosition = position;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return ExtensionEmotionUtil.res[mCategoryPosition].length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_reply_images, null);
		}

		ImageView iv = (ImageView) convertView;
		mImageLoader.displayImage(
				"assets://"
						+ ExtensionEmotionUtil.getFilePath(mCategoryPosition,
								position), iv, options);

		convertView
				.setLayoutParams(new android.widget.AbsListView.LayoutParams(
						DensityUtil.dip2px(mContext, 60), DensityUtil.dip2px(
								mContext, 60)));

		convertView
				.setTag(ExtensionEmotionUtil.res[mCategoryPosition][position]);
		return convertView;
	}

}
