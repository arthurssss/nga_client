package com.arthur.ngaclient.adapter;

import com.arthur.ngaclient.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ReplyListAdapter extends BaseAdapter {

	private LayoutInflater mInflater = null;

	public ReplyListAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return 20;
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
		convertView.setBackgroundResource(position % 2 == 0 ? R.color.shit1 : R.color.shit2);
		holder.tvFloor.setText("#" + position);
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
