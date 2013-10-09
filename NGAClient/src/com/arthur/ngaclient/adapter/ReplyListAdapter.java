package com.arthur.ngaclient.adapter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.bean.ReplyData;
import com.arthur.ngaclient.bean.ReplyListData;
import com.arthur.ngaclient.bean.UserInfoData;

import android.content.Context;
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

public class ReplyListAdapter extends BaseAdapter {

	private LayoutInflater mInflater = null;
	private ReplyListData mReplyListData = null;

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

		
		String ignoreCaseTag = "(?i)";
		String content = replyData.getContent();
		content = content
				.replaceAll(
						ignoreCaseTag
								+ "\\[img\\]\\s*\\.(/[^\\[|\\]]+)\\s*\\[/img\\]",
						"<a href='http://img6.ngacn.cc/attachments$1'><img src='http://img6.ngacn.cc/attachments$1' style= 'max-width:100%' ></a>");

		content = content.replaceAll(ignoreCaseTag
				+ "\\[img\\]\\s*(http[^\\[|\\]]+)\\s*\\[/img\\]",
				"<a href='$1'><img src='$1' style= 'max-width:100%' ></a>");

		new ImageGetTask().execute(holder.tvContent, content);
//		holder.tvContent.setText(Html.fromHtml(content, imgGetter, null));
		
		holder.tvFloor.setText("#" + replyData.getLou());
		// holder.ivAvatar();
		return convertView;
	}

	private class ViewHolder {
		public TextView tvUserName;
		public TextView tvReplyDate;
		public TextView tvFloor;
		public TextView tvContent;
		public ImageView ivAvatar;
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
					URL url;
					try {
//						url = new URL("http://avatar.csdn.net/3/9/1/1_pandazxx.jpg");
						url = new URL(source);

						HttpURLConnection connection = (HttpURLConnection) url
								.openConnection();
						connection.connect();
						

						drawable = Drawable.createFromStream(connection.getInputStream(), ""); // 获取网路图片
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight());
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
