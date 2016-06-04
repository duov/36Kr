package com.liangduo.kr36.news;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.liangduo.greendao.Collection;
import com.liangduo.greendao.CollectionDao;
import com.liangduo.greendao.DaoMaster;
import com.liangduo.greendao.DaoSession;
import com.liangduo.greendao.GreendaoSingle;
import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseActivity;
import com.liangduo.kr36.bean.AuthorInforBean;
import com.liangduo.kr36.bean.DetailedInforBean;
import com.liangduo.kr36.bean.NewsBean;
import com.liangduo.kr36.tool.VolleySingle;
import com.squareup.picasso.Picasso;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;


/**
 * Created by liangduo on 16/5/20.
 * 新闻item点击进入的详情界面
 */
public class DetailedInformationActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;//标题
    private TextView time;//发表时间
    private TextView summary;//摘要
    private TextView content;//文章
    private TextView pay;//打赏
    private ImageView head;//作者的头像
    private TextView name;//作者的名字
    private TextView signature;//作者的签名
    private ImageView downMoreIv;//作者的更多内容
    private TextView author;//作者的标签
    private ImageView line;//作者头像下面那条分割线

    private DetailedInforBean detailedInforBean = new DetailedInforBean();
    private AuthorInforBean authorInforBean = new AuthorInforBean();

    private PopupWindow authorMorePopWindow;//作者更多信息的popupWindow
    private ListView authorNearlyArcitleLv;//popupWindow里的最近文章的listView
    private AuthorPopLvAdapter authorPopLvAdapter;//popupWindow的ListView的Adapter

    private String pathAuther;//作者详情的网址
    private TextView frameText;//popupWindow 下面的占位

    //手指按下的点坐标为(x1,y1),离开屏幕时的坐标是(x2,y2)
    float x1 = 0;
    float y1 = 0;

    float x2 = 0;
    float y2 = 0;
    private LinearLayout downToolBar;//向上滑动时出现的工具栏
    private ScrollView scrollView;
    private String data;

    private Collection collection;
    private CollectionDao collectionDao;//数据库内相应表的操作

    private ImageView back;//返回
    private ImageView favorite;//收藏
    private ImageView send;//分享
    private String feedId;
    List<Collection> collectionList;

    int i= 0;

    @Override
    protected void initView() {

        title = bindView(R.id.detail_infor_titile_tv);
        time = bindView(R.id.detail_infor_time_tv);
        summary = bindView(R.id.detail_infor_summary_tv);
        content = bindView(R.id.detail_infor_content_tv);
        pay = bindView(R.id.detail_infor_pay_tv);

        head = bindView(R.id.detail_infor_author_head);
        name = bindView(R.id.detail_infor_author_name);
        signature = bindView(R.id.detail_infor_author_signature);
        downMoreIv = bindView(R.id.detail_infor_author_down);
        author = bindView(R.id.detail_infor_author_tag);
        line = bindView(R.id.detail_infor_author_line);

        authorNearlyArcitleLv = bindView(R.id.popup_window_author_lv);//popupWindow里的ListView
        downToolBar = bindView(R.id.detail_infor_down_toolbar);
        scrollView = bindView(R.id.detail_infor_scroll_view);

        favorite = bindView(R.id.detail_infor_favorite_iv);//收藏
        send = bindView(R.id.detail_infor_send_iv);//分享
        back = bindView(R.id.detail_infor_back_iv);

    }

    @Override
    protected void initData() {
        pay.setOnClickListener(this);
        downMoreIv.setOnClickListener(this);
        favorite.setOnClickListener(this);
        send.setOnClickListener(this);
        back.setOnClickListener(this);
        //跳转传值  获得了feedId
        Intent intent = getIntent();
        feedId = intent.getStringExtra("id");
        Log.d("======================", feedId + " ");

        //拼接得到了新闻详情的网址
        String path = "https://rong.36kr.com/api/mobi/news/" + feedId;
        //新闻详情上方的作者的信息的网址
        pathAuther = "https://rong.36kr.com/api/mobi/news/" + feedId + "/author-region";

        collectionDao = GreendaoSingle.getInstance().getCollectionDao();

         collectionList = collectionDao.queryBuilder().list();

        for (Collection collection : collectionList) {
            Log.d("我就想看看", collection.getFeedId());
            Log.d("DetailedInformationActi", feedId+"这是上面的feedfId");
            if (collection.getFeedId().equals(feedId)) {
                Log.d("DetailedInformationActi", "也想看看这");
                favorite.setImageResource(R.mipmap.news_toolbar_icon_favorite_blue);
                break;
            } else {
                favorite.setImageResource(R.mipmap.news_toolbar_icon_favorite);
            }
        }

        showPopupWindow();

        VolleySingle.addRequest(pathAuther, AuthorInforBean.class, new Response.Listener<AuthorInforBean>() {
            @Override
            public void onResponse(AuthorInforBean response) {
                Picasso.with(DetailedInformationActivity.this).load(response.getData().getAvatar()).into(head);//头像
                author.setVisibility(View.VISIBLE);
                name.setText(response.getData().getName());
                signature.setText(response.getData().getBrief());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        /**
         * 显示html中的图片
         * 这里面的resource就是fromhtml函数的第一个参数里面的含有的url
         * 其中Html.ImageGetter是一个接口，我们要实现此接口，在它的getDrawable
         * (String source)方法中返回图片的Drawable对象才可以。
         */

        VolleySingle.addRequest(path, DetailedInforBean.class, new Response.Listener<DetailedInforBean>() {
            @Override
            public void onResponse(DetailedInforBean response) {
                detailedInforBean = response;

                title.setText(response.getData().getTitle());

                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
                data = sdf.format(Long.valueOf(response.getData().getUpdateTime()));

                time.setText(data);
                summary.setText(" 『  " + response.getData().getSummary() + "  』 ");

                //获取HTML图片的方法
                struct();

                content.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
                final Html.ImageGetter imgGetter = new Html.ImageGetter() {
                    public Drawable getDrawable(String source) {
                        Log.i("RG", "source---?>>>" + source);
                        Drawable drawable = null;
                        URL url;
                        try {
                            url = new URL(source);
                            Log.i("RG", "url---?>>>" + url);
                            drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
                            Log.d("获取图片", "drawable:" + url.openStream());
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                        //设置图片边界
                        // Drawable的setBounds方法有四个参数，setBounds(int left, int top, int right, int bottom),
                        // 这个四参数指的是drawable将在被绘制在canvas的哪个矩形区域内。
                        drawable.setBounds(0, 0, 700, 400);
                        Log.i("RG", "url---?>>>" + url);
                        return drawable;
                    }
                };
                // Html.fromHtml的另外一个重构方法
                // Html.fromHtml(String source,ImageGetter imageGetter,TagHandler tagHandler)
                content.setText(Html.fromHtml(response.getData().getContent(), imgGetter, null));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //失败时的回调
            }
        });
    }


    //上下滑手势的监听
    //这个方法没用上了啦
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法,直接监听点击事件
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();

            if (y1 - y2 > 0) {
                //表示向上滑了
                downToolBar.setVisibility(View.VISIBLE);
            } else if (y1 - y2 < 0) {
                //表示向下滑了
                downToolBar.setVisibility(View.GONE);
            }
        }
        return super.onTouchEvent(event);
    }


    //在HTML中获取图片的方法
    public static void struct() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or
                // .detectAll()
                // for
                // all
                // detectable
                // problems
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
                .penaltyLog() // 打印logcat
                .penaltyDeath().build());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_detailinformation;
    }

    //通过feedId来判断数据库中是否已经收藏了该文章
    private boolean hasFeedId(String feedId) {
        boolean hasFeedId = false;
        List<Collection> collectionList1 = collectionDao.queryBuilder().list();
        for (Collection collections : collectionList1) {
            Log.d("DetailedInformationActi", "我是数据库里的feedId" + collections.getFeedId());
            Log.d("DetailedInformationActi", "走没走");
            Log.d("DetailedInformationActi", "我是传过来的id" + feedId);
            if (feedId.toString().equals(collections.getFeedId().toString())) {
                Log.d("DetailedInformationActi", "那这里呢");
                hasFeedId = true;
            }
        }
        return hasFeedId;
    }

    //将要收藏的文章的一些插入数据库
    private void initDataBase() {

        collection = new Collection(
                detailedInforBean.getData().getFeatureImg(),
                detailedInforBean.getData().getTitle(),
                detailedInforBean.getData().getUser().getName(),
                data,
                detailedInforBean.getData().getColumnName(),
                feedId
        );

//        collectionDao.deleteAll();

        //使用单例
        collectionDao = GreendaoSingle.getInstance().getCollectionDao();
//        if (hasFeedId(collection.getFeedId())) {
//            Toast.makeText(this, "你已经收藏过着篇文章了哦", Toast.LENGTH_SHORT).show();
//            Log.d("DetailedInformationActi", "我回来啦");
//        } else {
//            Log.d("DetailedInformationActi", "1");
        Toast.makeText(this, "已经收藏啦!主银~", Toast.LENGTH_SHORT).show();
        collectionDao.insert(collection);
//        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_infor_pay_tv:
                break;
            case R.id.detail_infor_author_down:
                if (authorMorePopWindow.isShowing()) {
                    authorMorePopWindow.dismiss();
                } else {
                    authorMorePopWindow.showAsDropDown(line);
                }
                break;
            case R.id.pop_author_article_frame:
                authorMorePopWindow.dismiss();
                break;
            case R.id.detail_infor_favorite_iv:
                collectionDao = GreendaoSingle.getInstance().getCollectionDao();
                collectionList = collectionDao.queryBuilder().list();
                for (Collection collection : collectionList) {
                    Log.d("看看", collection.getFeedId());
                    Log.d("DetailedInformationActi", "333333333333333333"+feedId);
                    Log.d("DetailedInformationActi", "这是删除之前collectionList.size():" + collectionList.size());
                    if (collection.getFeedId().toString().equals(feedId.toString())) {
                        Log.d("DetailedInformationActi", "看看这");

                        //删除数据库的方法
                        collectionDao.queryBuilder().where(CollectionDao.Properties.FeedId.eq(feedId)).buildDelete().executeDeleteWithoutDetachingEntities();

                        Log.d("DetailedInformationActi", "这是删除后collectionList.size():" + collectionList.size());
                        Toast.makeText(this, "已取消收藏~", Toast.LENGTH_SHORT).show();
                        favorite.setImageResource(R.mipmap.news_toolbar_icon_favorite);
                        i =1;
                    }
                }
                if (i== 0){
                    initDataBase();
                    favorite.setImageResource(R.mipmap.news_toolbar_icon_favorite_blue);
                }
                break;
            case R.id.detail_infor_back_iv:
                finish();
                break;
            case R.id.detail_infor_send_iv:
                /////////////
                showShare();
                break;
        }
    }

    private void showPopupWindow() {
        authorMorePopWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View v = LayoutInflater.from(this).inflate(R.layout.popup_detail_auther_more, null);
        final TextView articleNum = (TextView) v.findViewById(R.id.pop_author_article_num);
        TextView frameText = (TextView) v.findViewById(R.id.pop_author_article_frame);
        final TextView scanNum = (TextView) v.findViewById(R.id.pop_author_scan_num);
        final ListView nearlyArticleLv = (ListView) v.findViewById(R.id.popup_window_author_lv);

        authorMorePopWindow.setContentView(v);

        frameText.setOnClickListener(this);//给pop下方的占位设置监听

        authorPopLvAdapter = new AuthorPopLvAdapter(this);

        VolleySingle.addRequest(pathAuther, AuthorInforBean.class,
                new Response.Listener<AuthorInforBean>() {
                    @Override
                    public void onResponse(AuthorInforBean response) {
                        authorPopLvAdapter.setAuthorInforBean(response);
                        Log.d("4444444444444444", "response:" + response);
                        nearlyArticleLv.setAdapter(authorPopLvAdapter);
                        articleNum.setText(response.getData().getTotalCount() + "篇");

                        if (response.getData().getTotalView() % 10000 > 0) {

                            scanNum.setText(response.getData().getTotalView() % 10000 + "万");
                        } else {
                            scanNum.setText(response.getData().getTotalView());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("66666666666666666", "没解析作者详情的实体类" + error);
                    }
                });

    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        //oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }


}
