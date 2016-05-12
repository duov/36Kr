package com.liangduo.kr36.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by liangduo on 16/5/9.
 */
public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());//加载布局方法
        initView();//加载组件
        initData();//加载数据
    }

    /**
     * 加载组件的方法
     */
    protected abstract void initView();

    /**
     * 加载数据的方法
     */
    protected abstract void initData();

    /**
     * 这是加载布局的方法
     */
    protected abstract int getLayout();

    /**
     * 此方法使组件实例化不需要转型
     *
     * 使用方法
     * TextView textView = bindView(R.id.tv);
     * 这样使用这个方法的时候是不需要强转的
     */

    protected <T extends View> T bindView(int id){
        return (T) findViewById(id);
    }


}
