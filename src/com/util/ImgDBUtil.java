package com.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Image DB Util class
 * #USAGE PENDING
 * @author Luck
 *
 */
public class ImgDBUtil extends SQLiteOpenHelper{
	
	protected String SQL_CREATE = "CREATE TABLE tb_img";
	
	public ImgDBUtil(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE tb_img(key VARCHAR, img BLOB)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {		
	}
	
	public void doInsert(SQLiteDatabase db){
		//db.insert(table, nullColumnHack, values);
	}
	public void doDelete(){
		//db.delete(table, whereClause, whereArgs);		
	}
	public void doUpdate(){
		//db.update(table, values, whereClause, whereArgs);
	}
	public Object doSelect(){
		//db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		return null;
	}

}
