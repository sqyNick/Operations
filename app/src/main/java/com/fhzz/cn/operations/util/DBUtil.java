package com.fhzz.cn.operations.util;

import android.content.Context;
import android.os.Environment;

import com.fhzz.cn.operations.dbbean.WorkOrder;
import com.fhzz.cn.operations.value.StaticValues;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */
public class DBUtil {

    public static DbUtils dbUtils;
    /**
     * 创建数据库
     *@return 返回数据库操作类
     */
    public static DbUtils create(Context context, String user){
        String sdStatus = Environment.getExternalStorageState();
        if (Environment.MEDIA_UNMOUNTED.equals(sdStatus)) {
            return DbUtils.create(context);
        }
        File dbTopDirDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + StaticValues.APP_DIRECTORY + "/" + user + "/db");
        if (!dbTopDirDir.exists()) {
            dbTopDirDir.mkdirs();
        }
        LogUtil.d(dbTopDirDir.getAbsolutePath());
        return DbUtils.create(context, dbTopDirDir.getAbsolutePath() ,StaticValues.DB_NAME);
    }

    public static void init(Context context,String user){
        dbUtils = null;
        dbUtils = DBUtil.create(context,user);
        try{
            dbUtils.createTableIfNotExist(WorkOrder.class);
        }catch (DbException e){
            e.printStackTrace();
        }
    }

    public static boolean saveEvent(WorkOrder order){
        try {
            dbUtils.saveOrUpdate(order);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean deleteWorkOrder(String orderId){
        try {
            dbUtils.delete(WorkOrder.class,WhereBuilder.b("event_flow_num", "==", orderId));
            return true;
        } catch (DbException e) { e.printStackTrace();}
        return false;
    }

    public static List<WorkOrder> getEventsByPage(int pageIndex,int pageSize)
    {
        try {
            return dbUtils.findAll(Selector.from(WorkOrder.class).orderBy("id", true).limit(pageSize).offset(pageIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//
//    public static List<ExplorePoint> findAllUnSubmitPoint(){
//        try {
//            return dbUtils.findAll(Selector.from(ExplorePoint.class).where("is_submite","==",StaticValues.UN_SUBMIT_POINT));
//        } catch (DbException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static void updateByNetwork(List<ExplorePoint> lists){
//        if(lists == null){
//            return;
//        }
//        for( ExplorePoint point : lists ){
//            try {
//                dbUtils.update(point, WhereBuilder.b("point_id","==",point.point_id),
//                        "parea","pname","pnum","camera_type","install_mode","arm_height","arm_length","install_height","watch_area",
//                        "minne_enviriment","charge_method","whether_shader","whether_light","explore_date",
//                        "remote","point_map","install_map","whatch_angle_map","phone","explore_status"
//                        ,"lat","lon","is_submite","submit_person");
//            } catch (DbException e) {
//                e.printStackTrace();
//                LogUtil.d("TAG","update exception");
//            }
//        }
//    }
}
