package com.liangduo.kr36.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.bean.AuthorInforBean;
import com.squareup.picasso.Picasso;

/**
 * Created by liangduo on 16/5/23.
 */
public class AuthorPopLvAdapter extends BaseAdapter {
    private Context context;
    private AuthorInforBean authorInforBean;

    public AuthorPopLvAdapter(Context context) {
        this.context = context;
    }

    public void setAuthorInforBean(AuthorInforBean authorInforBean) {
        this.authorInforBean = authorInforBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return authorInforBean == null? 0 :authorInforBean.getData().getLatestArticle().size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_pop_auter_nearly_article,null);
           holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(authorInforBean.getData().getLatestArticle().get(position).getFeatureImg()).into(holder.headIv);
        holder.introduceTv.setText(authorInforBean.getData().getLatestArticle().get(position).getTitle());

        return convertView;
    }

     class ViewHolder {

         ImageView headIv;
         TextView introduceTv;

         public ViewHolder(View itemView){

             headIv = (ImageView) itemView.findViewById(R.id.item_author_pop_article_head_iv);
             introduceTv = (TextView) itemView.findViewById(R.id.item_author_pop_article_introduce_tv);
         }
    }
}
