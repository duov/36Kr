package com.liangduo.kr36.drawer;

import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
import com.liangduo.kr36.bean.EarlyBean;
import com.liangduo.kr36.drawer.EarlyItemAdapter;
import com.liangduo.kr36.tool.GsonRequest;

/**
 * Created by liangduo on 16/5/11.
 */
public class EarlyItemFragment extends BaseFragment {
    private EarlyItemAdapter earlyItemAdapter;
    private ListView planetLv;
    private EarlyBean earlyBean;

    @Override
    protected void initData() {
        analysisData();
        earlyItemAdapter = new EarlyItemAdapter(getActivity());
        planetLv.setAdapter(earlyItemAdapter);
    }

    @Override
    protected void initView() {
        planetLv = bindView(R.id.early_fragment_lv);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_early;
    }

    private void analysisData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        GsonRequest<EarlyBean> gsonRequest = new GsonRequest<>(Request.Method.GET
                , "https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=67&pagingAction=up"
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("--------", "没解析");
            }
        }, new Response.Listener<EarlyBean>() {
            @Override
            public void onResponse(EarlyBean response) {
                earlyItemAdapter.setEarlyBean(response);
                Log.d("^^^^^^^^^^^", "response.getData().getPageSize():" + response.getData().getPageSize());
            }
        },EarlyBean.class);
        requestQueue.add(gsonRequest);
    }
}
