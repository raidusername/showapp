package com.sapp.zrodo.showapps.utils;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.sapp.zrodo.showapps.Bean.AppMsg;

import java.util.ArrayList;
import java.util.List;

public class ApkTools {
    static  String TAG = "ApkTool";
   // public static List<AppMsg> mLocalInstallApps = null;

    public static List<AppMsg> scanLocalInstallAppList(PackageManager packageManager) {
        List<AppMsg> myAppInfos = new ArrayList<AppMsg>();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                //过滤掉系统app
            if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                continue;
            }

                //获取应用名
                String name=(String)packageManager.getApplicationLabel(packageInfo.applicationInfo);

                AppMsg myAppInfo = new AppMsg();
                myAppInfo.setAppname(name);
                //获取应用图标
                if (packageInfo.applicationInfo.loadIcon(packageManager) == null) {
                    continue;
                }
                myAppInfo.setAppimg(packageInfo.applicationInfo.loadIcon(packageManager));
                myAppInfos.add(myAppInfo);
            }
        }catch (Exception e){
            Log.e(TAG,"===============获取应用包信息失败");
        }
        return myAppInfos;
    }

}
