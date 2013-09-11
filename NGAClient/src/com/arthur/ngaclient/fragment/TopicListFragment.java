package com.arthur.ngaclient.fragment;

import com.arthur.ngaclient.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TopicListFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_board_list, container, false);
		TopicListAdapter adapter = new TopicListAdapter(getActivity());
		((ListView) rootView).setAdapter(adapter);
		return rootView;
	}
	
	private class TopicListAdapter extends BaseAdapter{
		
//		private Context mContext = null;
		private LayoutInflater mInflater = null;
		
		public TopicListAdapter(Context context){
//			mContext = context;
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
				convertView = mInflater.inflate(R.layout.item_board_topic, null);
				holder = new ViewHolder();
				holder.tvReplyCount = (TextView) convertView.findViewById(R.id.board_reply_count);
//				holder.tvTopicTitle = (TextView) convertView.findViewById(R.id.board_topic_title);
//				holder.tvTopicAuthor = (TextView) convertView.findViewById(R.id.board_topic_author);
//				holder.tvTopicPoster = (TextView) convertView.findViewById(R.id.board_topic_poster);
				holder.llTopicTitleBg = (LinearLayout) convertView.findViewById(R.id.board_title_bg);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (position % 2 == 0) {
				holder.tvReplyCount.setBackgroundResource(R.color.shit1);
				holder.llTopicTitleBg.setBackgroundResource(R.color.shit2);
			} else {
				holder.tvReplyCount.setBackgroundResource(R.color.shit2);
				holder.llTopicTitleBg.setBackgroundResource(R.color.shit3);
			}
			return convertView;
		}
		
		private class ViewHolder {
			public TextView tvReplyCount;
//			public TextView tvTopicTitle;
//			public TextView tvTopicAuthor;
//			public TextView tvTopicPoster;
			public LinearLayout llTopicTitleBg;
		}
		
	}

}