package com.arthur.ngaclient;

import java.util.ArrayList;
import java.util.List;

import com.arthur.ngaclient.bean.Board;
import com.arthur.ngaclient.bean.Plate;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class NGAClientApplication extends Application {

	public static final String USER_AGENT = "AndroidNga/460";
	private SharedPreferences mSharedPreferences;

	@Override
	public void onCreate() {
		super.onCreate();
		if (mSharedPreferences == null) {
			mSharedPreferences = getSharedPreferences("config",
					Context.MODE_PRIVATE);
		}
		initImageLoader(getApplicationContext());
	}

	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				//.writeDebugLogs()
				.build();
		ImageLoader.getInstance().init(config);
	}
	
	public SharedPreferences getConfig() {
		if (mSharedPreferences == null) {
			mSharedPreferences = getSharedPreferences("config",
					Context.MODE_PRIVATE);
		}
		return mSharedPreferences;
	}

	public List<Plate> loadDefaultBoard() {

		List<Plate> plates = new ArrayList<Plate>();

		int i = 0;
		Plate plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "7", "艾泽拉斯议事厅", R.drawable.p7));
		plate.add(new Board(i, "323", "国服以外讨论", R.drawable.p323));
		plate.add(new Board(i, "-7", "大漩涡", R.drawable.p354));
		plate.add(new Board(i, "230", "艾泽拉斯风纪委员会", R.drawable.p230));
		plate.add(new Board(i, "310", "国家地理精英议会", R.drawable.p310));
		plate.add(new Board(i, "414", "游戏综合讨论", R.drawable.p414));
		plate.add(new Board(i, "305", "305权贵区", R.drawable.pdefault));
		plate.add(new Board(i, "11", "诺森德埋骨地", R.drawable.pdefault));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "390", "五晨寺", R.drawable.p390));
		plate.add(new Board(i, "320", "黑锋要塞", R.drawable.p320));
		plate.add(new Board(i, "181", "铁血沙场", R.drawable.p181));
		plate.add(new Board(i, "182", "魔法圣堂", R.drawable.p182));
		plate.add(new Board(i, "183", "信仰神殿", R.drawable.p183));
		plate.add(new Board(i, "185", "风暴祭坛", R.drawable.p185));
		plate.add(new Board(i, "186", "翡翠梦境", R.drawable.p186));
		plate.add(new Board(i, "187", "猎手大厅", R.drawable.p187));
		plate.add(new Board(i, "184", "圣光之力", R.drawable.p184));
		plate.add(new Board(i, "188", "恶魔深渊", R.drawable.p188));
		plate.add(new Board(i, "189", "暗影裂口", R.drawable.p189));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "310", "国家地理精英议会", R.drawable.p310));
		plate.add(new Board(i, "190", "任务讨论", R.drawable.p190));
		plate.add(new Board(i, "218", "副本专区", R.drawable.p218));
		plate.add(new Board(i, "327", "成就讨论", R.drawable.p327));
		plate.add(new Board(i, "388", "幻化讨论", R.drawable.p388));
		plate.add(new Board(i, "411", "宠物讨论", R.drawable.p411));
		plate.add(new Board(i, "191", "地精商会", R.drawable.p191));
		plate.add(new Board(i, "200", "插件研究", R.drawable.p200));
		plate.add(new Board(i, "274", "插件发布", R.drawable.p274));
		plate.add(new Board(i, "272", "竞技场", R.drawable.p272));
		plate.add(new Board(i, "258", "战场讨论", R.drawable.p258));
		plate.add(new Board(i, "213", "战争档案", R.drawable.p213));
		plate.add(new Board(i, "255", "公会管理", R.drawable.p10));
		plate.add(new Board(i, "306", "人员招募", R.drawable.p10));
		plate.add(new Board(i, "315", "战斗统计", R.drawable.p315));
		plate.add(new Board(i, "240", "BigFoot", R.drawable.p240));
		plate.add(new Board(i, "333", "DKP系统", R.drawable.p333));
		plate.add(new Board(i, "173", "帐号安全", R.drawable.p193));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "8", "大图书馆", R.drawable.p8));
		plate.add(new Board(i, "254", "镶金玫瑰", R.drawable.p254));
		plate.add(new Board(i, "355", "龟岩兄弟会", R.drawable.p355));
		plate.add(new Board(i, "116", "奇迹之泉", R.drawable.p116));
		plate.add(new Board(i, "124", "壁画洞窟", R.drawable.pdefault));
		plate.add(new Board(i, "102", "作家协会", R.drawable.p102));
		plate.add(new Board(i, "264", "卡拉赞剧院", R.drawable.p264));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "318", "暗黑破坏神3", R.drawable.p318));
		plate.add(new Board(i, "409", "HC讨论区", R.drawable.p403));
		plate.add(new Board(i, "401", "装备交易", R.drawable.p401));
		plate.add(new Board(i, "404", "金币交易", R.drawable.p401));
		plate.add(new Board(i, "394", "装备询价", R.drawable.p401));
		plate.add(new Board(i, "393", "背景故事与文艺作品", R.drawable.p393));
		plate.add(new Board(i, "400", "职业讨论区", R.drawable.pdefault));
		plate.add(new Board(i, "395", "野蛮人", R.drawable.p395));
		plate.add(new Board(i, "396", "猎魔人", R.drawable.p396));
		plate.add(new Board(i, "397", "武僧", R.drawable.p397));
		plate.add(new Board(i, "398", "巫医", R.drawable.p398));
		plate.add(new Board(i, "399", "魔法师", R.drawable.p399));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "-152678", "英雄联盟", R.drawable.p152678));
		plate.add(new Board(i, "418", "游戏视频", R.drawable.pdefault));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "414", "游戏综合讨论", R.drawable.p414));
		plate.add(new Board(i, "427", " 怪物猎人", R.drawable.pdefault));
		plate.add(new Board(i, "426", " 手机游戏讨论", R.drawable.pdefault));
		plate.add(new Board(i, "425", " 行星边际2", R.drawable.pdefault));
		plate.add(new Board(i, "422", " 炉石传说", R.drawable.pdefault));
		plate.add(new Board(i, "-65653", "剑灵", R.drawable.pdefault));
		plate.add(new Board(i, "412", " 巫师之怒", R.drawable.p412));
		plate.add(new Board(i, "-235147", "激战2", R.drawable.pdefault));
		plate.add(new Board(i, "-46468", "坦克世界", R.drawable.p46468));
		plate.add(new Board(i, "321", "DotA", R.drawable.p321));
		plate.add(new Board(i, "-2371813", "EVE", R.drawable.p2371813));
		plate.add(new Board(i, "-7861121", "剑叁 ", R.drawable.pdefault));
		plate.add(new Board(i, "-793427", "无尽英雄", R.drawable.pdefault));
		plate.add(new Board(i, "332", "战锤40K", R.drawable.p332));
		plate.add(new Board(i, "416", "火炬之光2", R.drawable.pdefault));
		plate.add(new Board(i, "406", "星际争霸2", R.drawable.pdefault));
		plate.add(new Board(i, "420", "MT", R.drawable.pdefault));
		plate.add(new Board(i, "424", "圣斗士星矢Online", R.drawable.pdefault));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "271", "魔兽世界卡牌游戏", R.drawable.p201));
		i++;
		plates.add(plate);

		plate = new Plate(Plate.getPlateName(i));
		plate.add(new Board(i, "10", "银色黎明裁判所", R.drawable.p10));
		plate.add(new Board(i, "201", "系统问题", R.drawable.p201));
		plate.add(new Board(i, "334", "硬件配置", R.drawable.p334));
		plate.add(new Board(i, "335", "大IT科技", R.drawable.p335));
		plate.add(new Board(i, "-447601", " 二次元国家地理", R.drawable.houzi));
		plate.add(new Board(i, "-522474", "综合体育讨论区", R.drawable.pdefault));
		plate.add(new Board(i, "-1068355", "晴风村", R.drawable.pdefault));
		plate.add(new Board(i, "-343809", "寂寞的车俱乐部", R.drawable.pdefault));
		plate.add(new Board(i, "-131429", "红茶馆——小说馆", R.drawable.pdefault));
		plate.add(new Board(i, "-46468", " 坦克世界", R.drawable.pdefault));
		plate.add(new Board(i, "-2371813", "NGA驻吉他海四办公室", R.drawable.pdefault));
		plate.add(new Board(i, "-124119", "菠萝方舟 ", R.drawable.pdefault));
		plate.add(new Board(i, "-84", " 模玩之魂", R.drawable.pdefault));
		plate.add(new Board(i, "-187579", " 大旋涡历史博物馆", R.drawable.pdefault));
		plate.add(new Board(i, "-308670", "血库的个人空间", R.drawable.pdefault));
		plate.add(new Board(i, "-112905", "八圣祠", R.drawable.pdefault));
		plate.add(new Board(i, "-8725919", "小窗视界", R.drawable.pdefault));
		plate.add(new Board(i, "-608808", "血腥厨房", R.drawable.pdefault));
		plate.add(new Board(i, "-469608", "影视讨论", R.drawable.pdefault));
		plate.add(new Board(i, "-55912", "音乐讨论", R.drawable.pdefault));
		plate.add(new Board(i, "-353371", "傻乎乎的小宠物", R.drawable.pdefault));
		plate.add(new Board(i, "-538800", "乙女向二次元", R.drawable.pdefault));
		plate.add(new Board(i, "-522679", "Battlefield 3", R.drawable.pdefault));
		plate.add(new Board(i, "-7678526", "麻将科学院", R.drawable.pdefault));
		plate.add(new Board(i, "-202020", "一只IT喵的自我修养", R.drawable.pdefault));
		plate.add(new Board(i, "-444012", "我们的骑迹", R.drawable.pdefault));
		plate.add(new Board(i, "-47218", " 没有刀的漆器", R.drawable.pdefault));
		plate.add(new Board(i, "-349066", "开心茶园", R.drawable.pdefault));
		plate.add(new Board(i, "-314508", "世界尽头的百货公司", R.drawable.pdefault));
		plate.add(new Board(i, "-2671", "耳机区", R.drawable.pdefault));
		plate.add(new Board(i, "-168888", "育儿版", R.drawable.pdefault));
		plate.add(new Board(i, "-54214", "时尚板", R.drawable.pdefault));
		plate.add(new Board(i, "-1513130", "鲜血兄弟会", R.drawable.pdefault));
		plates.add(plate);

		return plates;
	}
}
