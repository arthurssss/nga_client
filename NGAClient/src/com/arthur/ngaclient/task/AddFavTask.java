package com.arthur.ngaclient.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import com.arthur.ngaclient.NGAClientApplication;
import com.arthur.ngaclient.R;
import com.arthur.ngaclient.bean.Global;
import com.arthur.ngaclient.util.HttpUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AddFavTask extends AsyncTask<String, Integer, String> {

	private Context mContext;
	private static final String TAG = AddFavTask.class.getSimpleName();

	public AddFavTask(Context context) {
		mContext = context;
	}

	@Override
	protected String doInBackground(String... params) {

		String tid = params[0];

		String url = Global.SERVER
				+ "/nuke.php?__lib=topic_favor&__act=topic_favor&action=add&raw=3&tid="
				+ tid;
		String param = "__lib=topic_favor&__act=topic_favor&action=add&raw=3&tid="
				+ tid;

		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("User-Agent", NGAClientApplication.USER_AGENT);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.addHeader("Accept-Charset", "GBK");
		httpPost.addHeader("Accept-Encoding", "gzip,deflate");
		httpPost.addHeader("Cookie", HttpUtil.getCookie(mContext));

		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		HttpConnectionParams.setSoTimeout(httpParams, 15000);
		DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);

		// 使不自动重定向
		httpclient.setRedirectHandler(new RedirectHandler() {

			@Override
			public URI getLocationURI(HttpResponse response, HttpContext context)
					throws ProtocolException {
				return null;
			}

			@Override
			public boolean isRedirectRequested(HttpResponse response,
					HttpContext context) {
				return false;
			}

		});

		StringEntity paramStr;
		try {
			paramStr = new StringEntity(param);
			httpPost.setEntity(paramStr);
			HttpResponse response = httpclient.execute(httpPost);
			Log.d(TAG, "" + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {

				InputStream is = response.getEntity().getContent();

				Header[] headers = response.getHeaders("Content-Encoding");
				String contentEncoding = "";
				if (headers.length > 0) {
					contentEncoding = headers[0].getValue();
				}
				if ("gzip".equals(contentEncoding)) {
					is = new GZIPInputStream(is);
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(
						is, "GBK"));
				String data = "";
				StringBuffer sb = new StringBuffer();
				while ((data = br.readLine()) != null) {
					sb.append(data);
				}
				String strResult = sb.toString();
				return strResult;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String strResult) {

		Log.i(TAG, "addfav result = " + strResult);
		if (strResult == null && "".equals(strResult)) {
			Toast.makeText(mContext,
					mContext.getResources().getString(R.string.add_fav_fail),
					Toast.LENGTH_SHORT).show();
			return;
		}

		String startStr = "{\"0\":\"", endStr = "\"},\"time\"";
		int begPosition = 0;
		String result = null;

		do {
			if (strResult == null || "".equals(strResult))
				break;

			int start = strResult.indexOf(startStr, begPosition);
			if (start == -1)
				break;

			start += startStr.length();
			int end = strResult.indexOf(endStr, start);
			if (end == -1)
				end = strResult.length();
			result = strResult.substring(start, end);

		} while (false);

		if (result != null && !"".equals(result)) {
			Toast.makeText(mContext, result.trim(), Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mContext,
					mContext.getResources().getString(R.string.add_fav_fail),
					Toast.LENGTH_SHORT).show();
		}
	}
}
