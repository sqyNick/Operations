package com.fhzz.cn.operations.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fhzz.cn.operations.R;
import com.fhzz.cn.operations.adapter.WorkOrderListsAdapter;
import com.fhzz.cn.operations.dbbean.WorkOrder;
import com.fhzz.cn.operations.view.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */

public class FinishedFragment extends Fragment implements View.OnClickListener {

    View emptyView;
    XListView xListView;

    TextView switchTv;
    LinearLayout switchLinear;

    WorkOrderListsAdapter adapter = null;
    List<WorkOrder> lists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finished, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListview(view);
        initListener();
    }

    public void initListener() {
        switchLinear.setOnClickListener(this);
    }

    public void initListview(View v) {
        xListView = (XListView) v.findViewById(R.id.xListview);
        emptyView = v.findViewById(R.id.emptyView);
        switchTv = (TextView) v.findViewById(R.id.switchTv);
        switchLinear = (LinearLayout) v.findViewById(R.id.switchLinear);

        xListView.setEmptyView(emptyView);
        lists = new ArrayList<WorkOrder>();
        lists.add(new WorkOrder());
        lists.add(new WorkOrder());
        lists.add(new WorkOrder());
        adapter = new WorkOrderListsAdapter(getContext(), lists);
        xListView.setAdapter(adapter);
    }

    boolean isFinish = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switchLinear:
                ObjectAnimator oa = ObjectAnimator.ofFloat(switchTv, "translationX", switchTv.getTranslationX(), isFinish ? (switchTv.getWidth()) : 0);
                oa.setDuration(300);
                oa.start();
                isFinish = !isFinish;
                if (isFinish) {
                    switchTv.setText("已解决");
                } else {
                    switchTv.setText("未解决");
                }
                break;
        }
    }
}
