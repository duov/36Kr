package com.liangduo.kr36.find;

import android.widget.TextView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;

/**
 * Created by liangduo on 16/5/15.
 */
public class Research36Fragment extends BaseFragment {
    private TextView textView;
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        textView = bindView(R.id.text);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_content_find_research36;
    }
}
