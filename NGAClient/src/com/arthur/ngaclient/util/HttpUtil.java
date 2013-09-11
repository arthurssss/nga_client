package com.arthur.ngaclient.util;

import android.content.Context;

public class HttpUtil {
	public static String getCookie(Context context) {
		if (Config.get(context, "uid") != null
				&& Config.get(context, "cid") != null) {
			return "ngaPassportUid=" + Config.get(context, "uid")
					+ "; ngaPassportCid=" + Config.get(context, "cid");
		}
		return "";
	}
}
