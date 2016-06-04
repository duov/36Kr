package com.liangduo.kr36.news;

import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseActivity;

/**
 * Created by liangduo on 16/5/23.
 */
public class CycleActivity extends BaseActivity {
    private WebView cycleWebView;

    @Override
    protected void initView() {
        cycleWebView = bindView(R.id.cycle_web_view);
    }

    @Override
    protected void initData() {
//        cycleWebView.getSettings().setBuiltInZoomControls(false);
        Intent intent = getIntent();
        String url =intent.getStringExtra("url");
        cycleWebView.getSettings().setJavaScriptEnabled(true);

        // 只让本应用程序的webview加载网页，而不调用外部浏览器打开的办法就是：
        // 设置WebViewClient，并重写WebViewClient的shouldOverrideUrlLoading方法返回true
        cycleWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }
        });

        cycleWebView.loadUrl(url);


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_cycle;
    }
}
