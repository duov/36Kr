package com.liangduo.kr36.search;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.liangduo.kr36.R;
import com.liangduo.kr36.base.BaseActivity;

/**
 * Created by liangduo on 16/5/25.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private ImageView searchIv;//搜索的放大镜ImageView
    private EditText inputEt;//输入要搜索的内容EditText
    private ImageView cancelAllIv;//删除Et里内容的×
    private TextView cancel;//取消
    private LinearLayout backgroundLayout;//背景
    private ScrollView searchItem;//输入搜索内容后显示的布局
    private LinearLayout searchNews;//搜索新闻
    private LinearLayout searchAboutUser;//搜索相关用户
    private LinearLayout searchCompany;//搜索创业公司

    private TextView searchHistory1;//最后一个历史记录
    private TextView searchHistory2;//倒数第二个历史记录
    private TextView searchHistory3;//倒数第三个历史记录
    private TextView searchHistory4;//倒数第四个历史记录
    private TextView searchHistory5;//倒数第五个历史记录
    private TextView searchClean;//清除历史记录



    @Override
    protected void initView() {
        searchIv = bindView(R.id.activity_search_iv);
        inputEt = bindView(R.id.activity_search_et);
        cancelAllIv = bindView(R.id.activity_search_cancel_all_iv);
        cancel = bindView(R.id.activity_search_cancel_tv);
        backgroundLayout = bindView(R.id.activity_search_background);
        searchItem = bindView(R.id.search_search_item);
        searchNews = bindView(R.id.search_search_news_layout);
        searchAboutUser = bindView(R.id.search_search_about_user_layout);
        searchCompany = bindView(R.id.search_search_company_layout);

        searchHistory1 = bindView(R.id.search_history_tv_1);
        searchHistory2 = bindView(R.id.search_history_tv_2);
        searchHistory3 = bindView(R.id.search_history_tv_3);
        searchHistory4 = bindView(R.id.search_history_tv_4);
        searchHistory5 = bindView(R.id.search_history_tv_5);
        searchClean = bindView(R.id.search_history_clean);
    }

    @Override
    protected void initData() {
        searchIv.setOnClickListener(this);
        cancelAllIv.setOnClickListener(this);
        cancel.setOnClickListener(this);
        searchNews.setOnClickListener(this);
        searchAboutUser.setOnClickListener(this);
        searchCompany.setOnClickListener(this);

        searchHistory1.setOnClickListener(this);
        searchHistory2.setOnClickListener(this);
        searchHistory3.setOnClickListener(this);
        searchHistory4.setOnClickListener(this);
        searchHistory5.setOnClickListener(this);
        searchClean.setOnClickListener(this);
        //输入文本加监听
        inputEt.addTextChangedListener(this);





    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,SearchMoreActivity.class);
        intent.putExtra("search",inputEt.getText().toString());
        switch (v.getId()){
            case R.id.activity_search_iv:
                //查找
                if (inputEt.getText().toString().equals("")){
                    Toast.makeText(this, "搜索不能为空哦~", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_search_cancel_all_iv:
                //删除所有
                inputEt.setText("");
                break;
            case R.id.activity_search_cancel_tv:
                //取消
                finish();
                break;
            case R.id.search_search_news_layout:
                //搜索新闻
                intent.putExtra("searchWhat","搜索新闻");
                startActivity(intent);

                break;
            case R.id.search_search_about_user_layout:
                //相关用户
                intent.putExtra("searchWhat","相关用户");
                startActivity(intent);
                break;
            case R.id.search_search_company_layout:
                //创业公司
                intent.putExtra("searchWhat","创业公司");
                startActivity(intent);
                break;
            case R.id.search_history_tv_1:
                break;
            case R.id.search_history_tv_2:
                break;
            case R.id.search_history_tv_3:
                break;
            case R.id.search_history_tv_4:
                break;
            case R.id.search_history_tv_5:
                break;
            case R.id.search_history_clean:
                //清理
                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
    //实时监听EditText
    @Override
    public void afterTextChanged(Editable s) {
        String textAtEt = inputEt.getText().toString();
        if (textAtEt.equals("")){
            cancelAllIv.setVisibility(View.GONE);
            backgroundLayout.setVisibility(View.VISIBLE);
            searchItem.setVisibility(View.GONE);
        }else{
            cancelAllIv.setVisibility(View.VISIBLE);
            backgroundLayout.setVisibility(View.GONE);
            searchItem.setVisibility(View.VISIBLE);
        }
    }
}
