package com.example.notes;

import android.content.ContentValues;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BaseActivity.BaseActivity;
import com.example.Bean.NotesBean;
import com.example.DabeseHelper.NoteOpenHelper;
import com.example.Dao.tableNote;

import java.util.Date;

import static com.example.notes.R.id.activity_notes_detail_title;

/**
 * Created by hasee on 2016/12/22.
 */

public class NoteDetailActivity extends BaseActivity {

    private TextView activity_notes_detail_back;
    private EditText activity_notes_detail_title;
    private EditText activity_notes_detail_content;
    private Button   activity_notes_detail_save;
    private int count;
    @Override
    public void process(View v) {
        switch (v.getId()) {
            case R.id.activity_notes_detail_back:
                ContentValues values = new ContentValues();

                String where=NoteOpenHelper.COLUMN_ID+"="+id;
                String time = System.currentTimeMillis()+"";
                values.put(NoteOpenHelper.COLUMN_TOTAL_REVIEWS,""+(count+1));
                values.put(NoteOpenHelper.COLUMN_LAST_REVIEWED,time);
                tableNote.upDateNote(getContentResolver(),values,where);
                finish();
                break;
            case  R.id.activity_notes_detail_save:
                saveNote();
                break;
        }
    }

    private void saveNote() {
        String title = activity_notes_detail_title.getText().toString();
        String content = activity_notes_detail_content.getText().toString();
        if(TextUtils.isEmpty(title)||TextUtils.isEmpty(content))
        {
            Toast toast = Toast.makeText(this,"标题或内容不能为空",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }else {
            ContentValues values = new ContentValues();
            values.put(NoteOpenHelper.COLUMN_TITLE,title);
            values.put(NoteOpenHelper.COLUMN_CONTENT,content);
            String where=NoteOpenHelper.COLUMN_ID+"="+id;
            String time = System.currentTimeMillis()+"";
            values.put(NoteOpenHelper.COLUMN_TOTAL_REVIEWS,""+(count+1));
            values.put(NoteOpenHelper.COLUMN_LAST_REVIEWED,time);
            tableNote.upDateNote(getContentResolver(),values,where);
            finish();
        }
    }

    long id;

    @Override
    public void InitView() {
        setContentView(R.layout.activity_notes_detail);
        //收数据
        activity_notes_detail_save = (Button) findViewById(R.id.activity_notes_detail_save);
        activity_notes_detail_back = (TextView) findViewById(R.id.activity_notes_detail_back);
        activity_notes_detail_content = (EditText) findViewById(R.id.activity_notes_detail_content);
        activity_notes_detail_title = (EditText) findViewById(R.id.activity_notes_detail_title);
    }

    @Override
    public void InitListener() {
        activity_notes_detail_back.setOnClickListener(this);
        activity_notes_detail_save.setOnClickListener(this);
    }

    @Override
    public void InitData() {
        id = getIntent().getLongExtra("_id", -20);

        NotesBean bean = tableNote.GetNotesBeanById(getContentResolver(),id);
        if (bean!=null)
        {

           count= Integer.parseInt(bean.getTotal_reviews());
            activity_notes_detail_title.setText(bean.getTitle());
           activity_notes_detail_content.setText(bean.getContent());
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
        ContentValues values = new ContentValues();

        String where=NoteOpenHelper.COLUMN_ID+"="+id;
        String time = System.currentTimeMillis()+"";
        values.put(NoteOpenHelper.COLUMN_TOTAL_REVIEWS,""+(count+1));
        values.put(NoteOpenHelper.COLUMN_LAST_REVIEWED,time);
        tableNote.upDateNote(getContentResolver(),values,where);
    }
}
