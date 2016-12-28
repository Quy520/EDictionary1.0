package com.example.qsd.edictionary.bean;

import java.util.List;

/**
 * Created by QSD on 2016/12/28.
 */

public class WordsBean {

    /**
     * code : SUCCESS
     * data : [{"sectionName":"小学","sectionDetail":[{"classifyName":"五年级上【沪教版】","id":5},{"classifyName":"五年级下【沪教版】","id":6}]}]
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
         * sectionName : 小学
         * sectionDetail : [{"classifyName":"五年级上【沪教版】","id":5},{"classifyName":"五年级下【沪教版】","id":6}]
         */

        private String sectionName;
        private List<SectionDetailBean> sectionDetail;

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public List<SectionDetailBean> getSectionDetail() {
            return sectionDetail;
        }

        public void setSectionDetail(List<SectionDetailBean> sectionDetail) {
            this.sectionDetail = sectionDetail;
        }

        public static class SectionDetailBean {
            /**
             * classifyName : 五年级上【沪教版】
             * id : 5
             */

            private String classifyName;
            private int id;

            public String getClassifyName() {
                return classifyName;
            }

            public void setClassifyName(String classifyName) {
                this.classifyName = classifyName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
