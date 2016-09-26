package com.dashen.xcwy.egame;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 广告展现前先调用sdk初始化方法，可以有效缩短广告第一次展现所需时间
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ShowActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        editMetaData();
    }
    private void editMetaData() {
        try {
            ApplicationInfo info = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
//            info.metaData.putString("BaiduMobAd_APP_ID","123456");

            Log.i("YL",info.metaData.getString("BaiduMobAd_APP_ID"));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

}
