package com.sapp.zrodo.showapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sapp.zrodo.showapps.Bean.AppMsg;

import java.util.ArrayList;
import java.util.List;

public class AppinfoAdapter extends BaseAdapter {
    private List<AppMsg> ls_appmsg = new ArrayList<>();
    private Context context;

    public AppinfoAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AppMsg> myAppInfos) {
        this.ls_appmsg = myAppInfos;
        notifyDataSetChanged();
    }

    public List<AppMsg> getData() {
        return ls_appmsg;
    }

    @Override
    public int getCount() {
        return ls_appmsg.size();
    }

    @Override
    public Object getItem(int position) {
        return ls_appmsg.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppMsg appinfo = ls_appmsg.get(position);
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapterlayout, null);
            viewHold = new ViewHold();
            viewHold.tv_app_name = (TextView) convertView.findViewById(R.id.tv_appanme);
            viewHold.img_appicon = (ImageView) convertView.findViewById(R.id.img_appicon);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }

        viewHold.tv_app_name.setText(appinfo.getAppname());
        viewHold.img_appicon.setImageDrawable(appinfo.getAppimg());


        return convertView;
    }

    class ViewHold {
        public TextView tv_app_name;
        public ImageView img_appicon;
    }
}
