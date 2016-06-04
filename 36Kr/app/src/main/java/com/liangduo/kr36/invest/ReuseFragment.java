package com.liangduo.kr36.invest;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
import com.liangduo.kr36.bean.InvestAllBean;
import com.liangduo.kr36.bean.InvestBean;
import com.liangduo.kr36.tool.VolleySingle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangduo on 16/5/13.
 */
public class ReuseFragment extends BaseFragment {
    private ListView reuseLv;
    private ReuseAdapter reuseAdapter;
    private InvestAllBean investAllBean;
    private String[] investNameList = {"all","underway","raise","finish"};
    private static final String TAG_POSITION = "fragmentPositionTag";
    int position ;



    @Override
    protected int initLayout() {
        return R.layout.invest_fragment_all;
    }

    @Override
    protected void initView() {
        reuseLv = bindView(R.id.invest_fragment_lv);

        Bundle args = getArguments();
        position = args.getInt(TAG_POSITION);
    }

    @Override
    protected void initData() {
        initVollley();
    }

    private void initVollley() {

        VolleySingle.addRequest("https://rong.36kr.com/api/mobi/cf/actions/list?page=1&type=" + investNameList[position] + "&pageSize=20",
                InvestAllBean.class, new Response.Listener<InvestAllBean>() {
                    @Override
                    public void onResponse(InvestAllBean response) {
                        Log.d("ReuseFragment", "复用Bean已经解析好了数据");
                        reuseAdapter = new ReuseAdapter(getActivity());
                        reuseAdapter.setInvestAllBeen(response);
                        reuseLv.setAdapter(reuseAdapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }



    /**
     * 静态工厂方法需要一个int型的值来初始化fragment的参数，
     * 然后返回新的fragment到调用者
     *
     * newInstance()方法是一种“静态工厂方法",让我们在初始化和设置一个新的fragment的时候省去调用它的构造函数和额外的setter方法
     * 为你的Fragment提供静态工厂方法是一种好的做法,因为它封装和抽象了在客户端构造对象所需的步骤。
     */

    //Fragment的实例化
 //   复用Fragment复写该方法,并将Fragment进行编号
    public static ReuseFragment newInstance(int position) {
        Bundle args = new Bundle();
        //在这里只需 给bundle赋值 ( key_value的形式)
        args.putInt(TAG_POSITION,position);
        ReuseFragment fragment = new ReuseFragment();
        fragment.setArguments(args);
        return fragment;

        //使用静态工厂方法，将外部传入的参数可以通过Fragment.setArgument保存在它自己身上，
        // 这样我们可以在Fragment.onCreate(...)调用的时候将这些参数取出来。
    }












}
