package com.arthur.ngaclient.adapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

	public ReplyListAdapter(Context context, ReplyListData replyListData) {
		mInflater = LayoutInflater.from(context);
		mReplyListData = replyListData;
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
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_replylist, null);
			holder.tvUserName = (TextView) convertView
					.findViewById(R.id.reply_user_name);
			holder.tvReplyDate = (TextView) convertView
					.findViewById(R.id.reply_date);
			holder.tvFloor = (TextView) convertView
					.findViewById(R.id.reply_floor);
			holder.tvContent = (TextView) convertView
					.findViewById(R.id.reply_content);
			holder.ivAvatar = (ImageView) convertView
					.findViewById(R.id.reply_user_avatar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setBackgroundResource(position % 2 == 0 ? R.color.shit2
				: R.color.shit3);
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
		// content = Utils.decodeForumTag(content, true);

		if (content.contains("<img")) {
			new ImageGetTask().execute(holder.tvContent, content);
		}
		// new ImageGetTask().execute(holder.tvContent, content);
		holder.tvContent.setText(Html.fromHtml(content));
		holder.tvFloor.setText("#" + replyData.getLou());
		mImageLoader.displayImage(userInfoData.getAvatar(), holder.ivAvatar,
				options, mAnimateFirstListener);
		return convertView;
	}

	private class ViewHolder {
		public TextView tvUserName;
		public TextView tvReplyDate;
		public TextView tvFloor;
		public TextView tvContent;
		public ImageView ivAvatar;
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

	private class ImageGetTask extends AsyncTask<Object, Integer, CharSequence> {

		private TextView mTextView;

		@Override
		protected CharSequence doInBackground(Object... params) {

			mTextView = (TextView) params[0];
			String content = (String) params[1];
			ImageGetter imgGetter = new Html.ImageGetter() {
				public Drawable getDrawable(String source) {
					Drawable drawable = null;
					URL url = null;
					HttpURLConnection connection = null;
					try {
						if (source.startsWith("file:///android_asset/")) {
							
							source = source.replace("file:///android_asset/", "");
							InputStream is = mTextView.getResources().getAssets().open(source);
							
							drawable = Drawable.createFromStream(is, "local");
						} else {
							url = new URL(source);

							connection = (HttpURLConnection) url
									.openConnection();
							connection.connect();

							drawable = Drawable.createFromStream(
									connection.getInputStream(), "net"); // 获取网路图片
						}
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					} finally {
						if (connection != null) {
							connection.disconnect();
							connection = null;
						}
					}
					if (drawable != null) {
						drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
								drawable.getIntrinsicHeight());
					}
					return drawable;
				}
			};
			Spanned c = Html.fromHtml(content, imgGetter, null);
			return c;
		}

		@Override
		protected void onPostExecute(CharSequence c) {
			if (c != null) {
				mTextView.setText(c);
			}
		}
	}

}
