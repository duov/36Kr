package com.liangduo.kr36.find;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;

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
    private LinearLayout nearlyActivity;//近期活动
    private LinearLayout research36;//36氪研究院
    private LinearLayout findInvestor;//寻找投资人
    private LinearLayout apply;//申请融资
    private LinearLayout krSpace;//氪空间报名
    private ImageView imageView;

    @Override
    protected void initData() {
        nearlyActivity.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        findSearch = bindView(R.id.find_search);
        deepService = bindView(R.id.find_deep_service_item);
        ventureCompany = bindView(R.id.find_venture_company);
        mostExplosive = bindView(R.id.find_the_most_explosive);
        newEmergence = bindView(R.id.find_new_emergence);
        itemAction = bindView(R.id.find_item_active);
        lookOfInvestor = bindView(R.id.find_looking_for_investor);
        index36 = bindView(R.id.find_item_index);
        nearlyActivity = bindView(R.id.find_item_near_activity);
        research36 = bindView(R.id.find_item_research);
        findInvestor = bindView(R.id.find_item_find_investor);
        apply = bindView(R.id.find_item_apply);
        krSpace = bindView(R.id.find_item_space);
        imageView = bindView(R.id.image_view);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_find;
    }

    @Override
    public void onClick(View v) {
        switch (getId()){
            case R.id.image_view:
                Intent intent = new Intent(getContext(),NearlyActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "一点击", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
