package com.fhzz.cn.operations.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.fhzz.cn.operations.R;
import com.fhzz.cn.operations.adapter.FragmentPagerAdapter;
import com.fhzz.cn.operations.adapter.WorkOrderListsAdapter;
import com.fhzz.cn.operations.broadcast.NetworkStateListener;
import com.fhzz.cn.operations.bus.NetworkBus;
import com.fhzz.cn.operations.config.UserInfo;
import com.fhzz.cn.operations.dbbean.WorkOrder;
import com.fhzz.cn.operations.http.param.RequestParams;
import com.fhzz.cn.operations.http.resp.RespLogin;
import com.fhzz.cn.operations.util.DBUtil;
import com.fhzz.cn.operations.util.ToastUtil;
import com.fhzz.cn.operations.value.StaticValues;
import com.fhzz.cn.operations.view.XListView;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    NetworkStateListener networkStateListener = null;

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.mDrawerLayout)
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    @ViewInject(R.id.networkView)
    View networkView;
    @ViewInject(R.id.csNetworkView)
    ImageView csNetworkView;

    @ViewInject(R.id.viewPager)
    ViewPager viewPager;
    FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        init();
    }
    public void init () {
        initToolbar();
        EventBus.getDefault().register(this);
        initNetworkListener();
        initListener();
//        initBaiduPushConnection();
        initDB();
        initViewPager();
    }
    public void initViewPager () {
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
    }
    public void initToolbar () {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);
    }
    public void initListener () {
        csNetworkView.setOnClickListener(this);
    }
    public void initDB () {
        DBUtil.init(this,UserInfo.USER_ID);
    }
    public void initBaiduPushConnection () {
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, StaticValues.PUSH_API_KEY);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(UserInfo.USER_ID);
        PushManager.setTags(this,arrayList);
    }

    public void initNetworkListener () {
        networkStateListener = new NetworkStateListener();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(networkStateListener,intentFilter);
    }
    public void unRegistNetworkListener () {
        this.unregisterReceiver(networkStateListener);
    }
    public void onEventMainThread (NetworkBus networkBus) {
        if (networkBus.IS_NETWORK_CONNECTED) {
            /**连网*/
            networkView.setVisibility(View.GONE);
        } else {
            /**未连网*/
            networkView.setVisibility(View.VISIBLE);
        }
    }

    public void onEventMainThread (WorkOrder WorkOrder) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unRegistNetworkListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSwitchUser:
                break;
            case R.id.tvSignOut:
                break;
            case R.id.csNetworkView:
                networkView.setVisibility(View.GONE);
                break;
        }
    }

    public void showSignOutDialog (String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setNegativeButton(getResources().getString(R.string.cancle), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton(getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SignOutRequest(true);
            }
        });
        builder.create().show();
    }
    public void SignOutRequest (final boolean signOut) {
        OkHttpUtils
                .get()
                .url(StaticValues.ACTION_LOGIN_OUT)
                .addParams(RequestParams.HANDLERID , UserInfo.USER_ID == null ? StaticValues.EMPTY_VALUE : UserInfo.USER_ID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.show(getBaseContext(),"服务器异常");
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        RespLogin resp = new Gson().fromJson(response,RespLogin.class);
                        if (resp.errorCode == 1000) {
                            if (signOut) { // 退出登录
                                PushManager.stopWork(MainActivity.this);
                                MainActivity.this.finish();
                            } else { //切换用户
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                PushManager.stopWork(MainActivity.this);
                                MainActivity.this.finish();
                            }
                        } else {
                            ToastUtil.show(getBaseContext(),"登出失败,服务器异常");
                        }
                    }
                });
    }
}
