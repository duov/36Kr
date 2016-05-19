package com.liangduo.kr36.my;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;

/**
 * Created by liangduo on 16/5/9.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {
    private ImageView settingIv;//设置
    private RelativeLayout myData;//我的信息
    private LinearLayout myMessage;//我的消息
    private LinearLayout myOrder;//我的订单
    private LinearLayout myAccount;//账号信息
    private LinearLayout myProve;//跟投人认证
    private LinearLayout myCollection;//我的收藏
    private LinearLayout myInventCompany;//我投资的公司
    private LinearLayout myInventPaper;//我的投资劵
    private LinearLayout myUnderstand;//了解股权投资
    private LinearLayout myHotline;//热线


    @Override
    protected void initData() {
        settingIv.setOnClickListener(this);
        myData.setOnClickListener(this);
        myMessage.setOnClickListener(this);
        myOrder.setOnClickListener(this);
        myAccount.setOnClickListener(this);
        myProve.setOnClickListener(this);
        myCollection.setOnClickListener(this);
        myInventCompany.setOnClickListener(this);
        myInventPaper.setOnClickListener(this);
        myUnderstand.setOnClickListener(this);
        myHotline.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        settingIv = bindView(R.id.my_setting);
        myData = bindView(R.id.my_data);
        myMessage = bindView(R.id.my_message);
        myOrder = bindView(R.id.my_order);
        myAccount= bindView(R.id.my_account_item);
        myProve = bindView(R.id.my_prove_item);
        myCollection = bindView(R.id.my_collection_item);
        myInventCompany = bindView(R.id.my_invent_company_item);
        myInventPaper = bindView(R.id.my_invent_paper_item);
        myUnderstand = bindView(R.id.my_understand_item);
        myHotline = bindView(R.id.my_hotline_item);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_my;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_setting:
                Toast.makeText(getContext(), "点击0", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_data:
                Toast.makeText(getContext(), "点击1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_message:
                Toast.makeText(getContext(), "点击2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_order:
                Toast.makeText(getContext(), "点击3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_account_item:
                Toast.makeText(getContext(), "点击4", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_prove_item:
                Toast.makeText(getContext(), "点击5", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_collection_item:
                Toast.makeText(getContext(), "点击6", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_invent_company_item:
                Toast.makeText(getContext(), "点击7", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_invent_paper_item:
                Toast.makeText(getContext(), "点击8", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_understand_item:
                Toast.makeText(getContext(), "点击9", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_hotline_item:
                Toast.makeText(getContext(), "点击10", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
