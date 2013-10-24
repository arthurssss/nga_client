package com.arthur.ngaclient.activity;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.adapter.ReplyImagesCategoryAdapter;
import com.arthur.ngaclient.fragment.ReplyImagesFragment;
import com.arthur.ngaclient.task.ReplyTask;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

public class ReplyActivity extends FragmentActivity {

	private static final String TAG = ReplyActivity.class.getSimpleName();
	private boolean mIsImagesShow = false;
	private InputMethodManager mInputMethodManger = null;

	private EditText mReplyContentEdit = null;
	private EditText mReplyTitleEdit = null;
	private GridView mReplyImages = null;
	private View mReplyImagesLayout = null;

	private MenuItem mImagesItem = null;

	private String mAction = null;
	private String mTid = null;
	private String mFid = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);

		mAction = getIntent().getStringExtra("action");
		mTid = getIntent().getStringExtra("tid");
		mFid = getIntent().getStringExtra("fid");

		if (mAction != null) {
			Log.i(TAG, "action = " + mAction);
		}
		if (mTid != null) {
			Log.i(TAG, "tid = " + mTid);
		}
		if (mFid != null) {
			Log.i(TAG, "fid = " + mFid);
		}

		mInputMethodManger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		mReplyContentEdit = (EditText) findViewById(R.id.reply_content);
		mReplyTitleEdit = (EditText) findViewById(R.id.reply_title);
		mReplyImagesLayout = findViewById(R.id.reply_images_layout);

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.popBackStackImmediate();

		mReplyImages = (GridView) findViewById(R.id.reply_images_grid);
		mReplyImages.setAdapter(new ReplyImagesCategoryAdapter(this));
		mReplyImages.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(TAG, "onItemClick");
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				Fragment fragment = new ReplyImagesFragment();
				ft.replace(R.id.reply_images_layout, fragment);
				ft.addToBackStack(null);
				ft.commit();
				mReplyImagesLayout.setVisibility(View.VISIBLE);
				mReplyImages.setVisibility(View.GONE);
			}

		});

		mReplyTitleEdit.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				hideImages();
				return false;
			}

		});

		mReplyContentEdit.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.i(TAG, "onTouch Down");
					hideImages();
				}
				return false;
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
			return true;
		case R.id.action_send:
			String content = mReplyContentEdit.getText().toString();
			String title = mReplyTitleEdit.getText().toString();
			new ReplyTask(this).execute(title, content, mAction, mFid, mTid);
			return true;
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

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			if (!fragmentManager.popBackStackImmediate()) {
				onBackPressed();
			}
		}
		return true;
	}

	private void showImages() {
		mImagesItem.setIcon(R.drawable.ic_action_keyboard);
		mInputMethodManger.hideSoftInputFromWindow(
				mReplyContentEdit.getWindowToken(), 0);
		mReplyImages.setVisibility(View.VISIBLE);
		mReplyImagesLayout.setVisibility(View.GONE);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.popBackStackImmediate();
		mIsImagesShow = true;
	}

	private void hideImages() {
		mImagesItem.setIcon(R.drawable.ic_action_picture);
		mInputMethodManger.showSoftInput(mReplyContentEdit, 0);
		mReplyImages.setVisibility(View.GONE);
		mReplyImagesLayout.setVisibility(View.GONE);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.popBackStackImmediate();
		mIsImagesShow = false;
	}
}
