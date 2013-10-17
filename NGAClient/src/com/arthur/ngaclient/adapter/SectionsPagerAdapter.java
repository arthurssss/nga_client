package com.arthur.ngaclient.adapter;

import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.fragment.AllBoardsFragment;
import com.arthur.ngaclient.fragment.MyBoardsFragment;
import com.arthur.ngaclient.fragment.PersonalCenterFragment;

/**
 * 
 * 首页ViewPager Adapter
 * 
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
	
	private static final String TAG = SectionsPagerAdapter.class.getSimpleName();
	
	private Context mContext = null;

	public SectionsPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		mContext = context;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;

		Log.d(TAG, "getItem ================ " + position);
		switch (position) {
		case 0:
			fragment = new PersonalCenterFragment();
			Bundle args = new Bundle();
			fragment.setArguments(args);
			break;
		case 1:
			fragment = new MyBoardsFragment();
			args = new Bundle();
			fragment.setArguments(args);
			break;
		case 2:
			fragment = new AllBoardsFragment();
			args = new Bundle();
			fragment.setArguments(args);
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return mContext.getString(R.string.title_section1).toUpperCase(l);
		case 1:
			return mContext.getString(R.string.title_section2).toUpperCase(l);
		case 2:
			return mContext.getString(R.string.title_section3).toUpperCase(l);
		}
		return null;
	}

	@Override
	public float getPageWidth(int position) {
		return position == 0 ? mContext.getResources().getInteger(
				R.integer.personal_center_width) / 100f : 1.0f;
	}
}
