package com.example.Constans;

import android.net.Uri;

/**
 * Created by hasee on 2016/12/21.
 */

public class Contants {
    public interface URI{
        Uri URI_INSERT_NOTES= Uri.parse("content://com.example.notes/notes/insert");
        Uri URI_QUERY_NOTES= Uri.parse("content://com.example.notes/notes/query");
        Uri URI_DELETE_NOTES= Uri.parse("content://com.example.notes/notes/delete");
        Uri URI_UPDATE_NOTES= Uri.parse("content://com.example.notes/notes/update");
    }
}
