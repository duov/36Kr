package com.liangduo.kr36.login;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangduo on 16/5/16.
 */
public class LoginActivity  extends BaseActivity{
    private TabLayout loginTabLayout;
    private ViewPager loginViewPager;
    private LoginVpAdapter loginVpAdapter;
    private List<Fragment> fragments;

    @Override
    protected void initView() {
        loginTabLayout = bindView(R.id.login_tab_layout);
        loginViewPager = bindView(R.id.login_vp);
    }

    @Override
    protected void initData() {
        initFragment();


        loginVpAdapter = new LoginVpAdapter(getSupportFragmentManager());
        loginVpAdapter.setFragments(fragments);
        loginViewPager.setAdapter(loginVpAdapter);
        loginTabLayout.setupWithViewPager(loginViewPager);
    }


    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }
}
