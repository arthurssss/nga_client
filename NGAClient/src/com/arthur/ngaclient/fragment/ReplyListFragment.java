package com.arthur.ngaclient.fragment;

import com.arthur.ngaclient.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReplyListFragment extends Fragment {

	private static final String TAG = ReplyListFragment.class.getSimpleName();
	private View mRootView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_replylist_list,
				container, false);
		Log.i(TAG, getArguments().getInt("itemIndex") + "");
		return mRootView;
	}

}
