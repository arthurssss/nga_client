package com.arthur.ngaclient.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.activity.MainActivity;
import com.arthur.ngaclient.activity.TopicListActivity;
import com.arthur.ngaclient.bean.Board;
import com.arthur.ngaclient.bean.Plate;
import com.arthur.ngaclient.util.DensityUtil;
import com.arthur.ngaclient.widget.CustomGridView;

/**
 * 
 * 所有板块GridViewAdapter
 * 
 */
public class BoardGridViewAdapter extends BaseAdapter {

	private static String TAG = "BoardGridViewAdapter";
	private LayoutInflater mInflater = null;
	private Plate mPlate = null;
	private List<Board> mBoardList = null;
	private Context mContext = null;

	public BoardGridViewAdapter(Context context, Plate plate) {
		mInflater = LayoutInflater.from(context);
		mPlate = plate;
		mBoardList = plate.getBoardList();
		mContext = context;

	}

	@Override
	public int getCount() {
		return mPlate.getCount();
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
		final int index = position;
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_main_board, null);
			holder = new ViewHolder();
			holder.tvBoardName = (TextView) convertView
					.findViewById(R.id.main_board_name);
			holder.vClick = convertView
					.findViewById(R.id.main_board_item_click);
			convertView.setTag(holder);
			holder.vClick.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.d(TAG, "onClick =================== ");
					Log.d(TAG, "onClick boardName === "
							+ mBoardList.get(index).getName());
					((MainActivity) mContext).mDBManager
							.insertOrUpdateBoard(mBoardList.get(index));
					Intent intent = new Intent();
					intent.setClass((MainActivity) mContext,
							TopicListActivity.class);
					intent.putExtra("title", mBoardList.get(index).getName());
					intent.putExtra("fid", mBoardList.get(index).getUrl());
					mContext.startActivity(intent);
				}

			});
			convertView
					.setLayoutParams(new android.widget.AbsListView.LayoutParams(
							android.widget.AbsListView.LayoutParams.MATCH_PARENT,
							DensityUtil.dip2px(mContext, 45)));
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvBoardName.setCompoundDrawablesWithIntrinsicBounds(
				mContext.getResources().getDrawable(
						mBoardList.get(position).getIcon()), null, null, null);
		holder.tvBoardName.setText(mBoardList.get(position).getName());

		if (position == 0) {
			holder.tvBoardName.setBackgroundResource(R.drawable.main_board_bg);
			holder.tvBoardName.setTextColor(mContext.getResources().getColor(
					R.color.white));
		} else {
			int numCol = ((CustomGridView) parent).getNumColumns();
			int raw = position / numCol;
			int col = position % numCol;

			holder.tvBoardName
					.setBackgroundColor((raw % 2 == 0 && col % 2 == 0)
							|| (raw % 2 != 0 && col % 2 != 0) ? mContext
							.getResources().getColor(R.color.myboard_shit1)
							: mContext.getResources().getColor(
									R.color.myboard_shit2));
		}

		return convertView;
	}

	private class ViewHolder {
		public TextView tvBoardName;
		public View vClick;
	}

}