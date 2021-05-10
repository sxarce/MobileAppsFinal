package com.ualr.customcalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "task_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "year";
    private static final String COL3 = "month";
    private static final String COL4 = "day";
    private static final String COL5 = "hour";
    private static final String COL6 = "min";
    private static final String COL7 = "time_type";
    private static final String COL8 = "task_title";
    private static final String COL9 = "task_priority";
    private static final String COL10 = "task_description";

    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, year INTEGER, " +
                "month INTEGER, day INTEGER, hour INTEGER, min INTEGER, time_type TEXT, task_title TEXT, " +
                "task_priority INTEGER, task_description TEXT)";

        /*
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " INTEGER, " + COL3 + " INTEGER, " + COL4 + " INTEGER, " + COL5 + " INTEGER, " +
                COL6 + " INTEGER, " + COL7 + " TEXT, " + COL8 + " TEXT, " + COL9 + " TEXT, " + COL10 + " TEXT)";
         */
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP IF TABLE EXISTS %s", TABLE_NAME));
        onCreate(db);
    }

    public boolean addData(int year, int month, int day, int hour, int min, String time_type,
                           String title, int priority, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, year);
        contentValues.put(COL3, month);
        contentValues.put(COL4, day);
        contentValues.put(COL5, hour);
        contentValues.put(COL6, min);
        contentValues.put(COL7, time_type);
        contentValues.put(COL8, title);
        contentValues.put(COL9, priority);
        contentValues.put(COL10, description);

        Log.d(TAG, String.format("addData: Adding %s/%s/%s, %s:%s %s :: \ntitle:[%s], priority[%s], " +
                "\ndescription[%s] in %s", month, day, year, hour, min, time_type, title, priority, description, TABLE_NAME));

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataForDate(int month, int day, int year) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL2 + "=" + year + " and " + COL3 + "=" + month + " and " + COL4 + "=" + day + " ORDER BY " + COL7 + ", " + COL5 + " ASC, " + COL6 + " ASC";
        //String query1 = String.format("SELECT * FROM %d WHERE", TABLE_NAME);
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " + COL8 + " = '" + title + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateTask(int id, String newTitle, String newDescription, int newPriority) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL8 + " = '" + newTitle + "', " + COL9 + " " +
                "= '" + newPriority + "', " + COL10 + " = '" + newDescription + "' WHERE " + COL1 + " = '" + id + "'";

        Log.d(TAG, "Query:" + query);
        db.execSQL(query);
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + id + "'";
        db.execSQL(query);
    }
}
