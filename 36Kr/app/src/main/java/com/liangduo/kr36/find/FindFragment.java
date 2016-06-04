package com.liangduo.kr36.find;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
import com.liangduo.kr36.login.LoginActivity;
import com.liangduo.kr36.search.SearchActivity;

/**
 * Created by liangduo on 16/5/9.
 * 发现
 */
public class FindFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout findSearch;//搜索
    private LinearLayout deepService;//深度服务
    private LinearLayout ventureCompany;//创业公司的
    private LinearLayout mostExplosive;//最具爆发力
    private LinearLayout newEmergence;//最新涌现
    private LinearLayout itemAction;//项目活动
    private LinearLayout lookOfInvestor;//投资人在看
    private LinearLayout index36;//36氪指数
    private LinearLayout nearlyAty;//近期活动
    private LinearLayout research36;//36氪研究院
    private LinearLayout findInvestor;//寻找投资人
    private LinearLayout apply;//申请融资
    private LinearLayout krSpace;//氪空间报名
    private TextView nearlyAtyTitle;
    private TextView research36Title;
    private TextView findInvestorTitle;


    @Override
    protected void initData() {

        nearlyAtyTitle = bindView(R.id.find_item_near_activity_tv);
        nearlyAtyTitle.setText(R.string.discovery_activity);

        findInvestorTitle = bindView(R.id.find_item_find_investor_tv);
        findInvestorTitle.setText(R.string.discovery_find_investor);

        research36Title = bindView(R.id.find_item_research_tv);
        research36Title.setText(R.string.discovery_research);

        findSearch.setOnClickListener(this);
        deepService.setOnClickListener(this);
        ventureCompany.setOnClickListener(this);
        mostExplosive.setOnClickListener(this);
        newEmergence .setOnClickListener(this);
        itemAction.setOnClickListener(this);
        lookOfInvestor.setOnClickListener(this);
        index36.setOnClickListener(this);
        nearlyAty.setOnClickListener(this);
        research36.setOnClickListener(this);
        findInvestor.setOnClickListener(this);
        apply.setOnClickListener(this);
        krSpace.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        findSearch     = bindView(R.id.find_search);
        deepService    = bindView(R.id.find_deep_service_item);
        ventureCompany = bindView(R.id.find_venture_company);
        mostExplosive  = bindView(R.id.find_the_most_explosive);
        newEmergence   = bindView(R.id.find_new_emergence);
        itemAction     = bindView(R.id.find_item_active);
        lookOfInvestor = bindView(R.id.find_looking_for_investor);
        index36        = bindView(R.id.find_item_index);
        nearlyAty      = bindView(R.id.find_item_near_activity);
        research36     = bindView(R.id.find_item_research);
        findInvestor   = bindView(R.id.find_item_find_investor);
        apply          = bindView(R.id.find_item_apply);
        krSpace        = bindView(R.id.find_item_space);
    }
    @Override
    protected int initLayout() {
        return R.layout.fragment_find;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.find_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.find_deep_service_item:
                Toast.makeText(getActivity(), "以点击2", Toast.LENGTH_SHORT).show();
                turnLoginAty();
                break;
            case R.id.find_venture_company:
                Toast.makeText(getActivity(), "以点击3", Toast.LENGTH_SHORT).show();
                turnLoginAty();
                break;
            case R.id.find_the_most_explosive:
                Toast.makeText(getActivity(), "以点击4", Toast.LENGTH_SHORT).show();
                turnLoginAty();
                break;
            case R.id.find_new_emergence:
                Toast.makeText(getActivity(), "以点击5", Toast.LENGTH_SHORT).show();
                turnLoginAty();
                break;
            case R.id.find_item_active:
                Toast.makeText(getActivity(), "以点击6", Toast.LENGTH_SHORT).show();
                turnLoginAty();
                break;
            case R.id.find_looking_for_investor:
                Toast.makeText(getActivity(), "以点击7", Toast.LENGTH_SHORT).show();
                turnLoginAty();
                break;
            case R.id.find_item_index:
                Toast.makeText(getActivity(), "以点击8", Toast.LENGTH_SHORT).show();
                turnLoginAty();
                break;
            case R.id.find_item_near_activity:
                Intent intentNear = new Intent(getActivity(),ReuseActivity.class);
                intentNear.putExtra("title",nearlyAtyTitle.getText());
                startActivity(intentNear);
                Toast.makeText(getActivity(), "以点击9", Toast.LENGTH_SHORT).show();
                break;
            case R.id.find_item_research:
                Intent intentResearch = new Intent(getActivity(),ReuseActivity.class);
                intentResearch.putExtra("title",research36Title.getText());
                startActivity(intentResearch);
                Toast.makeText(getActivity(), "以点击10", Toast.LENGTH_SHORT).show();
                break;
            case R.id.find_item_find_investor:
                Intent intentFindInvestor = new Intent(getActivity(),ReuseActivity.class);
                intentFindInvestor.putExtra("title",findInvestorTitle.getText());
                startActivity(intentFindInvestor);
                Toast.makeText(getActivity(), "以点击11", Toast.LENGTH_SHORT).show();
                break;
            case R.id.find_item_apply:
                Toast.makeText(getActivity(), "以点击12", Toast.LENGTH_SHORT).show();
                break;
            case R.id.find_item_space:
                Toast.makeText(getActivity(), "以点击13", Toast.LENGTH_SHORT).show();
                turnLoginAty();
                break;
        }
    }

    //跳转至LoginActivity
    private void turnLoginAty(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
