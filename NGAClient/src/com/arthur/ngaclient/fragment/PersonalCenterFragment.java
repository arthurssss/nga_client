package com.arthur.ngaclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.activity.LoginActivity;

public class PersonalCenterFragment extends Fragment {

	private static final String TAG = "DummySectionFragment";

	public PersonalCenterFragment() {
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
		View rootView = inflater.inflate(
				R.layout.fragment_main_personal_center, container, false);
		Button loginBtn = (Button) rootView
				.findViewById(R.id.setting_login_btn);
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), LoginActivity.class);
				getActivity().startActivity(intent);
			}

		});
		
		return rootView;
	}
}
