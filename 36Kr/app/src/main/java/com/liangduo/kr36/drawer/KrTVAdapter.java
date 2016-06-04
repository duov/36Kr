package com.liangduo.kr36.drawer;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.liangduo.kr36.R;
import com.liangduo.kr36.bean.KrTVBean;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangduo on 16/5/14.
 */
public class KrTVAdapter extends BaseAdapter {
    private Context context;
    private KrTVBean krTVBean;
    private LinearLayout linearLayout;
    private ImageView imageView;

    private MediaController media;

//    private int currentIndex = -1;
//    private int playPosition = -1;
//    private boolean isPaused = false;
//    private boolean isPlaying = false;

    private List<Boolean> isPlay = new ArrayList<>();

    public void setIsPlay(List<Boolean> isPlay) {
        this.isPlay = isPlay;
        notifyDataSetChanged();
    }

    public void setKrTVBean(KrTVBean krTVBean) {
        this.krTVBean = krTVBean;
        notifyDataSetChanged();
    }

    public KrTVAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return krTVBean == null ? 0 : krTVBean.getData().getPageSize();
    }

    @Override
    public Object getItem(int position) {
        return krTVBean.getData().getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        isPlay.clear();
        for (int i = 0; i < krTVBean.getData().getPageSize(); i++) {
            isPlay.add(false);
        }

        final int mPosition = position;
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_krtv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

            if (holder.video.isPlaying()) {
                holder.video.setVisibility(View.GONE);
                holder.linearLayout.setVisibility(View.VISIBLE);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.titleTv.setVisibility(View.VISIBLE);
            } else if (holder.video.canPause()) {
                holder.video.setVisibility(View.GONE);
                holder.linearLayout.setVisibility(View.VISIBLE);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.titleTv.setVisibility(View.VISIBLE);
            }
        }
//
//        if (holder.video.getVisibility() == View.VISIBLE) {
//            holder.titleTv.setVisibility(View.VISIBLE);
//            holder.imageView.setVisibility(View.VISIBLE);
//            holder.backgroundIv.setVisibility(View.VISIBLE);
//            holder.video.setVisibility(View.INVISIBLE);
//        }


        //图片
        Picasso.with(context).load(krTVBean.getData().getData().get(position).getTv().getFeatureImg()).into(holder.backgroundIv);
        //标题
        holder.titleTv.setText(krTVBean.getData().getData().get(position).getTv().getTitle());
        //时间
        int minute = 60;
        String min = String.valueOf(krTVBean.getData().getData().get(position).getTv().getDurationLong() / minute);
        String sec = String.valueOf(krTVBean.getData().getData().get(position).getTv().getDurationLong() % minute);
        String time1 = min + "'" + sec + "''";
        Log.d("这是时间这是时间这是时间这是时间", time1);
        holder.duration.setText(time1);
        //这是视频的网址
        String path = krTVBean.getData().getData().get(position).getTv().getVideoSource();
        Log.d("KrTVAdapter", krTVBean.getData().getData().get(position).getTv().getVideoSource());
        Uri uri = Uri.parse(path);
        holder.video.setVideoURI(uri);

        final ViewHolder finalHolder = holder;
        media = new MediaController(context);

