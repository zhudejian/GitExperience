package contentprovider;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.DabeseHelper.NoteOpenHelper;

/**
 * Created by hasee on 2016/12/21.
 */

public class NotesProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        openHelper = NoteOpenHelper.GetNoteOpenHelper(getContext());
        database = openHelper.getWritableDatabase();
        return true;
    }

    NoteOpenHelper openHelper;
    SQLiteDatabase database;
    private static String authority = "com.example.notes";
    private static final int Notes_Insert = 0;
    private static final int Notes_Query = 1;
    private static final int Notes_Delete = 2;
    private static final int Notes_Update = 3;
    UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    {
        matcher.addURI(authority, "notes/insert", 0);
        matcher.addURI(authority, "notes/query", 1);
        matcher.addURI(authority, "notes/delete", 2);
        matcher.addURI(authority, "notes/update", 3);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        switch (matcher.match(uri)) {
            case Notes_Query:
                // 只要改uri上数据改变，注册者立即发现，重新找寻

                Cursor cursor = database.query(NoteOpenHelper.TABLE_NOTES, strings, s, strings1, null, null, s1);
                cursor.setNotificationUri(getContext().getContentResolver(), Uri.parse("content://" + authority));

                return cursor;

        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long num;
        switch (matcher.match(uri)) {
            case Notes_Insert:
                num = database.insert(NoteOpenHelper.TABLE_NOTES, null, contentValues);
                getContext().getContentResolver().notifyChange(Uri.parse("content://" + authority), null);
                if (num != -1)
                    return ContentUris.withAppendedId(uri, num);
                break;
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        switch (matcher.match(uri)) {
            case Notes_Delete:
                //Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
                int a = database.delete(NoteOpenHelper.TABLE_NOTES, s, strings);
                getContext().getContentResolver().notifyChange(Uri.parse("content://" + authority), null);
                return a;

        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        switch (matcher.match(uri)) {
            case Notes_Update:
                int a = database.update(NoteOpenHelper.TABLE_NOTES, contentValues, s, strings);
                getContext().getContentResolver().notifyChange(Uri.parse("content://" + authority), null);
                return a;
        }

        return 0;
    }
}
