package com.liangduo.kr36.news;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liangduo.kr36.bean.NewsCycleViewBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by liangduo on 16/5/21.
 */
public class NewsAutoVpAdapter extends PagerAdapter {
    private Context context;
    private List<String> cycleUrls;//装轮播图点击后的网址的集合
    private List<String> urlList;//装图片的网址的集合

    public NewsAutoVpAdapter(Context context) {
        this.context = context;
    }

    public void setCycleUrls(List<String> cycleUrls) {
        this.cycleUrls = cycleUrls;
        notifyDataSetChanged();
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
        notifyDataSetChanged();
    }

    // 获取要滑动的控件的数量，图片的ImageView数量
    @Override
    public int getCount() {
        return 100;
    }

    // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，
    // 我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {


        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);


        container.addView(setIv(imageView, urlList.get(position % urlList.size())));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CycleActivity.class);
                intent.putExtra("url", cycleUrls.get(position % urlList.size()));

                Log.d("NewsAutoVpAdapter", cycleUrls.get(position % urlList.size()));
                context.startActivity(intent);
            }
        });
        return setIv(imageView, urlList.get(position % urlList.size()));
    }

    // PagerAdapter只缓存3张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    public ImageView setIv(ImageView imageView, String url) {
        Picasso.with(context).load(url).into(imageView);
        return imageView;
    }
}
