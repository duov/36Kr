package com.liangduo.kr36.my;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
import com.liangduo.kr36.tool.CircleTransform;
import com.squareup.picasso.Picasso;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

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
    private TextView nameTv;
    private ImageView head;


    @Override
    protected void initData() {
        ShareSDK.initSDK(getContext());

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
        nameTv.setOnClickListener(this);

        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        if (qq.isValid()) {
            Log.d("NameBroadcast", "222");
            nameTv.setText(qq.getDb().getUserName());
            Picasso.with(getContext()).load(qq.getDb().getUserIcon()).transform(new CircleTransform()).into(head);
        }
        if (weibo.isValid()) {
            nameTv.setText(weibo.getDb().getUserName());
            Picasso.with(getContext()).load(weibo.getDb().getUserIcon()).transform(new CircleTransform()).into(head);
        }
    }

    //当页面重新被打开时设置他的内容
    @Override
    public void onStart() {
        super.onStart();
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        if ((!qq.isValid()) && (!weibo.isValid())) {
            nameTv.setText("请填写信息");
            head.setImageResource(R.mipmap.common_avatar);
        }
    }

    @Override
    protected void initView() {
        head = bindView(R.id.my_avatar);
        nameTv = bindView(R.id.my_do_not_finish_tv);
        settingIv = bindView(R.id.my_setting);
        myData = bindView(R.id.my_data);
        myMessage = bindView(R.id.my_message);
        myOrder = bindView(R.id.my_order);
        myAccount = bindView(R.id.my_account_item);
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
        switch (v.getId()) {
            case R.id.my_setting:
                Intent intent1 = new Intent(getContext(), SettingActivity.class);
                startActivity(intent1);
                break;
            case R.id.my_data:
                Toast.makeText(getActivity(), "点击1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_message:
                Toast.makeText(getActivity(), "去点击我收藏的文章!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_order:
                Toast.makeText(getActivity(), "去点击我收藏的文章!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_account_item:
                Toast.makeText(getActivity(), "去点击我收藏的文章!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_prove_item:
                Toast.makeText(getActivity(), "去点击我收藏的文章!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_collection_item:
                Intent intent = new Intent(getActivity(), MyReuseActivity.class);
                intent.putExtra("title", "我收藏的文章");
                startActivity(intent);
                break;
            case R.id.my_invent_company_item:
                Toast.makeText(getActivity(), "去点击我收藏的文章!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_invent_paper_item:
                Toast.makeText(getActivity(), "去点击我收藏的文章!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_understand_item:
                Toast.makeText(getActivity(), "去点击我收藏的文章!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_hotline_item:
                Toast.makeText(getActivity(), "去点击我收藏的文章!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_do_not_finish_tv:
                Toast.makeText(getContext(), "idandiandiadlashf;lkshf", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
