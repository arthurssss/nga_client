package com.arthur.ngaclient.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import com.arthur.ngaclient.NGAClientApplication;
import com.arthur.ngaclient.bean.Global;

import android.os.AsyncTask;
import android.util.Log;

public class TopicListTask extends AsyncTask<String, Integer, Boolean> {
	
	public final static String TAG = "TopicListTask";

	public TopicListTask() {
	}

	@Override
	protected Boolean doInBackground(String... params) {
		String fid = params[0];
		String page = params[1];
		String url = Global.SERVER + "/thread.php?lite=js&noprefix&fid=" + fid + "&page" + page;
		
		
		
		
		
		
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
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("User-Agent", NGAClientApplication.USER_AGENT);
		httpGet.addHeader("Content-Type",
				"application/x-www-form-urlencoded");
		httpGet.addHeader("Accept-Charset", "GBK");
		httpGet.addHeader("Accept-Encoding", "gzip,deflate");
		httpGet.addHeader("Cookie", "");
		

		try {
			HttpResponse response = httpclient.execute(httpGet);
			Log.d(TAG, "" + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				
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

}
