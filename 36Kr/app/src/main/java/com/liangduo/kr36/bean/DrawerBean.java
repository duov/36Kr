package com.liangduo.kr36.bean;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by liangduo on 16/5/13.
 */
public class DrawerBean {
    private int mark;
    private String tagTv;

    public DrawerBean(int mark, String tagTv) {
        this.mark = mark;
        this.tagTv = tagTv;
    }

    public DrawerBean() {
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getTagTv() {
        return tagTv;
    }

    public void setTagTv(String tagTv) {
        this.tagTv = tagTv;
    }
}
