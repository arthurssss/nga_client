package com.arthur.ngaclient.bean;

import java.util.List;

public class TopicListData {

	/**
	 * 主题列表
	 */
	private List<TopicData> topicList;

	/**
	 * 子版面信息
	 */
	private SubForumListData __F;

	/**
	 * 本页的主题数量
	 */
	private int __T__ROWS;

	/**
	 * 主题列表每页的主题数
	 */
	private int __T__ROWS_PAGE;

	/**
	 * 阅读主题时每页的回复数
	 */
	private int __R__ROWS_PAGE;

	/**
	 * 数据的总行数
	 */
	private int __ROWS;
	
	/**
	 * 当前时间
	 */
	private long time;

	/**
	 * 获取数据的总行数
	 */
	public int get__ROWS() {
		return __ROWS;
	}

	/**
	 * 设置数据的总行数
	 */
	public void set__ROWS(int __ROWS) {
		this.__ROWS = __ROWS;
	}

	/**
	 * 获取本页的主题数量
	 */
	public int get__T__ROWS() {
		return __T__ROWS;
	}

	/**
	 * 设置本页的主题数量
	 */
	public void set__T__ROWS(int __T__ROWS) {
		this.__T__ROWS = __T__ROWS;
	}

	/**
	 * 获取主题列表每页的主题数
	 */
	public int get__T__ROWS_PAGE() {
		return __T__ROWS_PAGE;
	}

	/**
	 * 设置主题列表每页的主题数
	 */
	public void set__T__ROWS_PAGE(int __T__ROWS_PAGE) {
		this.__T__ROWS_PAGE = __T__ROWS_PAGE;
	}

	/**
	 * 获取阅读主题时每页的回复数
	 */
	public int get__R__ROWS_PAGE() {
		return __R__ROWS_PAGE;
	}

	/**
	 * 设置阅读主题时每页的回复数
	 */
	public void set__R__ROWS_PAGE(int __R__ROWS_PAGE) {
		this.__R__ROWS_PAGE = __R__ROWS_PAGE;
	}

	/**
	 * 获取主题列表
	 */
	public List<TopicData> getTopicList() {
		return topicList;
	}

	/**
	 * 设置主题列表
	 */
	public void setTopicList(List<TopicData> topicList) {
		this.topicList = topicList;
	}

	/**
	 * 获取子版面信息
	 */
	public SubForumListData get__F() {
		return __F;
	}

	/**
	 * 设置子版面信息
	 */
	public void set__F(SubForumListData __F) {
		this.__F = __F;
	}

	/**
	 * 获取当前时间
	 */
	public long getTime() {
		return time;
	}
	
	/**
	 * 设置当前时间
	 */
	public void setTime(long time) {
		this.time = time;
	}

}
