package com.liangduo.kr36.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.liangduo.greendao.GreendaoSingle;
import com.liangduo.greendao.User;
import com.liangduo.greendao.UserDao;
import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
import com.liangduo.kr36.bean.EventBusBean;
import com.liangduo.kr36.main.MainActivity;

import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.EventBusException;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by liangduo on 16/5/17.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private TextView loginNumTv;
    private EditText loginNumEt;
    private ImageView loginNumDelIv;

    private TextView loginPswTv;
    private EditText loginPswEt;
    private ImageView loginPswDelIv;
    private CheckBox loginPswEye;

    private ImageView qq;
    private ImageView weibo;
    private ImageView wechat;

    private TextView loginForgetPswTv;
    private TextView loginTv;

    private RegisterBroadCast registerBroadCast;

    private UserDao userDao;
    private User user;

//    private OnClickViewPagerToOne onClickViewPagerToOne;
//
//    public void setOnClickViewPagerToOne(OnClickViewPagerToOne onClickViewPagerToOne) {
//        this.onClickViewPagerToOne = onClickViewPagerToOne;
//    }


    @Override
    protected void initData() {

        loginNumEt.setVisibility(View.INVISIBLE);
        loginPswEt.setVisibility(View.INVISIBLE);
        loginNumTv.setOnClickListener(this);
        loginPswTv.setOnClickListener(this);
        loginNumDelIv.setOnClickListener(this);
        loginPswDelIv.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        loginPswEye.setOnCheckedChangeListener(this);
        qq.setOnClickListener(this);
        weibo.setOnClickListener(this);
        wechat.setOnClickListener(this);

        loginPswEt.setTransformationMethod(PasswordTransformationMethod.getInstance());

        //注册广播接收者
        registerBroadCast = new RegisterBroadCast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.liangduo.kr36.USER");
        getContext().registerReceiver(registerBroadCast, intentFilter);
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
        loginTv = bindView(R.id.login_btn);

        wechat = bindView(R.id.login_wechat);
        qq = bindView(R.id.login_qq);
        weibo = bindView(R.id.login_weibo);

        ShareSDK.initSDK(getContext());

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void onClick(View v) {

        final Intent intent = new Intent(getContext(),MainActivity.class);

        switch (v.getId()) {

            case R.id.login_num_tv:
                if (loginPswEt.getText().toString().equals("")) {
//                    TvUpAnimotion(loginNumTv);
                    loginPswTv.clearAnimation();
                    loginPswEt.setVisibility(View.INVISIBLE);

                    if (loginNumEt.getText().toString().equals("")) {
                        TvUpAnimotion(loginNumTv);
                        loginNumDelIv.setVisibility(View.VISIBLE);
                        loginNumEt.setVisibility(View.VISIBLE);
                    }
                } else {
                    loginPswDelIv.setVisibility(View.VISIBLE);
                    if (loginNumEt.getText().toString().equals("")) {
                        TvUpAnimotion(loginNumTv);
                        loginNumDelIv.setVisibility(View.VISIBLE);
                        loginNumEt.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.login_psw_tv:
                if (loginNumEt.getText().toString().equals("")) {
                    loginNumTv.clearAnimation();
                    loginNumEt.setVisibility(View.INVISIBLE);
                } else {
                    if (loginPswEt.getText().toString().equals("")) {
                        TvUpAnimotion(loginPswTv);
                        loginPswEt.setVisibility(View.VISIBLE);
                        if (loginPswEt.getText().toString().equals("")) {
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
            case R.id.login_btn:
                initDataBase();
                break;
            case R.id.login_qq:
                final Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        Log.d("LoginFragment", qq.getDb().getUserName());
                        Log.d("LoginFragment", "QQ已登录");
                        intent.putExtra("change",3);
//                        intent.putExtra("qq",qq.getDb().getUserName());
                        startActivity(intent);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });

                qq.showUser(null);
                break;
            case R.id.login_wechat:
                Toast.makeText(getContext(), "不可以哦", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_weibo:
                final Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                weibo.SSOSetting(false);  //设置false表示使用SSO授权方式
                weibo.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        Log.d("LoginFragment", weibo.getDb().getUserName());
                        //Intent传值让ViewPager划到第三页
                        intent.putExtra("change",3);
//                        intent.putExtra("weibo",weibo.getDb().getUserName());
                        startActivity(intent);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                }); // 设置分享事件回调
                weibo.authorize();
                break;
        }
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked == false) {
            loginPswEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            loginPswEt.setSelection(loginPswEt.getText().length());
        } else {
            loginPswEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            loginPswEt.setSelection(loginPswEt.getText().length());
        }
    }

    //动态接收广播
    class RegisterBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //参数里的Intent就是发送广播所使用的intent,可以从他里面拿到值
            loginNumEt.setText(intent.getStringExtra("phone"));
            loginPswEt.setText(intent.getStringExtra("psw"));
            loginPswEt.setVisibility(View.VISIBLE);
            loginNumEt.setVisibility(View.VISIBLE);
            loginNumTv.setVisibility(View.GONE);
            loginPswTv.setVisibility(View.GONE);
            Log.d("登录收到广播啦", loginPswEt.getText().toString());
        }
    }



    private void initDataBase() {

        user = new User(
                loginNumEt.getText().toString(),
                loginPswEt.getText().toString()
        );
//        collectionDao.deleteAll();
        Log.d("RegisterFragment", "最开始着呢________");
        //使用单例
        userDao = GreendaoSingle.getInstance().getUserDao();
        Log.d("Register获取一下电话号码", loginNumEt.getText().toString());
        if (hasPhone(loginNumEt.getText().toString(),loginPswEt.getText().toString())) {
            Log.d("RegisterFragment", "这里有没有走啊=============");
            Toast.makeText(getActivity(), "已登录", Toast.LENGTH_SHORT).show();

            Intent intent1 = new Intent(getContext(),MainActivity.class);
            intent1.putExtra("name",loginNumEt.getText().toString());
            startActivity(intent1);
            getActivity().finish();
        } else {
            Toast.makeText(getContext(), "用户名或密码不正确", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasPhone(String phone,String psw) {
        boolean hasPhone = false;
        List<User> users = userDao.queryBuilder().list();

        for (User user1 : users) {
            if (phone.toString().equals(user1.getNumber().toString()) && psw.toString().equals(user1.getPsw().toString())) {
                hasPhone = true;
            }
        }
        return hasPhone;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(registerBroadCast);
    }
}
