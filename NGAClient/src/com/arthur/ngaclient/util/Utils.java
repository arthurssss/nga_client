package com.arthur.ngaclient.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

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
								.format(new Date(curSec))).getTime();
				String time = new SimpleDateFormat("hh:mm", Locale.getDefault())
						.format(date);
				if (sec * 1000 > todayMs
						&& sec * 1000 <= todayMs - 24 * 60 * 60000) {
					return "今天 " + time;
				} else if (sec * 1000 > todayMs - 24 * 60 * 60000
						&& sec * 1000 <= todayMs - 48 * 60 * 60000) {
					return "昨天 " + time;
				} else if (sec * 1000 > todayMs - 48 * 60 * 60000
						&& sec * 1000 <= todayMs - 72 * 60 * 60000) {
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
}
