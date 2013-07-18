package com.arthur.ngaclient.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import com.arthur.ngaclient.NGAClientApplication;
import com.arthur.ngaclient.R;

import android.app.ActionBar;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);

		Button btn = (Button) findViewById(R.id.login_btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText userEdit = (EditText) LoginActivity.this
						.findViewById(R.id.login_edit_user);
				EditText pwdEdit = (EditText) LoginActivity.this
						.findViewById(R.id.login_edit_pwd);
				InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				im.hideSoftInputFromWindow(getCurrentFocus()
						.getApplicationWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
				String postUrl = "http://account.178.com/q_account.php";
				StringBuffer bodyBuffer = new StringBuffer();
				bodyBuffer.append("_act=login&type=username&email=");

				try {
					bodyBuffer.append(URLEncoder.encode(userEdit.getText()
							.toString(), "utf-8"));
					bodyBuffer.append("&password=");
					bodyBuffer.append(URLEncoder.encode(pwdEdit.getText()
							.toString(), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				new LoginTask().execute(postUrl, bodyBuffer.toString());
			}
		});
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

	private class LoginTask extends AsyncTask<String, Integer, Boolean> {

		private static final String TAG = "LoginTask";

		@Override
		protected Boolean doInBackground(String... params) {
			String url = params[0];
			String param = params[1];
			Log.d(TAG, "url ==== " + url);
			Log.d(TAG, "param ==== " + param);
			DefaultHttpClient httpclient = new DefaultHttpClient();
			// 使不自动重定向
			httpclient.setRedirectHandler(new RedirectHandler() {

				@Override
				public URI getLocationURI(HttpResponse response,
						HttpContext context) throws ProtocolException {
					return null;
				}

				@Override
				public boolean isRedirectRequested(HttpResponse response,
						HttpContext context) {
					return false;
				}

			});
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("User-Agent", NGAClientApplication.USER_AGENT);
			httpPost.addHeader("Content-Type",
					"application/x-www-form-urlencoded");
			httpPost.addHeader("Accept-Charset", "GBK");

			try {
				StringEntity paramStr = new StringEntity(param);
				httpPost.setEntity(paramStr);
				HttpResponse response = httpclient.execute(httpPost);
				Log.d(TAG, "" + response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_MOVED_PERM
						|| response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
					Header header[] = response.getHeaders("Set-Cookie");
					String uid = "";
					String cid = "";
					String location = "";
					for (int i = 0; i < header.length; i++) {
						String cookieVal = header[i].getValue();
						cookieVal = cookieVal.substring(0,
								cookieVal.indexOf(';'));
						if (cookieVal.indexOf("_sid=") == 0) {
							cid = cookieVal.substring(5);
						}
						if (cookieVal.indexOf("_178c=") == 0) {
							uid = cookieVal
									.substring(6, cookieVal.indexOf('%'));
						}
					}
					Header header2[] = response.getHeaders("Location");
					location = header2[0].getValue();
					if (cid != "" && uid != ""
							&& location.indexOf("login_success&error=0") != -1) {
						// this.uid = uid;
						// this.cid = cid;
						Log.i(TAG, "uid =" + uid + ",csid=" + cid);
						return true;
					}
				}
				return false;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 关闭连接,释放资源
				httpclient.getConnectionManager().shutdown();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result.booleanValue()) {
				Log.d(TAG, "success !!!!!!!!!!! ");
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.login_success),
						Toast.LENGTH_SHORT).show();
			} else {
				Log.d(TAG, "fail !!!!!!!!!");
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.login_fail),
						Toast.LENGTH_SHORT).show();
			}
		}

	}

}
