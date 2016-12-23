package com.example.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.Bean.NotesBean;
import com.example.notes.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hasee on 2016/12/22.
 */

public class mainAdapter extends CursorAdapter {
    public mainAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }



    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        mainHolder holder = GetmainHolder(view);
        NotesBean bean = NotesBean.GetNotesFromCursor(cursor);
        holder.tv_item_main_list_title.setText(bean.getTitle());

        holder.tv_item_main_list_body.setText(bean.getContent());
        holder.tv_item_main_list_total_reviews.setText("总浏览次数："+bean.getTotal_reviews());
        //DateUtils.formatDateTime(context,Long.parseLong(bean.getLast_reviewed(),);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String ll= formatter.format(Long.parseLong(bean.getLast_reviewed()));

        holder.tv_item_main_list_lastreviewed.setText("上次编辑于:"+ll);
       // Date date = new Date();
        //long time=date.getTime()-Long.parseLong(bean.getLast_reviewed());


    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        View v = View.inflate(context,R.layout.item_main_list,null);
        return v;
    }

    public mainHolder GetmainHolder(View view){
        mainHolder holder;
        if (view.getTag()==null)
        {
            holder= new mainHolder(view);
        }else {
            holder = (mainHolder) view.getTag();

        }

        return  holder;
    }

    class mainHolder{

        TextView tv_item_main_list_title;
        TextView tv_item_main_list_body;
        TextView tv_item_main_list_lastreviewed;
        TextView tv_item_main_list_total_reviews;

        public mainHolder(View view) {
            tv_item_main_list_body= (TextView) view.findViewById(R.id.tv_item_main_list_body);
            tv_item_main_list_lastreviewed= (TextView) view.findViewById(R.id.tv_item_main_list_lastreviewed);
            tv_item_main_list_total_reviews= (TextView) view.findViewById(R.id.tv_item_main_list_total_reviews);
            tv_item_main_list_title= (TextView) view.findViewById(R.id.tv_item_main_list_title);
        }
    }
}
