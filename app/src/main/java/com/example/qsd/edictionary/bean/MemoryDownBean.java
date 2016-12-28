package com.example.qsd.edictionary.bean;

import java.util.List;

/**
 * Created by QSD on 2016/12/27.
 */

public class MemoryDownBean {


    /**
     * code : SUCCESS
     * data : [{"courseID":1,"courseImageUrl":"wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1","courseInstructions":"这是一部很短的视频","courseName":"记忆法课程","coursePayStatus":0,"coursePrice":20,"courseTitle":"这是一部很不错的视频","courseVideo":"http://baobab.wdjcdn.com/14564977406580.mp4"},
     * {"courseID":2,"courseImageUrl":"wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1","courseInstructions":"这是一部高清短片","courseName":"记纸牌","coursePayStatus":0,"coursePrice":30,"courseTitle":"这是一部高清视频","courseVideo":"http://baobab.wdjcdn.com/1456480115661mtl.mp4"},
     * {"courseID":3,"courseImageUrl":"https://www.baidu.com/img/bd_logo1.png","courseInstructions":"这个可以有","courseName":"记圆周率","coursePayStatus":0,"coursePrice":40,"courseTitle":"这个可以有","courseVideo":"http://baobab.wdjcdn.com/1456665467509qingshu.mp4"},
     * {"courseID":4,"courseImageUrl":"http://img03.tooopen.com/images/20131102/sy_45237377439.jpg","courseInstructions":"很不错","courseName":"记弟子规","coursePayStatus":0,"coursePrice":2,"courseTitle":"very good","courseVideo":"http://baobab.wdjcdn.com/1456231710844S(24).mp4"}]
     */

    private String code;
    private List<DataBean> data;

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
         * courseID : 1
         * courseImageUrl : wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1
         * courseInstructions : 这是一部很短的视频
         * courseName : 记忆法课程
         * coursePayStatus : 0
         * coursePrice : 20
         * courseTitle : 这是一部很不错的视频
         * courseVideo : http://baobab.wdjcdn.com/14564977406580.mp4
         */

        private int courseID;
        private String courseImageUrl;
        private String courseInstructions;
        private String courseName;
        private int coursePayStatus;
        private int coursePrice;
        private String courseTitle;
        private String courseVideo;

        public int getCourseID() {
            return courseID;
        }

        public void setCourseID(int courseID) {
            this.courseID = courseID;
        }

        public String getCourseImageUrl() {
            return courseImageUrl;
        }

        public void setCourseImageUrl(String courseImageUrl) {
            this.courseImageUrl = courseImageUrl;
        }

        public String getCourseInstructions() {
            return courseInstructions;
        }

        public void setCourseInstructions(String courseInstructions) {
            this.courseInstructions = courseInstructions;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getCoursePayStatus() {
            return coursePayStatus;
        }

        public void setCoursePayStatus(int coursePayStatus) {
            this.coursePayStatus = coursePayStatus;
        }

        public int getCoursePrice() {
            return coursePrice;
        }

        public void setCoursePrice(int coursePrice) {
            this.coursePrice = coursePrice;
        }

        public String getCourseTitle() {
            return courseTitle;
        }

        public void setCourseTitle(String courseTitle) {
            this.courseTitle = courseTitle;
        }

        public String getCourseVideo() {
            return courseVideo;
        }

        public void setCourseVideo(String courseVideo) {
            this.courseVideo = courseVideo;
        }
    }
}
