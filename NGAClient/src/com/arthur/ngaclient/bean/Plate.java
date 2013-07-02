package com.arthur.ngaclient.bean;

import java.util.ArrayList;
import java.util.List;

public class Plate {

	/**
	 * 综合讨论区
	 */
	public static final int ZHTL = 0;

	/**
	 * 职业讨论区
	 */
	public static final int ZYTL = 1;

	/**
	 * 冒险心得
	 */
	public static final int MXXD = 2;

	/**
	 * 麦迪文之塔
	 */
	public static final int MDWZT = 3;

	/**
	 * 暗黑破坏神
	 */
	public static final int AHPHS = 4;

	/**
	 * 英雄联盟
	 */
	public static final int YXLM = 5;

	/**
	 * 其他游戏
	 */
	public static final int QTYX = 6;

	/**
	 * 推荐专区
	 */
	public static final int TJZQ = 7;

	/**
	 * 其他版面
	 */
	public static final int QTBM = 8;

	private String name;
	private List<Board> boardList = new ArrayList<Board>();
	
	public Plate(String name){
		this.name = name;
	}
	
	public Plate(String name, List<Board> boardList){
		this.name = name;
		this.boardList = boardList;
	}
	
	public void add(Board board){
		boardList.add(board);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Board> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<Board> boardList) {
		this.boardList = boardList;
	}
	
	public static String getPlateName(int i){
		String name = null;
		switch (i){
		case 0:
			name = "综合讨论";
			break;
		case 1:
			name = "职业讨论区";
			break;
		case 2:
			name = "冒险心得";
			break;
		case 3:
			name = "麦迪文之塔";
			break;
		case 4:
			name = "暗黑破坏神";
			break;
		case 5:
			name = "英雄联盟";
			break;
		case 6:
			name = "其他游戏";
			break;
		case 7:
			name = "推荐专区";
			break;
		case 8:
			name = "其他版面";
			break;
		}
		return name;
	}
	
}
