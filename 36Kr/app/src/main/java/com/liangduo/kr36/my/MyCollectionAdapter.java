package com.liangduo.kr36.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangduo.greendao.Collection;
import com.liangduo.kr36.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by liangduo on 16/5/30.
 */
public class MyCollectionAdapter extends BaseAdapter {
    private Context context;
    private List<Collection> collection;//这是我的Bean

    public MyCollectionAdapter(Context context) {
        this.context = context;
    }

    public void setCollection(List<Collection> collection) {
        this.collection = collection;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return collection == null ? 0 : collection.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_news, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(collection.get(position).getTitle());
        holder.name.setText(collection.get(position).getName());

        holder.date.setText(collection.get(position).getDate());
        holder.tag.setText(collection.get(position).getTag());
        Picasso.with(context).load(collection.get(position).getImageUrl()).error(R.mipmap.defaultbg).placeholder(R.mipmap.defaultbg).into(holder.head);;


        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView name;
        TextView date;
        TextView tag;
        ImageView head;

        public ViewHolder(View itemView) {
            title = (TextView) itemView.findViewById(R.id.item_news_title_tv);
            name = (TextView) itemView.findViewById(R.id.item_news_name_tv);
            date = (TextView) itemView.findViewById(R.id.item_news_time_tv);
            tag = (TextView) itemView.findViewById(R.id.item_news_tag_tv);
            head  = (ImageView) itemView.findViewById(R.id.item_news_iv);
        }
    }
}
