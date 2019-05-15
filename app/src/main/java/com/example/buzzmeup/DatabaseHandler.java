package com.example.buzzmeup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Patterndb";
    private static final String TABLE_PATTERN = "patterns";
    private static final String KEY_ID = "id";
    private static final String KEY_PATTERN = "pattern";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PATTERNS_TABLE = "CREATE TABLE " + TABLE_PATTERN + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PATTERN + " TEXT" + ")";
        db.execSQL(CREATE_PATTERNS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATTERN);

        onCreate(db);
    }

    void addPattern(String pattern) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PATTERN, pattern);

        db.insert(TABLE_PATTERN, null, values);
        db.close();
    }
    String getPattern(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PATTERN, new String[] { KEY_ID, KEY_PATTERN }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getString(1);
    }

    public ArrayList<String> getAllPatterns() {
        ArrayList<String> patternList = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_PATTERN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                patternList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return patternList;

    }


}
