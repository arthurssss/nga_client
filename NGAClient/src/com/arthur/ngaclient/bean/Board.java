package com.arthur.ngaclient.bean;

public class Board {
	private int category;
	private String url;
	private String name;
	private int icon;

	public Board(int category, String url, String name, int icon) {
		this.category = category;
		this.url = url;
		this.name = name;
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

}
