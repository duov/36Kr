package com.liangduo.kr36.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liangduo.greendao.GreendaoSingle;
import com.liangduo.greendao.User;
import com.liangduo.greendao.UserDao;
import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;

import java.util.List;

/**
 * Created by liangduo on 16/5/17.
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener {
    private AnimationSet animationSet = new AnimationSet(true);//动画
    private TextView registerPhoneNumTv;//提示输入电话号码的文字
    private EditText registerPhoneEt;//输入电话号码的EditText
    private ImageView registerPhoneDel;//一键删除EditText中所有内容的×
    private TextView registerPswTv;//提示输入密码的文字
    private EditText registerPswEt;//输入的密码
    private ImageView registerPswDel;//一键删除密码EditText中的内容
    private TextView ensureRegister;//确认注册并登录
    private UserDao userDao;
    private User user;
    private OnClickViewPagerToOne onClickViewPagerToOne;

    public void setOnClickViewPagerToOne(OnClickViewPagerToOne onClickViewPagerToOne) {
        this.onClickViewPagerToOne = onClickViewPagerToOne;
    }

    @Override
    protected void initData() {
        registerPhoneNumTv.setOnClickListener(this);//提示的监听
        registerPhoneDel.setOnClickListener(this);//清空的监听
        registerPswTv.setOnClickListener(this);//密码提示的监听
        registerPswDel.setOnClickListener(this);//清空密码的监听
        ensureRegister.setOnClickListener(this);

    }

    @Override
    protected void initView() {
        registerPhoneNumTv = bindView(R.id.register_phone_number_tv);
        registerPhoneEt = bindView(R.id.register_phone_number_et);
        registerPhoneDel = bindView(R.id.register_num_del);
        registerPswTv = bindView(R.id.register_psw_tv);
        registerPswEt = bindView(R.id.register_psw_et);
        registerPswDel = bindView(R.id.register_psw_del);
        ensureRegister = bindView(R.id.register_get_code_btn);

    }




    @Override
    protected int initLayout() {
        return R.layout.fragment_register;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击注册的电话号码
            case R.id.register_phone_number_tv:
                //判断输入的密码是不是空
                if (registerPswEt.getText().toString().equals("")) {
                    //如果密码是空,ba密码提示的TextView取消动画
                    registerPswTv.clearAnimation();
                    //设置密码的EditText为隐藏
                    registerPswEt.setVisibility(View.INVISIBLE);
                    //如果无电话号输入
                    if (registerPhoneEt.getText().toString().equals("")) {
                        //动画
                        TvUpAnimotion(registerPhoneNumTv);
                        //一键删除和电话号的EditText都设为可见
                        registerPhoneDel.setVisibility(View.VISIBLE);
                        registerPhoneEt.setVisibility(View.VISIBLE);
                    }
                } else {
                    registerPswDel.setVisibility(View.VISIBLE);
                    if (registerPhoneEt.getText().toString().equals("")) {
                        TvUpAnimotion(registerPhoneNumTv);
                        registerPhoneDel.setVisibility(View.VISIBLE);
                        registerPhoneEt.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.register_psw_tv:
                if (registerPhoneEt.getText().toString().equals("")) {
                    registerPhoneNumTv.clearAnimation();
                    registerPhoneEt.setVisibility(View.INVISIBLE);
                } else if (registerPhoneEt.getText().toString().length() < 11) {
                    Toast.makeText(getActivity(), "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                } else {
                    if (registerPswEt.getText().toString().equals("")) {
                        TvUpAnimotion(registerPswTv);
                        registerPswEt.setVisibility(View.VISIBLE);
                        if (registerPswEt.getText().toString().equals("")) {
                            registerPswDel.setVisibility(View.VISIBLE);
                        }
                    }
                }
                break;
            case R.id.register_num_del:
                registerPhoneEt.setText("");
                break;
            case R.id.register_psw_del:
                registerPswEt.setText("");
                break;
            case R.id.register_get_code_btn:
                if (registerPhoneEt.getText().toString().length() > 0 && registerPswEt.getText().toString().length() > 0) {
                    initDataBase();
                }else {
                    Toast.makeText(getActivity(), "请输入同户名和密码", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void initDataBase() {

        user = new User(
                registerPhoneEt.getText().toString(),
                registerPswEt.getText().toString()
        );
//        collectionDao.deleteAll();
        Log.d("RegisterFragment", "最开始着呢________");
        //使用单例
        userDao = GreendaoSingle.getInstance().getUserDao();
        Log.d("Register获取一下电话号码", registerPhoneEt.getText().toString());
        if (hasPhone(registerPhoneEt.getText().toString())) {
            Log.d("RegisterFragment", "这里有没有走啊=============");
            Toast.makeText(getActivity(), "这个电话号码已经注册过啦", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("RegisterFragment", "这里呢++++++");
            Toast.makeText(getActivity(), "注册完成啦!主银~", Toast.LENGTH_SHORT).show();
            userDao.insert(user);
            //发送广播.把注册的信息传给登录的Fragment
            Intent intent = new Intent("com.liangduo.kr36.USER");
            intent.putExtra("phone",registerPhoneEt.getText().toString());
            intent.putExtra("psw",registerPswEt.getText().toString());
            getContext().sendBroadcast(intent);

            onClickViewPagerToOne.toOne();
        }
    }

    private boolean hasPhone(String phone) {
        boolean hasPhone = false;
        List<User> users = userDao.queryBuilder().list();

        for (User user1 : users) {
            if (phone.toString().equals(user1.getNumber().toString())) {
                hasPhone = true;
            }
        }
        return hasPhone;
    }

    private void TvUpAnimotion(TextView view) {
        AnimationSet animationSet = new AnimationSet(true);

//        缩小
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.6f, 1, 0.6f,
                //前两个参数 x轴缩放多少, 3-4各参数是 y轴缩放多少-----都是倍数
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        //设置每次动画的持续时间
        scaleAnimation.setDuration(100);

        animationSet.addAnimation(scaleAnimation);

        //平移
        TranslateAnimation translateAnimation = new TranslateAnimation
                (Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_PARENT, -0.4f);//只是比例,不是数值
        translateAnimation.setDuration(100);
        //设置重复播放的方式倒叙,
        //REVERSE会在重复时播放,进行倒叙播放
//        translateAnimation.setRepeatMode(Animation.REVERSE);

        animationSet.addAnimation(translateAnimation);
        //设置动画停在结束时的位置
        animationSet.setFillAfter(true);
        view.startAnimation(animationSet);
    }



}
