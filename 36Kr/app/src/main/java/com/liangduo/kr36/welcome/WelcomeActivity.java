package com.liangduo.kr36.welcome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseActivity;
import com.liangduo.kr36.main.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by liangduo on 16/5/31.
 */
public class WelcomeActivity extends BaseActivity {
    private ImageView welcomeIv;
    private TextView timeTv;
    private CountDownTimer time;

    @Override
    protected void initView() {
        welcomeIv = bindView(R.id.welcome_iv);
        timeTv = (TextView) findViewById(R.id.welcome_aty_time_tv);
    }

    @Override
    protected void initData() {

        time = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeTv.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                timeTv.setText("跳转");
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }.start();

        timeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.cancel();//跳转的时候,取消time
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
