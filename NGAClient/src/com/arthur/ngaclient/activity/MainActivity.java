package com.arthur.ngaclient.activity;

import java.util.List;
import java.util.Locale;

import com.arthur.ngaclient.NGAClientApplication;
import com.arthur.ngaclient.R;
import com.arthur.ngaclient.bean.Board;
import com.arthur.ngaclient.bean.Plate;
import com.arthur.ngaclient.util.DBManager;
import com.arthur.ngaclient.util.DensityUtil;
import com.arthur.ngaclient.widget.CustomGridView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
		setContentView(R.layout.activity_main);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setCurrentItem(1);

		mDBManager = new DBManager(this);
		mDBManager.getBoardList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;

			Log.d(TAG, "getItem ================" + position);
			switch (position) {
			case 0:
			case 1:
				fragment = new DummySectionFragment();
				Bundle args = new Bundle();
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
	}

	public static class DummySectionFragment extends Fragment {

		private static final String TAG = "DummySectionFragment";

		public DummySectionFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Log.d(TAG, "onCreate ============== ");
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.d(TAG, "onCreateView ================ ");
			int i = getArguments().getInt(ARG_SECTION_NUMBER);
			Log.d(TAG, "onCreateView i ================ " + i);
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
	
	public static class MyBoardsFragment extends Fragment {

		private static final String TAG = "MyBoardsFragment";

		public MyBoardsFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Log.d(TAG, "onCreate ============== ");
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.d(TAG, "onCreateView ================ ");
			int i = getArguments().getInt(ARG_SECTION_NUMBER);
			Log.d(TAG, "onCreateView i ================ " + i);
			View rootView = inflater.inflate(R.layout.fragment_main_my_board,
					container, false);
			return rootView;
		}
	}

	public static class AllBoardsFragment extends Fragment {

		private static final String TAG = "AllBoardsFragment";

		public AllBoardsFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Log.d(TAG, "onCreate ============== ");
			int i = getArguments().getInt(ARG_SECTION_NUMBER);
			Log.d(TAG, "onCreate i ================ " + i);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.d(TAG, "onCreateView ================ ");
			int i = getArguments().getInt(ARG_SECTION_NUMBER);
			Log.d(TAG, "onCreateView i ================ " + i);
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
	}

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
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_main_board, null);
				holder = new ViewHolder();
				holder.ivBoardIcon = (ImageView) convertView
						.findViewById(R.id.main_board_ic);
				holder.tvBoardName = (TextView) convertView
						.findViewById(R.id.main_board_name);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.ivBoardIcon.setImageResource(mBoardList.get(position)
					.getIcon());
			holder.tvBoardName.setText(mBoardList.get(position).getName());
			convertView
					.setLayoutParams(new android.widget.AbsListView.LayoutParams(
							android.widget.AbsListView.LayoutParams.MATCH_PARENT,
							DensityUtil.dip2px(mContext, 45)));

			if (position == 0) {
				convertView.setBackgroundResource(R.drawable.main_board_bg);
				holder.tvBoardName.setTextColor(mContext.getResources()
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

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					TextView tvBoardName = (TextView) v
							.findViewById(R.id.main_board_name);
					Toast.makeText(mContext, tvBoardName.getText(),
							Toast.LENGTH_SHORT).show();
					Log.d(TAG, "onClick boardName === " + tvBoardName.getText());
					((MainActivity)mContext).mDBManager.insertOrUpdateBoard(mBoardList.get(index));
				}

			});
			return convertView;
		}

		private class ViewHolder {
			public ImageView ivBoardIcon;
			public TextView tvBoardName;
		}

	}

}
