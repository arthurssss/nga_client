package com.arthur.ngaclient.bean;

import java.util.Map;

public class ReplyData {
	private String content; // 帖子内容
	private String alterinfo; // 修改/加分信息
	private int type; // 帖子状态bit
	private int authorid; // 发帖人uid
	private String postdate; // 无用
	private String subject; // 帖子标题
	private int pid; // 回复id 主贴本身为0
	private int tid; // 主题id
	private int fid; // 所在版面id
	private int content_length; // 内容长度
	private int org_fid; // 发帖时所在版面id
	private Map<String, AttachsData> attachs; // 附件
	private int lou; // 楼层
	private long postdatetimestamp; // 发帖时间
	private String htmlContent; 

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAlterinfo() {
		return alterinfo;
	}

	public void setAlterinfo(String alterinfo) {
		this.alterinfo = alterinfo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getAuthorid() {
		return authorid;
	}

	public void setAuthorid(int authorid) {
		this.authorid = authorid;
	}

	public String getPostdate() {
		return postdate;
	}

	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public int getContent_length() {
		return content_length;
	}

	public void setContent_length(int content_length) {
		this.content_length = content_length;
	}

	public int getOrg_fid() {
		return org_fid;
	}

	public void setOrg_fid(int org_fid) {
		this.org_fid = org_fid;
	}

	public Map<String, AttachsData> getAttachs() {
		return attachs;
	}

	public void setAttachs(Map<String, AttachsData> attachs) {
		this.attachs = attachs;
	}

	public int getLou() {
		return lou;
	}

	public void setLou(int lou) {
		this.lou = lou;
	}

	public long getPostdatetimestamp() {
		return postdatetimestamp;
	}

	public void setPostdatetimestamp(long postdatetimestamp) {
		this.postdatetimestamp = postdatetimestamp;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

}
