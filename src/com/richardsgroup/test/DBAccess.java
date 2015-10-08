package com.richardsgroup.test;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAccess {

	public static void createDB(SQLiteDatabase myDB) {
		myDB.execSQL("create table if not exists collections("
				+ "trackId varchar," + "trackName varchar,"
				+ "trackCensoredName varchar," + "primaryGenreName varchar,"
				+ "collectionName varchar," + "collectionPrice varchar,"
				+ "trackPrice varchar" + ");");

		Log.i("DBAcess", "create DB");
	}

	public static void insertDB(SQLiteDatabase myDB, String trackId,
			String trackName, String trackCensoredName,
			String primaryGenreName, String collectionName,
			String collectionPrice, String trackPrice) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("trackId", trackId);
		contentValues.put("trackName", trackName);
		contentValues.put("trackCensoredName", trackCensoredName);
		contentValues.put("primaryGenreName", primaryGenreName);
		contentValues.put("collectionName", collectionName);
		contentValues.put("collectionPrice", collectionPrice);
		contentValues.put("trackPrice", trackPrice);
		myDB.insert("collections", null, contentValues);

		Log.i("DBAccess", "insert data - " + trackName);
	}

	public static ArrayList<String> retrieveDB(SQLiteDatabase myDB, String track) {
		ArrayList<String> dbData = new ArrayList<String>();
		Cursor rs;
		if (track == null) {
			String sql = "select * from collections";
			rs = myDB.rawQuery(sql, null);
			rs.moveToFirst();

			while (!rs.isAfterLast()) {
				String trackId = rs.getString(rs.getColumnIndex("trackId"));
				String trackName = rs.getString(rs.getColumnIndex("trackName"));
				String trackCensoredName = rs.getString(rs
						.getColumnIndex("trackCensoredName"));
				String primaryGenreName = rs.getString(rs
						.getColumnIndex("primaryGenreName"));
				String collectionName = rs.getString(rs
						.getColumnIndex("collectionName"));
				String collectionPrice = rs.getString(rs
						.getColumnIndex("collectionPrice"));
				String trackPrice = rs.getString(rs
						.getColumnIndex("trackPrice"));

				String data = trackId + ";" + trackName + ";"
						+ trackCensoredName + ";" + primaryGenreName + ";"
						+ collectionName + ";" + collectionPrice + ";"
						+ trackPrice;

				dbData.add(data);

				Log.i("DBAccess", "retrieve - " + data);

				rs.moveToNext();
			}

			return dbData;
		} else {
			String sql = "select * from collections where trackId=" + track;
			rs = myDB.rawQuery(sql, null);
			rs.moveToFirst();

			while (!rs.isAfterLast()) {
				String trackId = rs.getString(rs.getColumnIndex("trackId"));
				String trackName = rs.getString(rs.getColumnIndex("trackName"));
				String trackCensoredName = rs.getString(rs
						.getColumnIndex("trackCensoredName"));
				String primaryGenreName = rs.getString(rs
						.getColumnIndex("primaryGenreName"));
				String collectionName = rs.getString(rs
						.getColumnIndex("collectionName"));
				String collectionPrice = rs.getString(rs
						.getColumnIndex("collectionPrice"));
				String trackPrice = rs.getString(rs
						.getColumnIndex("trackPrice"));

				String data = trackId + ";" + trackName + ";"
						+ trackCensoredName + ";" + primaryGenreName + ";"
						+ collectionName + ";" + collectionPrice + ";"
						+ trackPrice;

				dbData.add(data);

				Log.i("DBAccess", "retrieve - " + data);

				rs.moveToNext();
			}

			return dbData;
		}
	}
}
