package com.arthur.ngaclient.bean;

import java.util.List;

public class SubForumListData {
	private int fid; // ��ǰ����id (�����û������������û�е�ǰ����id
	private int topped_topic; // �ö�����ID �����ж�� ���ŷָ�
	private List<SubForumData> sub_forums; // ��ǰ���������̳�������ϰ���� ����ͼ���ڹ��������ļ���
	private String __UNION_FORUM; // ���ϰ�����Ĭ�����õİ���ID ���ŷָ�
	private String __UNION_FORUM_DEFAULT; // ���ϰ�����Ĭ����ʾ�İ���ID ���ŷָ�
	private String __SELECTED_FORUM; // ���ϰ������û�ѡ����ʾ�İ���id ���ŷָ�
										// (��ѡ����һ�����ϻ�������ϰ��汾���id)

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public int getTopped_topic() {
		return topped_topic;
	}

	public void setTopped_topic(int topped_topic) {
		this.topped_topic = topped_topic;
	}

	public List<SubForumData> getSub_forums() {
		return sub_forums;
	}

	public void setSub_forums(List<SubForumData> sub_forums) {
		this.sub_forums = sub_forums;
	}

	public String get__UNION_FORUM() {
		return __UNION_FORUM;
	}

	public void set__UNION_FORUM(String __UNION_FORUM) {
		this.__UNION_FORUM = __UNION_FORUM;
	}

	public String get__UNION_FORUM_DEFAULT() {
		return __UNION_FORUM_DEFAULT;
	}

	public void set__UNION_FORUM_DEFAULT(String __UNION_FORUM_DEFAULT) {
		this.__UNION_FORUM_DEFAULT = __UNION_FORUM_DEFAULT;
	}

	public String get__SELECTED_FORUM() {
		return __SELECTED_FORUM;
	}

	public void set__SELECTED_FORUM(String __SELECTED_FORUM) {
		this.__SELECTED_FORUM = __SELECTED_FORUM;
	}
}
