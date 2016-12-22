package com.example.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.view.View;

import com.example.notes.R;

/**
 * Created by hasee on 2016/12/21.
 */

public abstract  class BaseDialog extends AlertDialog implements View.OnClickListener {
    protected BaseDialog(Context context) {
       // super(context);
        super(context, android.R.style.Theme_Dialog);
    }

    @Override
    public void onClick(View view) {
        process(view);
    }
    public abstract void initView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    public abstract void initListener();
    public abstract void initData();
    public abstract void process(View v);
}
