package com.liangduo.kr36.login;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
/**
 * Created by liangduo on 16/5/17.
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener {
    private AnimationSet animationSet = new AnimationSet(true);
    private TextView registerPhoneNum;
    private EditText registerPhoneEt;
    private ImageView registerDel;
    private TextView registerCountryNum;



    @Override
    protected void initData() {
        registerPhoneNum.setOnClickListener(this);
        registerDel.setOnClickListener(this);
        registerCountryNum.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        registerPhoneNum = bindView(R.id.register_phone_number_tv);
        registerPhoneEt = bindView(R.id.register_phone_number_et);
        registerDel  = bindView(R.id.register_num_del);
        registerCountryNum =bindView(R.id.register_country_num_tv);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_register;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_phone_number_tv:
                TvUpAnimotion(registerPhoneNum);
                registerPhoneEt.setVisibility(View.VISIBLE);
                registerDel.setVisibility(View.VISIBLE);
                break;
            case R.id.register_num_del:
                registerPhoneEt.setText("");
                break;
            case R.id.register_country_num_tv:

                break;
        }
    }

    private void TvUpAnimotion(View view) {
//        缩小
        ScaleAnimation scaleAnimation = new ScaleAnimation(1,0.6f,1,0.6f,
                //前两个参数 x轴缩放多少, 3-4各参数是 y轴缩放多少-----都是倍数
                Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,1f);
        //设置每次动画的持续时间
        scaleAnimation.setDuration(100);

        animationSet.addAnimation(scaleAnimation);


        //平移
        TranslateAnimation translateAnimation =new TranslateAnimation
                (Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_PARENT,-0.5f);//只是比例,不是数值
        translateAnimation.setDuration(100);
        //设置重复播放的方式倒叙,
        //REVERSE会在重复时播放,进行倒叙播放
        translateAnimation.setRepeatMode(Animation.REVERSE);

        animationSet.addAnimation(translateAnimation);
        //设置动画停在结束时的位置
        animationSet.setFillAfter(true);

        view.startAnimation(animationSet);
    }
}
