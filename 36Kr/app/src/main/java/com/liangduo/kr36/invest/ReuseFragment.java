package com.liangduo.kr36.invest;

import android.os.Bundle;
import android.widget.ListView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
import com.liangduo.kr36.bean.InvestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangduo on 16/5/13.
 */
public class ReuseFragment extends BaseFragment {
    private ListView reuseLv;
    private ReuseAdapter reuseAdapter;
    private List<String> list;

    @Override
    protected void initData() {
        initList();
        reuseAdapter = new ReuseAdapter(getContext());
        reuseAdapter.setInvestBeen(list);
        reuseLv.setAdapter(reuseAdapter);

    }

    private void initList() {
        list = new ArrayList<>();
        for (int i = 0; i< 10;i++ ){
            list.add(i + "");
        }
    }

    @Override
    protected void initView() {
        reuseLv = bindView(R.id.invest_fragment_lv);
    }

    @Override
    protected int initLayout() {
        return R.layout.invest_fragment_all;
    }


}
