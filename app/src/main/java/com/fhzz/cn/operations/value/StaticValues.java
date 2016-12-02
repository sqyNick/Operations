package com.fhzz.cn.operations.value;

/**
 * Created by Administrator on 2016/9/27.
 */
public interface StaticValues {

    /**
     * 服务器配置
     */
    //服务器IP
//    String IP = "119.97.185.205";
    String IP = "172.30.16.105";

    //服务器端口
    int PORT = 8080;
    //工程名
    String PROJECT = "nms";
    //服务器基地址
    String SERVER = IP + ":" +PORT +"/" +PROJECT;
    /**登录请求*/
    String ACTION_LOGIN = "http://" + SERVER + "/service/mobileApp_login.do";
    String ACTION_LOGIN_OUT = "http://" + SERVER + "/service/mobileApp_logout.do";
    String ACTION_QUERY_WORKORDER = "http://" + SERVER + "/mobile/woListForMoblie";
    String ACTION_SUBMIT_EVENT = "http://" + SERVER + "/service/mobileApp_updateEventStatus.do";

    /**服务器配置结束*/

    /**
     * 数据库配置
     */
    //数据库名
    String DB_NAME = "work_order_db";

    /**数据库配置结束*/

    /**
     * SharedPreferenced配置
     */
    String SHARED_PREFERENCED_NAME = "CopyRight_SQY";

    /**SharedPreferenced配置结束*/

    /**空字符*/
    String EMPTY_VALUE = "";
    /**分隔符*/
    String SPLIT = ",";
    /**成功登录过的账号*/
    String LOGINED_USER = "LOGINED_USER";
    /**上次登录的账号*/
    String LAST_LOGINED_USER = "LAST_LOGINED_USER";
    String LAST_LOGINED_PSD = "LAST_LOGINED_PSD";
    String LAST_LOGIN_USER_ID = "LAST_LOGIN_USER_ID";
    /**现在登录的账号*/
    String NOW_LOGIN_USER = "NOW_LOGIN_USER";
    /**http请求超时时长*/
    long CONNECT_TIME_OUT = 6000L;
    /**http请求读取时长*/
    long READ_TIME_OUT = 6000L;
    /**引导界面显示时长*/
    long SPLASH_TIME = 1900L;
    /**应用根目录名*/
    String APP_DIRECTORY = "Operations";

    String CAPTURE_PIC_DIR = "capture";

   String PUSH_API_KEY = "yVusAPPcxQgjgwjS8XGiTtBp";

    String SERVER_EXCEPTION = "服务器异常";

    String UN_KONW = "未知";
    int PUSH_EVENT_NOTIFYCATION = 1;
}
