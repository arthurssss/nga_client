package com.arthur.ngaclient.interfaces;

import com.arthur.ngaclient.bean.TopicListData;

public interface ITopicDataLoadedListener {
	public void onPostFinished(TopicListData topicListData);
	public void onPostError(Integer status);
}
