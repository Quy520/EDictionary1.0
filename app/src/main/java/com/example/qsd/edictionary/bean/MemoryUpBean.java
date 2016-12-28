package com.example.qsd.edictionary.bean;

import java.util.List;

/**
 * Created by QSD on 2016/12/27.
 */

public class MemoryUpBean {

    /**
     * code : SUCCESS
     * data : [{"linkUrl":"https://mp.weixin.qq.com/s/vA4JdmntNAZFFqrY2IfMYA","topImageID":4,"topImageUrl":"http://img03.tooopen.com/images/20131102/sy_45237377439.jpg","topTitle":"test4"},
     * {"linkUrl":"https://mp.weixin.qq.com/s/vsjvbWx_RQ2gb9QrDX4Y2g","topImageID":3,"topImageUrl":"https://www.baidu.com/img/bd_logo1.png","topTitle":"test3"},
     * {"linkUrl":"http://mp.weixin.qq.com/s/llM5GA5INcGpA2qV5JqiIg","topImageID":2,"topImageUrl":"http://mmbiz.qpic.cn/mmbiz_gif/rKNjS0fWmxAY68Ycjk6TXorH5hDZR7AnkF5wNDWBicHJb2hibuWlJBgZicvByqyiap4Du90gssUWNJGgXRtRPtZDVg/0?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1","topTitle":"test2"},
     * {"linkUrl":"http://mp.weixin.qq.com/s/llM5GA5INcGpA2qV5JqiIg","topImageID":1,"topImageUrl":"http://mmbiz.qpic.cn/mmbiz_gif/rKNjS0fWmxAY68Ycjk6TXorH5hDZR7An70gnePCWBHiboagpov6K6jWR6pW0ucL0bcwxa56kLphoiahFUZicvnPOg/0?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1","topTitle":"test1"}]
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
         * linkUrl : https://mp.weixin.qq.com/s/vA4JdmntNAZFFqrY2IfMYA
         * topImageID : 4
         * topImageUrl : http://img03.tooopen.com/images/20131102/sy_45237377439.jpg
         * topTitle : test4
         */

        private String linkUrl;
        private int topImageID;
        private String topImageUrl;
        private String topTitle;

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public int getTopImageID() {
            return topImageID;
        }

        public void setTopImageID(int topImageID) {
            this.topImageID = topImageID;
        }

        public String getTopImageUrl() {
            return topImageUrl;
        }

        public void setTopImageUrl(String topImageUrl) {
            this.topImageUrl = topImageUrl;
        }

        public String getTopTitle() {
            return topTitle;
        }

        public void setTopTitle(String topTitle) {
            this.topTitle = topTitle;
        }
    }
}
