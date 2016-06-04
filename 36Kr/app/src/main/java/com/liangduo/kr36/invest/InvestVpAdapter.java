package com.liangduo.kr36.invest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by liangduo on 16/5/13.
 */
public class InvestVpAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragments;
    private String[] tabs= {"全部","募资中","募资完成","融资成功"};

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public InvestVpAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ReuseFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        //tabs的个数就是我的Fragment的个数
        return tabs == null? 0:tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
