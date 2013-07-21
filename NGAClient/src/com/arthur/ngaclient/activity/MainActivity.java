package com.arthur.ngaclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.arthur.ngaclient.NGAClientApplication;
import com.arthur.ngaclient.R;
import com.arthur.ngaclient.bean.Board;
import com.arthur.ngaclient.bean.Plate;
import com.arthur.ngaclient.util.DBManager;
import com.arthur.ngaclient.util.DensityUtil;
import com.arthur.ngaclient.widget.CustomGridView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private static final String TAG = "MainActivity";
	public static final String ARG_SECTION_NUMBER = "section_number";

	protected SectionsPagerAdapter mSectionsPagerAdapter;

	protected ViewPager mViewPager;

	protected DBManager mDBManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;

		Log.e(TAG + "  DisplayMetrics", "xdpi=" + xdpi + "; ydpi=" + ydpi);
		Log.e(TAG + "  DisplayMetrics", "density=" + density + "; densityDPI="
				+ densityDPI);

		int screenWidth = dm.widthPixels; // 屏幕宽（dip，如：320dip）
		int screenHeight = dm.heightPixels; // 屏幕宽（dip，如：533dip）

		Log.e(TAG + "  DisplayMetrics(222)", "screenWidth=" + screenWidth
				+ "; screenHeight=" + screenHeight);

		Log.e(TAG + "  DisplayMetrics(222)",
				"screenWidthDp=" + DensityUtil.px2dip(this, screenWidth)
						+ "; screenHeightDp="
						+ DensityUtil.px2dip(this, screenHeight));
		Log.i(TAG, "------------------------------------------");

		setContentView(R.layout.activity_main);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(2);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setCurrentItem(1);
		PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);

		pagerTabStrip.setTabIndicatorColor(this.getResources().getColor(
				R.color.tab_line));

		mDBManager = new DBManager(this);

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d(TAG, "onConfigurationChanged");
		// 检测屏幕的方向：纵向或横向
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 当前为横屏， 在此处添加额外的处理代码
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// 当前为竖屏， 在此处添加额外的处理代码
		}
		// 检测实体键盘的状态：推出或者合上
		if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
			// 实体键盘处于推出状态，在此处添加额外的处理代码
		} else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
			// 实体键盘处于合上状态，在此处添加额外的处理代码
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
		mDBManager.close();
	}

	/**
	 * 
	 * 首页ViewPager Adapter
	 * 
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;

			Log.d(TAG, "getItem ================ " + position);
			switch (position) {
			case 0:
				fragment = new PersonalCenterFragment();
				Bundle args = new Bundle();
				args.putInt(ARG_SECTION_NUMBER, position);
				fragment.setArguments(args);
				break;
			case 1:
				fragment = new MyBoardsFragment();
				args = new Bundle();
				args.putInt(ARG_SECTION_NUMBER, position);
				fragment.setArguments(args);
				break;
			case 2:
				fragment = new AllBoardsFragment();
				args = new Bundle();
				args.putInt(ARG_SECTION_NUMBER, position);
				fragment.setArguments(args);
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}

		@Override
		public float getPageWidth(int position) {
			return position == 0 ? getResources().getInteger(
					R.integer.personal_center_width) / 100f : 1.0f;
		}
	}

	public static class PersonalCenterFragment extends Fragment {

		private static final String TAG = "DummySectionFragment";

		public PersonalCenterFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Log.d(TAG, "onCreate");
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.d(TAG, "onCreateView");
			View rootView = inflater.inflate(
					R.layout.fragment_main_personal_center, container, false);
			Button loginBtn = (Button) rootView.findViewById(R.id.setting_login_btn);
			loginBtn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), LoginActivity.class);
					getActivity().startActivity(intent);
				}
				
			});
			return rootView;
		}
	}

	/**
	 * 
	 * 常用板块Fragment
	 * 
	 */
	public static class MyBoardsFragment extends Fragment {

		private static final String TAG = "MyBoardsFragment";
		private MyBoardListAdapter mMyBoardListAdapter;
		private List<Board> mMyBoardsList = null;

		public MyBoardsFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Log.d(TAG, "onCreate");
			mMyBoardsList = ((MainActivity) getActivity()).mDBManager
					.getBoardList();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.d(TAG, "onCreateView");
			View rootView = inflater.inflate(R.layout.fragment_main_my_board,
					container, false);
			ListView lvMyBoards = (ListView) rootView
					.findViewById(R.id.main_myboards_listview);
			lvMyBoards.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

			lvMyBoards.setMultiChoiceModeListener(new ModeCallback(lvMyBoards));
			mMyBoardListAdapter = new MyBoardListAdapter(getActivity(),
					mMyBoardsList);
			lvMyBoards.setAdapter(mMyBoardListAdapter);
			lvMyBoards.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Log.d(TAG, "onClick =================== ");
					TextView tvBoardName = (TextView) view
							.findViewById(R.id.main_board_name);

					Toast.makeText(getActivity(), tvBoardName.getText(),
							Toast.LENGTH_SHORT).show();
				}

			});
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			Log.d(TAG, "onAttach");
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			Log.d(TAG, "onActivityCreated");
		}

		@Override
		public void onStart() {
			super.onStart();
			Log.d(TAG, "onStart");
		}

		@Override
		public void onResume() {
			super.onResume();
			Log.d(TAG, "onResume");
		}

		@Override
		public void onPause() {
			super.onPause();
			Log.d(TAG, "onPause");
		}

		@Override
		public void onStop() {
			super.onStop();
			Log.d(TAG, "onStop");
		}

		@Override
		public void onDestroyView() {
			super.onDestroyView();
			Log.d(TAG, "onDestroyView");
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			Log.d(TAG, "onDestroy");
		}

		@Override
		public void onDetach() {
			super.onDetach();
			Log.d(TAG, "onDetach");
		}

		/**
		 * 
		 * 常用板块，item长按多选删除listener
		 * 
		 */
		private class ModeCallback implements ListView.MultiChoiceModeListener {

			private static final String TAG = "ModeCallback";
			private ListView mListView = null;
			@SuppressLint("UseSparseArrays")
			private Map<Integer, Boolean> mItemChecked = new HashMap<Integer, Boolean>();

			public ModeCallback(ListView lv) {
				mListView = lv;
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				MenuInflater inflater = getActivity().getMenuInflater();
				inflater.inflate(R.menu.delete, menu);
				mode.setTitle("Select Items");
				setSubtitle(mode);
				return true;
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return true;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				Log.d(TAG, "onActionItemClicked");
				Toast.makeText(getActivity(),
						"Delete " + mListView.getCheckedItemCount() + " items",
						Toast.LENGTH_SHORT).show();

				Iterator<Board> iterator = mMyBoardsList.iterator();
				List<String> urlList = new ArrayList<String>();
				int index = 0;
				while (iterator.hasNext()) {
					Board board = iterator.next();
					for (Integer position : mItemChecked.keySet()) {
						if (position == index) {
							urlList.add(board.getUrl());
							iterator.remove();
						}
					}
					index++;
				}
				((MainActivity) getActivity()).mDBManager.delete(urlList);
				mMyBoardListAdapter.notifyDataSetChanged();
				mode.finish();
				return false;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				Log.d(TAG, "onDestroyActionMode");
				mItemChecked.clear();
			}

			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				if (checked) {
					mItemChecked.put(position, true);
				} else {
					mItemChecked.remove(position);
				}
				setSubtitle(mode);
			}

			private void setSubtitle(ActionMode mode) {
				final int checkedCount = mListView.getCheckedItemCount();
				switch (checkedCount) {
				case 0:
					mode.setSubtitle(null);
					break;
				default:
					mode.setSubtitle(checkedCount + " items selected");
					break;
				}
			}

		}
	}

	/**
	 * 
	 * 所有板块Fragment
	 * 
	 */
	public static class AllBoardsFragment extends Fragment {

		private static final String TAG = "AllBoardsFragment";

		public AllBoardsFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Log.d(TAG, "onCreate");
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.d(TAG, "onCreateView");
			View rootView = inflater.inflate(R.layout.fragment_main_all_board,
					container, false);
			LinearLayout llAllBoard = (LinearLayout) rootView
					.findViewById(R.id.main_allboard_list);

			List<Plate> plates = ((NGAClientApplication) getActivity()
					.getApplication()).loadDefaultBoard();
			for (Plate plate : plates) {
				LayoutInflater inflate = LayoutInflater.from(getActivity());
				RelativeLayout rlItemPlate = (RelativeLayout) inflate.inflate(
						R.layout.item_main_plate, null);

				TextView tvPlate = (TextView) rlItemPlate
						.findViewById(R.id.plateName);
				CustomGridView gvBoardList = (CustomGridView) rlItemPlate
						.findViewById(R.id.main_board_gridview);

				tvPlate.setText(":: " + plate.getName() + " ::");
				gvBoardList.setAdapter(new BoardGridViewAdapter(getActivity(),
						plate));

				llAllBoard.addView(rlItemPlate);
			}
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			Log.d(TAG, "onAttach");
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			Log.d(TAG, "onActivityCreated");
		}

		@Override
		public void onStart() {
			super.onStart();
			Log.d(TAG, "onStart");
		}

		@Override
		public void onResume() {
			super.onResume();
			Log.d(TAG, "onResume");
		}

		@Override
		public void onPause() {
			super.onPause();
			Log.d(TAG, "onPause");
		}

		@Override
		public void onStop() {
			super.onStop();
			Log.d(TAG, "onStop");
		}

		@Override
		public void onDestroyView() {
			super.onDestroyView();
			Log.d(TAG, "onDestroyView");
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			Log.d(TAG, "onDestroy");
		}

		@Override
		public void onDetach() {
			super.onDetach();
			Log.d(TAG, "onDetach");
		}
	}

	/**
	 * 
	 * 所有板块GridViewAdapter
	 * 
	 */
	public static class BoardGridViewAdapter extends BaseAdapter {

		private static String TAG = "BoardGridViewAdapter";
		private LayoutInflater mInflater = null;
		private Plate mPlate = null;
		private List<Board> mBoardList = null;
		private Context mContext = null;

		public BoardGridViewAdapter(Context context, Plate plate) {
			mInflater = LayoutInflater.from(context);
			mPlate = plate;
			mBoardList = plate.getBoardList();
			mContext = context;

		}

		@Override
		public int getCount() {
			return mPlate.getCount();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final int index = position;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_main_board, null);
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Log.d(TAG, "onClick =================== ");
						TextView tvBoardName = (TextView) v
								.findViewById(R.id.main_board_name);
						Toast.makeText(mContext, tvBoardName.getText(),
								Toast.LENGTH_SHORT).show();
						Log.d(TAG,
								"onClick boardName === "
										+ tvBoardName.getText());
						((MainActivity) mContext).mDBManager
								.insertOrUpdateBoard(mBoardList.get(index));
						Intent intent = new Intent();
						intent.setClass((MainActivity) mContext, TopicListActivity.class);
						intent.putExtra("title", tvBoardName.getText());
						mContext.startActivity(intent);
					}

				});
				convertView
						.setLayoutParams(new android.widget.AbsListView.LayoutParams(
								android.widget.AbsListView.LayoutParams.MATCH_PARENT,
								DensityUtil.dip2px(mContext, 45)));
			}

			((TextView) convertView).setCompoundDrawablesWithIntrinsicBounds(
					mContext.getResources().getDrawable(
							mBoardList.get(position).getIcon()), null, null,
					null);
			((TextView) convertView)
					.setText(mBoardList.get(position).getName());

			if (position == 0) {
				convertView.setBackgroundResource(R.drawable.main_board_bg);
				((TextView) convertView).setTextColor(mContext.getResources()
						.getColor(R.color.white));
			} else {
				int numCol = ((CustomGridView) parent).getNumColumns();
				int raw = position / numCol;
				int col = position % numCol;

				convertView.setBackgroundColor((raw % 2 == 0 && col % 2 == 0)
						|| (raw % 2 != 0 && col % 2 != 0) ? mContext
						.getResources().getColor(R.color.shit1) : mContext
						.getResources().getColor(R.color.shit2));
			}

			return convertView;
		}

	}

	/**
	 * 
	 * 常用板块Adapter
	 * 
	 */
	public static class MyBoardListAdapter extends BaseAdapter {

		private Context mContext = null;
		private LayoutInflater mInflater = null;
		private List<Board> mBoardList = null;

		public MyBoardListAdapter(Context context, List<Board> boardList) {
			mContext = context;
			mInflater = LayoutInflater.from(context);
			mBoardList = boardList;
		}

		@Override
		public int getCount() {
			return mBoardList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_main_myboard,
						null);
				holder = new ViewHolder();
				holder.ivBoardIcon = (ImageView) convertView
						.findViewById(R.id.main_board_ic);
				holder.tvBoardName = (TextView) convertView
						.findViewById(R.id.main_board_name);

				convertView.setTag(holder);
				convertView
						.setLayoutParams(new android.widget.AbsListView.LayoutParams(
								android.widget.AbsListView.LayoutParams.MATCH_PARENT,
								DensityUtil.dip2px(mContext, 45)));

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.ivBoardIcon.setImageResource(mBoardList.get(position)
					.getIcon());
			holder.tvBoardName.setText(mBoardList.get(position).getName());

			convertView
					.setBackgroundResource(position % 2 == 0 ? R.drawable.item_myboard1_style
							: R.drawable.item_myboard2_style);
			return convertView;
		}

		private class ViewHolder {
			public ImageView ivBoardIcon;
			public TextView tvBoardName;
		}

	}

}
