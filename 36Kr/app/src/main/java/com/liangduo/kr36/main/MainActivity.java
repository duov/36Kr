package com.liangduo.kr36.main;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseActivity;
import com.liangduo.kr36.find.FindFragment;
import com.liangduo.kr36.invest.InvestFragment;
import com.liangduo.kr36.my.MyFragment;
import com.liangduo.kr36.news.NewsFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MainViewPagerAdapter mainViewPagerAdapter;
    private List<Fragment> fragmentList;
    private TextView titleTv;//标题
    private ImageView titleNavigationIv;//导航栏
    private ImageView titleSearchIv;//搜索

    private DrawerLayout mDrawerLayout;//抽屉
    private ImageView drawerBackIv;//抽屉里的返回
    private ListView drawerListView;//抽屉里的listView
    private String[] mPlanetTitles = {"全部", "早期项目", "氪TV"};


//    private LinearLayout drawerAllItemLayout;

    @Override
    protected void initView() {//初始化组件
        mTabLayout = bindView(R.id.main_tab_layout);
        mViewPager = bindView(R.id.main_vp);
        titleTv = bindView(R.id.title_tv);
        titleNavigationIv = bindView(R.id.title_navigation_iv);
        titleSearchIv = bindView(R.id.title_search_iv);
        mDrawerLayout = bindView(R.id.drawer_layout);
        drawerBackIv = bindView(R.id.drawer_back_iv);

        //抽屉里ListView
        drawerListView = bindView(R.id.drawer_list_view);
    }

    @Override
    protected void initData() {
        //初始化Fragment
        initFragment();

        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.setFragments(fragmentList);
        mViewPager.setAdapter(mainViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        //初始化图标
        initTab();
        //监听ViewPager的改变
        mViewPager.addOnPageChangeListener(this);
        //导航栏点击出现抽屉
        titleNavigationIv.setOnClickListener(this);
        //抽屉返回按键的监听
        drawerBackIv.setOnClickListener(this);
//        //抽屉全部item的点击
//        drawerAllItemLayout.setOnClickListener(this);

        //为ListView设置Adapter
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mPlanetTitles));
//        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
    }

    //绑定布局
    @Override
    protected int getLayout() {
        return R.layout.drawer_layout;
    }

    //设置title
    private void setTitleBar(int position) {
        titleTv.setText(mainViewPagerAdapter.getPageTitle(position));
        switch (position) {
            case 0:
                bindView(R.id.title_bar).setVisibility(View.VISIBLE);
                titleNavigationIv.setVisibility(View.VISIBLE);
                break;
            case 1:
                bindView(R.id.title_bar).setVisibility(View.VISIBLE);
                titleNavigationIv.setVisibility(View.INVISIBLE);//设置组件不可见
                break;
            case 2:
                bindView(R.id.title_bar).setVisibility(View.GONE);//隐藏标题栏
                break;
            case 3:
                bindView(R.id.title_bar).setVisibility(View.GONE);//隐藏标题栏
                break;
        }
    }

    //初始化图标的方法
    private void initTab() {
        int[] tabs = {R.drawable.selector_news,R.drawable.selector_invest, R.drawable.selector_find, R.drawable.selector_my};
        for (int i = 0; i < tabs.length; i++) {
            mTabLayout.getTabAt(i).setIcon(tabs[i]);
        }
    }

    //初始化Fragment的方法
    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new NewsFragment());
        fragmentList.add(new InvestFragment());
        fragmentList.add(new FindFragment());
        fragmentList.add(new MyFragment());

    }


    /**
     * 以下三个是监听ViewPager复写的方法
     **/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setTitleBar(position);//通过位置设置标题栏
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //导航栏
            case R.id.title_navigation_iv:
                mDrawerLayout.openDrawer(Gravity.LEFT);//抽屉从左侧打开
                break;
            case R.id.drawer_back_iv:
                mDrawerLayout.closeDrawer(Gravity.LEFT);//抽屉从左边收回
                break;

        }
    }

//    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            selectItem(position);
//        }
//    }

    //
//    private void selectItem(int position) {
//        // update the main content by replacing fragments
//        Fragment fragment = new PlanetFragment();
//        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//        // update selected item and activity_title, then close the drawer
//        drawerListView.setItemChecked(position, true);
////    setTitle(mPlanetTitles[position]);
//        mDrawerLayout.closeDrawer(drawerListView);
//    }
}
