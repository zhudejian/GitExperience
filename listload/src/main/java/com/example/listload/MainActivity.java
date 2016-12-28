package com.example.listload;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import view.AutoList;

public class MainActivity extends AppCompatActivity {

    private AutoList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (AutoList) findViewById(R.id.listview);

        list.setOnRefreshListener(new AutoList.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finally {
                            Uihandler.sendEmptyMessage(0);
                        }
                    }
                });
                t.start();
            }
        });
        list.setOnLoadListener(new AutoList.OnLoadListener() {
            @Override
            public void onLoad() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finally {
                            Uihandler.sendEmptyMessage(0);
                        }
                    }
                });
                t.start();
            }
        });
        final View head = View.inflate(this,R.layout.footer,null);
        final View foot = View.inflate(this, R.layout.footer,null);
        list.setHeader(head);
        list.setFooter(foot);
        list.setDifferentViews(new AutoList.ShowDifferentViews() {
            @Override
            public void OnPull() {
                ((TextView)head.findViewById(R.id.tvb)).setText("正在下拉");
            }

            @Override
            public void onRefresh() {
                ((TextView)head.findViewById(R.id.tvb)).setText("松开刷新");
            }

            @Override
            public void onrefreshing() {
                ((TextView)head.findViewById(R.id.tvb)).setText("正在刷新");
            }

            @Override
            public void onLoadingMore() {
                ((TextView)foot.findViewById(R.id.tvb)).setText("加载更多");

            }
        });
        list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }



            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {

                View v = view.inflate(MainActivity.this,R.layout.listview_item,null);
                TextView tv = (TextView) v.findViewById(R.id.text2);
                tv.setText("我是第"+i+"个View");
                return v;


            }
        });
    }
    Handler Uihandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            list.OncompleteRefresh();
        }
    };
}
