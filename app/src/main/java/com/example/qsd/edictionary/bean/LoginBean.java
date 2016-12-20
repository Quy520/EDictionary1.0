package com.example.qsd.edictionary.bean;

/**
 * Created by QSD on 2016/12/19.
 */

public class LoginBean {


    /**
     * code : SUCCESS
     * data : {"studyBean":0,"nickName":"qsd","headImageUrl":"http://www.dozer.cc/assets/image/avatar.jpg","costStudyBean":100,"studyCode":"EEFFEW","loginStatus":1,"userID":2}
     */

    private String code;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * studyBean : 0
         * nickName : qsd
         * headImageUrl : http://www.dozer.cc/assets/image/avatar.jpg
         * costStudyBean : 100
         * studyCode : EEFFEW
         * loginStatus : 1
         * userID : 2
         */

        private int studyBean;
        private String nickName;
        private String headImageUrl;
        private int costStudyBean;
        private String studyCode;
        private int loginStatus;
        private int userID;

        public int getStudyBean() {
            return studyBean;
        }

        public void setStudyBean(int studyBean) {
            this.studyBean = studyBean;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadImageUrl() {
            return headImageUrl;
        }

        public void setHeadImageUrl(String headImageUrl) {
            this.headImageUrl = headImageUrl;
        }

        public int getCostStudyBean() {
            return costStudyBean;
        }

        public void setCostStudyBean(int costStudyBean) {
            this.costStudyBean = costStudyBean;
        }

        public String getStudyCode() {
            return studyCode;
        }

        public void setStudyCode(String studyCode) {
            this.studyCode = studyCode;
        }

        public int getLoginStatus() {
            return loginStatus;
        }

        public void setLoginStatus(int loginStatus) {
            this.loginStatus = loginStatus;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }
    }
}
