package com.liangduo.kr36.drawer;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
import com.liangduo.kr36.bean.KrTVBean;
import com.liangduo.kr36.tool.VolleySingle;

/**
 * Created by liangduo on 16/5/14.
 */
public class KrTVFragment extends BaseFragment  {

    private KrTVAdapter krTVAdapter;
    private ListView krTVListView;
    private KrTVBean krTVBean;
    private VideoView mVideoView;

    private int currentIndex = -1;
    private int playPosition = -1;
    private boolean isPaused = false;
    private boolean isPlaying = false;
    private MediaController mMediaCtrl;


    @Override
    protected void initData() {

        krTVAdapter = new KrTVAdapter(getActivity());
        krTVListView.setAdapter(krTVAdapter);



        VolleySingle.addRequest("https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=tv&pagingAction=up",
                KrTVBean.class, new Response.Listener<KrTVBean>() {
                    @Override
                    public void onResponse(KrTVBean response) {
                        krTVBean = response;
                        krTVAdapter.setKrTVBean(response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
//        krTVListView.setOnScrollListener(this);
    }

    @Override
    protected void initView() {
        krTVListView = bindView(R.id.fragment_krtv_lv);
        mVideoView = bindView(R.id.item_krtv_vv);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_krtv;
    }

//    /**
//     * 这是监听ListView滑动复写的方法
//     * @param view
//     * @param scrollState
//     */
//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//    }
//
//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        //当这个游标小于第一个可见的item 或者 这个游标大于可见的最后一个ListView的item  并且他在播放
//        Log.d("KrTVFragment", "这里走没走");
//        if ((currentIndex < firstVisibleItem || currentIndex > krTVListView.getLastVisiblePosition()) && isPlaying) {
//            playPosition = mVideoView.getCurrentPosition();
//            //暂停播放
//            mVideoView.pause();
////            mVideoView.setMediaController(null);
//
//            Log.d("KrTVFragment", "这里走没了如果记录双方的基本功蓝色的福建广播lsdfjglsd走");
//            isPaused = true;
//            isPlaying = false;
//            System.out.println("视频已经暂停：" + playPosition);
//        }
//    }
}
