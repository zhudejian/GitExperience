package com.example.Dao;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CursorAdapter;

import com.example.BaseActivity.BaseActivity;
import com.example.notes.MainActivity;

/**
 * Created by hasee on 2016/12/22.
 */

public class SimpleQuery extends AsyncQueryHandler {
    public SimpleQuery(ContentResolver cr) {
        super(cr);
    }

    //异步查询结束时调用
    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        super.onQueryComplete(token, cookie, cursor);
        if (cookie!=null&&cookie instanceof CursorAdapter)
        {

            ((CursorAdapter) cookie).changeCursor(cursor);

        }
    }
}
