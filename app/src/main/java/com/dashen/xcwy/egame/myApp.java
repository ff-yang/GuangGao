package com.dashen.xcwy.egame;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by Administrator on 2016/9/26.
 */
public class myApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        editMetaData();
    }

    private void editMetaData() {
        try {
            ApplicationInfo info = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            info.metaData.putString("BaiduMobAd_APP_ID","123456");

            Log.i("YL",info.metaData.getString("BaiduMobAd_APP_ID"));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
