package com.liangduo.kr36.login;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;

/**
 * Created by liangduo on 16/5/17.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private TextView loginNumTv;
    private EditText loginNumEt;
    private ImageView loginNumDelIv;

    private TextView loginPswTv;
    private EditText loginPswEt;
    private ImageView loginPswDelIv;
    private CheckBox loginPswEye;

    private TextView loginForgetPswTv;



    @Override
    protected void initData() {
        loginNumEt.setVisibility(View.INVISIBLE);
        loginPswEt.setVisibility(View.INVISIBLE);
        loginNumTv.setOnClickListener(this);
        loginPswTv.setOnClickListener(this);
        loginNumDelIv.setOnClickListener(this);
        loginPswDelIv.setOnClickListener(this);

        loginPswEt.setTransformationMethod(PasswordTransformationMethod.getInstance());

        loginPswEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == false){
                    loginPswEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    loginPswEt.setSelection(loginPswEt.getText().length());
                }else {
                    loginPswEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    loginPswEt.setSelection(loginPswEt.getText().length());
                }
            }
        });

    }

    @Override
    protected void initView() {
        loginNumTv = bindView(R.id.login_num_tv);
        loginNumEt = bindView(R.id.login_num_et);
        loginNumDelIv = bindView(R.id.login_num_del);

        loginPswTv = bindView(R.id.login_psw_tv);
        loginPswEt = bindView(R.id.login_psw_et);
        loginPswDelIv = bindView(R.id.login_psw_del);
        loginPswEye = bindView(R.id.login_eye);

        loginForgetPswTv = bindView(R.id.login_forget_psw_tv);
    }
    @Override
    protected int initLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.login_num_tv:
                if (loginPswEt.getText().toString().equals("")){
//                    TvUpAnimotion(loginNumTv);
                    loginPswTv.clearAnimation();
                    loginPswEt.setVisibility(View.INVISIBLE);

                    if (loginNumEt.getText().toString().equals("")){
                        TvUpAnimotion(loginNumTv);
                        loginNumDelIv.setVisibility(View.VISIBLE);
                        loginNumEt.setVisibility(View.VISIBLE);
                    }
                }else{
                    loginPswDelIv.setVisibility(View.VISIBLE);
                    if (loginNumEt.getText().toString().equals("")){
                        TvUpAnimotion(loginNumTv);
                        loginNumDelIv.setVisibility(View.VISIBLE);
                        loginNumEt.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.login_psw_tv:
                if (loginNumEt.getText().toString().equals("")){
                    loginNumTv.clearAnimation();
                    loginNumEt.setVisibility(View.INVISIBLE);
                }else {
                    if (loginPswEt.getText().toString().equals("")){
                        TvUpAnimotion(loginPswTv);
                        loginPswEt.setVisibility(View.VISIBLE);
                        if (loginPswEt.getText().toString().equals("")){
                            loginPswDelIv.setVisibility(View.VISIBLE);
                        }
                    }
                }

                break;
            case R.id.login_num_del:
                loginNumEt.setText("");
                break;
            case R.id.login_psw_del:
                loginPswEt.setText("");
                break;
        }
    }

    private void TvUpAnimotion(TextView view) {
        AnimationSet animationSet = new AnimationSet(true);

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
                        Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_PARENT,-0.4f);//只是比例,不是数值
        translateAnimation.setDuration(100);
        //设置重复播放的方式倒叙,
        //REVERSE会在重复时播放,进行倒叙播放
//        translateAnimation.setRepeatMode(Animation.REVERSE);

        animationSet.addAnimation(translateAnimation);
        //设置动画停在结束时的位置
        animationSet.setFillAfter(true);

        view.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("LoginFragment", "---------start");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d("LoginFragment", "--------replace");
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getContext(), "结束", Toast.LENGTH_SHORT).show();
                Log.d("LoginFragment", "------end");

            }

        });



    }
}
