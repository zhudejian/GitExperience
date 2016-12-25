package com.example.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.text.TextUtils;
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

/**
 * Created by hasee on 2016/12/22.
 */

public class NewNoteActivity extends BaseActivity {

    private TextView activity_new_note_back;
    private EditText activity_new_note_title;
    private EditText activity_new_note_content;
    private Button activity_new_note_save;
    private int count;

    @Override
    public void process(View v) {
        switch (v.getId()) {
            case R.id.activity_new_note_back:

                finish();
                break;
            case R.id.activity_new_note_save:
                Intent data = new Intent();
                data.putExtra("name", 1+"100");
                int resultCode =100;
                setResult(resultCode, data);
                saveNote();
                break;
        }
    }

    private void saveNote() {
        String title = activity_new_note_title.getText().toString();
        String content = activity_new_note_content.getText().toString();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            Toast toast = Toast.makeText(this, "标题或内容不能为空", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            ContentValues values = new ContentValues();
            values.put(NoteOpenHelper.COLUMN_TITLE, title);
            values.put(NoteOpenHelper.COLUMN_CONTENT, content);

            String time = System.currentTimeMillis() + "";
            values.put(NoteOpenHelper.COLUMN_TOTAL_REVIEWS, "" + 1);
            values.put(NoteOpenHelper.COLUMN_LAST_REVIEWED, time);
            tableNote.saveNewNote(getContentResolver(), values);

            finish();
        }
    }



    @Override
    public void InitView() {
        setContentView(R.layout.activity_new_note);
        //收数据
        activity_new_note_save = (Button) findViewById(R.id.activity_new_note_save);
        activity_new_note_back = (TextView) findViewById(R.id.activity_new_note_back);
        activity_new_note_content = (EditText) findViewById(R.id.activity_new_note_content);
        activity_new_note_title = (EditText) findViewById(R.id.activity_new_note_title);
    }

    @Override
    public void InitListener() {
        activity_new_note_back.setOnClickListener(this);
        activity_new_note_save.setOnClickListener(this);
    }

    @Override
    public void InitData() {


    }

    public void onBackPressed() {
        super.onBackPressed();

    }
}
