package com.arthur.ngaclient.fragment;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.bean.TopicData;
import com.arthur.ngaclient.bean.TopicListData;
import com.arthur.ngaclient.interfaces.ITopicDataLoadedListener;
import com.arthur.ngaclient.task.TopicListTask;
import com.arthur.ngaclient.util.Utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TopicListFragment extends Fragment implements
		PullToRefreshAttacher.OnRefreshListener {

	private PullToRefreshAttacher mPullToRefreshAttacher;
	private View mRootView = null;
	private TopicListAdapter mTopicListAdapter = null;
	private ListView mTopicListView = null;
	private ProgressBar mLoading = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_board_list, container,
				false);

		mTopicListView = (ListView) mRootView
				.findViewById(R.id.topic_list);
		mLoading = (ProgressBar) mRootView.findViewById(R.id.fullscreen_loading);

		mPullToRefreshAttacher = PullToRefreshAttacher.get(getActivity());

		mPullToRefreshAttacher.addRefreshableView(mTopicListView, this);

		String fid = getActivity().getIntent().getStringExtra("fid");
		mTopicListView.setVisibility(View.GONE);
		mLoading.setVisibility(View.VISIBLE);
		new TopicListTask(getActivity(), new ITopicDataLoadedListener() {

			@Override
			public void onPostFinished(TopicListData topicListData) {
				mTopicListAdapter = new TopicListAdapter(getActivity(),
						topicListData);
				mTopicListView.setAdapter(mTopicListAdapter);
				mTopicListView.setVisibility(View.VISIBLE);
				mLoading.setVisibility(View.GONE);
			}

			@Override
			public void onPostError(Integer status) {
				mLoading.setVisibility(View.GONE);
			}

		}).execute(fid, "1");
		return mRootView;
	}

	private class TopicListAdapter extends BaseAdapter {

		// private Context mContext = null;
		private LayoutInflater mInflater = null;
		private TopicListData mTopicListData = null;

		public TopicListAdapter(Context context, TopicListData topicListData) {
			// mContext = context;
			mInflater = LayoutInflater.from(context);
			mTopicListData = topicListData;
		}

		public void refresh(TopicListData topicListData) {
			mTopicListData = topicListData;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTopicListData.get__T__ROWS();
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
				convertView = mInflater
						.inflate(R.layout.item_board_topic, null);
				holder = new ViewHolder();
				holder.tvReplyCount = (TextView) convertView
						.findViewById(R.id.board_reply_count);
				holder.tvTopicTitle = (TextView) convertView
						.findViewById(R.id.board_topic_title);
				holder.tvTopicAuthor = (TextView) convertView
						.findViewById(R.id.board_topic_author);
				holder.tvTopicPoster = (TextView) convertView
						.findViewById(R.id.board_topic_poster);
				holder.llTopicTitleBg = (LinearLayout) convertView
						.findViewById(R.id.board_title_bg);
				holder.tvTopicReplyTime = (TextView) convertView
						.findViewById(R.id.board_topic_replytime);
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
			TopicData topicData = mTopicListData.getTopicList().get(position);
			if (topicData != null) {
				holder.tvTopicTitle.setText(topicData.getSubject());
				holder.tvTopicAuthor.setText(topicData.getAuthor());
				holder.tvTopicPoster.setText(topicData.getLastposter());
				holder.tvReplyCount.setText(topicData.getReplies() + "");
				holder.tvTopicReplyTime.setText(Utils.timeFormat(
						topicData.getLastpost(), mTopicListData.getTime()));
			}
			return convertView;
		}

		private class ViewHolder {
			public TextView tvReplyCount;
			public TextView tvTopicTitle;
			public TextView tvTopicAuthor;
			public TextView tvTopicPoster;
			public TextView tvTopicReplyTime;
			public LinearLayout llTopicTitleBg;
		}

	}

	@Override
	public void onRefreshStarted(View view) {

		mTopicListView.setVisibility(View.GONE);
		mLoading.setVisibility(View.VISIBLE);
		new TopicListTask(this.getActivity(), new ITopicDataLoadedListener() {

			@Override
			public void onPostFinished(TopicListData topicListData) {
				mTopicListAdapter.refresh(topicListData);
				mPullToRefreshAttacher.setRefreshComplete();
			}

			@Override
			public void onPostError(Integer status) {
				mLoading.setVisibility(View.GONE);
			}

		}).execute(getActivity().getIntent().getStringExtra("fid"), "1");

	}

}
