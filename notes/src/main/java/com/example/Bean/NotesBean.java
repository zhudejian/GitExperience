package com.example.Bean;

import android.database.Cursor;

/**
 * Created by hasee on 2016/12/22.
 */

public class NotesBean {
    public long id;
    public String title;
    public String last_reviewed;
    public String total_reviews;
    public String content;
public static   String[] projections = {"_id","title","content","last_reviewed","total_reviews"};

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLast_reviewed() {
        return last_reviewed;
    }

    public String getTotal_reviews() {
        return total_reviews;
    }

    public String getContent() {
        return content;
    }

    public static String[] getProjections() {
        return projections;
    }

    public static  NotesBean GetNotesFromCursor(Cursor cursor) {
        long id = cursor.getInt(cursor.getColumnIndex("_id"));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String last = cursor.getString(cursor.getColumnIndex("last_reviewed"));
        String total = ""+cursor.getInt(cursor.getColumnIndex("total_reviews"));
        String content = cursor.getString(cursor.getColumnIndex("content"));

    NotesBean bean = new NotesBean(title,last,total,content,id);
        return  bean;
    }

    public NotesBean(String title, String last_reviewed, String total_reviews, String content,long id) {
        this.title = title;
        this.last_reviewed = last_reviewed;
        this.total_reviews = total_reviews;
        this.content = content;
        this.id=id;
    }
}
