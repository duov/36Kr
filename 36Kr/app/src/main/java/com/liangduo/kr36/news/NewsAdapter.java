package com.liangduo.kr36.news;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.bean.NewsBean;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by liangduo on 16/5/9.
 */
public class NewsAdapter extends BaseAdapter {
    private Context context;
    private NewsBean newsBean;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    public void setNewsBean(NewsBean newsBean) {
        this.newsBean = newsBean;
        Log.d("NewsAdapter", "newsBean.getData().getPageSize():" + newsBean.getData().getPageSize());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return newsBean == null ? 0 : newsBean.getData().getPageSize();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_news, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        //显示布局里的组件
        //解析图片
        Picasso.with(context).load(newsBean.getData().getData().get(position).getFeatureImg()).error(R.mipmap.defaultbg).placeholder(R.mipmap.defaultbg).into(holder.head);
        Log.d("********", newsBean.getData().getData().get(0).getFeatureImg()+"");

        //处理时间
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        String date = sdf.format(new Date(Long.valueOf(newsBean.getData().getData().get(position).getPublishTime())));


        holder.time.setText(date);

        //处理tag的颜色
        if (newsBean.getData().getData().get(position).getColumnName()!=null){
            if (newsBean.getData().getData().get(position).getColumnName().toString().equals("早期项目")){
                holder.tag.setTextColor(Color.parseColor("#6cb96a"));
            }else if (newsBean.getData().getData().get(position).getColumnName().toString().equals("大公司")){
                holder.tag.setTextColor(Color.parseColor("#72A8ff"));
            }else if (newsBean.getData().getData().get(position).getColumnName().toString().equals("B轮后")){
                holder.tag.setTextColor(Color.parseColor("#71c2f4"));
            }else if (newsBean.getData().getData().get(position).getColumnName().toString().equals("资本")){
                holder.tag.setTextColor(Color.parseColor("#3c65d9"));
            }else if (newsBean.getData().getData().get(position).getColumnName().toString().equals("深度")){
                holder.tag.setTextColor(Color.parseColor("#cf4f69"));
            }else if (newsBean.getData().getData().get(position).getColumnName().toString().equals("研究")){
                holder.tag.setTextColor(Color.parseColor("#e69400"));
            }
        }

        holder.tag.setText(newsBean.getData().getData().get(position).getColumnName());
        holder.title.setText(newsBean.getData().getData().get(position).getTitle());
        holder.name.setText(newsBean.getData().getData().get(position).getUser().getName());

        return convertView;
    }

    class ViewHolder {
        ImageView head;
        TextView title;
        TextView name;
        TextView time;
        TextView tag;

        public ViewHolder(View itemView) {
            head = (ImageView) itemView.findViewById(R.id.item_news_iv);

            title = (TextView) itemView.findViewById(R.id.item_news_title_tv);
            name = (TextView) itemView.findViewById(R.id.item_news_name_tv);
            time = (TextView) itemView.findViewById(R.id.item_news_time_tv);
            tag = (TextView) itemView.findViewById(R.id.item_news_tag_tv);

        }
    }
}
