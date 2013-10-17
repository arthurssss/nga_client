package com.arthur.ngaclient.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.bean.Board;
import com.arthur.ngaclient.util.DensityUtil;

/**
 * 
 * 常用板块Adapter
 * 
 */
public class MyBoardListAdapter extends BaseAdapter {

	private Context mContext = null;
	private LayoutInflater mInflater = null;
	private List<Board> mBoardList = null;

	public MyBoardListAdapter(Context context, List<Board> boardList) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mBoardList = boardList;
	}

	@Override
	public int getCount() {
		return mBoardList.size();
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
			convertView = mInflater.inflate(R.layout.item_main_myboard, null);
			holder = new ViewHolder();
			holder.ivBoardIcon = (ImageView) convertView
					.findViewById(R.id.main_board_ic);
			holder.tvBoardName = (TextView) convertView
					.findViewById(R.id.main_board_name);

			convertView.setTag(holder);
			convertView
					.setLayoutParams(new android.widget.AbsListView.LayoutParams(
							android.widget.AbsListView.LayoutParams.MATCH_PARENT,
							DensityUtil.dip2px(mContext, 45)));

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.ivBoardIcon.setImageResource(mBoardList.get(position).getIcon());
		holder.tvBoardName.setText(mBoardList.get(position).getName());

		convertView
				.setBackgroundResource(position % 2 == 0 ? R.drawable.item_myboard1_style
						: R.drawable.item_myboard2_style);
		return convertView;
	}

	private class ViewHolder {
		public ImageView ivBoardIcon;
		public TextView tvBoardName;
	}

}