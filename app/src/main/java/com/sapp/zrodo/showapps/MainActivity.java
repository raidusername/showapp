package com.sapp.zrodo.showapps;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.sapp.zrodo.showapps.Bean.AppMsg;
import com.sapp.zrodo.showapps.Bean.DateBean;
import com.sapp.zrodo.showapps.utils.ApkTools;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView lv_appinfo;
    private List<AppMsg> ls_appmsg;
    private AppinfoAdapter appinfoAdapter;
    private Handler mhandle = new Handler();
    private EditText edt_search;
    private ProgressDialog dialog;
    private ImageView im_close;
    private DateBean dateBean;
    private ImageView img_search;
    private List<AppMsg> listnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        lv_appinfo = (ListView) findViewById(R.id.lv_Appinfo);
        edt_search = (EditText) findViewById(R.id.edt_content);
        im_close = (ImageView) findViewById(R.id.img_close);
        img_search = (ImageView) findViewById(R.id.img_search);
        appinfoAdapter = new AppinfoAdapter(this);
        lv_appinfo.setAdapter(appinfoAdapter);

        if (savedInstanceState == null) {
            //扫描得到APP列表
            ls_appmsg = ApkTools.scanLocalInstallAppList(MainActivity.this.getPackageManager());
            dateBean = new DateBean();
            dateBean.setLs(ls_appmsg);
        } else {
            //如果进行过备份直接取用list
            dateBean = (DateBean) savedInstanceState.getSerializable("appinfo");
            ls_appmsg = dateBean.getLs();
        }

        initAppList();
        //搜索
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showlist();

                if (listnew.size() == 0) {
                    mhandle.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "很抱歉，没有安装此应用", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
        //清楚edt_search 中的信息
        im_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_search.setText("");
            }
        });
        //对edt_search进行监听变化
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    im_close.setVisibility(View.GONE);
                    initAppList();
                } else {
                    im_close.setVisibility(View.VISIBLE);
                    showlist();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void showlist() {
        //获取输入的内容
        listnew = new ArrayList<>();
        String str = edt_search.getText().toString().trim();
        for (AppMsg appMsg : ls_appmsg) {
            if (appMsg.getAppname().contains(str)) {
                listnew.add(appMsg);
            }
        }
        mhandle.post(new Runnable() {
            @Override
            public void run() {
                appinfoAdapter.setData(listnew);
            }
        });

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("appinfo", dateBean);
    }

    private void initAppList() {
        showDialogprogress();
        new Thread() {
            @Override
            public void run() {
                super.run();

                mhandle.post(new Runnable() {
                    @Override
                    public void run() {
                        appinfoAdapter.setData(ls_appmsg);
                        closeDialogprogress();
                    }
                });
            }
        }.start();

    }


    /**
     * 显示进度光
     **/
    private void showDialogprogress() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("正在加载中");
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
    }

    /**
     * 关闭进度框
     */
    private void closeDialogprogress() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
