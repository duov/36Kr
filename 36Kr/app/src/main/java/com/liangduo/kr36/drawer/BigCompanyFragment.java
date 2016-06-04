package com.liangduo.kr36.drawer;

import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
import com.liangduo.kr36.bean.AfterBBean;
import com.liangduo.kr36.tool.GsonRequest;

/**
 * Created by liangduo on 16/5/28.
 *
 *  大公司
 */
public class BigCompanyFragment extends BaseFragment {
    private BItemAdapter bItemAdapter;
    private ListView planetLv;
    private AfterBBean afterBBean;

    @Override
    protected void initData() {

        analysisData();
        bItemAdapter = new BItemAdapter(getActivity());
        planetLv.setAdapter(bItemAdapter);
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
        GsonRequest<AfterBBean> gsonRequest = new GsonRequest<>(Request.Method.GET
                , "https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=23&pagingAction=up"
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("--------", "没解析"+error);
            }
        }, new Response.Listener<AfterBBean>() {
            @Override
            public void onResponse(AfterBBean response) {
                Log.d("BigCompanyFragment", "走了这个解析了没?");

                bItemAdapter.setAfterBBean(response);
                Log.d("^^^^^^^^^^^", "response.getData().getPageSize():" + response.getData().getPageSize());
            }
        },AfterBBean.class);
        requestQueue.add(gsonRequest);
    }
}
