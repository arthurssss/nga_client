package com.arthur.ngaclient.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arthur.ngaclient.NGAClientApplication;
import com.arthur.ngaclient.R;
import com.arthur.ngaclient.bean.Global;
import com.arthur.ngaclient.bean.SubForumData;
import com.arthur.ngaclient.bean.SubForumListData;
import com.arthur.ngaclient.bean.TopicData;
import com.arthur.ngaclient.bean.TopicListData;
import com.arthur.ngaclient.util.FileUtils;
import com.arthur.ngaclient.util.HttpUtil;
import com.arthur.ngaclient.util.IOUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
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
		String url = Global.SERVER + "/thread.php?lite=js&noprefix&fid=" + fid
				+ "&page=" + page;

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("User-Agent", NGAClientApplication.USER_AGENT);
		httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
		httpGet.addHeader("Accept-Charset", "GBK");
		httpGet.addHeader("Accept-Encoding", "gzip,deflate");
		httpGet.addHeader("Cookie", HttpUtil.getCookie(mContext));

		Log.d(TAG, url);

		try {
			HttpResponse response = httpclient.execute(httpGet);
			Log.d(TAG, "" + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {

				InputStream is = response.getEntity().getContent();

				is = new GZIPInputStream(is);
				String strResult = IOUtil.inputStreamToString(is, "GBK");

				Log.d(TAG, strResult);

				JSONObject jsonObject = JSON.parseObject(strResult).getJSONObject("data");

				SubForumListData subForumListData = new SubForumListData();
				JSONObject __F = jsonObject.getJSONObject("__F");
				subForumListData.set__SELECTED_FORUM((String) __F
						.get("__SELECTED_FORUM"));
				subForumListData.set__UNION_FORUM((String) __F
						.get("__UNION_FORUM"));
				subForumListData.set__UNION_FORUM_DEFAULT((String) __F
						.get("__UNION_FORUM_DEFAULT"));
				subForumListData.setTopped_topic((Integer) __F
						.get("topped_topic"));
				subForumListData.setFid((Integer) __F.get("fid"));
				String __UNION_FORUM = subForumListData.get__UNION_FORUM();
				String[] subForumIDs = __UNION_FORUM.split(",");
				int subForumListCount = subForumIDs.length;
				List<SubForumData> subForumList = new ArrayList<SubForumData>();
				JSONObject subForumListJson = jsonObject.getJSONObject("__F")
						.getJSONObject("sub_forums");
				for (int i = 0; i < subForumListCount; i++) {
					subForumList.add((SubForumData) JSONObject.toJavaObject(
							subForumListJson.getJSONObject(i + ""),
							SubForumData.class));
				}
				TopicListData topicListData = new TopicListData();
				topicListData.set__F(subForumListData);
				topicListData.set__R__ROWS_PAGE((Integer) jsonObject
						.get("__R__ROWS_PAGE"));
				topicListData.set__T__ROWS_PAGE((Integer) jsonObject
						.get("__T__ROWS_PAGE"));
				topicListData.set__T__ROWS((Integer) jsonObject
						.get("__T__ROWS"));
				topicListData.set__ROWS((Integer) jsonObject.get("__ROWS"));
				List<TopicData> topicDataList = new ArrayList<TopicData>();
				for (int i = 0; i < topicListData.get__T__ROWS(); i++) {
					topicDataList.add((TopicData) JSONObject.toJavaObject(
							jsonObject.getJSONObject("__T").getJSONObject(
									i + ""), TopicData.class));
				}
				topicListData.setTopicList(topicDataList);
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
