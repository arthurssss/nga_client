package com.arthur.ngaclient.bean;

public class UserInfoData {

    private int uid; //uid
    private String username; //用户名
    private int credit; //无用
    private String medal; //徽章id 逗号分隔
    private String reputation; //无用
    private int groupid; //用户组 如果是-1使用下一个用户组
    private int memberid; //用户组
    private String avatar; //头像 和以前一样 可能是字符串也可能是object
    private int yz; //激活状态 1激活 0未激活 -1nuke -2往下账号禁用 
    private String site; //个人版名 
    private String honor; //头衔 
    private long regdate; //注册日期 
    private long mute_time; //禁言到期时间 
    private long postnum; //发帖数 
    private int rvrc; //威望 
    private long money; //金钱 铜币数 
    private long thisvisit; //最后一次访问 
    private String signature; //签名 
    private String nickname; //无用 
	private int bit_data; // 用户状态bit

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public String getMedal() {
		return medal;
	}

	public void setMedal(String medal) {
		this.medal = medal;
	}

	public String getReputation() {
		return reputation;
	}

	public void setReputation(String reputation) {
		this.reputation = reputation;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public int getMemberid() {
		return memberid;
	}

	public void setMemberid(int memberid) {
		this.memberid = memberid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getYz() {
		return yz;
	}

	public void setYz(int yz) {
		this.yz = yz;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getHonor() {
		return honor;
	}

	public void setHonor(String honor) {
		this.honor = honor;
	}

	public long getRegdate() {
		return regdate;
	}

	public void setRegdate(long regdate) {
		this.regdate = regdate;
	}

	public long getMute_time() {
		return mute_time;
	}

	public void setMute_time(long mute_time) {
		this.mute_time = mute_time;
	}

	public long getPostnum() {
		return postnum;
	}

	public void setPostnum(long postnum) {
		this.postnum = postnum;
	}

	public int getRvrc() {
		return rvrc;
	}

	public void setRvrc(int rvrc) {
		this.rvrc = rvrc;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public long getThisvisit() {
		return thisvisit;
	}

	public void setThisvisit(long thisvisit) {
		this.thisvisit = thisvisit;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getBit_data() {
		return bit_data;
	}

	public void setBit_data(int bit_data) {
		this.bit_data = bit_data;
	}

}
