package com.fhzz.cn.operations.util;

import com.fhzz.cn.operations.value.StaticValues;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/10/8.
 */

public class HttpUtil {
    public static OkHttpClient okHttpClient;
    public static void init(){
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(StaticValues.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .readTimeout(StaticValues.READ_TIME_OUT,TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
