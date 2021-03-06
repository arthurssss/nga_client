package com.arthur.ngaclient.adapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.bean.TopicData;
import com.arthur.ngaclient.bean.TopicListData;
import com.arthur.ngaclient.util.Utils;

public class TopicListAdapter extends BaseAdapter {

	private static final String TAG = TopicListAdapter.class.getSimpleName();
	private Context mContext = null;
	private LayoutInflater mInflater = null;
	private TopicListData mTopicListData = null;

	public TopicListAdapter(Context context, TopicListData topicListData) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mTopicListData = topicListData;
	}

	public void refresh(TopicListData topicListData) {
		mTopicListData = topicListData;
		notifyDataSetChanged();
	}

	public void addTopicAndRefresh(List<TopicData> toplicList) {
		mTopicListData.getTopicList().addAll(toplicList);
		mTopicListData.set__T__ROWS(mTopicListData.get__T__ROWS()
				+ toplicList.size());
		notifyDataSetChanged();
	}

	public TopicListData getTopicListData() {
		return mTopicListData;
	}

	@Override
	public int getCount() {
		return mTopicListData.get__T__ROWS();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.item_topiclist_topic, null);
			holder = new ViewHolder();
			holder.tvReplyCount = (TextView) convertView
					.findViewById(R.id.board_reply_count);
			holder.tvTopicTitle = (TextView) convertView
					.findViewById(R.id.board_topic_title);
			holder.tvTopicAuthor = (TextView) convertView
					.findViewById(R.id.board_topic_author);
			holder.tvTopicPoster = (TextView) convertView
					.findViewById(R.id.board_topic_poster);
			holder.llTopicTitleBg = (LinearLayout) convertView
					.findViewById(R.id.board_title_bg);
			holder.tvTopicReplyTime = (TextView) convertView
					.findViewById(R.id.board_topic_replytime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		TextPaint tp = holder.tvTopicTitle.getPaint();
		TopicData topicData = mTopicListData.getTopicList().get(position);
		tp.setFakeBoldText(false);
		if (topicData != null) {
			String titleFont = topicData.getTitlefont();
			if (titleFont != null && !titleFont.equals("")) {
				if (titleFont.contains("~1")) {
					tp.setFakeBoldText(true);
				}
				if (topicData.getPostdate() == 0) {
					holder.tvTopicTitle.setTextColor(mContext.getResources()
							.getColor(R.color.topic_timeout));
				} else {
					if (titleFont.startsWith("green")) {
						holder.tvTopicTitle.setTextColor(mContext
								.getResources().getColor(
										R.color.topictile_green));
					} else if (titleFont.startsWith("blue")) {
						holder.tvTopicTitle.setTextColor(mContext
								.getResources()
								.getColor(R.color.topictile_blue));
					} else if (titleFont.startsWith("red")) {
						holder.tvTopicTitle
								.setTextColor(mContext.getResources().getColor(
										R.color.topictile_red));
					} else if (titleFont.startsWith("orange")) {
						holder.tvTopicTitle.setTextColor(mContext
								.getResources().getColor(
										R.color.topictile_orange));
					}
				}
			} else {
				if (topicData.getPostdate() == 0) {
					holder.tvTopicTitle.setTextColor(mContext.getResources()
							.getColor(R.color.topic_timeout));
				} else {
					holder.tvTopicTitle.setTextColor(mContext.getResources()
							.getColor(R.color.topictile_normal));
				}
			}

			SpannableString ss = new SpannableString(Html.fromHtml(topicData
					.getSubject()));

			Pattern p = Pattern.compile("\\[.+?\\]");
			Matcher m = p.matcher(topicData.getSubject());
			while (m.find()) {
				System.out.println(m.start() + "," + m.end());
				ss.setSpan(new ForegroundColorSpan(mContext.getResources()
						.getColor(R.color.topic_title_class)), m.start(), m
						.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

			holder.tvTopicTitle.setText(ss);
			holder.tvTopicAuthor.setText(topicData.getAuthor());
			holder.tvTopicPoster.setText(topicData.getLastposter());
			holder.tvReplyCount.setText(topicData.getReplies() + "");
			holder.tvTopicReplyTime.setText(Utils.timeFormat(
					topicData.getLastpost(), mTopicListData.getTime()));

			float fontSize = 30;

			if (topicData.getReplies() > 9999) {
				fontSize = 14;
			} else if (topicData.getReplies() > 999) {
				fontSize = 17;
			} else if (topicData.getReplies() > 99) {
				fontSize = 20;
			} else if (topicData.getReplies() > 9) {
				fontSize = 25;
			}

			holder.tvReplyCount.setTextSize(fontSize);
			if (position % 2 == 0) {
				holder.tvReplyCount.setBackgroundResource(R.color.shit1_2);
				holder.llTopicTitleBg.setBackgroundResource(R.color.shit1_1);

				if (topicData.getType() == 1024) {
					int color = mContext.getResources().getColor(
							R.color.replycount_1024);
					holder.tvReplyCount.setTextColor(color);
				} else {
					int c = mContext.getResources().getColor(R.color.shit1_2);
					int[] replyColor = Utils.genReplyColor(
							Utils.rgbToHsv(Color.red(c), Color.green(c),
									Color.blue(c)), topicData.getReplies());

					Log.d(TAG, replyColor[0] + "," + replyColor[1] + ","
							+ replyColor[2]);
					holder.tvReplyCount.setTextColor(Color.rgb(replyColor[0],
							replyColor[1], replyColor[2]));
				}

			} else {
				holder.tvReplyCount.setBackgroundResource(R.color.shit2_2);
				holder.llTopicTitleBg.setBackgroundResource(R.color.shit2_1);

				if (topicData.getType() == 1024) {
					int color = mContext.getResources().getColor(
							R.color.replycount_1024);
					holder.tvReplyCount.setTextColor(color);
				} else {
					int c = mContext.getResources().getColor(R.color.shit2_2);

					int[] replyColor = Utils.genReplyColor(
							Utils.rgbToHsv(Color.red(c), Color.green(c),
									Color.blue(c)), topicData.getReplies());
					Log.d(TAG, replyColor[0] + "," + replyColor[1] + ","
							+ replyColor[2]);
					holder.tvReplyCount.setTextColor(Color.rgb(replyColor[0],
							replyColor[1], replyColor[2]));
				}
			}
		}
		return convertView;
	}

	private class ViewHolder {
		public TextView tvReplyCount;
		public TextView tvTopicTitle;
		public TextView tvTopicAuthor;
		public TextView tvTopicPoster;
		public TextView tvTopicReplyTime;
		public LinearLayout llTopicTitleBg;
	}

}