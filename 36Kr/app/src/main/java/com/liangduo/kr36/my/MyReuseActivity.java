package com.liangduo.kr36.my;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseActivity;

/**
 * Created by liangduo on 16/5/30.
 */
public class MyReuseActivity extends BaseActivity implements View.OnClickListener {
    private TextView titleTv;//标题栏的标题
    private ImageView titleBackIv;//标题栏的返回键
    private ImageView titleFindIv;//标题栏的搜索
    private FrameLayout frameLayout;

    private LinearLayout myCollectionFragmentLayout;//我的收藏
    private MyCollectionFragment myCollectionFragment = new MyCollectionFragment();

    @Override
    protected void initView() {
        titleTv = bindView(R.id.title_tv);//标题
        titleBackIv = bindView(R.id.title_navigation_iv);//返回
        titleFindIv = bindView(R.id.title_search_iv);//搜索

        frameLayout = bindView(R.id.frame_layout_my);
        myCollectionFragmentLayout = bindView(R.id.my_collection_fragment);
    }

    @Override
    protected void initData() {
        titleBackIv.setImageResource(R.mipmap.news_toolbar_icon_back);//标题
        titleFindIv.setVisibility(View.GONE);//搜索
        titleBackIv.setOnClickListener(this);//返回

        //获得Intent传值 获得title
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        titleTv.setText(title);
        //设置复用Activity中的fragment;
        setFragment();
    }

    private void setFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (titleTv.getText().equals("我收藏的文章")) {
            transaction.replace(R.id.frame_layout_my, myCollectionFragment);
            transaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_my_reuse;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_navigation_iv:
                finish();
                break;
        }
    }
}
