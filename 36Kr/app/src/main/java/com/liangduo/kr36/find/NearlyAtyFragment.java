package com.liangduo.kr36.find;

import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
import com.liangduo.kr36.bean.NearlyAtyBean;
import com.liangduo.kr36.tool.VolleySingle;

/**
 * Created by liangduo on 16/5/15.
 */
public class NearlyAtyFragment extends BaseFragment {
    private ListView nearlyLv;
    private NearlyAtyAdapter nearlyAtyAdapter;
    @Override
    protected void initData() {
        nearlyAtyAdapter = new NearlyAtyAdapter(getContext());
        initVolley();
        nearlyLv.setAdapter(nearlyAtyAdapter);
    }

    @Override
    protected void initView() {
        nearlyLv = bindView(R.id.fragment_content_find_nearly_lv);

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_content_find_nearlyaty;
    }

    private void initVolley() {
        VolleySingle.addRequest("https://rong.36kr.com/api/mobi/activity/list?page=1",
                NearlyAtyBean.class,new Response.Listener<NearlyAtyBean>(){
            @Override
            public void onResponse(NearlyAtyBean response) {
                Log.d("NearlyAtyFragment", "Nearly已经获得了网络数据");

                nearlyAtyAdapter.setNearlyAtyBeen(response);

                Log.d("NearlyAtyFragment", "response:已经有啦" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }
}