//
//        //如果 游标 是获得的这个位置
//        if (currentIndex == position) {
//            Log.d("3", currentIndex + "currentIndex == position");
//            holder.linearLayout.setVisibility(View.INVISIBLE);
//            holder.video.setVisibility(View.INVISIBLE);
//
//            if (isPlaying || playPosition == -1) {
//                if (holder.video != null) {
//                    Log.d("4", holder.video + "mVideoView!=null");
//                    holder.video.setVisibility(View.GONE);
//                    //停止视频播放，并释放资源。
//                    holder.video.stopPlayback();
//                }
//            }
//            holder.video.setVisibility(View.VISIBLE);
//            holder.linearLayout.setVisibility(View.GONE);
//            holder.backgroundIv.setVisibility(View.GONE);
//            //设置这个控制器绑定(anchor/锚)到VideoView上
//            media.setAnchorView(holder.video);
//            media.setMediaPlayer(holder.video);
//            holder.video.setMediaController(media);
////            holder.video.requestFocus();
//            if (playPosition > 0 && isPaused) {
//                holder.video.start();
//                isPaused = false;
//                isPlaying = true;
//            } else {
//                holder.video.setVideoPath(krTVBean.getData().getData().get(position).getTv().getVideoSource());
//                isPaused = false;
//                isPlaying = true;
//                System.out.println("播放新的视频");
//            }
//
//            //注册一个回调函数，视频播放完成后调用。
//            final ViewHolder finalHolder2 = holder;
//            holder.video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    if (finalHolder2.video != null) {
//                        //从0进度开始播放
////                        finalHolder2.video.seekTo(0);
//                        finalHolder2.video.stopPlayback();
//                        currentIndex = -1;
//                        isPaused = false;
//                        isPlaying = false;
//                        notifyDataSetChanged();
//
//                    }
//                }
//            });
//            //注册一个回调函数，在视频预处理完成后调用。在视频预处理完成后被调用。此时视频的宽度、高度、宽高比信息已经获取到，此时可调用seekTo让视频从指定位置开始播放。
//            final ViewHolder finalHolder1 = holder;
//            holder.video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    finalHolder1.video.start();
//                }
//            });
//
//        } else {
//            holder.linearLayout.setVisibility(View.VISIBLE);
//            holder.backgroundIv.setVisibility(View.VISIBLE);
//            holder.video.setVisibility(View.GONE);
//        }
        /**
         * 点击ListView里面的布局播放视频
         */
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("KrTVAdapter", "点击了一下下");
                for (Boolean aBoolean : isPlay) {
                    Log.d("KrTVAdapter", "aBoolean:" + aBoolean);
                    if (aBoolean == true) {
                        for (int i = 0; i < krTVBean.getData().getPageSize(); i++) {
                            isPlay.set(i, false);
                            Log.d("KrTVAdapter", "isPlay:+++++++++" + isPlay);
                            finalHolder.backgroundIv.setVisibility(View.VISIBLE);
                            finalHolder.linearLayout.setVisibility(View.VISIBLE);
                            finalHolder.titleTv.setVisibility(View.VISIBLE);
                            finalHolder.video.setVisibility(View.GONE);
                            notifyDataSetChanged();
                        }
                    } else {
                        isPlay.set(position, true);
                        notifyDataSetChanged();
                        Log.d("KrTVAdapter", "isPlay:----------" + isPlay);
                        finalHolder.linearLayout.setVisibility(View.GONE);
                        finalHolder.imageView.setVisibility(View.GONE);
                        //设置VideoView可见
                        finalHolder.video.setVisibility(View.VISIBLE);
                        //VideoView与MediaController进行关联
                        finalHolder.video.setMediaController(media);
                        media.setMediaPlayer(finalHolder.video);
                        //开始播放视频
                        finalHolder.video.start();
                        isPlay.set(position, false);
                        notifyDataSetChanged();
                    }
                }
                // 视频结束时的监听事件
                finalHolder.video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        finalHolder.video.setVisibility(View.GONE);
                        finalHolder.linearLayout.setVisibility(View.VISIBLE);
                        finalHolder.imageView.setVisibility(View.VISIBLE);
                        isPlay.set(position,false);
                    }
                });
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    class ViewHolder {

        ImageView backgroundIv;
        TextView titleTv;
        TextView duration;//视频持续时间
        VideoView video;
        LinearLayout linearLayout;
        ImageView imageView;


        public ViewHolder(View itemView) {
            backgroundIv = (ImageView) itemView.findViewById(R.id.item_krtv_background_iv);
            titleTv = (TextView) itemView.findViewById(R.id.item_krtv_title_tv);
            duration = (TextView) itemView.findViewById(R.id.item_krtv_duration_tv);
            video = (VideoView) itemView.findViewById(R.id.item_krtv_vv);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.krtv_layout);
            imageView = (ImageView) itemView.findViewById(R.id.item_krtv_background_iv);
        }
    }
}
