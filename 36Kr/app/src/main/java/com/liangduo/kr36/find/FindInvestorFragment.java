package com.liangduo.kr36.find;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
import com.liangduo.kr36.bean.EarlyBean;
import com.liangduo.kr36.bean.FindInvestorBean;
import com.liangduo.kr36.tool.DrawableCircle;
import com.liangduo.kr36.tool.GsonRequest;

/**
 * Created by liangduo on 16/5/15.
 */
public class FindInvestorFragment extends BaseFragment {
    private FindInvestorAdapter findInvestorAdapter;
    private FindInvestorBean findInvestorBean;
    private ListView investorLv;
    private ImageView headIv;

    @Override
    protected void initData() {
        analysisData();
        findInvestorAdapter = new FindInvestorAdapter(getActivity());
        investorLv.setAdapter(findInvestorAdapter);


    }

    @Override
    protected void initView() {
        investorLv = bindView(R.id.fragment_content_find_find_investor_lv);
        headIv = bindView(R.id.item_lv_find_find_investor_head_iv);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_content_find_find_investor;
    }

    private void analysisData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        GsonRequest<FindInvestorBean> gsonRequest = new GsonRequest<>(Request.Method.GET
                , "https://rong.36kr.com/api/mobi/investor?page=1&pageSize=20"
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("--------", "没解析");
            }
        }, new Response.Listener<FindInvestorBean>() {
            @Override
            public void onResponse(FindInvestorBean response) {
                findInvestorAdapter.setFindInvestorBean(response);

                Log.d("6666666666", "response.getData().getPageSize():" + response.getData().getPageSize());
            }
        },FindInvestorBean.class);
        requestQueue.add(gsonRequest);
    }
}
