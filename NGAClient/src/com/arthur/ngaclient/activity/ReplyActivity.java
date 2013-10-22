package com.arthur.ngaclient.activity;

import com.arthur.ngaclient.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ReplyActivity extends Activity {

	private boolean mIsImagesShow = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.action_image:
			if (!mIsImagesShow) {
				item.setIcon(R.drawable.ic_action_keyboard);
			} else {
				item.setIcon(R.drawable.ic_action_picture);
			}
			mIsImagesShow = !mIsImagesShow;
		default:
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.reply, menu);
		return true;
	}
}
