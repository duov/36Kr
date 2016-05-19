package com.liangduo.kr36.find;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangduo on 16/5/14.
 */
public class ReuseActivity extends BaseActivity implements View.OnClickListener {
    private TextView titleTv;//标题栏的标题
    private ImageView titleBackIv;//标题栏的返回键
    private ImageView titleFindIv;//标题栏的搜索

    private LinearLayout nearlyAtyFragmentLayout;
    private LinearLayout research36FragmentLayout;
    private LinearLayout findInvestorFragmentLayout;

    private NearlyAtyFragment nearlyAtyFragment = new NearlyAtyFragment();
    private Research36Fragment research36Fragment = new Research36Fragment();
    private FindInvestorFragment findInvestorFragment  = new FindInvestorFragment();
    private FrameLayout frameLayout;

    @Override
    protected void initView() {
        titleTv = bindView(R.id.title_tv);//标题
        titleBackIv = bindView(R.id.title_navigation_iv);//返回
        titleFindIv = bindView(R.id.title_search_iv);//搜索

        frameLayout = bindView(R.id.frame_layout_find_item);

        nearlyAtyFragmentLayout = bindView(R.id.fragment_content_find_nearly);
        research36FragmentLayout = bindView(R.id.fragment_content_find_research);
        findInvestorFragmentLayout = bindView(R.id.fragment_content_find_find_investor);
    }

    @Override
    protected void initData() {
        titleBackIv.setImageResource(R.mipmap.news_toolbar_icon_back);//标题
        titleFindIv.setVisibility(View.GONE);//搜索
        titleBackIv.setOnClickListener(this);//返回

        //设置标题栏的标题
        Intent intent = getIntent();
        String  title = intent.getStringExtra("title");
        titleTv.setText(title);
        Log.d("^^^^^^^^^^", "titleTv.getText():" + titleTv.getText()+"66666666666666");

        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (titleTv.getText().equals("近期活动")) {
            transaction.replace(R.id.frame_layout_find_item, nearlyAtyFragment);
            transaction.commit();
        }else if ((titleTv.getText()).equals("36氪研究院")){
            transaction.replace(R.id.frame_layout_find_item, research36Fragment);
            transaction.commit();
        }else if ((titleTv.getText()).equals("寻找投资人")){
            transaction.replace(R.id.frame_layout_find_item, findInvestorFragment);
            transaction.commit();
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_reuse;
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
