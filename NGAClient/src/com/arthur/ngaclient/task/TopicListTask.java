package com.arthur.ngaclient.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.arthur.ngaclient.NGAClientApplication;
import com.arthur.ngaclient.R;
import com.arthur.ngaclient.bean.Global;
import com.arthur.ngaclient.util.HttpUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class TopicListTask extends AsyncTask<String, Integer, Boolean> {
	
	public final static String TAG = "TopicListTask";
	public Context mContext = null;

	public TopicListTask(Context context) {
		mContext = context;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		String fid = params[0];
		String page = params[1];
		String url = Global.SERVER + "/thread.php?lite=js&noprefix&fid=" + fid + "&page=" + page;
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("User-Agent", NGAClientApplication.USER_AGENT);
		httpGet.addHeader("Content-Type",
				"application/x-www-form-urlencoded");
		httpGet.addHeader("Accept-Charset", "GBK");
		httpGet.addHeader("Accept-Encoding", "gzip,deflate");
		httpGet.addHeader("Cookie", HttpUtil.getCookie(mContext));
		
		Log.d(TAG, url);

		try {
			HttpResponse response = httpclient.execute(httpGet);
			Log.d(TAG, "" + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				String strResult = EntityUtils.toString(response.getEntity(), "GBK");  
                Log.d(TAG, strResult);
			}
			return true;
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
			Toast.makeText(mContext.getApplicationContext(),
					mContext.getResources().getString(R.string.login_success),
					Toast.LENGTH_SHORT).show();
		} else {
			Log.d(TAG, "fail !!!!!!!!!");
			Toast.makeText(mContext.getApplicationContext(),
					mContext.getResources().getString(R.string.login_fail),
					Toast.LENGTH_SHORT).show();
		}
	}

}
