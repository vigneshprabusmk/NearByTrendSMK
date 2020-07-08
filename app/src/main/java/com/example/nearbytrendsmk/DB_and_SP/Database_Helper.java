package com.example.nearbytrendsmk.DB_and_SP;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nearbytrendsmk.Arraylist.Data_Model;

import java.util.ArrayList;
import java.util.List;

public class Database_Helper extends SQLiteOpenHelper {

	// Database Name
	private static final String Database_Name = "TwitterFeeds";

	// Database Version
	private static final int Database_Version = 1;

	// Table Name
	private static final String Table_Name = "User_Data";

	// Column Id Primary Key
	private static final String Column_Id = "id";

	// Table fields
	private static final String Name = "name";
	private static final String Destext = "Destext";
	//private static final String Address = "Address";

	// Create table query
	private static final String Create_Table = "Create table " + Table_Name
			+ " ( " + Column_Id + " integer primary key autoincrement, " + Name
			+ " text not null, " + Destext + " text not null);";

	// Drop/delete table query
	private static final String Drop_Table = "Drop table if exists "
			+ Table_Name;

	public Database_Helper(Context context) {
		super(context, Database_Name, null, Database_Version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// executing the created table query
		db.execSQL(Create_Table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int olderVersion, int newVersion) {

		// executing the drop table query if database version is changed
		db.execSQL(Drop_Table);
		onCreate(db);

	}

	// Insert data into database method
	public void insertData(Data_Model data) {

		// Accessing SQL database to write data
		SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();

			// Putting datas into content values
			values.put(Name, String.valueOf(data.getName()));
			values.put(Destext, data.getcontent());
			//values.put(Address, data.getAddress());

		// Now inserting content values data into table
		db.insert(Table_Name, null, values);
		System.out.println("setvalues"+values);
		// Closing database after using
		db.close();


	}

	// Getting all saved data
	public List<Data_Model> getAllData() {

		// Data model list in which we have to return the data
		List<Data_Model> data = new ArrayList<Data_Model>();

		// Accessing database for reading data
		SQLiteDatabase db = this.getReadableDatabase();

		// Select query for selecting whole table data
		String select_query = "Select * from " + Table_Name;

		// Cursor for traversing whole data into database
		Cursor cursor = db.rawQuery(select_query, null);
		try {
			// check if cursor move to first
			if (cursor.moveToFirst()) {

				// looping through all data and adding to arraylist
				do {

					Data_Model data_model = new Data_Model(cursor.getString(1),
							cursor.getString(2));
					data.add(data_model);

				} while (cursor.moveToNext());

			}
		} finally {

			// After using cursor we have to close it
			cursor.close();

		}

		// Closing database
		db.close();

		// returning list
		return data;
	}

	// Deleting table from database
	public void deleteTable() {

		SQLiteDatabase db = this.getWritableDatabase();

		// Deleting table
		db.delete(Table_Name, null, null);

		System.out.println("setvalues"+db);
		// Closing database
		db.close();

	}
}
