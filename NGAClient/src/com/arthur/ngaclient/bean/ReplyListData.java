package com.arthur.ngaclient.bean;

import java.util.Map;

public class ReplyListData {

	private Map<String, UserInfoData> __U; // 用户数据
	private Map<String, ReplyData> __R; // 帖子数据
	private int __ROWS; // 贴数总和 (总页数=ceil(__ROWS/__R__ROWS_PAGE)
	private int __R__ROWS; // 本页帖子数据 的数量
	private int __R__ROWS_PAGE; // 每页的帖子数
	private long time; // 当前时间

	public Map<String, UserInfoData> get__U() {
		return __U;
	}

	public void set__U(Map<String, UserInfoData> __U) {
		this.__U = __U;
	}

	public Map<String, ReplyData> get__R() {
		return __R;
	}

	public void set__R(Map<String, ReplyData> __R) {
		this.__R = __R;
	}

	public int get__ROWS() {
		return __ROWS;
	}

	public void set__ROWS(int __ROWS) {
		this.__ROWS = __ROWS;
	}

	public int get__R__ROWS() {
		return __R__ROWS;
	}

	public void set__R__ROWS(int __R__ROWS) {
		this.__R__ROWS = __R__ROWS;
	}

	public int get__R__ROWS_PAGE() {
		return __R__ROWS_PAGE;
	}

	public void set__R__ROWS_PAGE(int __R__ROWS_PAGE) {
		this.__R__ROWS_PAGE = __R__ROWS_PAGE;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
