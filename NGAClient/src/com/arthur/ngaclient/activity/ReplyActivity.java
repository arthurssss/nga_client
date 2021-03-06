package com.arthur.ngaclient.activity;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.adapter.ReplyImagesCategoryAdapter;
import com.arthur.ngaclient.fragment.LoadingDialogFragment;
import com.arthur.ngaclient.fragment.ReplyImagesFragment;
import com.arthur.ngaclient.interfaces.IOnReplySuccessListener;
import com.arthur.ngaclient.interfaces.IOnSetTextEditImageListener;
import com.arthur.ngaclient.task.ReplyTask;
import com.arthur.ngaclient.util.ExtensionEmotionUtil;

import android.app.ActionBar;
import android.content.Intent;
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
	private GridView mReplyCategoryImages = null;
	private View mReplyImagesLayout = null;

	private MenuItem mImagesItem = null;

	private String mAction = null;
	private String mTid = null;
	private String mFid = null;

	private LoadingDialogFragment mLoading = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
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

		mReplyCategoryImages = (GridView) findViewById(R.id.reply_images_grid);
		mReplyCategoryImages.setAdapter(new ReplyImagesCategoryAdapter(this));
		mReplyCategoryImages.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(TAG, "onItemClick");
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				Fragment fragment = new ReplyImagesFragment();
				Bundle b = new Bundle();
				b.putString("category", ExtensionEmotionUtil.dirs[position]);
				b.putSerializable("OnSetTextEditImageListener",
						new IOnSetTextEditImageListener() {

							private static final long serialVersionUID = 1L;

							@Override
							public void OnSetTextEditImage(String imgUri) {
								mReplyContentEdit.getText().insert(
										mReplyContentEdit.getSelectionStart(),
										"[img]" + imgUri + "[/img]");
							}
						});
				fragment.setArguments(b);
				ft.replace(R.id.reply_images_layout, fragment);
				ft.addToBackStack(null);
				ft.commit();
				mReplyImagesLayout.setVisibility(View.VISIBLE);
				mReplyCategoryImages.setVisibility(View.GONE);
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
			mLoading = LoadingDialogFragment.newInstance(this.getResources()
					.getString(R.string.loading_reply));
			mLoading.show(getSupportFragmentManager(), "reply");
			new ReplyTask(this, new IOnReplySuccessListener() {

				@Override
				public void onReplySuccess() {
					Log.i(TAG, "onReplySuccess");
					ReplyActivity.this.finish();
				}

				@Override
				public void onReplyFailed() {
					Log.i(TAG, "onReplyFailed");
					mLoading.dismiss();
				}
			}).execute(title, content, mAction, mFid, mTid);
			return true;
		case R.id.action_upload:
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, 1);
			return true;
		default:
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG, "onActivityResult");
		if (resultCode == RESULT_CANCELED || data == null) {
			return;
		}
		switch (requestCode) {
		case 1:
			Log.i(TAG, " select file :" + data.getDataString());
			break;
		default:
			;
		}
		super.onActivityResult(requestCode, resultCode, data);
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
				if (mReplyCategoryImages.getVisibility() == View.VISIBLE) {
					mReplyCategoryImages.setVisibility(View.GONE);
					mImagesItem.setIcon(R.drawable.ic_action_picture);
					mIsImagesShow = false;
				} else {
					onBackPressed();
				}
			} else {
				mImagesItem.setIcon(R.drawable.ic_action_picture);
				mIsImagesShow = false;
			}
			return true;
		}
		return false;
	}

	private void showImages() {
		mImagesItem.setIcon(R.drawable.ic_action_keyboard);
		mInputMethodManger.hideSoftInputFromWindow(
				mReplyContentEdit.getWindowToken(), 0);
		mReplyCategoryImages.setVisibility(View.VISIBLE);
		mReplyImagesLayout.setVisibility(View.GONE);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.popBackStackImmediate();
		mIsImagesShow = true;
	}

	private void hideImages() {
		mImagesItem.setIcon(R.drawable.ic_action_picture);
		mInputMethodManger.showSoftInput(mReplyContentEdit, 0);
		mReplyCategoryImages.setVisibility(View.GONE);
		mReplyImagesLayout.setVisibility(View.GONE);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.popBackStackImmediate();
		mIsImagesShow = false;
	}

}
