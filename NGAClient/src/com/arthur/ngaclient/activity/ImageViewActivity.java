package com.arthur.ngaclient.activity;

import com.arthur.ngaclient.R;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ImageViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_imageview);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		WebView webView = (WebView) findViewById(R.id.image_webview);
		String uri = (String) getIntent().getExtras().get("uri");
		String html = "<center><img src='" + uri + "'/></center>";

		if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.FROYO) {
			webView.setLongClickable(false);
		}
		webView.setFocusableInTouchMode(false);
		webView.setFocusable(false);
		webView.setBackgroundColor(Color.parseColor("#00000000"));

		WebSettings setting = webView.getSettings();
		setting.setJavaScriptEnabled(false);
		setting.setSupportZoom(true);
		setting.setBuiltInZoomControls(true);
		setting.setDisplayZoomControls(false);

		webView.setWebChromeClient(new WebChromeClient() {
			// 当WebView进度改变时更新窗口进度
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// Activity的进度范围在0到10000之间,所以这里要乘以100
				ImageViewActivity.this.setProgress(newProgress * 100);
			}
		});

		webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return false;
		}
	}
}
