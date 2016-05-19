package com.liangduo.kr36.find;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.bean.NearlyAtyBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by liangduo on 16/5/15.
 */
public class NearlyAtyAdapter extends BaseAdapter {
    private Context context;
    private NearlyAtyBean nearlyAtyBeen;

    public NearlyAtyAdapter(Context context) {
        this.context = context;
    }

    public void setNearlyAtyBeen(NearlyAtyBean nearlyAtyBeen) {
        this.nearlyAtyBeen = nearlyAtyBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return nearlyAtyBeen == null? 0:nearlyAtyBeen.getData().getPageSize();
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
        ViewHolder holder= null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_nearly_aty_lv,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //解析图片
        Picasso.with(context).load(nearlyAtyBeen.getData().getData().get(position).getActivityImg()).into(holder.picture);

        holder.title.setText(nearlyAtyBeen.getData().getData().get(position).getActivityName());
        holder.information.setText(nearlyAtyBeen.getData().getData().get(position).getActivityDesc());
        holder.state.setText(nearlyAtyBeen.getData().getData().get(position).getActivityStatus());
        holder.address.setText(nearlyAtyBeen.getData().getData().get(position).getActivityCity());
        holder.date.setText(nearlyAtyBeen.getData().getData().get(position).getActivityTime());

        return convertView;
    }

    class ViewHolder {
        ImageView picture;
        TextView title;
        TextView information;
        TextView state;
        TextView address;
        TextView date;

        public  ViewHolder(View itemView){
            picture                = (ImageView) itemView.findViewById(R.id.nearly_aty_lv_iv);
            title                   = (TextView) itemView.findViewById(R.id.nearly_aty_lv_title_tv);
            information             = (TextView) itemView.findViewById(R.id.nearly_aty_lv_information_tv);
            state                   = (TextView) itemView.findViewById(R.id.nearly_aty_lv_state_tv);
            address                 = (TextView) itemView.findViewById(R.id.nearly_aty_lv_address_iv);
            date                    = (TextView) itemView.findViewById(R.id.nearly_aty_lv_date_tv);
        }
    }
}
