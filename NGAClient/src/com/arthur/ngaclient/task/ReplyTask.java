package com.arthur.ngaclient.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
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
import com.arthur.ngaclient.bean.Global;
import com.arthur.ngaclient.interfaces.IOnReplySuccessListener;
import com.arthur.ngaclient.util.HttpUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ReplyTask extends AsyncTask<String, Integer, Boolean> {

	private static final String TAG = ReplyTask.class.getSimpleName();
	private Context mContext = null;
	private IOnReplySuccessListener mListener = null;

	private String mResultStr = "";

	public ReplyTask(Context context, IOnReplySuccessListener listener) {
		mContext = context;
		mListener = listener;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		String title = params[0];
		String content = params[1];
		String action = params[2];
		String fid = params[3];
		String tid = params[4];

		String url = Global.SERVER + "/post.php";
		String param = "";
		StringBuffer sb = new StringBuffer();

		try {

			sb.append("step=2");
			sb.append("&pid=");
			sb.append("&action=");
			sb.append(action);
			sb.append("&_ff=");
			if (fid != null) {
				sb.append("&fid=");
				sb.append(fid);
			}
			if (tid != null) {
				sb.append("&tid=");
				sb.append(tid);
			}
			sb.append("&attachments=");
			sb.append("&attachments_check=");
			sb.append("&force_topic_key=");
			sb.append("&filter_key=1");
			sb.append("post_subject=");
			sb.append(URLEncoder.encode(title, "GBK"));
			sb.append("&post_content=");
			sb.append(URLEncoder.encode(content, "GBK"));
			sb.append("&mention=");
			sb.append("&checkkey=");

		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		param = sb.toString();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("User-Agent", NGAClientApplication.USER_AGENT);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.addHeader("Accept-Charset", "GBK");
		httpPost.addHeader("Accept-Encoding", "gzip");
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
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK
					|| response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_FORBIDDEN) {
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
				StringBuffer resultStringBuffer = new StringBuffer();
				while ((data = br.readLine()) != null) {
					resultStringBuffer.append(data);
				}
				String strResult = resultStringBuffer.toString();
				mResultStr = getReplyResult(strResult);
				String successStr[] = { "发贴完毕 ...", " @提醒每24小时不能超过50个" };
				if (successStr[0].equals(mResultStr)
						|| successStr[1].equals(mResultStr)) {
					return true;
				}
				return false;
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
		return false;
	}

	@Override
	protected void onPostExecute(Boolean isSuccess) {
		Toast.makeText(mContext, mResultStr, Toast.LENGTH_LONG).show();
		if (isSuccess) {
			mListener.onReplySuccess();
		}
	}

	private String getReplyResult(String html) {
		String result_start_tag = "<span style='color:#aaa'>&gt;</span>";
		String result_end_tag = "<br/>";
		int start = html.indexOf(result_start_tag);
		if (start == -1)
			return "发帖失败";
		start += result_start_tag.length();
		int end = html.indexOf(result_end_tag, start);
		if (start == -1)
			return "发帖失败";
		return html.substring(start, end);
	}
}
