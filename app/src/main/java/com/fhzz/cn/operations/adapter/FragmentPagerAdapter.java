package com.fhzz.cn.operations.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.fhzz.cn.operations.fragment.FinishedFragment;
import com.fhzz.cn.operations.fragment.UnFinishFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/2.
 */

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new FinishedFragment());
        fragments.add(new UnFinishFragment());
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {

        return fragments.size();
    }
}
