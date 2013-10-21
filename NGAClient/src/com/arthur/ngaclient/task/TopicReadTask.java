package com.arthur.ngaclient.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
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
import com.arthur.ngaclient.bean.CommentData;
import com.arthur.ngaclient.bean.Global;
import com.arthur.ngaclient.bean.ReplyData;
import com.arthur.ngaclient.bean.ReplyListData;
import com.arthur.ngaclient.bean.UserInfoData;
import com.arthur.ngaclient.interfaces.IDataLoadedListener;
import com.arthur.ngaclient.util.HttpUtil;
import com.arthur.ngaclient.util.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class TopicReadTask extends AsyncTask<String, Integer, Integer> {

	public static final String TAG = TopicReadTask.class.getSimpleName();
	private Context mContext = null;
	private IDataLoadedListener mDataListener = null;
	private ReplyListData mReplyListData = null;

	private final static Integer SUCCESS = 0;
	private final static Integer TIMEOUT = 1;
	private final static Integer DATAERROR = 2;
	private final static Integer NETERROR = 3;
	private final static Integer SERVERERROR = 4;
	private final static Integer FORBIDDEN = 5;
	private final static Integer OTHERERROR = 6;

	public TopicReadTask(Context context, IDataLoadedListener listener) {
		mContext = context;
		mDataListener = listener;
	}

	@Override
	protected Integer doInBackground(String... params) {
		String tid = params[0];
		String fid = params[1];
		String page = params[2];
		String url = Global.SERVER + "/read.php?lite=js&noprefix&tid=" + tid
				+ "&_ff=" + fid + "&page=" + page;

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

				JSONObject jsonRoot = JSON.parseObject(strResult);
				JSONObject dataObject = jsonRoot.getJSONObject("data");

				JSONObject __U = dataObject.getJSONObject("__U");

				mReplyListData = new ReplyListData();
				Map<String, UserInfoData> userMap = new HashMap<String, UserInfoData>();
				for (String key : __U.keySet()) {
					UserInfoData userInfoData = __U.getObject(key,
							UserInfoData.class);
					userMap.put(key, userInfoData);
				}
				mReplyListData.set__U(userMap);

				JSONObject __R = dataObject.getJSONObject("__R");

				Map<String, ReplyData> replyDataMap = new HashMap<String, ReplyData>();
				for (String key : __R.keySet()) {
					ReplyData replyData = __R.getObject(key, ReplyData.class);

					// 评论列表
					JSONObject comment = __R.getJSONObject(key).getJSONObject(
							"comment");
					List<CommentData> commentList = new ArrayList<CommentData>();
					if (comment != null) {
						Log.i(TAG, "comment size = " + comment.size());
						for (int i = 0; i < comment.size(); i++) {
							Log.i(TAG, "commentKey = " + i);
							CommentData commentData = comment.getObject(i + "",
									CommentData.class);
							commentList.add(commentData);
						}
						replyData.setCommentList(commentList);
					}

					// 回复内容整理为html
					int type = replyData.getType();
					if (replyData.getType() == 2) {
						replyData
								.setHtmlContent("<span style='color:#D00;white-space:nowrap'>[隐藏]</span>");

					} else if (type == 1024) {
						replyData
								.setHtmlContent("<span style='color:#D00;white-space:nowrap'>[锁定]</span>");

					} else {
						boolean loadImg = true;
						SharedPreferences prefs = PreferenceManager
								.getDefaultSharedPreferences(mContext);
						Boolean isLoadImage = prefs.getBoolean("is_load_image",
								false);
						if (Utils.getNetworkType(mContext) == Utils.NetworkType.MOBILE
								&& !isLoadImage) {
							loadImg = false;
						}
						replyData.setHtmlContent(Utils.decodeForumTag(
								replyData.getContent(), loadImg));
					}
					replyDataMap.put(key, replyData);
				}
				mReplyListData.set__R(replyDataMap);

				mReplyListData
						.set__R__ROWS(dataObject.getIntValue("__R__ROWS"));
				mReplyListData.set__R__ROWS_PAGE(dataObject
						.getIntValue("__R__ROWS_PAGE"));
				mReplyListData.set__ROWS(dataObject.getIntValue("__ROWS"));
				mReplyListData.setTime(jsonRoot.getLongValue("time"));

				return SUCCESS;
			} else if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
				return SERVERERROR;
			} else if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_FORBIDDEN) {
				return FORBIDDEN;
			}
			return OTHERERROR;
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
			httpclient.getConnectionManager().shutdown();
		}
	}

	@Override
	protected void onPostExecute(Integer status) {
		if (status == SUCCESS) {
			mDataListener.onPostFinished(mReplyListData);
		} else {
			mDataListener.onPostError(status);
			if (status == TIMEOUT) {
				Toast.makeText(
						mContext.getApplicationContext(),
						mContext.getResources().getString(
								R.string.request_timeout), Toast.LENGTH_SHORT)
						.show();
			} else if (status == DATAERROR) {
				Toast.makeText(
						mContext.getApplicationContext(),
						mContext.getResources().getString(
								R.string.request_dataerror), Toast.LENGTH_SHORT)
						.show();
			} else if (status == NETERROR) {
				Toast.makeText(
						mContext.getApplicationContext(),
						mContext.getResources().getString(
								R.string.request_neterror), Toast.LENGTH_SHORT)
						.show();
			} else if (status == SERVERERROR) {
				Toast.makeText(
						mContext.getApplicationContext(),
						mContext.getResources().getString(
								R.string.request_servererror),
						Toast.LENGTH_SHORT).show();
			} else if (status == FORBIDDEN) {
				Toast.makeText(
						mContext.getApplicationContext(),
						mContext.getResources().getString(
								R.string.request_forbiddenerror),
						Toast.LENGTH_SHORT).show();
			} else if (status == OTHERERROR) {
				Toast.makeText(
						mContext.getApplicationContext(),
						mContext.getResources().getString(
								R.string.request_othererror),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

}
