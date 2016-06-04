package com.liangduo.kr36.news;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
import com.liangduo.kr36.bean.NewsBean;
import com.liangduo.kr36.bean.NewsCycleViewBean;
import com.liangduo.kr36.drawer.BItemFragment;
import com.liangduo.kr36.drawer.BigCompanyFragment;
import com.liangduo.kr36.drawer.CapitalItemFragment;
import com.liangduo.kr36.drawer.DeepItemFragment;
import com.liangduo.kr36.drawer.EarlyItemFragment;
import com.liangduo.kr36.drawer.KrTVFragment;
import com.liangduo.kr36.drawer.ResearchItemFragment;
import com.liangduo.kr36.tool.RefreshableView;
import com.liangduo.kr36.tool.VolleySingle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangduo on 16/5/9.
 * 新闻
 */
public class NewsFragment extends BaseFragment implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener {
    private NewsAdapter newsAdapter;
    private ListView newsListView;
    private ViewPager autoViewPager;//自动轮播ViewPager
    private FrameLayout frameLayout;
    private ListView drawerLv;//抽屉的ListView
    private boolean flag = true;

    private NewsBroadCast newsBroadCast;//广播
    private static final String CHANGE_DRAWER_LV_BEAN = "com.liangduo.kr36.CHANGE_DRAWER_LV_BEAN";
    private List<Fragment> fragmentList = new ArrayList<>();

    //private SwipeRefreshLayout swipeRefreshLayout;//下拉刷新1
    private RefreshableView refreshableView;

    private NewsBean newsBean;
    private int itemSize = 20;//新闻界面最开始网络拉取的条数

    private NewsAutoVpAdapter newsAutoVpAdapter;//自动轮播图的ViewPager的Adapter

    private List<String> urls = new ArrayList<>();//创建一个集合去装轮播图的url

    private Handler handler = new Handler();
    private Runnable cycleRunnable;

    private List<String> cycleUrl = new ArrayList<>();//创建一个集合去装轮播图点击后的link

    private LinearLayout cyclePoint;//轮播图下方的指示图标

    private String[] mPlanetTitles = {"全部", "早期项目", "B轮后", "大公司", "资本", "深度", "研究", "氪TV"};
//    private TextView title;//


    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        refreshableView = bindView(R.id.news_lv_head_layout);
        newsListView = bindView(R.id.news_fragment_lv);
        drawerLv = bindView(R.id.drawer_list_view);//下拉刷新
//        swipeRefreshLayout = bindView(R.id.refresh);
        View autoVp = LayoutInflater.from(getActivity()).inflate(R.layout.news_lv_head, null);
        autoViewPager = (ViewPager) autoVp.findViewById(R.id.view_pager);
        cyclePoint = (LinearLayout) autoVp.findViewById(R.id.cycle_bean);
        newsListView.addHeaderView(autoVp);
        newsAutoVpAdapter = new NewsAutoVpAdapter(getActivity());
        refreshableView = bindView(R.id.refreshable_view);
//        title = bindView(R.id.title_tv);

    }

    @Override
    protected void initData() {
        urls.clear();
        cycleUrl.clear();
        //解析网络数据
        analysisData();

        //注册自定义广播接收者
        newsBroadCast = new NewsBroadCast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CHANGE_DRAWER_LV_BEAN);
        getActivity().registerReceiver(newsBroadCast, intentFilter);

        newsAdapter = new NewsAdapter(getActivity());
        newsListView.setAdapter(newsAdapter);

        fragmentList.add(new NewsFragment());
        fragmentList.add(new EarlyItemFragment());
        fragmentList.add(new BItemFragment());
        fragmentList.add(new BigCompanyFragment());
        fragmentList.add(new CapitalItemFragment());
        fragmentList.add(new DeepItemFragment());
        fragmentList.add(new ResearchItemFragment());
        fragmentList.add(new KrTVFragment());

        initCyclePlayPicture();
        newsListView.setOnItemClickListener(this);

        newsListView.setOnScrollListener(this);
        autoViewPager.setOnPageChangeListener(this);
        /**
         * 解决轮播图越来越快的问题,
         * 让他只开启一个线程
         */
        if (flag) {
            //轮播图
            startPlayPicture();
            flag = false;
        }
