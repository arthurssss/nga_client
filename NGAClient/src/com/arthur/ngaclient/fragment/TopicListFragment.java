package com.arthur.ngaclient.fragment;

import uk.co.senab.actionbarpulltorefresh.library.DefaultHeaderTransformer;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.adapter.TopicListAdapter;
import com.arthur.ngaclient.bean.TopicData;
import com.arthur.ngaclient.bean.TopicListData;
import com.arthur.ngaclient.interfaces.IDataLoadedListener;
import com.arthur.ngaclient.task.TopicListTask;
import com.arthur.ngaclient.util.DensityUtil;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

public class TopicListFragment extends Fragment implements
		PullToRefreshAttacher.OnRefreshListener, OnScrollListener,
		OnItemClickListener {

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
		Log.i(TAG, "onCreate");
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
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
		((DefaultHeaderTransformer) (mPullToRefreshAttacher
				.getHeaderTransformer())).setProgressBarColor(getActivity()
				.getResources().getColor(R.color.refresh_progress));

		String fid = getActivity().getIntent().getStringExtra("fid");
		mTopicListView.setVisibility(View.GONE);
		mTopicListView.setOnItemClickListener(TopicListFragment.this);

		mLoading.setVisibility(View.VISIBLE);
		mCurPageIndex = 1;
		new TopicListTask(getActivity(), new IDataLoadedListener() {

			@Override
			public void onPostFinished(Object obj) {
				mTopicListAdapter = new TopicListAdapter(getActivity(),
						(TopicListData) obj);
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
				new TopicListTask(getActivity(), new IDataLoadedListener() {

					@Override
					public void onPostFinished(Object obj) {

						mTopicListAdapter
								.addTopicAndRefresh(((TopicListData) obj)
										.getTopicList());
						mFooterView.setVisibility(View.GONE);
					}

					@Override
					public void onPostError(Integer status) {
						mFooterView.setVisibility(View.GONE);
					}

				}).execute(getActivity().getIntent().getStringExtra("fid"),
						++mCurPageIndex + "");
				mFooterView.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public void onRefreshStarted(View view) {

		mCurPageIndex = 1;
		new TopicListTask(this.getActivity(), new IDataLoadedListener() {

			@Override
			public void onPostFinished(Object obj) {
				mTopicListAdapter.refresh((TopicListData) obj);
				mPullToRefreshAttacher.setRefreshComplete();
			}

			@Override
			public void onPostError(Integer status) {
				mLoading.setVisibility(View.GONE);
			}

		}).execute(getActivity().getIntent().getStringExtra("fid"),
				mCurPageIndex + "");

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.d(TAG, "onItemClick =================== ");

		TopicData topicData = mTopicListAdapter.getTopicListData()
				.getTopicList().get(position);

		if (topicData.getPostdate() == 0) {
			return;
		}

		Fragment fragment = new ReplyListFragment();
		Bundle bundle = new Bundle();

		int topicid = 0;
		if (topicData.getQuote_from() != 0) {
			topicid = topicData.getQuote_from();
		} else {
			topicid = topicData.getTid();
		}
		final int tid = topicid;

		bundle.putInt("tid", tid);
		bundle.putString("title", topicData.getSubject());
		fragment.setArguments(bundle);

		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		Configuration configuration = getActivity().getResources()
				.getConfiguration();
		int ori = configuration.orientation;

		fragmentTransaction.replace(R.id.topiclist_replyview, fragment,
				"ReplyListFragment");

		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels; // 屏幕宽（dip，如：320dip）
		int screenHeight = dm.heightPixels; // 屏幕宽（dip，如：533dip）

		int widthDip = DensityUtil.px2dip(getActivity(), screenWidth);
		int heightDip = DensityUtil.px2dip(getActivity(), screenHeight);

		int minDip = Math.min(widthDip, heightDip);

		if (ori == Configuration.ORIENTATION_PORTRAIT || minDip < 600) {
			fragmentTransaction.addToBackStack(null);
		}

		fragmentTransaction.commit();

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		Log.i(TAG, "onCreateOptionsMenu");
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		Fragment f = fragmentManager.findFragmentByTag("ReplyListFragment");
		if (f == null) {
			Log.i(TAG, "create");
			inflater.inflate(R.menu.topic_list, menu);
		}
		super.onCreateOptionsMenu(menu, inflater);
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
