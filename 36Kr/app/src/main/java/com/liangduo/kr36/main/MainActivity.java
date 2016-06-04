package com.liangduo.kr36.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.liangduo.greendao.CollectionDao;
import com.liangduo.greendao.DaoMaster;
import com.liangduo.greendao.DaoSession;
import com.liangduo.greendao.GreendaoSingle;
import com.liangduo.greendao.Collection;
import com.liangduo.greendao.CollectionDao;
import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseActivity;
import com.liangduo.kr36.bean.DrawerBean;
import com.liangduo.kr36.bean.EarlyBean;
import com.liangduo.kr36.find.FindFragment;
import com.liangduo.kr36.invest.InvestFragment;
import com.liangduo.kr36.login.LoginFragment;
import com.liangduo.kr36.login.OnClickViewPagerToOne;
import com.liangduo.kr36.login.RegisterFragment;
import com.liangduo.kr36.my.MyFragment;
import com.liangduo.kr36.news.NewsFragment;
import com.liangduo.kr36.search.SearchActivity;
import com.liangduo.kr36.tool.GsonRequest;
import com.liangduo.kr36.util.ExampleUtil;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, AdapterView.OnItemClickListener,OnClickViewPagerToOne {
    public static boolean isForeground = false;

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
    private List<DrawerBean> drawerBeen;
    private DrawerAdapter drawerAdapter;
    //发送ListView点击的位置的广播
    private static final String CHANGE_DRAWER_LV_BEAN = "com.liangduo.kr36.CHANGE_DRAWER_LV_BEAN";
    final Intent intent = new Intent(CHANGE_DRAWER_LV_BEAN);

//    private LinearLayout drawerAllItemLayout; my_do_not_finish_tv

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
        mViewPager.setOnPageChangeListener(this);
        //导航栏点击出现抽屉
        titleNavigationIv.setOnClickListener(this);
        titleSearchIv.setOnClickListener(this);
        //抽屉返回按键的监听
        drawerBackIv.setOnClickListener(this);
        initDrawer();
        drawerAdapter = new DrawerAdapter(this);
        drawerAdapter.setDrawerList(drawerBeen);
        drawerListView.setAdapter(drawerAdapter);

//        final Intent intent = new Intent(CHANGE_DRAWER_LV_BEAN);

        drawerListView.setOnItemClickListener(this);

        registerMessageReceiver();  // used for receive msg
        //用户自定义的Notification
        CustomPushNotificationBuilder builder = new
                CustomPushNotificationBuilder(MainActivity.this,
                R.layout.customer_notitfication_layout,
                R.id.icon,
                R.id.title,
                R.id.text);
        // 指定定制的 Notification Layout
        builder.statusBarDrawable = R.mipmap.icon;
        // 指定最顶层状态栏小图标
        builder.layoutIconDrawable = R.mipmap.ic_kr;
        // 指定下拉状态栏时显示的通知图标
        JPushInterface.setPushNotificationBuilder(2, builder);



        //在这里接收了登录的传值,让MainActivity的ViewPager自动划到第三页
        Intent intent1 = getIntent();
        Integer changePage = intent1.getIntExtra("change",0);
        mViewPager.setCurrentItem(changePage);
    }

    private void initDrawer() {
        drawerBeen = new ArrayList<>();
        drawerBeen.add(new DrawerBean(R.mipmap.menu_item_all, "全部"));
        drawerBeen.add(new DrawerBean(R.mipmap.menu_item_early, "早期项目"));
        drawerBeen.add(new DrawerBean(R.mipmap.news_after_b_wheel, "B轮后"));
        drawerBeen.add(new DrawerBean(R.mipmap.news_big_company, "大公司"));
        drawerBeen.add(new DrawerBean(R.mipmap.news_capital, "资本"));
        drawerBeen.add(new DrawerBean(R.mipmap.news_depth, "深度"));
        drawerBeen.add(new DrawerBean(R.mipmap.news_research, "研究"));
        drawerBeen.add(new DrawerBean(R.mipmap.menu_item_tv, "氪TV"));

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
        int[] tabs = {R.drawable.selector_news, R.drawable.selector_invest, R.drawable.selector_find, R.drawable.selector_my};
        for (int i = 0; i < tabs.length; i++) {
            mTabLayout.getTabAt(i).setIcon(tabs[i]);
        }
    }

    //初始化Fragment的方法
    private void initFragment() {
        fragmentList = new ArrayList<>();

        MyFragment myFragment = new MyFragment();

//      //  myFragment.setOnClickViewPagerToOne(this);
//        LoginFragment loginFragment = new LoginFragment();
//        loginFragment.setOnClickViewPagerToOne(this);


        fragmentList.add(new NewsFragment());
        fragmentList.add(new InvestFragment());
        fragmentList.add(new FindFragment());
        fragmentList.add(myFragment);
    }



    /**
     * `
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
            case R.id.title_search_iv:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
        }
    }


    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }

    @Override
    protected void onResume() {
        isForeground = true;
        JPushInterface.onResume(this);
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        JPushInterface.onPause(this);
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                sendPosition(0);
                break;
            case 1:
               sendPosition(1);
                break;
            case 2:
                sendPosition(2);
                break;
            case 3:
                sendPosition(3);
                break;
            case 4:
                sendPosition(4);
                break;
            case 5:
                sendPosition(5);
                break;
            case 6:
                sendPosition(6);
                break;
            case 7:
                sendPosition(7);
                break;
        }
    }

    private void sendPosition(int position) {
        intent.putExtra("position", position);
        sendBroadcast(intent);
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void toOne() {
        mViewPager.setCurrentItem(3);
    }


    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }

}
