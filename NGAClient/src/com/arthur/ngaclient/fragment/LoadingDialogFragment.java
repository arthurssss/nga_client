package com.arthur.ngaclient.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

public class LoadingDialogFragment extends DialogFragment {

	private static final String TAG = LoadingDialogFragment.class
			.getSimpleName();

	private static LoadingDialogFragment mFragment;
	
	public static LoadingDialogFragment newInstance(String msg) {
		Log.i(TAG, "LoadingDialogFragment");
		mFragment = new LoadingDialogFragment();
		Bundle args = new Bundle();
		args.putString("msg", msg);
		mFragment.setArguments(args);
		return mFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Log.i(TAG, "onCreateDialog");
		String title = getArguments().getString("msg");
		ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setMessage(title);
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}

}
