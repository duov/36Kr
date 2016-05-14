package com.liangduo.kr36.bean;

/**
 * Created by liangduo on 16/5/13.
 */
public class InvestBean {
    private int head;
    private String name;
    private String introduce;
    private int focus;
    private int picture;
    private DataBean  data;
    private String process;
    private String schedule;
    private int progressBar;
    private String buy;

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getFocus() {
        return focus;
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(int progressBar) {
        this.progressBar = progressBar;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    private class DataBean {
        private String question;
        private String answer;

        public void setQuestion(String question) {
            this.question = question;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }
}
