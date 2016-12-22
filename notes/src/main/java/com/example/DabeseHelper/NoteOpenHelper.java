package com.example.DabeseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hasee on 2016/12/21.
 * 对日记数据库的操作帮助
 */

public class NoteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LAST_REVIEWED = "last_reviewed";
    public static final String COLUMN_TOTAL_REVIEWS = "total_reviews";
    public static final String COLUMN_CONTENT = "content";

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NOTES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_LAST_REVIEWED
            + " text not null);";

private  static  NoteOpenHelper openHelper;
    public static NoteOpenHelper GetNoteOpenHelper(Context context){
         if (openHelper==null)
         {
             openHelper= new NoteOpenHelper(context,DATABASE_NAME,null,1);
         }
        return  openHelper;
    }
    private NoteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NOTES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_LAST_REVIEWED + " TEXT NOT NULL, " +
                COLUMN_TOTAL_REVIEWS + " INT NOT NULL, " +
                COLUMN_CONTENT + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
