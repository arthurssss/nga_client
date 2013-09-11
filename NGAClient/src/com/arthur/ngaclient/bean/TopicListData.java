package com.arthur.ngaclient.bean;

import java.util.List;

public class TopicListData {

	private List<TopicData> topicList;
	private List<SubForumData> subForumList;

	public List<TopicData> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<TopicData> topicList) {
		this.topicList = topicList;
	}

	public List<SubForumData> getSubForumList() {
		return subForumList;
	}

	public void setSubForumList(List<SubForumData> subForumList) {
		this.subForumList = subForumList;
	}
}
