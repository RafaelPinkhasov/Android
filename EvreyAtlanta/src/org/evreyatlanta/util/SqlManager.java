package org.evreyatlanta.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.evreyatlanta.models.ClassModel;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlManager extends SQLiteOpenHelper  {

	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "evreyatlanta";
	public static final String TABLE_HISTORY = "history";
	public static final String HISTORY_COLUMN_ID = "id";
	public static final String HISTORY_COLUMN_POSITION = "position";	
	public static final String HISTORY_COLUMN_TITLE = "title";	
	public static final String HISTORY_COLUMN_NOTES = "notes";
	public static final String HISTORY_COLUMN_URL = "url";
	public static final String HISTORY_COLUMN_DATE = "published_date";
	public static final String HISTORY_COLUMN_SUBJECT = "subject";
	public static final String HISTORY_COLUMN_TEACHER = "teacher";
	public static final String HISTORY_COLUMN_CREATED_DATE = "created_date";
	public static final String TABLE_DOWNLOADS = "downloads";
	public static final String DOWNLOAD_COLUMN_POSITION = "position";	
	public static final String DOWNLOAD_COLUMN_URL = "url";
	
	private static final String CREATE_HISTORY_TABLE = 
									"CREATE TABLE " + TABLE_HISTORY + "(" + 
											HISTORY_COLUMN_ID + " TEXT NOT NULL, " + 
											HISTORY_COLUMN_POSITION + " INTEGER NOT NULL, " +
											HISTORY_COLUMN_TITLE + " TEXT, " +
											HISTORY_COLUMN_NOTES + " TEXT, " +
											HISTORY_COLUMN_URL + " TEXT, " +
											HISTORY_COLUMN_DATE + " TEXT, " +
											HISTORY_COLUMN_SUBJECT + " TEXT, " +
											HISTORY_COLUMN_TEACHER + " TEXT, " +
											HISTORY_COLUMN_CREATED_DATE + " DATE default CURRENT_DATE );";
	
	private static final String CREATE_DOWNLOADS_TABLE = 
			"CREATE TABLE " + TABLE_DOWNLOADS + "(" + 
					DOWNLOAD_COLUMN_POSITION + " INTEGER NOT NULL, " +
					DOWNLOAD_COLUMN_URL + " TEXT);";
	
	public SqlManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_DOWNLOADS_TABLE);
	    database.execSQL(CREATE_HISTORY_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOWNLOADS);
		onCreate(db);
	}
	
	public void saveHistory(ClassModel model) {
		boolean exist = existHistory(model.id);
		if (exist) {
			updateHistory(model);
		} else {
			createHistory(model);
		}
	}
	
	private void createHistory(ClassModel model) {
        SQLiteDatabase db = null;
        try {
        	db = this.getWritableDatabase();
	        ContentValues values = new ContentValues();
	        values.put(HISTORY_COLUMN_ID, model.id);
	        values.put(HISTORY_COLUMN_TITLE, model.name);
	        values.put(HISTORY_COLUMN_NOTES, model.notes);
	        values.put(HISTORY_COLUMN_URL, model.url);
	        values.put(HISTORY_COLUMN_DATE, model.publishedDate);
	        values.put(HISTORY_COLUMN_SUBJECT, model.mediaType);
	        values.put(HISTORY_COLUMN_TEACHER, model.teacher);
	        values.put(HISTORY_COLUMN_POSITION, model.position);
	        values.put(HISTORY_COLUMN_CREATED_DATE, getNow());
	        
	        db.insert(TABLE_HISTORY, null, values);
        } finally {
        	if (db != null)
        		db.close();
        }
    }
	
	private void updateHistory(ClassModel model) {
		SQLiteDatabase db = null;
        try {
        	db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
	        values.put(HISTORY_COLUMN_ID, model.id);
	        values.put(HISTORY_COLUMN_TITLE, model.name);
	        values.put(HISTORY_COLUMN_NOTES, model.notes);
	        values.put(HISTORY_COLUMN_URL, model.url);
	        values.put(HISTORY_COLUMN_DATE, model.publishedDate);
	        values.put(HISTORY_COLUMN_SUBJECT, model.mediaType);
	        values.put(HISTORY_COLUMN_TEACHER, model.teacher);
	        values.put(HISTORY_COLUMN_POSITION, model.position);
	        values.put(HISTORY_COLUMN_CREATED_DATE, getNow());
	        db.update(TABLE_HISTORY, values, HISTORY_COLUMN_ID + " = ?", new String[] { model.id });
        } finally {
        	if (db != null)
        		db.close();
        }	
    }
	
	public void deleteHistory(ClassModel model) {
        SQLiteDatabase db = null;
        try {
        	db = this.getWritableDatabase();
        	db.delete(TABLE_HISTORY, HISTORY_COLUMN_ID + " = ?", new String[] { model.id });
        } finally {
        	if (db != null)
        		db.close();
        }        
    }
	
	public boolean existHistory(String id) {
		SQLiteDatabase db = null;
		Cursor c = null;
		boolean flag = false;
		try {
			String sql = "SELECT COUNT(*) FROM " + TABLE_HISTORY + " WHERE " + HISTORY_COLUMN_ID + " = ?";
			
			db = this.getReadableDatabase();
			c = db.rawQuery(sql, new String[] { id });
        
			if (c.moveToFirst()) {
				flag = c.getInt(0) > 0;
			}
		} finally {
			if (c != null) 
				c.close();
			if (db != null)
				db.close();
		}

        return flag;
    }
	
	public ArrayList<ClassModel> getHistoryList() {
		SQLiteDatabase db = null;
        Cursor c = null;
        ArrayList<ClassModel> list = new ArrayList<ClassModel>();
        try {
		
        	String sql = "SELECT * FROM " + TABLE_HISTORY + " ORDER BY " + HISTORY_COLUMN_CREATED_DATE + " DESC";
        	db = this.getReadableDatabase();       	 
        	c = db.rawQuery(sql, null);
        
	        if (c.moveToFirst()) {
	            do {
	            	ClassModel item = new ClassModel();
	            	item.id = c.getString((c.getColumnIndex(HISTORY_COLUMN_ID)));
	            	item.name = c.getString((c.getColumnIndex(HISTORY_COLUMN_TITLE)));
	            	item.mediaType = c.getString((c.getColumnIndex(HISTORY_COLUMN_SUBJECT)));
	            	item.notes = c.getString((c.getColumnIndex(HISTORY_COLUMN_NOTES)));
	            	item.publishedDate = c.getString((c.getColumnIndex(HISTORY_COLUMN_DATE)));
	            	item.teacher = c.getString((c.getColumnIndex(HISTORY_COLUMN_TEACHER)));
	            	item.url = c.getString((c.getColumnIndex(HISTORY_COLUMN_URL)));
	            	item.position = c.getInt((c.getColumnIndex(HISTORY_COLUMN_POSITION)));
	            	               
	                list.add(item);
	            } while (c.moveToNext());
	        }
	        c.close();
        } finally {
        	if (c != null)
        		c.close();
        	if (db != null)
        		db.close();
        }
        
        while (list.size() > 25) {
        	deleteHistory(list.get(list.size() - 1));
        	list.remove(list.size() - 1);
        }
        
        return list;
    }
	
	public int getHistoryCurrentPosistion(String id) {
        SQLiteDatabase db = null;
        Cursor c = null;
        int position = 0;
        try {
        	String sql = "SELECT " + HISTORY_COLUMN_POSITION + " FROM " + TABLE_HISTORY + " WHERE " + HISTORY_COLUMN_ID + "=?";
        	db = this.getReadableDatabase();        	 
        	c = db.rawQuery(sql, new String[] { id });
        	
	        if (c.moveToFirst()) {
	        	position = c.getInt((c.getColumnIndex(HISTORY_COLUMN_POSITION)));            
	        }
        } finally {
        	if (c != null)
        		c.close();
        	if (db != null)
        		db.close();
        }
        
        return position;
    }
	
	public void saveDownload(ClassModel model) {
		boolean exist = existDownload(model.url);
		if (exist) {
			updateDownload(model);
		} else {
			createDownload(model);
		}
	}
	
	private void createDownload(ClassModel model) {
        SQLiteDatabase db = null;
        try {
        	db = this.getWritableDatabase();
	        ContentValues values = new ContentValues();
	        values.put(DOWNLOAD_COLUMN_URL, model.url);
	        values.put(DOWNLOAD_COLUMN_POSITION, model.position);
	        db.insert(TABLE_DOWNLOADS, null, values);
	        Log.i("create sql", model.url + " = > > = > " + model.position);
        } finally {
        	if (db != null)
        		db.close();
        }
    }
	
	private void updateDownload(ClassModel model) {
		SQLiteDatabase db = null;
        try {
        	db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
	        values.put(DOWNLOAD_COLUMN_URL, model.url);
	        values.put(DOWNLOAD_COLUMN_POSITION, model.position);
	        db.update(TABLE_DOWNLOADS, values, DOWNLOAD_COLUMN_URL + " = ?", new String[] { model.url });
	        Log.i("update sql", model.url + " = > > = > " + model.position);
        } finally {
        	if (db != null)
        		db.close();
        }	
    }
	
	public void deleteDownload(ClassModel model) {
        SQLiteDatabase db = null;
        try {
        	db = this.getWritableDatabase();
        	db.delete(TABLE_DOWNLOADS, DOWNLOAD_COLUMN_URL + " = ?", new String[] { model.url });
        } finally {
        	if (db != null)
        		db.close();
        }        
    }
	
	public boolean existDownload(String id) {
		SQLiteDatabase db = null;
		Cursor c = null;
		boolean flag = false;
		try {
			String sql = "SELECT COUNT(*) FROM " + TABLE_DOWNLOADS + " WHERE " + DOWNLOAD_COLUMN_URL + " = ?";
			
			db = this.getReadableDatabase();
			c = db.rawQuery(sql, new String[] { id });
        
			if (c.moveToFirst()) {
				flag = c.getInt(0) > 0;
			}
		} finally {
			if (c != null) 
				c.close();
			if (db != null)
				db.close();
		}

        return flag;
    }
	
	public int getDownloadCurrentPosistion(String url) {
        SQLiteDatabase db = null;
        Cursor c = null;
        int position = 0;
        try {
        	String sql = "SELECT " + DOWNLOAD_COLUMN_POSITION + " FROM " + TABLE_DOWNLOADS + " WHERE " + DOWNLOAD_COLUMN_URL + "=?";
        	db = this.getReadableDatabase();        	 
        	c = db.rawQuery(sql, new String[] { url });
        	
	        if (c.moveToFirst()) {
	        	position = c.getInt((c.getColumnIndex(DOWNLOAD_COLUMN_POSITION)));  
	        }
        } finally {
        	if (c != null)
        		c.close();
        	if (db != null)
        		db.close();
        }
        
        return position;
    }

	
	private String getNow(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	
}
