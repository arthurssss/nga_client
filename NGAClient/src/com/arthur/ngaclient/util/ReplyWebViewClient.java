package com.arthur.ngaclient.util;

import com.arthur.ngaclient.activity.ImageViewActivity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ReplyWebViewClient extends WebViewClient {

	private static final String TAG = ReplyWebViewClient.class.getSimpleName();

	public ReplyWebViewClient() {
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		Log.d(TAG, "url = " + url);

		if (url.endsWith(".gif") || url.endsWith(".jpg")
				|| url.endsWith(".png") || url.endsWith(".jpeg")
				|| url.endsWith(".bmp")) {
			Intent intent = new Intent();
			intent.putExtra("uri", url);
			intent.setClass(view.getContext(), ImageViewActivity.class);
			view.getContext().startActivity(intent);
			return true;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		view.getContext().startActivity(intent);
		return true;
	}

}
