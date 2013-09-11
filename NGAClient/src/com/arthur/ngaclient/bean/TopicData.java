package com.arthur.ngaclient.bean;

public class TopicData {
	private int tid; //主题id 
    private int fid; //主题所在版面id 
    private int quote_from;//引用自主题 
    private String quote_to;//此主题引用到主题 
    private int icon;//图标 
    private String titlefont;//标题样式 
    private String author;//作者 
    private int authorid;//作者uid 
    private String subject;//标题 
    private int ifmark; 
    private int type;//主题类型bit 
    private int type_2; 
    private int postdate;//发帖时间 
    private int lastpost;//最后回复时间 
    private String lastposter;//最后回复人 
    private int replies;//回复数量 (回复页数=回复数量/__R__ROWS_PAGE 
    private int locked; 
    private int digest; 
    private int ifupload; 
    private int lastmodify;//最后修改时间 
    private int recommend; 
    private int admin_ui;//用户是否对此主题有权限bit 
    private String tpcurl;//主题地址 
	private String ispage;

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

	public int getQuote_from() {
		return quote_from;
	}

	public void setQuote_from(int quote_from) {
		this.quote_from = quote_from;
	}

	public String getQuote_to() {
		return quote_to;
	}

	public void setQuote_to(String quote_to) {
		this.quote_to = quote_to;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getTitlefont() {
		return titlefont;
	}

	public void setTitlefont(String titlefont) {
		this.titlefont = titlefont;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getAuthorid() {
		return authorid;
	}

	public void setAuthorid(int authorid) {
		this.authorid = authorid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getIfmark() {
		return ifmark;
	}

	public void setIfmark(int ifmark) {
		this.ifmark = ifmark;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType_2() {
		return type_2;
	}

	public void setType_2(int type_2) {
		this.type_2 = type_2;
	}

	public int getPostdate() {
		return postdate;
	}

	public void setPostdate(int postdate) {
		this.postdate = postdate;
	}

	public int getLastpost() {
		return lastpost;
	}

	public void setLastpost(int lastpost) {
		this.lastpost = lastpost;
	}

	public String getLastposter() {
		return lastposter;
	}

	public void setLastposter(String lastposter) {
		this.lastposter = lastposter;
	}

	public int getReplies() {
		return replies;
	}

	public void setReplies(int replies) {
		this.replies = replies;
	}

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public int getDigest() {
		return digest;
	}

	public void setDigest(int digest) {
		this.digest = digest;
	}

	public int getIfupload() {
		return ifupload;
	}

	public void setIfupload(int ifupload) {
		this.ifupload = ifupload;
	}

	public int getLastmodify() {
		return lastmodify;
	}

	public void setLastmodify(int lastmodify) {
		this.lastmodify = lastmodify;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public int getAdmin_ui() {
		return admin_ui;
	}

	public void setAdmin_ui(int admin_ui) {
		this.admin_ui = admin_ui;
	}

	public String getTpcurl() {
		return tpcurl;
	}

	public void setTpcurl(String tpcurl) {
		this.tpcurl = tpcurl;
	}

	public String getIspage() {
		return ispage;
	}

	public void setIspage(String ispage) {
		this.ispage = ispage;
	}
}