//
//        autoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(final int position) {
//
//                for (int i = 0; i < urls.size(); i++) {
//                    Log.d("NewsFragment", "urls.size():" + urls.size());
//
//                    ImageView point = (ImageView) cyclePoint.getChildAt(i);
//
//                    point.setImageResource(R.mipmap.bean_be);
//                }
//                ImageView point = (ImageView) cyclePoint.getChildAt(position % urls.size());
//               point.setImageResource(R.mipmap.bean_af);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        /**
         * 下拉加载
         */
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                VolleySingle.addRequest("https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=all&pagingAction=up", NewsBean.class,
                        new Response.Listener<NewsBean>() {
                            @Override
                            public void onResponse(NewsBean response) {
                                newsAdapter.setNewsBean(response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                try {
                    Thread.sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);


    }

    //轮播图片
    private void startPlayPicture() {
        //轮播图开启的线程 初始化线程对象
        cycleRunnable = new Runnable() {
            @Override
            public void run() {
                int now = autoViewPager.getCurrentItem();
                autoViewPager.setCurrentItem(++now);
                handler.postDelayed(cycleRunnable, 4000);
            }
        };
        handler.postDelayed(cycleRunnable, 4000);
    }

    //解析网络数据
    public void analysisData() {

        VolleySingle.addRequest("https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=all&pagingAction=up", NewsBean.class,
                new Response.Listener<NewsBean>() {
                    @Override
                    public void onResponse(NewsBean response) {
                        //       成功时的回调
                        newsBean = response;
                        newsAdapter.setNewsBean(response);

                        Log.d("---------------------", "response.getData().getPageSize():" + response.getData().getPageSize());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //失败时的回调
                        Log.d("NewsFragment", "没打出来");
                    }
                });
    }


    //初始化轮播图
    private void initCyclePlayPicture() {
        VolleySingle.addRequest("https://rong.36kr.com/api/mobi/roundpics/v4", NewsCycleViewBean.class,
                new Response.Listener<NewsCycleViewBean>() {
                    @Override
                    public void onResponse(NewsCycleViewBean response) {
                        Log.d("获取了轮播图的网络数据", "response.getData().getPageSize():" + response.getData().getPics().size());

                        for (int i = 0; i < response.getData().getPics().size(); i++) {

                            cycleUrl.add(response.getData().getPics().get(i).getLocation());
                            urls.add(response.getData().getPics().get(i).getImgUrl());
                            Log.d("--------------", "images.size():" + urls.size());
                        }
                        newsAutoVpAdapter.setCycleUrls(cycleUrl);
                        newsAutoVpAdapter.setUrlList(urls);
                        Log.d("轮播图集合的大小", "url.size():" + urls.size());
                        autoViewPager.setAdapter(newsAutoVpAdapter);
                        getPiont();
                        //解决最后一个跳转到第一个闪动问题
                        //最后一个图到第一个图
                        autoViewPager.setCurrentItem((100 / 2 - 100 / 2 % urls.size()));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //失败时的回调
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(newsBroadCast);
    }
//    /**
//     * 下拉刷新第一种
//     */
//    @Override
//    public void onRefresh() {
//        Toast.makeText(getActivity(), "刷新了", Toast.LENGTH_SHORT).show();
////      newsAdapter.setNewsBean();
//        VolleySingle.addRequest("https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=all&pagingAction=up", NewsBean.class,
//                new Response.Listener<NewsBean>() {
//                    @Override
//                    public void onResponse(NewsBean response) {
//                        newsAdapter.setNewsBean(response);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//        swipeRefreshLayout.setRefreshing(false);
//    }

    /**
     * 上拉加载的监听事件
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.d("NewsFragment", "newsListView.getLastVisiblePosition():" + newsListView.getLastVisiblePosition());
        Log.d("NewsFragment", "newsBean.getData().getData().size():" + newsBean.getData().getData().size());

        /**
         * https://rong.36kr.com/api/mobi/news?pageSize=  item的条数    &columnId=70&pagingAction=up
         * 接口中的item的条数 就是加载的条数 我们可以设置需要加载的条数,放到newsBean里,实现加载数据的效果
         */

        if (view.getLastVisiblePosition() == itemSize) {
            itemSize += 20;
            Log.d("========", "itemSize:" + itemSize);

            String path = "https://rong.36kr.com/api/mobi/news?pageSize=" + itemSize + "&columnId=all&pagingAction=up";
            VolleySingle.addRequest(path,
                    NewsBean.class, new Response.Listener<NewsBean>() {
                        @Override
                        public void onResponse(NewsBean response) {
                            Log.d("上拉加载已经获取到网络数据啦", "response:" + response);
                            newsBean = response;
                            newsAdapter.setNewsBean(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("NewsFragment", "error:" + error);
                        }
                    });
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    /**
     * 新闻的ListView的item的点击事件
     * 点击进入详情界面
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "已点击", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), new DetailedInformationActivity().getClass());

        String feedId = newsBean.getData().getData().get(position - 1).getFeedId();
        intent.putExtra("id", feedId);

        Log.d("传值", newsBean.getData().getData().get(position - 1).getFeedId());
        Log.d("=====position====", "newsBean.getData().getData().get(position):" + newsBean.getData().getData().get(position - 1));
        startActivity(intent);
    }

    //轮播图ViewPager的滚动监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < urls.size(); i++) {
            Log.d("NewsFragment", "urls.得得得得得size():" + urls.size());
            ImageView point = (ImageView) cyclePoint.getChildAt(i);
//            Log.d("NewsFragment", "cyclePoint.getChildAt(0).getHeight():" + cyclePoint.getChildAt(0).getHeight());
            point.setImageResource(R.mipmap.bean_be);

        }
        ImageView point = (ImageView) cyclePoint.getChildAt(position % urls.size());
        point.setImageResource(R.mipmap.bean_af);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void getPiont() {

        Log.d("NewsFragment", "走没走这个方法");
        for (int i = 0; i < urls.size(); i++) {
            Log.d("NewsFragment", "嘛嘛嘛.我是url.size" + urls.size());
            ImageView point1 = new ImageView(getActivity());
            point1.setPadding(5, 5, 5, 5);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
            point1.setLayoutParams(layoutParams);
            Log.d("NewsFragment", "bibibi");

            //设置小点样式
            if (i == 0) {
                point1.setImageResource(R.mipmap.bean_be);
            } else {
                point1.setImageResource(R.mipmap.bean_af);
            }

            cyclePoint.addView(point1);
            Log.d("NewsFragment", "这里这里这里" + cyclePoint.getChildAt(0).getHeight());
        }
    }

    /**
     * 广播接收
     */
    public class NewsBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            autoViewPager.setVisibility(View.GONE);
            int position = intent.getIntExtra("position", 0);
            Log.d("#####广播得到了Intent的传值#", position + "");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            newsListView.setVisibility(View.GONE);
            transaction.replace(R.id.framelayout_fragment, fragmentList.get(position));
            transaction.commit();
            Log.d("NewsBroadCast", "打个log" + mPlanetTitles[position].toString());
        }
    }
}


