package com.arthur.ngaclient.fragment;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.adapter.ReplyImagesAdapter;
import com.arthur.ngaclient.interfaces.IOnSetTextEditImageListener;
import com.arthur.ngaclient.util.ExtensionEmotionUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ReplyImagesFragment extends Fragment {

	private static final String TAG = ReplyImagesFragment.class.getSimpleName();
	private GridView mRootView = null;
	private IOnSetTextEditImageListener mListener = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (GridView) inflater.inflate(R.layout.fragment_reply_images,
				container, false);
		Bundle args = getArguments();
		String category = args.getString("category");
		mListener = (IOnSetTextEditImageListener) args
				.getSerializable("OnSetTextEditImageListener");
		int categoryPosition = -1;
		for (int i = 0; i < ExtensionEmotionUtil.dirs.length; i++) {
			if (ExtensionEmotionUtil.dirs[i].equals(category)) {
				categoryPosition = i;
				break;
			}
		}
		Log.i(TAG, "categoryPosition = " + categoryPosition);
		mRootView.setAdapter(new ReplyImagesAdapter(getActivity(),
				categoryPosition));
		mRootView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mListener.OnSetTextEditImage((String) view.getTag());
			}

		});
		return mRootView;
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
