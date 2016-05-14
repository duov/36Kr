package com.liangduo.kr36.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.bean.DrawerBean;

import java.util.List;

/**
 * Created by liangduo on 16/5/13.
 */
public class DrawerAdapter extends BaseAdapter {
    private List<DrawerBean> drawerList;
    private Context context;

    public DrawerAdapter(Context context) {
        this.context = context;
    }

    public void setDrawerList(List<DrawerBean> drawerList) {
        this.drawerList = drawerList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return drawerList == null? 0:drawerList.size();
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
        ViewHolder holder;
        if (convertView  == null ){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_drawer_listview,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tagTv.setText(drawerList.get(position).getTagTv());
        holder.mark.setImageResource(drawerList.get(position).getMark());
        return convertView;
    }

    class ViewHolder {
        TextView tagTv;
        ImageView mark;
        public ViewHolder (View itemView){
            mark = (ImageView) itemView.findViewById(R.id.drawer_list_item_mark_iv);
            tagTv = (TextView) itemView.findViewById(R.id.drawer_list_item_tag_tv);
        }
    }
}
