package com.liangduo.kr36.base;

import android.app.Application;
import android.content.Context;



import cn.jpush.android.api.JPushInterface;

/**
 * Created by liangduo on 16/5/17.
 */
public class BaseApplication extends Application {
    //Application创建原因是我们需要一个自己的大环境
    private static Context mContext;

    //第一个生命周期 我们对context赋值
    @Override
    public void onCreate() {
        super.onCreate();
        //this代表当前的环境
        mContext = this;

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    //对外提供一个方法这个方法就是让别的类获取自己的context对象
    public static Context getContext(){
        return mContext;
    }


}
