package com.arthur.ngaclient.util;

import java.util.ArrayList;
import java.util.List;

import com.arthur.ngaclient.bean.Board;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {

	DBHelper mHelper = null;
	SQLiteDatabase mDB = null;

	public DBManager(Context context) {
		mHelper = new DBHelper(context);
		mDB = mHelper.getWritableDatabase();
	}
	
	public void close() {
		mDB.close();
	}

	public void insertOrUpdateBoard(Board board) {
		Cursor cursor = mDB.rawQuery("SELECT 1 from board where url=?",
				new String[] { board.getUrl() });
		if (cursor.getCount() > 0) {
			String sql = "update board set updatetime=datetime('now','localtime') where url=?";
			mDB.execSQL(sql, new Object[] {board.getUrl()});
		} else {
			String sql = "INSERT INTO board (id,name,url,src) VALUES (NULL,?,?,?)";
			mDB.execSQL(sql, new Object[] { board.getName(), board.getUrl(), board.getIcon() });
		}
	}
	
	public List<Board> getBoardList(){
		Cursor cursor = mDB.rawQuery("SELECT * from board", null);
		List<Board> boardList = new ArrayList<Board>();
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String url = cursor.getString(cursor.getColumnIndex("url"));
			int src = cursor.getInt(cursor.getColumnIndex("src"));
			Log.d("getBoardList", "name ============= " + name);
			boardList.add(new Board(0, name, url, src));
		}
		return boardList;
	}

}
