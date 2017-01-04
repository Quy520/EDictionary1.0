package com.example.qsd.edictionary.bean;

import java.util.List;

/**
 * Created by QSD on 2016/12/29.
 */

public class CourseVedioBean {

    /**
     * code : SUCCESS
     * data : [{"productType":0,"videoDetail":"很不错","videoID":9,"videoImageUrl":"http://img03.tooopen.com/images/20131102/sy_45237377439.jpg","videoName":"小学五年级上一年级上【沪教版】","videoPrice":2,"videoTime":9999,"videoUrl":"http://baobab.wdjcdn.com/1456231710844S(24).mp4","videoWordNum":0},{"productType":0,"videoDetail":"很不错","videoID":10,"videoImageUrl":"http://img03.tooopen.com/images/20131102/sy_45237377439.jpg","videoName":"小学五年级上一年级下【沪教版】","videoPrice":2,"videoTime":9999,"videoUrl":"http://baobab.wdjcdn.com/1456231710844S(24).mp4","videoWordNum":0}]
     */

    private String code;
    public List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * productType : 0
         * videoDetail : 很不错
         * videoID : 9
         * videoImageUrl : http://img03.tooopen.com/images/20131102/sy_45237377439.jpg
         * videoName : 小学五年级上一年级上【沪教版】
         * videoPrice : 2
         * videoTime : 9999
         * videoUrl : http://baobab.wdjcdn.com/1456231710844S(24).mp4
         * videoWordNum : 0
         */

        private int productType;
        private String videoDetail;
        private int videoID;
        private String videoImageUrl;
        private String videoName;
        private int videoPrice;
        private int videoTime;
        private String videoUrl;
        private int videoWordNum;

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
        }

        public String getVideoDetail() {
            return videoDetail;
        }

        public void setVideoDetail(String videoDetail) {
            this.videoDetail = videoDetail;
        }

        public int getVideoID() {
            return videoID;
        }

        public void setVideoID(int videoID) {
            this.videoID = videoID;
        }

        public String getVideoImageUrl() {
            return videoImageUrl;
        }

        public void setVideoImageUrl(String videoImageUrl) {
            this.videoImageUrl = videoImageUrl;
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public int getVideoPrice() {
            return videoPrice;
        }

        public void setVideoPrice(int videoPrice) {
            this.videoPrice = videoPrice;
        }

        public int getVideoTime() {
            return videoTime;
        }

        public void setVideoTime(int videoTime) {
            this.videoTime = videoTime;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public int getVideoWordNum() {
            return videoWordNum;
        }

        public void setVideoWordNum(int videoWordNum) {
            this.videoWordNum = videoWordNum;
        }
    }
}
