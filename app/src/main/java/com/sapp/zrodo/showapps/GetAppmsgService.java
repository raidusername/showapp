package com.sapp.zrodo.showapps;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.sapp.zrodo.showapps.Bean.AppMsg;

import java.util.ArrayList;
import java.util.List;
//暂时用不到
public class GetAppmsgService extends Service {
    private static final String TAG = "GetAppmsgService";
    private List<AppMsg> applist;

    public GetAppmsgService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    class MyBind extends Binder {
        //
        public List<AppMsg> GetMsg() {

            return applist;
        }

    }

    /**
     * 判断某一个应用程序是不是系统的应用程序，
     * 如果是返回true，否则返回false。
     */
    public boolean filterApp(ApplicationInfo info) {
        //有些系统应用是可以更新的，如果用户自己下载了一个系统的应用来更新了原来的，它还是系统应用，这个就是判断这种情况的
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
            //判断是不是系统应用
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //用来存储App的名字和图标
                applist = new ArrayList<>();
                //获取PackageManager对象
                PackageManager packageManager = getPackageManager();
                //获取所有已安装的应用程序
                List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);// GET_UNINSTALLED_PACKAGES代表已删除，但还有安装目录的

                ;

                for (PackageInfo info : packageInfos) {
                    AppMsg appMsg = new AppMsg();
                    ApplicationInfo appinfo = info.applicationInfo;
                    //Log.d(TAG, "一个app的信息"+appinfo.toString());
//                    if (!filterApp(appinfo)) {
//                        continue;
//                    }
                    //应用名称
                    String appName = appinfo.loadLabel(packageManager).toString();
                    //图标
                    Drawable icon = appinfo.loadIcon(packageManager);
                    appMsg.setAppimg(icon);
                    appMsg.setAppname(appName);
                    applist.add(appMsg);
                }
                //com.raid.sender


                Log.d(TAG, "获取app的个数" + applist.size());
            }
        }).start();

        Log.d(TAG, "onCreate: ");
    }


}
