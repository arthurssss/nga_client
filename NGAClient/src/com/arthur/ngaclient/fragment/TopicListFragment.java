package com.arthur.ngaclient.fragment;

import java.util.List;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TopicListFragment extends Fragment implements
		PullToRefreshAttacher.OnRefreshListener, OnScrollListener {

	private static final String TAG = TopicListFragment.class.getSimpleName();
	private PullToRefreshAttacher mPullToRefreshAttacher;
	private View mRootView = null;
	private TopicListAdapter mTopicListAdapter = null;
	private ListView mTopicListView = null;
	private ProgressBar mLoading = null;
	private LinearLayout mFooterView = null;

	private int mCurPageIndex = 1;
	private int mLastItemIndex;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_topiclist_list,
				container, false);

		LinearLayout llFooterRootView = (LinearLayout) inflater.inflate(
				R.layout.view_listview_footer, null);
		mTopicListView = (ListView) mRootView.findViewById(R.id.topic_list);
		mFooterView = (LinearLayout) llFooterRootView
				.findViewById(R.id.listview_footer_layout);
		mTopicListView.addFooterView(llFooterRootView);
		mFooterView.setVisibility(View.GONE);
		mTopicListView.setOnScrollListener(this);
		mLoading = (ProgressBar) mRootView
				.findViewById(R.id.fullscreen_loading);

		mPullToRefreshAttacher = PullToRefreshAttacher.get(getActivity());

		mPullToRefreshAttacher.addRefreshableView(mTopicListView, this);

		String fid = getActivity().getIntent().getStringExtra("fid");
		mTopicListView.setVisibility(View.GONE);
		mLoading.setVisibility(View.VISIBLE);
		mCurPageIndex = 1;
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

		}).execute(fid, mCurPageIndex + "");
		return mRootView;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		mLastItemIndex = firstVisibleItem + visibleItemCount - 1;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		Log.i(TAG, "scrollState=" + scrollState);
		if (mLastItemIndex == mTopicListAdapter.getCount()
				&& scrollState == OnScrollListener.SCROLL_STATE_IDLE) {

			if (mFooterView.getVisibility() != View.VISIBLE) {
				new TopicListTask(getActivity(),
						new ITopicDataLoadedListener() {

							@Override
							public void onPostFinished(
									TopicListData topicListData) {

								mTopicListAdapter
										.addTopicAndRefresh(topicListData
												.getTopicList());
								mFooterView.setVisibility(View.GONE);
							}

							@Override
							public void onPostError(Integer status) {
								mFooterView.setVisibility(View.GONE);
							}

						}).execute(
						getActivity().getIntent().getStringExtra("fid"),
						++mCurPageIndex + "");
				mFooterView.setVisibility(View.VISIBLE);
			}
		}
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

		public void addTopicAndRefresh(List<TopicData> toplicList) {
			mTopicListData.getTopicList().addAll(toplicList);
			mTopicListData.set__T__ROWS(mTopicListData.get__T__ROWS() + toplicList.size());
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
				convertView = mInflater.inflate(R.layout.item_topiclist_topic,
						null);
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

		mCurPageIndex = 1;
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

		}).execute(getActivity().getIntent().getStringExtra("fid"),
				mCurPageIndex + "");

	}

}
