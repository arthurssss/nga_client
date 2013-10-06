package com.arthur.ngaclient.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.bean.ReplyData;
import com.arthur.ngaclient.bean.ReplyListData;
import com.arthur.ngaclient.bean.UserInfoData;

import android.content.Context;
import android.text.Html;
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
		holder.tvContent.setText(Html.fromHtml(replyData.getContent()));
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

}
