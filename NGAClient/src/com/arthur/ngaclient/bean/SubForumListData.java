package com.arthur.ngaclient.bean;

import java.util.List;

public class SubForumListData {
	private int fid; // 当前版面id (搜索用户发帖等情况是没有当前版面id
	private int topped_topic; // 置顶贴的ID 可能有多个 逗号分隔
	private List<SubForumData> sub_forums; // 当前版面的子论坛或者联合版面等 版面图标在公共变量文件里
	private String __UNION_FORUM; // 联合版面中默认设置的版面ID 逗号分隔
	private String __UNION_FORUM_DEFAULT; // 联合版面中默认显示的版面ID 逗号分隔
	private String __SELECTED_FORUM; // 联合版面中用户选择显示的版面id 逗号分隔
										// (如选择了一个以上会包括联合版面本身的id)

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
