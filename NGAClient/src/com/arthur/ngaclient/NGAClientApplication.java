package com.arthur.ngaclient;

import java.util.ArrayList;
import java.util.List;

import com.arthur.ngaclient.bean.Board;
import com.arthur.ngaclient.bean.Plate;

import android.app.Application;

public class NGAClientApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public List<Plate> loadDefaultBoard() {

		List<Plate> plates = new ArrayList<Plate>();

		int i = 0;
		Plate plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "7", "������˹������", R.drawable.p7));
		plate.add(new Board(i, "323", "������������", R.drawable.p323));
		plate.add(new Board(i, "-7", "������", R.drawable.p354));
		plate.add(new Board(i, "230", "������˹���ίԱ��", R.drawable.p230));
		plate.add(new Board(i, "310", "���ҵ���Ӣ���", R.drawable.p310));
		plate.add(new Board(i, "414", "��Ϸ�ۺ�����", R.drawable.p414));
		plate.add(new Board(i, "305", "305Ȩ����", R.drawable.pdefault));
		plate.add(new Board(i, "11", "ŵɭ����ǵ�", R.drawable.pdefault));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "390", "�峿��", R.drawable.p390));
		plate.add(new Board(i, "320", "�ڷ�Ҫ��", R.drawable.p320));
		plate.add(new Board(i, "181", "��Ѫɳ��", R.drawable.p181));
		plate.add(new Board(i, "182", "ħ��ʥ��", R.drawable.p182));
		plate.add(new Board(i, "183", "�������", R.drawable.p183));
		plate.add(new Board(i, "185", "�籩��̳", R.drawable.p185));
		plate.add(new Board(i, "186", "����ξ�", R.drawable.p186));
		plate.add(new Board(i, "187", "���ִ���", R.drawable.p187));
		plate.add(new Board(i, "184", "ʥ��֮��", R.drawable.p184));
		plate.add(new Board(i, "188", "��ħ��Ԩ", R.drawable.p188));
		plate.add(new Board(i, "189", "��Ӱ�ѿ�", R.drawable.p189));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "310", "���ҵ���Ӣ���", R.drawable.p310));
		plate.add(new Board(i, "190", "��������", R.drawable.p190));
		plate.add(new Board(i, "218", "����ר��", R.drawable.p218));
		plate.add(new Board(i, "327", "�ɾ�����", R.drawable.p327));
		plate.add(new Board(i, "388", "�û�����", R.drawable.p388));
		plate.add(new Board(i, "411", "��������", R.drawable.p411));
		plate.add(new Board(i, "191", "�ؾ��̻�", R.drawable.p191));
		plate.add(new Board(i, "200", "����о�", R.drawable.p200));
		plate.add(new Board(i, "274", "�������", R.drawable.p274));
		plate.add(new Board(i, "272", "������", R.drawable.p272));
		plate.add(new Board(i, "258", "ս������", R.drawable.p258));
		plate.add(new Board(i, "213", "ս������", R.drawable.p213));
		plate.add(new Board(i, "255", "�������", R.drawable.p10));
		plate.add(new Board(i, "306", "��Ա��ļ", R.drawable.p10));
		plate.add(new Board(i, "315", "ս��ͳ��", R.drawable.p315));
		plate.add(new Board(i, "240", "BigFoot", R.drawable.p240));
		plate.add(new Board(i, "333", "DKPϵͳ", R.drawable.p333));
		plate.add(new Board(i, "173", "�ʺŰ�ȫ", R.drawable.p193));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "8", "��ͼ���", R.drawable.p8));
		plate.add(new Board(i, "254", "���õ��", R.drawable.p254));
		plate.add(new Board(i, "355", "�����ֵܻ�", R.drawable.p355));
		plate.add(new Board(i, "116", "�漣֮Ȫ", R.drawable.p116));
		plate.add(new Board(i, "124", "�ڻ�����", R.drawable.pdefault));
		plate.add(new Board(i, "102", "����Э��", R.drawable.p102));
		plate.add(new Board(i, "264", "�����޾�Ժ", R.drawable.p264));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "318", "�����ƻ���3", R.drawable.p318));
		plate.add(new Board(i, "409", "HC������", R.drawable.p403));
		plate.add(new Board(i, "401", "װ������", R.drawable.p401));
		plate.add(new Board(i, "404", "��ҽ���", R.drawable.p401));
		plate.add(new Board(i, "394", "װ��ѯ��", R.drawable.p401));
		plate.add(new Board(i, "393", "����������������Ʒ", R.drawable.p393));
		plate.add(new Board(i, "400", "ְҵ������", R.drawable.pdefault));
		plate.add(new Board(i, "395", "Ұ����", R.drawable.p395));
		plate.add(new Board(i, "396", "��ħ��", R.drawable.p396));
		plate.add(new Board(i, "397", "��ɮ", R.drawable.p397));
		plate.add(new Board(i, "398", "��ҽ", R.drawable.p398));
		plate.add(new Board(i, "399", "ħ��ʦ", R.drawable.p399));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "-152678", "Ӣ������", R.drawable.p152678));
		plate.add(new Board(i, "418", "��Ϸ��Ƶ", R.drawable.pdefault));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "414", "��Ϸ�ۺ�����", R.drawable.p414));
		plate.add(new Board(i, "427", " ��������", R.drawable.pdefault));
		plate.add(new Board(i, "426", " �ֻ���Ϸ����", R.drawable.pdefault));
		plate.add(new Board(i, "425", " ���Ǳ߼�2", R.drawable.pdefault));
		plate.add(new Board(i, "422", " ¯ʯ��˵", R.drawable.pdefault));
		plate.add(new Board(i, "-65653", "����", R.drawable.pdefault));
		plate.add(new Board(i, "412", " ��ʦ֮ŭ", R.drawable.p412));
		plate.add(new Board(i, "-235147", "��ս2", R.drawable.pdefault));
		plate.add(new Board(i, "-46468", "̹������", R.drawable.p46468));
		plate.add(new Board(i, "321", "DotA", R.drawable.p321));
		plate.add(new Board(i, "-2371813", "EVE", R.drawable.p2371813));
		plate.add(new Board(i, "-7861121", "���� ", R.drawable.pdefault));
		plate.add(new Board(i, "-793427", "�޾�Ӣ��", R.drawable.pdefault));
		plate.add(new Board(i, "332", "ս��40K", R.drawable.p332));
		plate.add(new Board(i, "416", "���֮��2", R.drawable.pdefault));
		plate.add(new Board(i, "406", "�Ǽ�����2", R.drawable.pdefault));
		plate.add(new Board(i, "420", "MT", R.drawable.pdefault));
		plate.add(new Board(i, "424", "ʥ��ʿ��ʸOnline", R.drawable.pdefault));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "271", "ħ�����翨����Ϸ", R.drawable.p201));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "10", "��ɫ����������", R.drawable.p10));
		plate.add(new Board(i, "201", "ϵͳ����", R.drawable.p201));
		plate.add(new Board(i, "334", "Ӳ������", R.drawable.p334));
		plate.add(new Board(i, "335", "��IT�Ƽ�", R.drawable.p335));
		plate.add(new Board(i, "-447601", " ����Ԫ���ҵ���", R.drawable.houzi));
		plate.add(new Board(i, "-522474", "�ۺ�����������", R.drawable.pdefault));
		plate.add(new Board(i, "-1068355", "����", R.drawable.pdefault));
		plate.add(new Board(i, "-343809", "��į�ĳ����ֲ�", R.drawable.pdefault));
		plate.add(new Board(i, "-131429", "���ݡ���С˵��", R.drawable.pdefault));
		plate.add(new Board(i, "-46468", " ̹������", R.drawable.pdefault));
		plate.add(new Board(i, "-2371813", "NGAפ�������İ칫��", R.drawable.pdefault));
		plate.add(new Board(i, "-124119", "���ܷ��� ", R.drawable.pdefault));
		plate.add(new Board(i, "-84", " ģ��֮��", R.drawable.pdefault));
		plate.add(new Board(i, "-187579", " ��������ʷ�����", R.drawable.pdefault));
		plate.add(new Board(i, "-308670", "Ѫ��ĸ��˿ռ�", R.drawable.pdefault));
		plate.add(new Board(i, "-112905", "��ʥ��", R.drawable.pdefault));
		plate.add(new Board(i, "-8725919", "С���ӽ�", R.drawable.pdefault));
		plate.add(new Board(i, "-608808", "Ѫ�ȳ���", R.drawable.pdefault));
		plate.add(new Board(i, "-469608", "Ӱ������", R.drawable.pdefault));
		plate.add(new Board(i, "-55912", "��������", R.drawable.pdefault));
		plate.add(new Board(i, "-353371", "ɵ������С����", R.drawable.pdefault));
		plate.add(new Board(i, "-538800", "��Ů�����Ԫ", R.drawable.pdefault));
		plate.add(new Board(i, "-522679", "Battlefield 3", R.drawable.pdefault));
		plate.add(new Board(i, "-7678526", "�齫��ѧԺ", R.drawable.pdefault));
		plate.add(new Board(i, "-202020", "һֻIT������������", R.drawable.pdefault));
		plate.add(new Board(i, "-444012", "���ǵ��Ｃ", R.drawable.pdefault));
		plate.add(new Board(i, "-47218", " û�е�������", R.drawable.pdefault));
		plate.add(new Board(i, "-349066", "���Ĳ�԰", R.drawable.pdefault));
		plate.add(new Board(i, "-314508", "���羡ͷ�İٻ���˾", R.drawable.pdefault));
		plate.add(new Board(i, "-2671", "������", R.drawable.pdefault));
		plate.add(new Board(i, "-168888", "������", R.drawable.pdefault));
		plate.add(new Board(i, "-54214", "ʱ�а�", R.drawable.pdefault));
		plate.add(new Board(i, "-1513130", "��Ѫ�ֵܻ�", R.drawable.pdefault));
		plates.add(plate);

		return plates;
	}
}
