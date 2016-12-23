package com.example.notes;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.BaseActivity.BaseActivity;
import com.example.Bean.NotesBean;
import com.example.Dao.tableNote;

import static com.example.notes.R.id.activity_notes_detail_title;

/**
 * Created by hasee on 2016/12/22.
 */

public class NoteDetailActivity extends BaseActivity {

    private TextView activity_notes_detail_back;
    private EditText activity_notes_detail_title;
    private EditText activity_notes_detail_content;

    @Override
    public void process(View v) {
        switch (v.getId()) {
            case R.id.activity_notes_detail_back:
                finish();
                break;
        }
    }

    long id;

    @Override
    public void InitView() {
        setContentView(R.layout.activity_notes_detail);
        //收数据

        activity_notes_detail_back = (TextView) findViewById(R.id.activity_notes_detail_back);
        activity_notes_detail_content = (EditText) findViewById(R.id.activity_notes_detail_content);
        activity_notes_detail_title = (EditText) findViewById(R.id.activity_notes_detail_title);
    }

    @Override
    public void InitListener() {
        activity_notes_detail_back.setOnClickListener(this);
    }

    @Override
    public void InitData() {
        id = getIntent().getLongExtra("_id", -20);

        NotesBean bean = tableNote.GetNotesBeanById(getContentResolver(),id);
        if (bean!=null)
        {


            activity_notes_detail_title.setText(bean.getTitle());
           activity_notes_detail_content.setText(bean.getContent());
        }
    }
}
