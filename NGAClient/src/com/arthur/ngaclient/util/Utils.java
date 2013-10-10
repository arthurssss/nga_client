package com.arthur.ngaclient.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public final static String key = "asdfasdf";
	private static final String mLesserNukeStyle = "<div style='border:1px solid #B63F32;margin:10px 10px 10px 10px;padding:10px' > <span style='color:#EE8A9E'>用户因此贴被暂时禁言，此效果不会累加</span><br/>";
	private static final String mStyleAlignRight = "<div style='text-align:right' >";
	private static final String mStyleAlignLeft = "<div style='text-align:left' >";
	private static final String mStyleAlignCenter = "<div style='text-align:center' >";
	private static final String mStyleColor = "<span style='color:$1' >";
	private static final String mCollapseStart = "<div style='border:1px solid #888' >";
	private static final String mIgnoreCaseTag = "(?i)";
	private static final String mEndDiv = "</div>";

	public static String timeFormat(Long sec, Long curSec) {
		Long spentSec = curSec - sec;
		if (spentSec <= 60) {
			return "刚才";
		}
		if (spentSec > 60 && spentSec <= 300) {
			return "5分钟前";
		} else if (spentSec > 300 && spentSec <= 600) {
			return "10分钟前";
		} else if (spentSec > 600 && spentSec <= 900) {
			return "15分钟前";
		} else if (spentSec > 900 && spentSec <= 1200) {
			return "20分钟前";
		} else if (spentSec > 1200 && spentSec <= 1500) {
			return "25分钟前";
		} else if (spentSec > 1500 && spentSec <= 1800) {
			return "30分钟前";
		} else if (spentSec > 1800 && spentSec <= 2400) {
			return "40分钟前";
		} else if (spentSec > 2400 && spentSec <= 3000) {
			return "50分钟前";
		} else if (spentSec > 3000 && spentSec <= 3600) {
			return "1小时前";
		} else if (spentSec > 3600) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
					Locale.getDefault());
			try {
				Date date = new Date(sec * 1000);
				Long todayMs = sdf.parse(
						new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
								.format(new Date(curSec * 1000))).getTime();
				String time = new SimpleDateFormat("HH:mm", Locale.getDefault())
						.format(date);
				if (sec * 1000 >= todayMs) {
					return "今天 " + time;
				} else if (sec * 1000 >= todayMs - 24 * 60 * 60000
						&& sec * 1000 < todayMs) {
					return "昨天 " + time;
				} else if (sec * 1000 >= todayMs - 48 * 60 * 60000
						&& sec * 1000 < todayMs - 24 * 60 * 60000) {
					return "前天 " + time;
				} else {
					Calendar c = Calendar.getInstance();
					c.setTime(new Date(sec * 1000));
					int year = c.get(Calendar.YEAR);
					c.setTime(new Date(curSec * 1000));
					int curYear = c.get(Calendar.YEAR);
					if (year == curYear) {
						return new SimpleDateFormat("MM-dd hh:mm",
								Locale.getDefault()).format(date);
					}
					return new SimpleDateFormat("yyyy-MM-dd",
							Locale.getDefault()).format(date);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public static String decodeForumTag(String s, boolean showImage) {
		if (s == null || "".equals(s)) {
			return "";
		}

		String quoteStyle = "<div style='background:#E8E8E8;border:1px solid #888' >";
		// if(ThemeManager.getInstance().getMode() == ThemeManager.MODE_NIGHT)
		// quoteStyle =
		// "<div style='background:#000000;border:1px solid #888' >";

		final String styleLeft = "<div style='float:left' >";
		final String styleRight = "<div style='float:right' >";

		s = s.replaceAll(mIgnoreCaseTag + "\\[l\\]", styleLeft);
		s = s.replaceAll(mIgnoreCaseTag + "\\[/l\\]", mEndDiv);

		s = s.replaceAll(mIgnoreCaseTag + "\\[r\\]", styleRight);
		s = s.replaceAll(mIgnoreCaseTag + "\\[/r\\]", mEndDiv);

		s = s.replaceAll(mIgnoreCaseTag + "\\[align=right\\]", mStyleAlignRight);
		s = s.replaceAll(mIgnoreCaseTag + "\\[align=left\\]", mStyleAlignLeft);
		s = s.replaceAll(mIgnoreCaseTag + "\\[align=center\\]",
				mStyleAlignCenter);
		s = s.replaceAll(mIgnoreCaseTag + "\\[/align\\]", mEndDiv);

		s = s.replaceAll(mIgnoreCaseTag + "\\[quote\\]", quoteStyle);
		s = s.replaceAll(mIgnoreCaseTag + "\\[/quote\\]", mEndDiv);
		// reply
		s = s.replaceAll(mIgnoreCaseTag + "\\[pid=\\d+\\]Reply\\[/pid\\]",
				"Reply");
		s = s.replaceAll(mIgnoreCaseTag
				+ "\\[pid=\\d+,\\d+,\\d\\]Reply\\[/pid\\]", "Reply");

		// topic
		s = s.replaceAll(mIgnoreCaseTag + "\\[tid=\\d+\\]Topic\\[/pid\\]",
				"Topic");
		// reply
		// s =
		// s.replaceAll("\\[b\\]Reply to \\[pid=\\d+\\]Reply\\[/pid\\] (Post by .+ \\(\\d{4,4}-\\d\\d-\\d\\d \\d\\d:\\d\\d\\))\\[/b\\]"
		// , "Reply to Reply <b>$1</b>");
		// 转换 tag
		// [b]
		s = s.replaceAll(mIgnoreCaseTag + "\\[b\\]", "<b>");
		s = s.replaceAll(mIgnoreCaseTag + "\\[/b\\]", "</b>"/* "</font>" */);

		// item
		s = s.replaceAll(mIgnoreCaseTag + "\\[item\\]", "<b>");
		s = s.replaceAll(mIgnoreCaseTag + "\\[/item\\]", "</b>");

		s = s.replaceAll(mIgnoreCaseTag + "\\[u\\]", "<u>");
		s = s.replaceAll(mIgnoreCaseTag + "\\[/u\\]", "</u>");

		s = s.replaceAll(mIgnoreCaseTag + "\\[s:(\\d+)\\]",
				"<img src='file:///android_asset/a$1.gif'>");
		s = s.replace(mIgnoreCaseTag + "<br/><br/>", "<br/>");
		// [url][/url]
		s = s.replaceAll(mIgnoreCaseTag
				+ "\\[url\\](http[^\\[|\\]]+)\\[/url\\]",
				"<a href=\"$1\">$1</a>");
		s = s.replaceAll(mIgnoreCaseTag
				+ "\\[url=(http[^\\[|\\]]+)\\]\\s*(.+?)\\s*\\[/url\\]",
				"<a href=\"$1\">$2</a>");
		// flash
		s = s.replaceAll(
				mIgnoreCaseTag + "\\[flash\\](http[^\\[|\\]]+)\\[/flash\\]",
				"<a href=\"$1\"><img src='file:///android_asset/flash.png' style= 'max-width:100%;' ></a>");
		// color

		// s = s.replaceAll("\\[color=([^\\[|\\]]+)\\]\\s*(.+?)\\s*\\[/color\\]"
		// ,"<b style=\"color:$1\">$2</b>");
		s = s.replaceAll(mIgnoreCaseTag + "\\[color=([^\\[|\\]]+)\\]",
				mStyleColor);
		s = s.replaceAll(mIgnoreCaseTag + "\\[/color\\]", "</span>");

		// lessernuke
		s = s.replaceAll("\\[lessernuke\\]", mLesserNukeStyle);
		s = s.replaceAll("\\[/lessernuke\\]", mEndDiv);

		s = s.replaceAll(
				"\\[table\\]",
				"<table border='1px' cellspacing='0px' style='border-collapse:collapse;color:blue'><tbody>");
		s = s.replaceAll("\\[/table\\]", "</tbody></table>");
		s = s.replaceAll("\\[tr\\]", "<tr>");
		s = s.replaceAll("\\[/tr\\]", "<tr>");
		s = s.replaceAll("\\[td\\]", "<td>");
		s = s.replaceAll("\\[/td\\]", "<td>");
		// [i][/i]
		s = s.replaceAll(mIgnoreCaseTag + "\\[i\\]",
				"<i style=\"font-style:italic\">");
		s = s.replaceAll(mIgnoreCaseTag + "\\[/i\\]", "</i>");
		// [del][/del]
		s = s.replaceAll(mIgnoreCaseTag + "\\[del\\]", "<del class=\"gray\">");
		s = s.replaceAll(mIgnoreCaseTag + "\\[/del\\]", "</del>");

		s = s.replaceAll(mIgnoreCaseTag + "\\[font=([^\\[|\\]]+)\\]",
				"<span style=\"font-family:$1\">");
		s = s.replaceAll(mIgnoreCaseTag + "\\[/font\\]", "</span>");

		// collapse
		s = s.replaceAll(mIgnoreCaseTag
				+ "\\[collapse([^\\[|\\]])*\\](([\\d|\\D])+?)\\[/collapse\\]",
				mCollapseStart + "$2" + mEndDiv);

		// size
		s = s.replaceAll(mIgnoreCaseTag + "\\[size=(\\d+)%\\]",
				"<span style=\"font-size:$1%;line-height:$1%\">");
		s = s.replaceAll(mIgnoreCaseTag + "\\[/size\\]", "</span>");

		// [img]./ddd.jpg[/img]
		s = s.replaceAll(
				mIgnoreCaseTag + "\\[img\\]\\s*\\.(/[^\\[|\\]]+)\\s*\\[/img\\]",
				"<a href='http://img6.ngacn.cc/attachments$1'><img src='http://img6.ngacn.cc/attachments$1' style= 'max-width:100%' ></a>");
		s = s.replaceAll(mIgnoreCaseTag
				+ "\\[img\\]\\s*(http[^\\[|\\]]+)\\s*\\[/img\\]",
				"<a href='$1'><img src='$1' style= 'max-width:100%' ></a>");

		Pattern p = Pattern
				.compile("<img src='(http\\S+)' style= 'max-width:100%' >");
		Matcher m = p.matcher(s);
		try {
			while (m.find()) {
				String s0 = m.group();
				String s1 = m.group(1);
				String path = ExtensionEmotionUtil.getPathByURI(s1);
				if (path != null) {
					String newImgBlock = "<img src='"
							+ "file:///android_asset/" + path
							+ "' style= 'max-width:100%' >";
					s = s.replace(s0, newImgBlock);
				} else if (!showImage) {
					path = "ic_offline_image.png";
					String newImgBlock = "<img src='"
							+ "file:///android_asset/" + path
							+ "' style= 'max-width:100%' >";
					s = s.replace(s0, newImgBlock);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return s;
	}

}
