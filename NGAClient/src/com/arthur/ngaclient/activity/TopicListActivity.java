package com.arthur.ngaclient.activity;

import com.arthur.ngaclient.R;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

public class TopicListActivity extends FragmentActivity {

	private static final String TAG = TopicListActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topiclist);
		String title = getIntent().getStringExtra("title");
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(title);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(TAG, "onOptionsItemSelected");
		switch (item.getItemId()) {
		case android.R.id.home:
			FragmentManager fragmentManager = getSupportFragmentManager();
			if (!fragmentManager.popBackStackImmediate()) {
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setClass(this, MainActivity.class);
				this.startActivity(intent);
			} else {
				ActionBar actionBar = getActionBar();
				actionBar.setTitle(getIntent().getStringExtra("title"));
				actionBar.setDisplayShowHomeEnabled(true);
			}
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			if (!fragmentManager.popBackStackImmediate()) {
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setClass(this, MainActivity.class);
				this.startActivity(intent);
			} else {
				ActionBar actionBar = getActionBar();
				actionBar.setTitle(getIntent().getStringExtra("title"));
				actionBar.setDisplayShowHomeEnabled(true);
			}
		}
		return true;
	}

}
