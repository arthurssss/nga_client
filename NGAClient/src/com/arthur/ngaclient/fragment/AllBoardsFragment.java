package com.arthur.ngaclient.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arthur.ngaclient.NGAClientApplication;
import com.arthur.ngaclient.R;
import com.arthur.ngaclient.adapter.BoardGridViewAdapter;
import com.arthur.ngaclient.bean.Plate;
import com.arthur.ngaclient.widget.CustomGridView;

/**
 * 
 * 所有板块Fragment
 * 
 */
public class AllBoardsFragment extends Fragment {

	private static final String TAG = "AllBoardsFragment";

	public AllBoardsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_main_all_board,
				container, false);
		LinearLayout llAllBoard = (LinearLayout) rootView
				.findViewById(R.id.main_allboard_list);

		List<Plate> plates = ((NGAClientApplication) getActivity()
				.getApplication()).loadDefaultBoard();
		for (Plate plate : plates) {
			LayoutInflater inflate = LayoutInflater.from(getActivity());
			RelativeLayout rlItemPlate = (RelativeLayout) inflate.inflate(
					R.layout.item_main_plate, null);

			TextView tvPlate = (TextView) rlItemPlate
					.findViewById(R.id.plateName);
			CustomGridView gvBoardList = (CustomGridView) rlItemPlate
					.findViewById(R.id.main_board_gridview);

			tvPlate.setText(":: " + plate.getName() + " ::");
			gvBoardList.setAdapter(new BoardGridViewAdapter(getActivity(),
					plate));

			llAllBoard.addView(rlItemPlate);
		}
		return rootView;
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