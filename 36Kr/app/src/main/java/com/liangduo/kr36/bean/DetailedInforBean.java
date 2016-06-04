package com.liangduo.kr36.bean;

/**
 * Created by liangduo on 16/5/21.
 */
public class DetailedInforBean {
    /**
     * code : 0
     * data : {"summary":"我们是否有点高兴的太早了？","publishTime":1463144841000,"updateTime":1463187696000,"columnId":69,"commentCount":1,"featureImg":"http://a.36krcnd.com/nil_class/ddaadd12-3196-4686-a295-2ccf32bd763b/4444.jpg","type":"article","postId":5046975,"content":"","favoriteCount":4,"myFavorites":false,"columnName":"资本","user":{"avatar":"https://krplus-pic.b0.upaiyun.com/201603/18081152/cnlr4o0wtxehireu.jpg!480","name":"尹靖霏","ssoId":590130},"viewCount":1653}
     * msg : 操作成功！
     */

    private int code;
    /**
     * summary : 我们是否有点高兴的太早了？
     * publishTime : 1463144841000
     * updateTime : 1463187696000
     * columnId : 69
     * commentCount : 1
     * featureImg : http://a.36krcnd.com/nil_class/ddaadd12-3196-4686-a295-2ccf32bd763b/4444.jpg
     * type : article
     * postId : 5046975
     * content :
     * favoriteCount : 4
     * myFavorites : false
     * columnName : 资本
     * user : {"avatar":"https://krplus-pic.b0.upaiyun.com/201603/18081152/cnlr4o0wtxehireu.jpg!480","name":"尹靖霏","ssoId":590130}
     * viewCount : 1653
     */

    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private String summary;
        private long publishTime;
        private long updateTime;
        private int columnId;
        private String title;
        private int commentCount;
        private String featureImg;
        private String type;
        private int postId;
        private String content;
        private int favoriteCount;
        private boolean myFavorites;
        private String columnName;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * avatar : https://krplus-pic.b0.upaiyun.com/201603/18081152/cnlr4o0wtxehireu.jpg!480
         * name : 尹靖霏
         * ssoId : 590130
         */

        private UserBean user;
        private int viewCount;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getColumnId() {
            return columnId;
        }

        public void setColumnId(int columnId) {
            this.columnId = columnId;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getFeatureImg() {
            return featureImg;
        }

        public void setFeatureImg(String featureImg) {
            this.featureImg = featureImg;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(int favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public boolean isMyFavorites() {
            return myFavorites;
        }

        public void setMyFavorites(boolean myFavorites) {
            this.myFavorites = myFavorites;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public static class UserBean {
            private String avatar;
            private String name;
            private int ssoId;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSsoId() {
                return ssoId;
            }

            public void setSsoId(int ssoId) {
                this.ssoId = ssoId;
            }
        }
    }
}


