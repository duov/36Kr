package com.liangduo.kr36.invest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.bean.InvestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangduo on 16/5/13.
 */
public class ReuseAdapter extends BaseAdapter {
    private Context context;
    private List<String> investBeen;

    private List<String> list1;

    public ReuseAdapter(Context context) {
        this.context = context;
    }

    public void setInvestBeen(List<String> investBeen) {
        this.investBeen = investBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return investBeen == null? 0 :investBeen.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_invest,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

//        holder.head.setImageResource(investBeen.get(position).getHead());
        holder.name.setText(investBeen.get(position));
//        holder.introduce.setText(investBeen.get(position).getIntroduce());
//        holder.picture.setImageResource(investBeen.get(position).getPicture());
//        holder.process.setText(investBeen.get(position).getProcess());
//        holder.schedule.setText(investBeen.get(position).getSchedule());
//        holder.progressBar.setImageResource(investBeen.get(position).getProgressBar());
//        holder.buy.setText(investBeen.get(position).getBuy());


//        list1 = new ArrayList<>();
//        for (int i = 0 ;i <3 ;i++){
//            list1.add(i,i+"");
//        }
//
//        holder.listView.setAdapter(new BaseAdapter() {
//            private List<String> list1;
//            @Override
//            public int getCount() {
//                return list1 == null? 0:list1.size();
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                ViewHolder holder1 = null;
//                if (convertView == null){
//                    convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_lv_invent,null);
//                    holder1 = new ViewHolder(convertView);
//                    convertView.setTag(holder1);
//                }else {
//                    holder1 = (ViewHolder) convertView.getTag();
//                }
//                holder1.answer.setText(list1.get(position));
//                holder1.question.setText(list1.get(position));
//
//                return convertView;
//            }
//            class ViewHolder {
//                TextView question;
//                TextView answer;
//                public ViewHolder(View itemView){
//                    question  = (TextView) itemView.findViewById(R.id.invest_lv_lv_question);
//                    answer = (TextView) itemView.findViewById(R.id.invest_lv_lv_answer);
//                }
//            }
//
//
//        });**/


        return convertView;
    }

    class ViewHolder {
//        ImageView head;
        TextView name;
//        TextView introduce;
//        ImageView focus;
//        ImageView picture;
        ListView listView;
//        TextView process;
//        TextView schedule;
//        ImageView progressBar;
//        TextView buy;

        public ViewHolder(View itemView){
//            head = (ImageView) itemView.findViewById(R.id.item_invent_lv_head);
            name = (TextView) itemView.findViewById(R.id.item_invest_lv_name);
//            introduce = (TextView) itemView.findViewById(R.id.item_invent_lv_introduce);
//            picture = (ImageView) itemView.findViewById(R.id.item_invent_lv_picture);
            listView = (ListView) itemView.findViewById(R.id.item_invent_lv_lv);
//            process = (TextView) itemView.findViewById(R.id.item_invent_lv_process);
//            schedule = (TextView) itemView.findViewById(R.id.item_invent_lv_schedule);
//            progressBar = (ImageView) itemView.findViewById(R.id.item_invest_lv_progress_bar);
//            buy = (TextView) itemView.findViewById(R.id.item_invest_lv_buy);
        }
    }


}
