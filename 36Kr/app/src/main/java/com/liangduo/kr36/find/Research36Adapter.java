package com.liangduo.kr36.find;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.liangduo.kr36.bean.Research36Bean;

/**
 * Created by liangduo on 16/5/16.
 */
public class Research36Adapter extends BaseAdapter {
    private Research36Bean research36Bean;
    private Context context;

    public Research36Adapter(Context context) {
        this.context = context;
    }

    public void setResearch36Bean(Research36Bean research36Bean) {
        this.research36Bean = research36Bean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 0;
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
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {

        public ViewHolder(View itemView){

        }
    }
}
