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
import android.widget.ListView;
import android.widget.ProgressBar;

public class ReplyListFragment extends Fragment {

	private static final String TAG = ReplyListFragment.class.getSimpleName();
	private View mRootView = null;

	private ListView mReplyListView = null;
	private ProgressBar mLoading = null;

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

		mLoading = (ProgressBar) mRootView
				.findViewById(R.id.fullscreen_loading);

		mReplyListView = (ListView) mRootView.findViewById(R.id.reply_list);

		new TopicReadTask(getActivity(), new IDataLoadedListener() {

			@Override
			public void onPostFinished(Object obj) {
				mReplyListView.setAdapter(new ReplyListAdapter(getActivity(), (ReplyListData) obj));
			}

			@Override
			public void onPostError(Integer status) {

			}
		}).execute(tid + "", "1");

		mLoading.setVisibility(View.GONE);
		mReplyListView.setVisibility(View.VISIBLE);
		return mRootView;
	}

}
