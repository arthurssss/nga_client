package com.arthur.ngaclient.task;

import java.io.FileNotFoundException;

import com.arthur.ngaclient.interfaces.IUploadStatusListener;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;

public class UploadTask extends AsyncTask<String, Integer, String> {

	private static final String TAG = UploadTask.class.getSimpleName();
	private Context mContext;
	private IUploadStatusListener mStatusListener;
	private Uri mFileUri;

	public UploadTask(Context context, IUploadStatusListener listener, Uri uri) {
		mContext = context;
		mStatusListener = listener;
		mFileUri = uri;
	}

	@Override
	protected String doInBackground(String... params) {
		ContentResolver cr = mContext.getContentResolver();
		try {
			ParcelFileDescriptor pfd = cr.openFileDescriptor(mFileUri, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
