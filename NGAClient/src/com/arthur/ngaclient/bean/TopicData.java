package com.arthur.ngaclient.bean;

public class TopicData {
	private int tid; //����id 
    private int fid; //�������ڰ���id 
    private int quote_from;//���������� 
    private String quote_to;//���������õ����� 
    private int icon;//ͼ�� 
    private String titlefont;//������ʽ 
    private String author;//���� 
    private int authorid;//����uid 
    private String subject;//���� 
    private int ifmark; 
    private int type;//��������bit 
    private int type_2; 
    private int postdate;//����ʱ�� 
    private int lastpost;//���ظ�ʱ�� 
    private String lastposter;//���ظ��� 
    private int replies;//�ظ����� (�ظ�ҳ��=�ظ�����/__R__ROWS_PAGE 
    private int locked; 
    private int digest; 
    private int ifupload; 
    private int lastmodify;//����޸�ʱ�� 
    private int recommend; 
    private int admin_ui;//�û��Ƿ�Դ�������Ȩ��bit 
    private String tpcurl;//�����ַ 
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
