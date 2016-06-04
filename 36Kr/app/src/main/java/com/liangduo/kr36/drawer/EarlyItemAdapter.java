package com.liangduo.kr36.drawer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.bean.EarlyBean;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liangduo on 16/5/13.
 */
public class EarlyItemAdapter extends BaseAdapter {
    private EarlyBean earlyBean;
    private Context context;

    public EarlyItemAdapter(Context context) {
        this.context = context;
    }

    public void setEarlyBean(EarlyBean earlyBean) {
        this.earlyBean = earlyBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return earlyBean  == null ? 0 : earlyBean.getData().getPageSize();
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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_early,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        //解析图片
        //显示图片
        Picasso.with(context).load(earlyBean.getData().getData().get(position).getFeatureImg()).into(holder.head);
        Log.d("#############", earlyBean.getData().getData().get(position).getFeatureImg());
        //处理时间
        SimpleDateFormat stf = new SimpleDateFormat("MM-dd HH:mm");
        String date= stf.format(new Date(Long.valueOf(earlyBean.getData().getData().get(position).getPublishTime())));
        //显示时间
        holder.time.setText(date);
        //显示姓名,标题
        holder.name.setText(earlyBean.getData().getData().get(position).getColumnName());
        holder.title.setText(earlyBean.getData().getData().get(position).getTitle());

        return convertView;

    }

    class ViewHolder {
        ImageView head;
        TextView title;
        TextView name;
        TextView time;
        TextView tag;

        public ViewHolder(View itemView) {
            head = (ImageView) itemView.findViewById(R.id.item_early_lv_iv);
            title = (TextView) itemView.findViewById(R.id.item_early_lv_title_tv);
            name = (TextView) itemView.findViewById(R.id.item_early_lv_name_tv);
            time = (TextView) itemView.findViewById(R.id.item_early_lv_time_tv);
            tag = (TextView) itemView.findViewById(R.id.item_early_lv_tag_tv);

        }
    }
}
