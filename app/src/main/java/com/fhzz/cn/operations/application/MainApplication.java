package com.fhzz.cn.operations.application;

import android.app.Application;

import com.fhzz.cn.operations.util.HttpUtil;

/**
 * Created by Administrator on 2016/12/2.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtil.init();
    }
}
