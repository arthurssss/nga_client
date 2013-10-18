package com.arthur.ngaclient.activity;

import com.arthur.ngaclient.NGAClientApplication;
import com.arthur.ngaclient.R;
import com.arthur.ngaclient.adapter.SectionsPagerAdapter;
import com.arthur.ngaclient.util.DBManager;
import com.arthur.ngaclient.util.DensityUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {

	private static final String TAG = "MainActivity";
	public static final String ARG_SECTION_NUMBER = "section_number";

	protected SectionsPagerAdapter mSectionsPagerAdapter;

	protected ViewPager mViewPager;
	protected int mCurPagerIndex = 1;

	public DBManager mDBManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;

		Log.e(TAG + "  DisplayMetrics", "xdpi=" + xdpi + "; ydpi=" + ydpi);
		Log.e(TAG + "  DisplayMetrics", "density=" + density + "; densityDPI="
				+ densityDPI);

		int screenWidth = dm.widthPixels; // 屏幕宽（dip，如：320dip）
		int screenHeight = dm.heightPixels; // 屏幕宽（dip，如：533dip）

		Log.e(TAG + "  DisplayMetrics(222)", "screenWidth=" + screenWidth
				+ "; screenHeight=" + screenHeight);

		Log.e(TAG + "  DisplayMetrics(222)",
				"screenWidthDp=" + DensityUtil.px2dip(this, screenWidth)
						+ "; screenHeightDp="
						+ DensityUtil.px2dip(this, screenHeight));
		Log.i(TAG, "------------------------------------------");

		setContentView(R.layout.activity_main);

		mSectionsPagerAdapter = new SectionsPagerAdapter(this,
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(2);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageSelected(int arg0) {
				mCurPagerIndex = arg0;
				Log.d(TAG, "onPageSelected mCurPagerIndex = " + mCurPagerIndex);
			}

		});
		mCurPagerIndex = ((NGAClientApplication) getApplication()).getConfig()
				.getInt("curPagerIndex", 1);
		Log.d(TAG, "mCurPagerIndex = " + mCurPagerIndex);
		mViewPager.setCurrentItem(mCurPagerIndex);
		PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);

		pagerTabStrip.setTabIndicatorColor(getResources().getColor(
				R.color.tab_line));

		mDBManager = new DBManager(this);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		Boolean isUseCellularNetwork = prefs.getBoolean("is_load_image", false);
		Log.d(TAG, "isUseCellularNetwork = " + isUseCellularNetwork.toString());
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mCurPagerIndex = savedInstanceState.getInt("viewPagerIndex", 1);
		Log.d(TAG, "onRestoreInstanceState mCurPagerIndex = " + mCurPagerIndex);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		Log.d(TAG, "onSaveInstanceState mCurPagerIndex = " + mCurPagerIndex);
		savedInstanceState.putInt("viewPagerIndex", mCurPagerIndex);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d(TAG, "onConfigurationChanged");
		// 检测屏幕的方向：纵向或横向
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 当前为横屏， 在此处添加额外的处理代码
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// 当前为竖屏， 在此处添加额外的处理代码
		}
		// 检测实体键盘的状态：推出或者合上
		if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
			// 实体键盘处于推出状态，在此处添加额外的处理代码
		} else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
			// 实体键盘处于合上状态，在此处添加额外的处理代码
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_settings) {
			Intent intent = new Intent();
			intent.setClass(this, SettingActivity.class);
			startActivity(intent);
		}
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
		Editor editor = ((NGAClientApplication) getApplication()).getConfig()
				.edit();
		editor.putInt("curPagerIndex", mCurPagerIndex);
		editor.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
		mDBManager.close();
	}

}
