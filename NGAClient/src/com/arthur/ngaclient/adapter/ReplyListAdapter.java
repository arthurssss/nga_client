package com.arthur.ngaclient.adapter;

import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.activity.ImageViewActivity;
import com.arthur.ngaclient.bean.CommentData;
import com.arthur.ngaclient.bean.ReplyData;
import com.arthur.ngaclient.bean.ReplyListData;
import com.arthur.ngaclient.bean.UserInfoData;
import com.arthur.ngaclient.util.ReplyWebViewClient;
import com.arthur.ngaclient.util.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

	private static final String TAG = ReplyListAdapter.class.getSimpleName();
	private Context mContext = null;
	private LayoutInflater mInflater = null;
	private ReplyListData mReplyListData = null;
	private ImageLoader mImageLoader = ImageLoader.getInstance();
	private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheInMemory(true).cacheOnDisc(true)
			.showImageForEmptyUri(R.drawable.ic_nga_avatar)
			.showImageOnFail(R.drawable.ic_nga_avatar)
			.displayer(new RoundedBitmapDisplayer(5)).build();

	private final SparseArray<SoftReference<View>> mViewCache;

	public ReplyListAdapter(Context context, ReplyListData replyListData) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mReplyListData = replyListData;
		mViewCache = new SparseArray<SoftReference<View>>();
	}

	@Override
	public int getCount() {
		return mReplyListData.get__R__ROWS();
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
		if (replyData.getSubject() != null
				&& !replyData.getSubject().equals("")) {
			holder.tvTitle.setText("»" + Html.fromHtml(replyData.getSubject()));
			holder.tvTitle.setVisibility(View.VISIBLE);
		} else {
			holder.tvTitle.setVisibility(View.GONE);
		}

		String content = replyData.getHtmlContent() + buildComment(replyData);
		holder.tvContent.loadDataWithBaseURL(null, content, "text/html",
				"utf-8", null);

		holder.tvFloor.setText("#" + replyData.getLou());

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		Boolean isLoadImage = prefs.getBoolean("is_load_avatar", false);
		if (Utils.getNetworkType(mContext) == Utils.NetworkType.MOBILE
				&& !isLoadImage) {
			mImageLoader.denyNetworkDownloads(true);
		} else {
			mImageLoader.denyNetworkDownloads(false);
		}

		holder.ivAvatar.setTag(userInfoData.getAvatar());
		mImageLoader.displayImage(parseAvatarUrl(userInfoData.getAvatar()),
				holder.ivAvatar, options, mAnimateFirstListener);
		return convertView;
	}

	private class ViewHolder {
		public TextView tvUserName;
		public TextView tvReplyDate;
		public TextView tvFloor;
		public WebView tvContent;
		public TextView tvTitle;
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
			this.tvTitle = (TextView) convertView
					.findViewById(R.id.reply_title);

			if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.FROYO) {
				this.tvContent.setLongClickable(false);
			}
			this.tvContent.setFocusableInTouchMode(false);
			this.tvContent.setFocusable(false);
			this.tvContent.setBackgroundColor(Color.parseColor("#00000000"));
			// holder.tvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

			WebSettings setting = this.tvContent.getSettings();
			// setting.setBlockNetworkImage(!false);
			setting.setDefaultFontSize(14);
			setting.setJavaScriptEnabled(false);

			ReplyWebViewClient webClient = new ReplyWebViewClient();
			this.tvContent.setWebViewClient(webClient);

			this.ivAvatar = (ImageView) convertView
					.findViewById(R.id.reply_user_avatar);
			this.ivAvatar.setOnClickListener(new AvatarOnClickListener());
			convertView.setTag(this);
		}
	}

	private class AvatarOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(mContext);
			Boolean isLoadImage = prefs.getBoolean("is_load_avatar", false);
			if (Utils.getNetworkType(mContext) != Utils.NetworkType.MOBILE
					|| isLoadImage) {

				String uri = (String) v.getTag();
				Intent intent = new Intent();
				intent.setClass(mContext, ImageViewActivity.class);
				intent.putExtra("uri", uri);
				mContext.startActivity(intent);
			}
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

	// 追加数据并更新列表
	public void addAndRefresh(Map<String, UserInfoData> userList,
			Map<String, ReplyData> replyList, int rows) {
		mReplyListData.get__U().putAll(userList);

		Map<String, ReplyData> __R = mReplyListData.get__R();
		int curRowCount = mReplyListData.get__R__ROWS();
		Set<String> index = replyList.keySet();
		for (String i : index) {
			__R.put(Integer.valueOf(i) + curRowCount + "", replyList.get(i));
		}

		mReplyListData.set__R__ROWS(mReplyListData.get__R__ROWS()
				+ replyList.size());

		mReplyListData.set__ROWS(rows);
		notifyDataSetChanged();
	}

	public boolean isHaveMore() {
		return mReplyListData.get__R__ROWS() < mReplyListData.get__ROWS();
	}

	@Override
	public void notifyDataSetChanged() {
		mViewCache.clear();
		super.notifyDataSetChanged();
	}

	private String buildComment(ReplyData replyData) {
		if (replyData == null || replyData.getCommentList() == null
				|| replyData.getCommentList().size() == 0) {
			return "";
		}

		StringBuilder ret = new StringBuilder();
		ret.append("<br/></br>").append("评论").append("<hr/><br/>");
		ret.append("<table  border='1px' cellspacing='0px' style='border-collapse:collapse;");
		ret.append("color:");
		ret.append(mContext.getResources().getColor(R.color.reply_content));
		ret.append("'>");

		ret.append("<tbody>");

		for (CommentData comment : replyData.getCommentList()) {
			ret.append("<tr><td>");
			ret.append("<span style='font-weight:bold' >");
			ret.append(comment.getAuthor());
			ret.append("</span><br/>");
			ret.append("<img src='");
			String avatarUrl = parseAvatarUrl(comment.getJs_escap_avatar());
			ret.append(avatarUrl);
			ret.append("' style= 'max-width:32;'>");

			ret.append("</td><td>");
			ret.append(comment.getContent());
			ret.append("</td></tr>");

		}
		ret.append("</tbody></table>");
		return ret.toString();
	}

	private static String parseAvatarUrl(String js_escap_avatar) {
		// "js_escap_avatar":"{ \"t\":1,\"l\":2,\"0\":{ \"0\":\"http://pic2.178.com/53/533387/month_1109/93ba4788cc8c7d6c75453fa8a74f3da6.jpg\",\"cX\":0.47,\"cY\":0.78},\"1\":{ \"0\":\"http://pic2.178.com/53/533387/month_1108/8851abc8674af3adc622a8edff731213.jpg\",\"cX\":0.49,\"cY\":0.68}}"
		if (null == js_escap_avatar)
			return null;

		int start = js_escap_avatar.indexOf("http");
		if (start == 0 || start == -1) {
			return js_escap_avatar;
		}
		int end = js_escap_avatar.indexOf("\"", start);
		if (end == -1) {
			end = js_escap_avatar.length();
		}
		String ret = null;
		try {
			ret = js_escap_avatar.substring(start, end);
		} catch (Exception e) {
			Log.e(TAG, "cann't handle avatar url " + js_escap_avatar);
		}
		return ret;
	}
}
