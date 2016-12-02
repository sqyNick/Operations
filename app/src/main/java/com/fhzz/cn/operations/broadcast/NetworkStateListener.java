package com.fhzz.cn.operations.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fhzz.cn.operations.bus.NetworkBus;
import com.fhzz.cn.operations.util.LogUtil;
import com.fhzz.cn.operations.util.NetUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/12/2.
 */

public class NetworkStateListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.d("on Receive");
        if (NetUtil.isNetWorkAvailable(context)) {
            /**网络已连接*/
            if (NetUtil.isMobileConnected(context)) {

            } else if(NetUtil.isWifiConnected(context)) {

            }
            NetworkBus networkBus = new NetworkBus();
            networkBus.IS_NETWORK_CONNECTED = true;
            EventBus.getDefault().post(networkBus);
        } else {
            /**网络未连接*/
            NetworkBus networkBus = new NetworkBus();
            networkBus.IS_NETWORK_CONNECTED = false;
            EventBus.getDefault().post(networkBus);
        }
    }
}
