package com.liangduo.kr36.find;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
import com.liangduo.kr36.bean.NearlyAtyBean;
import com.liangduo.kr36.tool.VolleySingle;

/**
 * Created by liangduo on 16/5/15.
 * 发现界面近期活动
 */
public class NearlyAtyFragment extends BaseFragment implements View.OnClickListener {

    private ListView nearlyLv;
    private NearlyAtyAdapter nearlyAtyAdapter;
    private NearlyAtyBean nearlyAtyBean;

    private LinearLayout atyType;//活动时间
    private LinearLayout atyTime;//活动类型

    private PopupWindow timePopupWindow;//点击活动时间的popupWindow
    private PopupWindow typePopupwindow;//点击活动类型的popupWindow

    private ImageView line;//这是一条分割线,让pop出现它的下面

    private TextView time;
    private TextView type;

    private boolean showTypePop = false ;//设置PopupWindow的默认状态是关闭的

    @Override
    protected void initData() {
        //popupWindow
        typeShowPopupWindow();
        timeShowPopupWindow();

        nearlyAtyAdapter = new NearlyAtyAdapter(getActivity());
        initVolley();
        nearlyLv.setAdapter(nearlyAtyAdapter);

        atyType.setOnClickListener(this);
        atyTime.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        nearlyLv = bindView(R.id.fragment_content_find_nearly_lv);
        atyType = bindView(R.id.find_nearly_activity_type);
        atyTime = bindView(R.id.find_nearly_activity_time);

        line = bindView(R.id.fragment_content_find_nearly_line);

        time = bindView(R.id.find_nearly_activity_time_textview);
        type = bindView(R.id.find_nearly_activity_type_textview);
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
                Log.d("NearlyAtyFragment", "1");
                nearlyAtyAdapter.setNearlyAtyBeen(response);

                Log.d("NearlyAtyFragment", "response:已经有啦" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.find_nearly_activity_time:
                //活动时间
                if ( typePopupwindow.isShowing()){
                    typePopupwindow.dismiss();
                    timePopupWindow.showAsDropDown(line);
                }else if (timePopupWindow.isShowing()){
                    timePopupWindow.dismiss();
                }else {
                    timePopupWindow.showAsDropDown(line);
                }
                break;
            case R.id.find_nearly_activity_type:
                //活动类型
                if ( timePopupWindow.isShowing()) {
                    timePopupWindow.dismiss();
                    typePopupwindow.showAsDropDown(line);
                }else if(typePopupwindow.isShowing()){
                    typePopupwindow.dismiss();
                }else {
                    typePopupwindow.showAsDropDown(line);
                }
                break;
            case R.id.find_nearly_aty_pop_frame_tv:
                typePopupwindow.dismiss();
                break;
            case R.id.time_find_nearly_aty_pop_frame_tv:
                timePopupWindow.dismiss();
                break;
            case R.id.find_nearly_aty_type_all:
                initVolley();
                type.setText("全部");
                type.setTextColor(Color.parseColor("#5d87f6"));
                typePopupwindow.dismiss();
                break;
            case R.id.find_nearly_aty_type_demo_day:
                initVolleyOther("1");
                type.setText("Demo Day");
                break;
            case R.id.find_nearly_aty_type_kr_space:
                initVolleyOther("4");
                type.setText("氪空间");
                break;
            case R.id.find_nearly_aty_type_invest:
                initVolleyOther("5");
                type.setText("股权投资");
                break;
            case R.id.find_nearly_aty_type_service:
                initVolleyOther("6");
                type.setText("企业服务");
                break;
            case R.id.find_nearly_aty_type_quick:
                initVolleyOther("7");
                type.setText("极速融资");
                break;
        }
    }

    private void initVolleyOther(String url) {
        //快速定位.每一次换ListView中的内容都让他返回到第一条
        nearlyLv.setSelection(0);
        VolleySingle.addRequest("https://rong.36kr.com/api/mobi/activity/list?page=1&categoryId="+url+"&pageSize=20",
                NearlyAtyBean.class,new Response.Listener<NearlyAtyBean>(){
                    @Override
                    public void onResponse(NearlyAtyBean response) {
                        Log.d("NearlyAtyFragment", "2");
                        nearlyAtyAdapter.setNearlyAtyBeen(response);
                        Log.d("NearlyAtyFragment", "response:已经有啦&&&&&&&&&" + response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        type.setTextColor(Color.parseColor("#5d87f6"));
        typePopupwindow.dismiss();
    }

    private void timeShowPopupWindow() {
        timePopupWindow  = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.popup_nearly_aty_time,null);
        //活动时间
        TextView timeAll = (TextView) v.findViewById(R.id.find_nearly_aty_time_all);
        TextView timeWeekend = (TextView) v.findViewById(R.id.find_nearly_aty_time_weekend);
        TextView timeWeek = (TextView) v.findViewById(R.id.find_nearly_aty_time_week);
        TextView timeNextWeek = (TextView) v.findViewById(R.id.find_nearly_aty_time_next_week);
        //占位
        TextView frameTimeTv = (TextView) v.findViewById(R.id.time_find_nearly_aty_pop_frame_tv);

        frameTimeTv.setOnClickListener(this);

        timePopupWindow.setContentView(v);

        timeAll.setOnClickListener(this);
        timeNextWeek.setOnClickListener(this);
        timeWeek.setOnClickListener(this);
        timeWeekend.setOnClickListener(this);

    }


    private void typeShowPopupWindow() {
        typePopupwindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.popup_nearly_aty_type,null);
        //活动类型
        TextView typeAll = (TextView) v.findViewById(R.id.find_nearly_aty_type_all);
        TextView typeDomo = (TextView) v.findViewById(R.id.find_nearly_aty_type_demo_day);
        TextView typeKr = (TextView) v.findViewById(R.id.find_nearly_aty_type_kr_space);
        TextView typeInvest = (TextView) v.findViewById(R.id.find_nearly_aty_type_invest);
        TextView typeService = (TextView) v.findViewById(R.id.find_nearly_aty_type_service);
        TextView typeQuick = (TextView) v.findViewById(R.id.find_nearly_aty_type_quick);

        //占位
        TextView frameTv = (TextView) v.findViewById(R.id.find_nearly_aty_pop_frame_tv);

        frameTv.setOnClickListener(this);

        typePopupwindow.setContentView(v);

        typeAll.setOnClickListener(this);
        typeDomo.setOnClickListener(this);
        typeKr.setOnClickListener(this);
        typeInvest.setOnClickListener(this);
        typeService.setOnClickListener(this);
        typeQuick.setOnClickListener(this);

    }
}
