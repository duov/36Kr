package com.liangduo.kr36.find;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.bean.FindInvestorBean;
import com.liangduo.kr36.tool.CircleTransform;
import com.liangduo.kr36.tool.DrawableCircle;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liangduo on 16/5/16.
 */
public class FindInvestorAdapter extends BaseAdapter {
    private FindInvestorBean findInvestorBean;
    private Context context;


    public FindInvestorAdapter(Context context) {
        this.context = context;
    }

    public void setFindInvestorBean(FindInvestorBean findInvestorBean) {
        this.findInvestorBean = findInvestorBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return findInvestorBean == null ? 0 : findInvestorBean.getData().getPageSize();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_find_find_investor,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //解析网络拉取头像
        Picasso.with(context).load(findInvestorBean.getData().getData().get(position).getUser().getAvatar())
                //在这里使用毕加索将图片处理成圆形
                .transform(new CircleTransform()).error(R.mipmap.ic_launcher).into(holder.head);
        holder.name.setText(findInvestorBean.getData().getData().get(position).getUser().getName());

//        holder.investStep.setText(findInvestorBean.getData().getData().get(position).getInvestPhases().get(position));
//        holder.focusPlace.setText(findInvestorBean.getData().getData().get(position).getFocusIndustry().get(position));

        /**
         * 将集合转为字符串  投资阶段
         */
        StringBuffer step=new StringBuffer();
        for(String item:findInvestorBean.getData().getData().get(position).getInvestPhases()){
            step.append(item+" ");
        }
        // sb.toString() 就是你要的字符串;
        Log.d("投资阶段的字符串", step.toString());

        if (step.toString().trim().equals("")){
            holder.investStep.setText("未披露");
        }else {
            holder.investStep.setText(step.toString());
        }

        /**
         * 将集合转为字符串  投资领域
         */
        StringBuffer place =new StringBuffer();
        for(String item:findInvestorBean.getData().getData().get(position).getFocusIndustry()){
            place.append(item+" ");
        }
        // sb.toString() 就是你要的字符串;
        Log.d("投资领域的字符串", place.toString());

        if (place.toString().trim().equals("")){
            holder.focusPlace.setText("未披露");
        }else {
            holder.focusPlace.setText(place.toString());
        }

        return convertView;
    }

    class ViewHolder {
        ImageView head;
        TextView name;
        TextView investStep;
        TextView focusPlace;

        public ViewHolder (View itemView){

            head = (ImageView) itemView.findViewById(R.id.item_lv_find_find_investor_head_iv);
            name = (TextView) itemView.findViewById(R.id.item_lv_find_find_investor_name_tv);
            investStep = (TextView) itemView.findViewById(R.id.item_lv_find_find_investor_invest_step_tv);
            focusPlace= (TextView) itemView.findViewById(R.id.item_lv_find_find_investor_focus_space_tv);

        }
    }
}
