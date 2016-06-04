package com.liangduo.kr36.invest;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
import com.liangduo.kr36.find.FindFragment;
import com.liangduo.kr36.my.MyFragment;
import com.liangduo.kr36.news.NewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangduo on 16/5/9.
 */
public class InvestFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private ViewPager investVp;
    private TabLayout investTabLayout;
    private InvestVpAdapter investVpAdapter;
    private List<Fragment> fragmentList ;




    @Override
    protected void initData() {

        investVpAdapter = new InvestVpAdapter(getChildFragmentManager());
        investVpAdapter.setFragments(fragmentList);
        investVp.setAdapter(investVpAdapter);
        investTabLayout.setupWithViewPager(investVp);

        investVp.setOnPageChangeListener(this);

    }

    @Override
    protected void initView() {
        investTabLayout = bindView(R.id.invest_fragment_tablayout);
        investVp = bindView(R.id.invest_fragment_vp);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_invest;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
