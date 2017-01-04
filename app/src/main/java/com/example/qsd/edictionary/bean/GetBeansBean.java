package com.example.qsd.edictionary.bean;

/**
 * Created by QSD on 2016/12/30.
 */

public class GetBeansBean {

    /**
     * code : SUCCESS
     * data : {"rechargeBean":0,"systemGiveBean":0,"consumeBean":92,"restBean":999999908}
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
         * rechargeBean : 0
         * systemGiveBean : 0
         * consumeBean : 92
         * restBean : 999999908
         */

        private int rechargeBean;
        private int systemGiveBean;
        private int consumeBean;
        private int restBean;

        public int getRechargeBean() {
            return rechargeBean;
        }

        public void setRechargeBean(int rechargeBean) {
            this.rechargeBean = rechargeBean;
        }

        public int getSystemGiveBean() {
            return systemGiveBean;
        }

        public void setSystemGiveBean(int systemGiveBean) {
            this.systemGiveBean = systemGiveBean;
        }

        public int getConsumeBean() {
            return consumeBean;
        }

        public void setConsumeBean(int consumeBean) {
            this.consumeBean = consumeBean;
        }

        public int getRestBean() {
            return restBean;
        }

        public void setRestBean(int restBean) {
            this.restBean = restBean;
        }
    }
}
