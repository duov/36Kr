package com.liangduo.kr36.login;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangduo on 16/5/16.
 */
public class LoginActivity  extends BaseActivity implements View.OnClickListener ,OnClickViewPagerToOne{
    private TabLayout loginTabLayout;
    private ViewPager loginViewPager;
    private LoginVpAdapter loginVpAdapter;
    private List<Fragment> fragments;

    private ImageView quitIv;//退出登录界面

    @Override
    protected void initView() {
        loginTabLayout = bindView(R.id.login_tab_layout);
        loginViewPager = bindView(R.id.login_vp);
        quitIv = (ImageView) findViewById(R.id.login_quit);
    }

    @Override
    protected void initData() {
        initFragment();

        loginVpAdapter = new LoginVpAdapter(getSupportFragmentManager());
        loginVpAdapter.setFragments(fragments);
        loginViewPager.setAdapter(loginVpAdapter);
        loginTabLayout.setupWithViewPager(loginViewPager);

        quitIv.setOnClickListener(this);
    }


    private void initFragment() {
        //给ViewPager添加两个Fragment
        fragments = new ArrayList<>();
        //想要跳到哪个页就用到哪个页
        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.setOnClickViewPagerToOne(this);

        fragments.add(new LoginFragment());
        fragments.add(registerFragment);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_quit:
                finish();
                break;
        }
    }

    @Override
    public void toOne() {
        //让ViewPager回到第0页
        loginViewPager.setCurrentItem(0);
    }
}
