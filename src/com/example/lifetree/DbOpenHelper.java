package com.example.lifetree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper {

	private static final String DATABASE_NAME = "addressbook.db";
	private static final int DATABASE_VERSION = 1;
	public static SQLiteDatabase mDB;
	private DatabaseHelper mDBHelper;
	private Context mCtx;

	private class DatabaseHelper extends SQLiteOpenHelper{

		//������
		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		//���� DB �ѹ� ȣ��
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DataBases.CreateDB._CREATE);

		}

		//������ ������Ʈ �Ǿ��� ��� DB�� �ٽ� ����� �ش�
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME);
			onCreate(db);
		}
	}

	public DbOpenHelper(Context context){
		this.mCtx = context;
	}

	public DbOpenHelper open() throws SQLException{
		mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
		mDB = mDBHelper.getWritableDatabase();
		return this;
	}

	public void close(){
		mDB.close();
	}

	// Insert DB
	public long insertColumn(String name, String contact, String email){
		ContentValues values = new ContentValues();
		values.put(DataBases.CreateDB.NAME, name);
		values.put(DataBases.CreateDB.CONTACT, contact);
		values.put(DataBases.CreateDB.EMAIL, email);
		return mDB.insert(DataBases.CreateDB._TABLENAME, null, values);
	}

	// Update DB
	public boolean updateColumn(long id , String name, String contact, String email){
		ContentValues values = new ContentValues();
		values.put(DataBases.CreateDB.NAME, name);
		values.put(DataBases.CreateDB.CONTACT, contact);
		values.put(DataBases.CreateDB.EMAIL, email);
		return mDB.update(DataBases.CreateDB._TABLENAME, values, "_id="+id, null) > 0;
	}

	// Delete ID
	public boolean deleteColumn(long id){
		return mDB.delete(DataBases.CreateDB._TABLENAME, "_id="+id, null) > 0;
	}
	
	// Delete Contact
	public boolean deleteColumn(String number){
		return mDB.delete(DataBases.CreateDB._TABLENAME, "contact="+number, null) > 0;
	}
	
	// Select All
	public Cursor getAllColumns(){
		return mDB.query(DataBases.CreateDB._TABLENAME, null, null, null, null, null, null);
	}

	// ID 컬럼 ?�어 ?�기
	public Cursor getColumn(long id){
		Cursor c = mDB.query(DataBases.CreateDB._TABLENAME, null, 
				"_id="+id, null, null, null, null);
		if(c != null && c.getCount() != 0)
			c.moveToFirst();
		return c;
	}

	// ?�름 �?�� ?�기 (rawQuery)
	public Cursor getMatchName(String name){
		Cursor c = mDB.rawQuery( "select * from address where name=" + "'" + name + "'" , null);
		return c;
	}


}






