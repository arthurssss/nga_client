package com.arthur.ngaclient.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arthur.ngaclient.NGAClientApplication;
import com.arthur.ngaclient.R;
import com.arthur.ngaclient.bean.Global;
import com.arthur.ngaclient.bean.SubForumData;
import com.arthur.ngaclient.bean.SubForumListData;
import com.arthur.ngaclient.bean.TopicData;
import com.arthur.ngaclient.bean.TopicListData;
import com.arthur.ngaclient.util.HttpUtil;
import com.arthur.ngaclient.util.IOUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class TopicListTask extends AsyncTask<String, Integer, Integer> {

	public final static String TAG = "TopicListTask";
	private Context mContext = null;
	private TopicListData mTopicListData = null;

	private final static Integer SUCCESS = 0;
	private final static Integer TIMEOUT = 1;
	private final static Integer DATAERROR = 2;
	private final static Integer NETERROR = 3;

	public TopicListTask(Context context) {
		mContext = context;
	}

	@Override
	protected Integer doInBackground(String... params) {
		String fid = params[0];
		String page = params[1];
		String url = Global.SERVER + "c/thread.php?lite=js&noprefix&fid=" + fid
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
		Log.d(TAG, url);

		try {
			HttpResponse response = httpclient.execute(httpGet);
			Log.d(TAG, "" + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {

				InputStream is = response.getEntity().getContent();

				is = new GZIPInputStream(is);
				String strResult = IOUtil.inputStreamToString(is, "GBK");

				Log.d(TAG, strResult);

				JSONObject jsonObject = JSON.parseObject(strResult)
						.getJSONObject("data");

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

				if (__UNION_FORUM != null && !"".equals(__UNION_FORUM)) {
					String[] subForumIDs = __UNION_FORUM.split(",");
					int subForumListCount = subForumIDs.length;
					List<SubForumData> subForumList = new ArrayList<SubForumData>();
					JSONObject subForumListJson = jsonObject.getJSONObject(
							"__F").getJSONObject("sub_forums");
					for (int i = 0; i < subForumListCount; i++) {
						subForumList.add((SubForumData) JSONObject
								.toJavaObject(
										subForumListJson.getJSONObject(i + ""),
										SubForumData.class));
					}
					subForumListData.setSub_forums(subForumList);
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
				mTopicListData = topicListData;
				return SUCCESS;
			}
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			return TIMEOUT;
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			return TIMEOUT;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return NETERROR;
		} catch (IOException e) {
			e.printStackTrace();
			return DATAERROR;
		} finally {
			// 关闭连接,释放资源
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Integer status) {
		if (status == SUCCESS) {

		} else if (status == TIMEOUT){
			Toast.makeText(mContext.getApplicationContext(),
					mContext.getResources().getString(R.string.request_timeout),
					Toast.LENGTH_SHORT).show();
		} else if (status == DATAERROR) {
			Toast.makeText(mContext.getApplicationContext(),
					mContext.getResources().getString(R.string.request_dataerror),
					Toast.LENGTH_SHORT).show();
		} else if (status == NETERROR) {
			Toast.makeText(mContext.getApplicationContext(),
					mContext.getResources().getString(R.string.request_neterror),
					Toast.LENGTH_SHORT).show();
		}
	}

}
