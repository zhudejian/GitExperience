package com.example.notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapter.mainAdapter;
import com.example.BaseActivity.BaseActivity;
import com.example.Bean.NotesBean;
import com.example.Constans.Contants;
import com.example.Dao.SimpleQuery;
import com.example.Dao.tableNote;
import com.example.Dialog.InformationDialog;

public class MainActivity extends BaseActivity {


    private ImageView img_main_about;
    private ImageView img_main_newtext;
    private ListView activity_main_list;
    private mainAdapter adapter;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:


                    break;
            }
        }
    };
    private static final int DataChange = 0;

    @Override
    public void process(View v) {
        switch (v.getId()) {
            case R.id.img_main_about:
                startInformation();
        }
    }

    /**
     * 进入软件介绍界面
     */
    private void startInformation() {
        InformationDialog.showInformationDialog(this, "About", "练习数据库的小记事本", new InformationDialog.DoProcess() {
            @Override
            public void confirm() {

            }
        });
    }

    private TextView tips;

    @Override
    public void InitView() {
        setContentView(R.layout.activity_main);

        img_main_about = (ImageView) findViewById(R.id.img_main_about);
        img_main_newtext = (ImageView) findViewById(R.id.img_main_newtext);
        activity_main_list = (ListView) findViewById(R.id.activity_main_list);
        tips = (TextView) findViewById(R.id.activity_main_tips);

        SpannableString localSpannableString = new SpannableString("Humans more easily remember or learn items when they are studied a few times spaced over a long time span rather than repeatedly studied in a short span of time\n\n" +
                "Just click on the + icon and start adding notes and let the app handle the rest\n\n" +
                "Warning: Having too many notes at once could lead to multiple notification making this app unusable, limit to few notes at a time to get the most from the app");
        tips.setText(localSpannableString);
    }

    @Override
    public void InitListener() {
        img_main_newtext.setOnClickListener(this);
        img_main_about.setOnClickListener(this);
        //添加长按提示，原作里使用了actionbar的特性
        img_main_about.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT);
                int[] a = new int[2];
                view.getLocationInWindow(a);
                toast.setGravity(Gravity.TOP | Gravity.LEFT, a[0], a[1]);
                toast.show();
                ContentResolver resolver = MainActivity.this.getContentResolver();
                ContentValues values = new ContentValues();
                values.put("title", "我是");
                values.put("content", "asdajksdhasfhjkafshhakjfhkja");
                values.put("last_reviewed", "10000000000000000");
                values.put("total_reviews", 1);
                tableNote.InsertValues(resolver, values);
//                String[] projections = {"_id","title","content","last_reviewed","total_reviews"};
//               Cursor cursor= tableNote.QueryNotes(resolver,projections,null,null,null);
//
//                if (cursor.moveToFirst())
//                    Log.i("System",cursor.getString(1));
                return true;
            }
        });
        img_main_newtext.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this, "新建短信", Toast.LENGTH_SHORT);
                int[] a = new int[2];
                view.getLocationInWindow(a);
                toast.setGravity(Gravity.TOP | Gravity.LEFT, a[0], a[1]);
                toast.show();
                return true;
                /**
                 * SY-118, EMS-232, N0936, ABP-119, ABP-108, ABP120, ABP119, STAR-512, N0935,
                 * SNIS-110, IPZ-127, PINK-001, SRS-022, SIRO-1690, PPPD276, ZEX-201, N0934, STAR512, MIDE-059, PGD-587,
                 * MILD-895, MDB519, SNIS-070, ARM327, MIDE-087,
                 * IPZ-339, DGL-031, SIV011, MILD895, PPPD-276, N0932, N0931, IPZ-336, ABP-120, N0933, MIDE020, IPZ-351, sw290*/
            }
        });
        //给listview设置长按监听，长按弹出删除提示
        activity_main_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                final NotesBean bean = NotesBean.GetNotesFromCursor(cursor);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("要删除" + bean.getTitle() + "么?");
                builder.setTitle("删除日记");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tableNote.DeletNote(MainActivity.this.getContentResolver(), "_id = " + bean.getId(), null);
                        adapter.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
                return true;
            }
        });
        activity_main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) adapter.getItem(i);
                NotesBean bean = NotesBean.GetNotesFromCursor(cursor);
                Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
                intent.putExtra("_id", bean.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void InitData() {

        adapter = new mainAdapter(this, null, true);
        String[] projections = {"_id", "title", "content", "last_reviewed", "total_reviews"};
        // Cursor cursor = tableNote.QueryNotes(getContentResolver(), projections, null, null, null);
        // adapter.changeCursor(cursor);

        //SimpleQuery query = new SimpleQuery(getContentResolver());
        AsyncQueryHandler handler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);
                if (cookie != null && cookie instanceof CursorAdapter) {

                    ((CursorAdapter) cookie).changeCursor(cursor);

                }
                if (cursor.getCount() <= 0)
                    tips.setVisibility(View.VISIBLE);
            }
        };
        handler.startQuery(0, adapter, Contants.URI.URI_QUERY_NOTES, projections, null, null, null);


        activity_main_list.setAdapter(adapter);
    }


}
