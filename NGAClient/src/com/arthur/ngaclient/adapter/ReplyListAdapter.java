package com.arthur.ngaclient.adapter;

import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.bean.ReplyData;
import com.arthur.ngaclient.bean.ReplyListData;
import com.arthur.ngaclient.bean.UserInfoData;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ReplyListAdapter extends BaseAdapter {

	private LayoutInflater mInflater = null;
	private ReplyListData mReplyListData = null;
	private ImageLoader mImageLoader = ImageLoader.getInstance();
	private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheInMemory(true).cacheOnDisc(true)
			.displayer(new RoundedBitmapDisplayer(5)).build();

	private final SparseArray<SoftReference<View>> mViewCache;

	public ReplyListAdapter(Context context, ReplyListData replyListData) {
		mInflater = LayoutInflater.from(context);
		mReplyListData = replyListData;
		mViewCache = new SparseArray<SoftReference<View>>();
	}

	@Override
	public int getCount() {
		return mReplyListData.get__R().size();
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
		SoftReference<View> srf = mViewCache.get(position);
		View cacheView = null;
		if (srf != null) {
			cacheView = srf.get();
		}
		if (cacheView != null) {
			return cacheView;
		}
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_replylist, null);
			holder.setViewHolder(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
			if (holder.position == position) {
				return convertView;
			}
			holder.tvContent.stopLoading();
			if (holder.tvContent.getHeight() > 300) {
				mViewCache.put(holder.position, new SoftReference<View>(
						convertView));
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_replylist, null);
				holder.setViewHolder(convertView);
			}
		}

		holder.position = position;

		convertView.setBackgroundResource(position % 2 == 0 ? R.color.shit2_1
				: R.color.shit2_2);
		Map<String, ReplyData> replyList = mReplyListData.get__R();
		Map<String, UserInfoData> userInfoList = mReplyListData.get__U();
		ReplyData replyData = replyList.get(position + "");
		int authorId = replyData.getAuthorid();
		UserInfoData userInfoData = userInfoList.get(authorId + "");
		holder.tvUserName.setText(userInfoData.getUsername());
		holder.tvReplyDate.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm",
				Locale.getDefault()).format(new Date(replyData
				.getPostdatetimestamp() * 1000)));

		String content = replyData.getHtmlContent();

		if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.FROYO) {
			holder.tvContent.setLongClickable(false);
		}
		holder.tvContent.setFocusableInTouchMode(false);
		holder.tvContent.setFocusable(false);
		holder.tvContent.setBackgroundColor(Color.parseColor("#00000000"));
		
		WebSettings setting = holder.tvContent.getSettings();
//		setting.setBlockNetworkImage(!false);
		setting.setDefaultFontSize(14);
		setting.setJavaScriptEnabled(false);
		holder.tvContent.loadDataWithBaseURL(null, content, "text/html",
				"utf-8", null);
//		holder.tvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		holder.tvFloor.setText("#" + replyData.getLou());
		mImageLoader.displayImage(userInfoData.getAvatar(), holder.ivAvatar,
				options, mAnimateFirstListener);
		return convertView;
	}

	private class ViewHolder {
		public TextView tvUserName;
		public TextView tvReplyDate;
		public TextView tvFloor;
		public WebView tvContent;
		public ImageView ivAvatar;
		public int position;

		public void setViewHolder(View convertView) {
			this.tvUserName = (TextView) convertView
					.findViewById(R.id.reply_user_name);
			this.tvReplyDate = (TextView) convertView
					.findViewById(R.id.reply_date);
			this.tvFloor = (TextView) convertView
					.findViewById(R.id.reply_floor);
			this.tvContent = (WebView) convertView
					.findViewById(R.id.reply_content);
			this.ivAvatar = (ImageView) convertView
					.findViewById(R.id.reply_user_avatar);
			convertView.setTag(this);
		}
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> mDisplayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !mDisplayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					mDisplayedImages.add(imageUri);
				}
			}
		}
	}

}
