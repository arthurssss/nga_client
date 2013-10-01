package com.arthur.ngaclient.task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import com.arthur.ngaclient.NGAClientApplication;
import com.arthur.ngaclient.bean.Global;
import com.arthur.ngaclient.util.HttpUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class TopicReadTask extends AsyncTask<String, Integer, Integer> {

	public static final String TAG = TopicReadTask.class.getSimpleName();
	private Context mContext = null;

	public TopicReadTask(Context context) {
		mContext = context;
	}

	@Override
	protected Integer doInBackground(String... params) {
		String fid = params[0];
		String page = params[1];
		String url = Global.SERVER + "/read.php?lite=js&noprefix&tid=" + fid
				+ "&page=" + page;

		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("User-Agent", NGAClientApplication.USER_AGENT);
		httpGet.addHeader("Content-Type", "application/x-www-formurlencoded");
		httpGet.addHeader("Accept-Charset", "GBK");
		httpGet.addHeader("Accept-Encoding", "gzip,deflate");
		httpGet.addHeader("Cookie", HttpUtil.getCookie(mContext));

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
		Log.d(TAG, url);
		try {
			HttpResponse response = httpclient.execute(httpGet);
			response.getStatusLine();
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

				Log.d(TAG, strResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
