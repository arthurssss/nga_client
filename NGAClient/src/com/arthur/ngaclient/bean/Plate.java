package com.arthur.ngaclient.bean;

import java.util.ArrayList;
import java.util.List;

public class Plate {

	/**
	 * �ۺ�������
	 */
	public static final int ZHTL = 0;

	/**
	 * ְҵ������
	 */
	public static final int ZYTL = 1;

	/**
	 * ð���ĵ�
	 */
	public static final int MXXD = 2;

	/**
	 * �����֮��
	 */
	public static final int MDWZT = 3;

	/**
	 * �����ƻ���
	 */
	public static final int AHPHS = 4;

	/**
	 * Ӣ������
	 */
	public static final int YXLM = 5;

	/**
	 * ������Ϸ
	 */
	public static final int QTYX = 6;

	/**
	 * �Ƽ�ר��
	 */
	public static final int TJZQ = 7;

	/**
	 * ��������
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
			name = "�ۺ�����";
			break;
		case 1:
			name = "ְҵ������";
			break;
		case 2:
			name = "ð���ĵ�";
			break;
		case 3:
			name = "�����֮��";
			break;
		case 4:
			name = "�����ƻ���";
			break;
		case 5:
			name = "Ӣ������";
			break;
		case 6:
			name = "������Ϸ";
			break;
		case 7:
			name = "�Ƽ�ר��";
			break;
		case 8:
			name = "��������";
			break;
		}
		return name;
	}
	
}
