package com.arthur.ngaclient.bean;

import java.util.List;

public class TopicListData {

	/**
	 * �����б�
	 */
	private List<TopicData> topicList;

	/**
	 * �Ӱ�����Ϣ
	 */
	private SubForumListData __F;

	/**
	 * ��ҳ����������
	 */
	private int __T__ROWS;

	/**
	 * �����б�ÿҳ��������
	 */
	private int __T__ROWS_PAGE;

	/**
	 * �Ķ�����ʱÿҳ�Ļظ���
	 */
	private int __R__ROWS_PAGE;

	/**
	 * ���ݵ�������
	 */
	private int __ROWS;
	
	/**
	 * ��ǰʱ��
	 */
	private long time;

	/**
	 * ��ȡ���ݵ�������
	 */
	public int get__ROWS() {
		return __ROWS;
	}

	/**
	 * �������ݵ�������
	 */
	public void set__ROWS(int __ROWS) {
		this.__ROWS = __ROWS;
	}

	/**
	 * ��ȡ��ҳ����������
	 */
	public int get__T__ROWS() {
		return __T__ROWS;
	}

	/**
	 * ���ñ�ҳ����������
	 */
	public void set__T__ROWS(int __T__ROWS) {
		this.__T__ROWS = __T__ROWS;
	}

	/**
	 * ��ȡ�����б�ÿҳ��������
	 */
	public int get__T__ROWS_PAGE() {
		return __T__ROWS_PAGE;
	}

	/**
	 * ���������б�ÿҳ��������
	 */
	public void set__T__ROWS_PAGE(int __T__ROWS_PAGE) {
		this.__T__ROWS_PAGE = __T__ROWS_PAGE;
	}

	/**
	 * ��ȡ�Ķ�����ʱÿҳ�Ļظ���
	 */
	public int get__R__ROWS_PAGE() {
		return __R__ROWS_PAGE;
	}

	/**
	 * �����Ķ�����ʱÿҳ�Ļظ���
	 */
	public void set__R__ROWS_PAGE(int __R__ROWS_PAGE) {
		this.__R__ROWS_PAGE = __R__ROWS_PAGE;
	}

	/**
	 * ��ȡ�����б�
	 */
	public List<TopicData> getTopicList() {
		return topicList;
	}

	/**
	 * ���������б�
	 */
	public void setTopicList(List<TopicData> topicList) {
		this.topicList = topicList;
	}

	/**
	 * ��ȡ�Ӱ�����Ϣ
	 */
	public SubForumListData get__F() {
		return __F;
	}

	/**
	 * �����Ӱ�����Ϣ
	 */
	public void set__F(SubForumListData __F) {
		this.__F = __F;
	}

	/**
	 * ��ȡ��ǰʱ��
	 */
	public long getTime() {
		return time;
	}
	
	/**
	 * ���õ�ǰʱ��
	 */
	public void setTime(long time) {
		this.time = time;
	}

}
