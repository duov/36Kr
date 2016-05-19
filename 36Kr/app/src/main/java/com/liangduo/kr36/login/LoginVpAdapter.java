package com.liangduo.kr36.login;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by liangduo on 16/5/16.
 */
public class LoginVpAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private String tab[]= {"登录","注册"};

    public LoginVpAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null? 0:fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab[position];
    }
}
