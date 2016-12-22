package com.example.Dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.notes.R;

/**
 * Created by hasee on 2016/12/21.
 */

public class InformationDialog extends BaseDialog {

    private Button bt_information_dialog_confirm;
    private TextView tv_information_dialog_body;
    private TextView tv_information_dialog_title;
    private String body;

    private String title;

    public void setBody(String body) {
        this.body = body;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {

        return body;
    }

    public String getTitle() {
        return title;
    }

    protected InformationDialog(Context context) {
        super(context);
    }

    public void setDoprocess(DoProcess doprocess) {
        this.doprocess = doprocess;
    }

    public static InformationDialog showInformationDialog(Context context, String title, String body, DoProcess doprocess) {
        InformationDialog informationDialog = new InformationDialog(context);

        informationDialog.body = body;
        informationDialog.title = title;
        informationDialog.setDoprocess(doprocess);
        informationDialog.setCanceledOnTouchOutside(false);
        informationDialog.show();

        return null;
    }

    @Override
    public void initView() {
        setContentView(R.layout.information_dialog);
        bt_information_dialog_confirm = (Button) findViewById(R.id.bt_information_dialog_confirm);
        tv_information_dialog_body = (TextView) findViewById(R.id.tv_information_dialog_body);
        tv_information_dialog_title = (TextView) findViewById(R.id.tv_information_dialog_title);

    }

    @Override
    public void initListener() {
        bt_information_dialog_confirm.setOnClickListener(this);
    }

    @Override
    public void initData() {
        tv_information_dialog_body.setText(body);
        tv_information_dialog_title.setText(title);
    }

    DoProcess doprocess;

    @Override
    public void process(View v) {
        switch (v.getId()) {
            case R.id.bt_information_dialog_confirm:
                if (doprocess != null)
                    doprocess.confirm();
        }
        dismiss();
    }

    public interface DoProcess {
        public void confirm();
    }
}
