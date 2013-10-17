package com.arthur.ngaclient.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.arthur.ngaclient.R;
import com.arthur.ngaclient.activity.MainActivity;
import com.arthur.ngaclient.activity.TopicListActivity;
import com.arthur.ngaclient.adapter.MyBoardListAdapter;
import com.arthur.ngaclient.bean.Board;

/**
 * 
 * 常用板块Fragment
 * 
 */
public class MyBoardsFragment extends Fragment {

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

				((MainActivity) getActivity()).mDBManager
						.insertOrUpdateBoard(mMyBoardsList.get(position));
				Intent intent = new Intent();
				intent.setClass((MainActivity) getActivity(),
						TopicListActivity.class);
				intent.putExtra("title", tvBoardName.getText());
				intent.putExtra("fid", mMyBoardsList.get(position).getUrl());
				getActivity().startActivity(intent);
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
			mode.setTitle("选择要删除的板块");
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
					"删除了" + mListView.getCheckedItemCount() + "个板块",
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
		public void onItemCheckedStateChanged(ActionMode mode, int position,
				long id, boolean checked) {
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
				mode.setSubtitle(checkedCount + "个板块被选择");
				break;
			}
		}

	}
}
