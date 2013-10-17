package com.arthur.ngaclient.fragment;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.adapter.ReplyListAdapter;
import com.arthur.ngaclient.bean.ReplyListData;
import com.arthur.ngaclient.interfaces.IDataLoadedListener;
import com.arthur.ngaclient.task.TopicReadTask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AbsListView.OnScrollListener;

public class ReplyListFragment extends Fragment implements OnScrollListener {

	private static final String TAG = ReplyListFragment.class.getSimpleName();
	private View mRootView = null;

	private ListView mReplyListView = null;
	private ProgressBar mLoading = null;
	private LinearLayout mFooterView = null;

	private int mCurPageIndex = 1;
	private int mLastItemIndex;
	private ReplyListAdapter mReplyListAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_replylist_list,
				container, false);
		int tid = getArguments().getInt("tid");
		Log.i(TAG, tid + "");

		View llFooterRootView = inflater.inflate(R.layout.view_listview_footer,
				null);

		mReplyListView = (ListView) mRootView.findViewById(R.id.reply_list);
		mFooterView = (LinearLayout) llFooterRootView
				.findViewById(R.id.listview_footer_layout);
		mReplyListView.addFooterView(llFooterRootView);
		mFooterView.setVisibility(View.GONE);
		mReplyListView.setOnScrollListener(this);
		mLoading = (ProgressBar) mRootView
				.findViewById(R.id.fullscreen_loading);

		mReplyListView.setVisibility(View.GONE);
		mLoading.setVisibility(View.VISIBLE);

		new TopicReadTask(getActivity(), new IDataLoadedListener() {

			@Override
			public void onPostFinished(Object obj) {
				mReplyListAdapter = new ReplyListAdapter(getActivity(),
						(ReplyListData) obj);
				mReplyListView.setAdapter(mReplyListAdapter);
				mLoading.setVisibility(View.GONE);
				mReplyListView.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPostError(Integer status) {
				mLoading.setVisibility(View.GONE);
			}
		}).execute(tid + "", getActivity().getIntent().getStringExtra("fid"),
				"1");
		return mRootView;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		Log.i(TAG, "scrollState=" + scrollState);
		Log.i(TAG, "OnScrollListener.SCROLL_STATE_IDLE = "
				+ OnScrollListener.SCROLL_STATE_IDLE);
		Log.i(TAG, "mLastItemIndex = " + mLastItemIndex);
		Log.i(TAG,
				"mReplyListAdapter.getCount() = "
						+ mReplyListAdapter.getCount());
		if (mLastItemIndex == mReplyListAdapter.getCount()
				&& scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& mReplyListAdapter.isHaveMore()) {

			if (mFooterView.getVisibility() != View.VISIBLE) {
				new TopicReadTask(getActivity(), new IDataLoadedListener() {

					@Override
					public void onPostFinished(Object obj) {

						ReplyListData data = (ReplyListData) obj;
						mReplyListAdapter.addAndRefresh(data.get__U(),
								data.get__R());
						mFooterView.setVisibility(View.GONE);
					}

					@Override
					public void onPostError(Integer status) {
						mFooterView.setVisibility(View.GONE);
					}

				}).execute(getArguments().getInt("tid") + "", getActivity()
						.getIntent().getStringExtra("fid"), ++mCurPageIndex
						+ "");
				mFooterView.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		mLastItemIndex = firstVisibleItem + visibleItemCount - 1;
	}

}
