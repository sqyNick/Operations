package com.fhzz.cn.operations.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fhzz.cn.operations.R;
import com.fhzz.cn.operations.dbbean.WorkOrder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */

public class WorkOrderListsAdapter extends BaseAdapter {
    List<WorkOrder> mLists;
    Context mContext;

    public WorkOrderListsAdapter (Context context, List<WorkOrder> lists) {
        mContext = context;
        mLists = lists;
    }
    @Override
    public int getCount() {
        return mLists == null ? 0 : mLists.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_work_order_list,viewGroup,false);
            view.setTag(holder);
            AutoUtils.autoSize(view);
        }
        holder = (ViewHolder) view.getTag();
        return view;
    }

    static class ViewHolder {
        ViewHolder (View v) {

        }
    }
}
