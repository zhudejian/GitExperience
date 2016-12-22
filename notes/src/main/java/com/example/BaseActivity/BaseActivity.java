package com.example.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by hasee on 2016/12/21.
 */

public  abstract  class BaseActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         InitView();
         InitListener();
         InitData();
    }

    @Override
    public void onClick(View view) {
        process(view);
    }

   public abstract  void process(View v);
    public abstract void InitView();
    public abstract void InitListener();
    public abstract  void InitData();
}
