package com.example.Dao;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.example.Bean.NotesBean;
import com.example.Constans.Contants;

/**
 * Created by hasee on 2016/12/21.
 * 对note表的一些操作
 */

public class tableNote {
    private static final int  sucess=1;
    private static final int  failure=0;
    public static int InsertValues(ContentResolver resolver, ContentValues values){
        Uri uri = resolver.insert(Contants.URI.URI_INSERT_NOTES,values);
        return uri==null?failure:sucess;
    }
    public static Cursor QueryNotes(ContentResolver resolver,String[] projections,String selection,String[] selectionargs,String order)
    {
        //参数意义
        /**
         * 第一个uri
         * 第二个查询字段
         * 第三个条件，
         * 第4个条件中占位符跳车字段
         * 最后一个排序方式*/
        Cursor cursor = resolver.query(Contants.URI.URI_QUERY_NOTES,null,null,null,null);
        return  cursor;
    }

    public static int DeletNote( ContentResolver resolver,String where, String[] zhanwei){

      return resolver.delete(Contants.URI.URI_DELETE_NOTES, where, zhanwei);

    }
    public static  NotesBean GetNotesBeanById(ContentResolver resolver,long id){
        //String[] projections = {"_id", "title", "content", "last_reviewed", "total_reviews"};
       Cursor cursor= resolver.query(Contants.URI.URI_QUERY_NOTES,null,"_id ="+id,null,null);
        NotesBean bean=null;
        if( cursor.moveToFirst())
       {
          bean = NotesBean.GetNotesFromCursor(cursor);

       }
        cursor.close();
        return bean;
    }
    public  static  int upDateNote(ContentResolver resolver,ContentValues values,String where )
    {
        return  resolver.update(Contants.URI.URI_UPDATE_NOTES,values,where,null);
    }
    public static  Uri saveNewNote(ContentResolver resolver,ContentValues values){
        return  resolver.insert(Contants.URI.URI_INSERT_NOTES,values);
    }
}
