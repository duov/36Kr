package com.liangduo.kr36.invest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.liangduo.kr36.R;
import com.liangduo.kr36.bean.InvestAllBean;
import com.liangduo.kr36.login.LoginActivity;
import com.liangduo.kr36.tool.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangduo on 16/5/13.
 */
public class ReuseAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private InvestAllBean investAllBeen;

    private List<String> listAdName = new ArrayList<>();//左边字
    private List<String> listConten = new ArrayList<>();//右边字

    private List<TextView> leftTvList = new ArrayList<>();//左边字位置的集合
    private List<TextView> rightTvList = new ArrayList<>();//右边字位置的集合

    public ReuseAdapter(Context context) {
        this.context = context;
    }

    public void setListConten(List<String> listConten) {
        this.listConten = listConten;
        notifyDataSetChanged();
    }

    public void setListAdName(List<String> listAdName) {
        this.listAdName = listAdName;
        notifyDataSetChanged();
    }

    public void setInvestAllBeen(InvestAllBean investAllBeen) {
        this.investAllBeen = investAllBeen;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return investAllBeen == null ? 0 : investAllBeen.getData().getData().size();
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
        rightTvList.clear();
        leftTvList.clear();
        listAdName.clear();
        listConten.clear();
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_invest, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(investAllBeen.getData().getData().get(position).getCompany_logo()).transform(new CircleTransform()).into(holder.head);
        holder.name.setText(investAllBeen.getData().getData().get(position).getCompany_name());
        holder.introduce.setText(investAllBeen.getData().getData().get(position).getCompany_brief());
        Picasso.with(context).load(investAllBeen.getData().getData().get(position).getFile_list_img()).into(holder.picture);
        String processTv = investAllBeen.getData().getData().get(position).getFundStatus().getCrowd_funding_status();

        holder.schedule.setTextColor(Color.parseColor("#979ea7"));

        //获得最ProgressBar大值  100
        if (investAllBeen.getData().getData().get(position).getRate() >= 1) {
            if ((int)((investAllBeen.getData().getData().get(position).getRate()) * 100)==100){
                holder.schedule.setText(investAllBeen.getData().getData().get(position).getFundStatus().getDesc());
                holder.schedule.setTextColor(Color.parseColor("#5d87f6"));
            }else {
                holder.schedule.setTextColor(Color.parseColor("#ff6301"));
            }
            holder.schedule.setText("已募资" + (int)((investAllBeen.getData().getData().get(position).getRate()) * 100) + "%");
            holder.progressBar.setProgress(100);
        } else {
            int i = (int) ((investAllBeen.getData().getData().get(position).getRate()) * 100);
            holder.schedule.setText(investAllBeen.getData().getData().get(position).getFundStatus().getDesc());
            holder.progressBar.setSecondaryProgress(i);
        }
        Log.d("ReuseAdapter", "百分比" + ((investAllBeen.getData().getData().get(position).getRate()) * 100));


        if (processTv.equals("MAO_DING")) {
            holder.process.setText("锚定中");
            holder.process.setTextColor(Color.parseColor("#ff6301"));
        } else if (processTv.equals("MU_ZI")) {
            holder.process.setText("募资中");
            holder.process.setTextColor(Color.parseColor("#5d87f6"));
        } else if (processTv.equals("MU_ZI_DONE")) {
            holder.process.setText("募资结束");
            holder.process.setTextColor(Color.parseColor("#979ea7"));
        } else {
            holder.process.setText("募资成功");
            holder.process.setTextColor(Color.parseColor("#979ea7"));
        }


        holder.conten.setText(investAllBeen.getData().getData().get(position).getLead_name());

        Log.d("可以有", "investAllBeen.getData().getData().get(position).getCf_advantage():" + investAllBeen.getData().getData().get(position).getCf_advantage().size());

        //遍历实例类里面的集合,就是那个ListView中写死的4行,判断bean中集合的大小,来决定要不要隐藏最后一条
        for (InvestAllBean.DataBean.DataBean1.CfAdvantageBean cfAdvantageBean : investAllBeen.getData().getData().get(position).getCf_advantage()) {
            listAdName.add(cfAdvantageBean.getAdname());
            Log.d("ReuseAdapter", "走了这个遍历");
            listConten.add(cfAdvantageBean.getAdcontent());

            rightTvList.add(holder.right1);
            rightTvList.add(holder.right2);

            if (investAllBeen.getData().getData().get(position).getCf_advantage().size() == 3) {
                holder.right3.setVisibility(View.VISIBLE);
                holder.right3.setVisibility(View.VISIBLE);
                rightTvList.add(holder.right3);
            } else {
                holder.left3.setVisibility(View.GONE);
                holder.right3.setVisibility(View.GONE);
            }

            leftTvList.add(holder.left1);
            leftTvList.add(holder.left2);

            if (investAllBeen.getData().getData().get(position).getCf_advantage().size() == 3) {
                holder.left3.setVisibility(View.VISIBLE);
                holder.right3.setVisibility(View.VISIBLE);
                leftTvList.add(holder.left3);
            } else {
                holder.left3.setVisibility(View.GONE);
                holder.right3.setVisibility(View.GONE);
            }
        }

        Log.d("ReuseAdapter+++++++++", "listAdName.size():" + listAdName.size());

        Log.d("ReuseAdapter======", "leftTvList.size():" + leftTvList.size());

        for (int i = 0; i < listAdName.size(); i++) {
            leftTvList.get(i).setText(listAdName.get(i));
            rightTvList.get(i).setText(listConten.get(i));
        }

        holder.buy.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_invest_lv_buy:
                showBuyDialog();
                break;
        }
    }

    private void showBuyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View question = LayoutInflater.from(context).inflate(R.layout.dialog_invest_buy,null);
        TextView ques = (TextView) question.findViewById(R.id.dialog_invest_buy);
        builder.setView(question).setPositiveButton("立即前往", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "那你就得先去注册啦!~_~", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("暂不申请",null).show();
    }

    class ViewHolder {
        ImageView head;
        TextView name;
        TextView introduce;
        ImageView picture;
        TextView process;
        TextView schedule;
        ProgressBar progressBar;
        TextView conten;

        TextView buy;

        TextView left1;
        TextView left2;
        TextView left3;
        TextView right1;
        TextView right2;
        TextView right3;


        public ViewHolder(View itemView) {
            head = (ImageView) itemView.findViewById(R.id.item_invent_lv_head);
            name = (TextView) itemView.findViewById(R.id.item_invest_lv_name);
            introduce = (TextView) itemView.findViewById(R.id.item_invent_lv_introduce);
            picture = (ImageView) itemView.findViewById(R.id.item_invent_lv_picture);
            process = (TextView) itemView.findViewById(R.id.item_invent_lv_process);
            schedule = (TextView) itemView.findViewById(R.id.item_invent_lv_schedule);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_invest_lv_progress_bar);
            conten = (TextView) itemView.findViewById(R.id.invest_lv_ad_conten_investor);
            buy = (TextView) itemView.findViewById(R.id.item_invest_lv_buy);

            left1 = (TextView) itemView.findViewById(R.id.invest_lv_left_investor1);
            left2 = (TextView) itemView.findViewById(R.id.invest_lv_left_investor2);
            left3 = (TextView) itemView.findViewById(R.id.invest_lv_left_investor3);

            right1 = (TextView) itemView.findViewById(R.id.invest_lv_right_investor1);
            right2 = (TextView) itemView.findViewById(R.id.invest_lv_right_investor2);
            right3 = (TextView) itemView.findViewById(R.id.invest_lv_right_investor3);

        }
    }


}
