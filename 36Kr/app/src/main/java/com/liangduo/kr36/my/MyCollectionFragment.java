package com.liangduo.kr36.my;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.liangduo.greendao.Collection;
import com.liangduo.greendao.CollectionDao;
import com.liangduo.greendao.DaoMaster;
import com.liangduo.greendao.DaoSession;
import com.liangduo.greendao.GreendaoSingle;
import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseFragment;
import com.liangduo.kr36.bean.DetailedInforBean;
import com.liangduo.kr36.bean.NewsBean;
import com.liangduo.kr36.news.DetailedInformationActivity;
import com.liangduo.kr36.news.NewsAdapter;
import com.liangduo.kr36.tool.VolleySingle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangduo on 16/5/30.
 */
public class MyCollectionFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView myCollectionLv;
    private LinearLayout noDataLayout;//没收藏时显示
    private LinearLayout collectionLayout;//有收藏时显示

    private MyCollectionAdapter myCollectionAdapter;
    private CollectionDao collectionDao;//数据库内相应表的操作
    private List<Collection> collectionList;

    @Override
    protected void initData() {
        myCollectionAdapter = new MyCollectionAdapter(getActivity());
        collectionDao= GreendaoSingle.getInstance().getCollectionDao();
        //  //查看数据库
         collectionList = collectionDao.queryBuilder().list();
        if (collectionList.size() != 0){
            //判断是否有收藏//有收藏就显示;没有就显示背景
            noDataLayout.setVisibility(View.GONE);
            collectionLayout.setVisibility(View.VISIBLE);
        }else {
            noDataLayout.setVisibility(View.VISIBLE);
            collectionLayout.setVisibility(View.GONE);
        }
        myCollectionLv.setAdapter(myCollectionAdapter);
        myCollectionAdapter.setCollection(collectionList);

        myCollectionLv.setOnItemClickListener(this);
    }

    @Override
    protected void initView() {
        noDataLayout = bindView(R.id.my_collection_background);
        collectionLayout = bindView(R.id.my_collection);
        myCollectionLv = bindView(R.id.my_collection_lv);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_my_collection;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        //获得点击文章的feedId
        String path = "https://rong.36kr.com/api/mobi/news/" +  collectionList.get(position).getFeedId();

        VolleySingle.addRequest(path, Collection.class,
                new Response.Listener<Collection>() {
                    @Override
                    public void onResponse(Collection response) {
                        Intent intent = new Intent(getActivity(), DetailedInformationActivity.class);
                        String feedId = collectionList.get(position).getFeedId();
                        intent.putExtra("id", feedId);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("MyCollectionFragment", "error:" + error);
                    }
                });

    }
}
