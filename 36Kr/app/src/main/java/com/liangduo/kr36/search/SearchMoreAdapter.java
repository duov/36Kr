package com.liangduo.kr36.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.bean.SearchMoreBean;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by liangduo on 16/5/26.
 * SearchMore 的ListView 的Adapter
 */
public class SearchMoreAdapter extends BaseAdapter {
    private Context context;
    private SearchMoreBean searchMoreBean;

    public SearchMoreAdapter(Context context) {
        this.context = context;
    }

    public void setSearchMoreBean(SearchMoreBean searchMoreBean) {
        this.searchMoreBean = searchMoreBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return searchMoreBean == null ? 0: searchMoreBean.getData().getTotalCount();
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
        ViewHolder holder = null ;
        if (convertView == null ){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_search_more,null);
            holder = new  ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.d("SearchMoreAdapter", searchMoreBean.getData().getData().get(0).getFeatureImg()+"");


        if ("/assets/images/post_thumbs_default.png" == searchMoreBean.getData().getData().get(position).getFeatureImg()){
            holder.head.setImageResource(R.mipmap.defaultbg);
        }else{
            Picasso.with(context).load(searchMoreBean.getData().getData().get(position).getFeatureImg()).into(holder.head);
        }

        holder.title.setText(searchMoreBean.getData().getData().get(position).getTitle());

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        String date = sdf.format(new Date(Long.valueOf(searchMoreBean.getData().getData().get(position).getPublishTime())));
        holder.time.setText(date);

        holder.tag.setText(searchMoreBean.getData().getData().get(position).getColumnName());
        holder.name.setText(searchMoreBean.getData().getData().get(position).getUser().getName());
        return convertView;
    }

    class ViewHolder {
        ImageView head;
        TextView title;
        TextView name;
        TextView time;
        TextView tag;

        public ViewHolder(View itemView) {
            head = (ImageView) itemView.findViewById(R.id.item_search_more_iv);

            title = (TextView) itemView.findViewById(R.id.item_search_more_title_tv);
            name = (TextView) itemView.findViewById(R.id.item_search_more_name_tv);
            time = (TextView) itemView.findViewById(R.id.item_search_more_time_tv);
            tag = (TextView) itemView.findViewById(R.id.item_search_more_tag_tv);

        }
    }
}
