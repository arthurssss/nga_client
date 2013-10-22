package com.arthur.ngaclient.activity;

import com.arthur.ngaclient.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ReplyActivity extends Activity {

	private boolean mIsImagesShow = false;
	private InputMethodManager mInputMethodManger = null;

	private EditText mReplyContentEdit = null;
	
	private MenuItem mImagesItem = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);

		mInputMethodManger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		mReplyContentEdit = (EditText) findViewById(R.id.reply_content);

		mReplyContentEdit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// 显示软键盘
				hideImages();
			}
	});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.action_image:
			if (mReplyContentEdit.isFocused()) {
				if (!mIsImagesShow) {
					showImages();
				} else {
					hideImages();
				}
			}
		default:
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.reply, menu);
		mImagesItem = menu.findItem(R.id.action_image);
		return true;
	}

	private void showImages() {
		mImagesItem.setIcon(R.drawable.ic_action_keyboard);
		mInputMethodManger.hideSoftInputFromWindow(
				mReplyContentEdit.getWindowToken(), 0);
		mIsImagesShow = true;
	}

	private void hideImages() {
		mImagesItem.setIcon(R.drawable.ic_action_picture);
		mInputMethodManger.showSoftInput(mReplyContentEdit, 0);
		mIsImagesShow = false;
	}
}
