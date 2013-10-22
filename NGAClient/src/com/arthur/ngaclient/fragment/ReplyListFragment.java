package com.arthur.ngaclient.fragment;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.adapter.ReplyListAdapter;
import com.arthur.ngaclient.bean.ReplyListData;
import com.arthur.ngaclient.interfaces.IDataLoadedListener;
import com.arthur.ngaclient.task.AddFavTask;
import com.arthur.ngaclient.task.TopicReadTask;

import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

	private TopicReadTask mTopicReadTask = null;
	private AddFavTask mAddFavTask = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		ActionBar actionBar = getActivity().getActionBar();
		String title = getArguments().getString("title");
		actionBar.setTitle(Html.fromHtml(title));
		actionBar.setDisplayShowHomeEnabled(false);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
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

		mTopicReadTask = new TopicReadTask(getActivity(),
				new IDataLoadedListener() {

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
				});
		mTopicReadTask.execute(tid + "", getActivity().getIntent()
				.getStringExtra("fid"), "1");
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
								data.get__R(), data.get__ROWS());
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

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		Log.i(TAG, "onCreateOptionsMenu");
		inflater.inflate(R.menu.reply_list, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(TAG, "onOptionsItemSelected");
		switch (item.getItemId()) {
		case R.id.action_addfav:
			Log.i(TAG, "action_addfav");
			if (mAddFavTask != null) {
				mAddFavTask.cancel(true);
			}
			mAddFavTask = new AddFavTask(getActivity());
			mAddFavTask.execute(String.valueOf(getArguments().getInt("tid")));

			return true;
		}
		return false;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d(TAG, "onAttach");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "onActivityCreated");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		if (!mTopicReadTask.isCancelled()) {
			mTopicReadTask.cancel(true);
		}
		if (!mAddFavTask.isCancelled()) {
			mAddFavTask.cancel(true);
		}
		Log.d(TAG, "onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.d(TAG, "onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.d(TAG, "onDetach");
	}

}
