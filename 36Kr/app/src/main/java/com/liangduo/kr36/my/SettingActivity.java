package com.liangduo.kr36.my;

import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseActivity;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by liangduo on 16/6/3.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private TextView title;
    private ImageView search;
    private ImageView back;
    private TextView quit;
    private Switch aSwitch;


    @Override
    protected void initView() {
        title = bindView(R.id.title_tv);
        search = bindView(R.id.title_search_iv);
        back = bindView(R.id.title_navigation_iv);
        quit = bindView(R.id.setting_quite_item);
        aSwitch = bindView(R.id.setting_switch);
    }

    @Override
    protected void initData() {

        title.setText("设置");
        search.setVisibility(View.GONE);
        back.setImageResource(R.mipmap.news_toolbar_icon_back);
        back.setOnClickListener(this);
        quit.setOnClickListener(this);
        aSwitch.setOnCheckedChangeListener(this);


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.setting_quite_item:
                Platform qq = ShareSDK.getPlatform(this, QQ.NAME);
                Platform weibo = ShareSDK.getPlatform(this, SinaWeibo.NAME);
                //如果qq登陆了就取消登录
                if (qq.isValid()){
                    qq.removeAccount();
                    Toast.makeText(this, "qq已经取消登录", Toast.LENGTH_SHORT).show();
                    //如果微博登录了就取消微博登录
                }else if(weibo.isValid()){
                    weibo.removeAccount();
                    Toast.makeText(this, "微博已经取消登录", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "还未登陆", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.title_navigation_iv:
                finish();
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            Toast.makeText(this, "Switch状态为开", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Switch状态为关", Toast.LENGTH_SHORT).show();
        }
    }
}
