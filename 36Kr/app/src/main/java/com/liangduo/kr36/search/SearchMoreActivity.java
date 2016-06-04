package com.liangduo.kr36.search;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseActivity;
import com.liangduo.kr36.bean.SearchMoreBean;
import com.liangduo.kr36.news.DetailedInformationActivity;
import com.liangduo.kr36.tool.VolleySingle;

/**
 * Created by liangduo on 16/5/26.
 * 搜索更多的界面
 * 这里面有ListView 装我搜索到的20条
 */
public class SearchMoreActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private SearchMoreAdapter searchMoreAdapter = new SearchMoreAdapter(this);
    private SearchMoreBean searchMoreBean;

    private ImageView backSearch;//点击返回搜索
    private TextView title;
    private TextView totalCount;//搜索的总条数
    private ListView listView;


    @Override
    protected void initView() {
        backSearch = bindView(R.id.activity_search_more_back_iv);
        title = bindView(R.id.activity_search_more_title_tv);
        totalCount = bindView(R.id.search_search_count_tv);
        listView = bindView(R.id.search_more_lv);

    }

    @Override
    protected void initData() {
        //解析网络数据
        VolleyData();
        backSearch.setOnClickListener(this);
        listView.setAdapter(searchMoreAdapter);

        listView.setOnItemClickListener(this);

    }

    private void VolleyData() {
        Intent intent = getIntent();
        final String searchInfor = intent.getStringExtra("search");
        String searchWhat = intent.getStringExtra("searchWhat");
        title.setText(searchWhat + ":" + searchInfor);


        VolleySingle.addRequest("https://rong.36kr.com/api/mobi/news/search?keyword=" + searchInfor + "&page=1&pageSize=20",
                SearchMoreBean.class, new Response.Listener<SearchMoreBean>() {
                    @Override
                    public void onResponse(SearchMoreBean response) {
                        searchMoreBean = response;

                        if (response.getData().getTotalCount() >=1) {
                            searchMoreAdapter.setSearchMoreBean(response);
                        }else {
                            totalCount.setText("共搜到" + "0" + "篇文章");
                        }
                        totalCount.setText("共搜到" + response.getData().getTotalCount() + "篇文章");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("SearchMoreActivity", "没解析到SearchMoreBean数据哦");
                    }
                });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search_more;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_search_more_back_iv:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "已点击", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, new DetailedInformationActivity().getClass());

        String feedId = searchMoreBean.getData().getData().get(position).getFeedId();
        intent.putExtra("id", feedId);

        Log.d("传值", searchMoreBean.getData().getData().get(position).getFeedId());
        Log.d("=====position====", "newsBean.getData().getData().get(position):" + searchMoreBean.getData().getData().get(position));
        startActivity(intent);
    }
}
