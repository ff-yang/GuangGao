package com.dashen.xcwy.egame;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mobad.feeds.BaiduNative;
import com.baidu.mobad.feeds.BaiduNative.BaiduNativeNetworkListener;
import com.baidu.mobad.feeds.NativeErrorCode;
import com.baidu.mobad.feeds.NativeResponse;
import com.baidu.mobad.feeds.NativeResponse.MaterialType;
import com.baidu.mobad.feeds.RequestParameters;

import java.util.ArrayList;
import java.util.List;


public class ShowActivity extends Activity {

    private NativeResponse mNrAd;
    LinearLayout adsParent;
    private DisplayMetrics metric;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editMetaData();
        setContentView(R.layout.templatefeed);
         metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        adsParent = (LinearLayout) findViewById(R.id.adsRl);

            fetchAd(this, YOUR_AD_PLACE_ID);

//        fetchAd(this,YOUR_AD_PLACE_ID);
//        fetchAd(this,YOUR_AD_PLACE_ID);
//        fetchAd(this,YOUR_AD_PLACE_ID);
//        fetchAd(this,YOUR_AD_PLACE_ID);
//        fetchAd(this,YOUR_AD_PLACE_ID);
//        fetchAd(this,YOUR_AD_PLACE_ID);
//        fetchAd(this,YOUR_AD_PLACE_ID);
//        fetchAd(this,YOUR_AD_PLACE_ID);


    }



    List<NativeResponse> nrAdList = new ArrayList<NativeResponse>();
    private static String YOUR_AD_PLACE_ID = "2923293"; // 双引号中填写自己的广告位ID
    //2403624
    public void fetchAd(Activity activity,String YOUR_AD_PLACE_ID) {
        /**
         * Step 1. 创建BaiduNative对象，参数分别为：
         * 上下文context，广告位ID, BaiduNativeNetworkListener监听（监听广告请求的成功与失败）
         * 注意：请将YOUR_AD_PALCE_ID替换为自己的广告位ID
         */
        BaiduNative baidu = new BaiduNative(activity, YOUR_AD_PLACE_ID,
                new BaiduNativeNetworkListener() {

                    @Override
                    public void onNativeFail(NativeErrorCode arg0) {
                        Toast.makeText(ShowActivity.this, "没有收到轮播模板广告，请检查", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNativeLoad(List<NativeResponse> arg0) {
                        if (arg0 != null && arg0.size() > 0) {
                            nrAdList = arg0;

                            mNrAd = nrAdList.get(0);
                            Log.i("YL",""+mNrAd.getImageUrl());

                            if (mNrAd.getMaterialType() == MaterialType.HTML) {
                                Toast.makeText(ShowActivity.this, "收到轮播模板广告.", Toast.LENGTH_SHORT).show();
                                    myHandler.sendEmptyMessage(SHOW_HTML);
                            } else {
                                Toast.makeText(ShowActivity.this,
                                        "收到广告,但不是模板广告,请检查", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    }

                });

        /**
         * Step 2. 创建requestParameters对象，并将其传给baidu.makeRequest来请求广告
         */
        RequestParameters requestParameters = new RequestParameters.Builder()
                .setWidth(metric.widthPixels)
                .setHeight(100)
                .downloadAppConfirmPolicy(RequestParameters.DOWNLOAD_APP_CONFIRM_ONLY_MOBILE) // 用户点击下载类广告时，是否弹出提示框让用户选择下载与否
                .build();
        baidu.makeRequest(requestParameters);
    }

    int i=0;
    private static int SHOW_HTML = 0;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == SHOW_HTML) {
                WebView webView = mNrAd.getWebView();
                //添加到
                adsParent.addView(webView);
                if (i<=10){
                    fetchAd(ShowActivity.this,YOUR_AD_PLACE_ID);
                    i++;
                }


            }
            super.handleMessage(msg);
        }
    };
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

