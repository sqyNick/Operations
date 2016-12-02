package com.fhzz.cn.operations.push;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.fhzz.cn.operations.R;
import com.fhzz.cn.operations.activity.SplashActivity;
import com.fhzz.cn.operations.dbbean.WorkOrder;
import com.fhzz.cn.operations.util.LogUtil;
import com.fhzz.cn.operations.value.StaticValues;
import com.google.gson.Gson;

import java.util.List;

import de.greenrobot.event.EventBus;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Administrator on 2016/12/1.
 */

public class PushWorkOrder extends PushMessageReceiver {
    @Override
    public void onBind(Context context, int errorCode, String appid, String userId, String channelId, String requestId) {
        String responseString = "onBind errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId;
        LogUtil.d(responseString);
    }

    @Override
    public void onUnbind(Context context, int i, String s) {

    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {

    }

    @Override
    public void onMessage(Context context, String s, String s1) {
        String mPackageName = null;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        if(Build.VERSION.SDK_INT > 20){
             mPackageName = activityManager.getRunningAppProcesses().get(0).processName;
        }else{
            mPackageName =  activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        }
        LogUtil.d(mPackageName);
        WorkOrder workOrder = new Gson().fromJson(s,WorkOrder.class);
        if (context.getPackageName().equals(mPackageName)) {
            EventBus.getDefault().post(workOrder);
        } else {
            NotifySubmitStatus(context);
        }
    }
    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {

    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {

    }

    NotificationCompat.Builder builder = null;
    NotificationManager notificationManager = null;
    RemoteViews remoteViews = null;
    public void NotifySubmitStatus(Context context){
        if(notificationManager == null){
            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }
        if(builder == null){
            builder = new NotificationCompat.Builder(context);
        }
        if(remoteViews == null){
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.remote_views);
        }
        builder.setAutoCancel(true);
        builder.setOngoing(false); //设置是否可清除
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContent(remoteViews);
        builder.setTicker("新任务");
        Intent it = new Intent(context, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,it,0);
        remoteViews.setOnClickPendingIntent(R.id.remoteViews,pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher));
        notificationManager.notify(StaticValues.PUSH_EVENT_NOTIFYCATION,builder.build());
    }
}
