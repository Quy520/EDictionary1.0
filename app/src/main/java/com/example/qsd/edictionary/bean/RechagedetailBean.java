package com.example.qsd.edictionary.bean;

import java.util.List;

/**
 * Created by QSD on 2017/1/3.
 */

public class RechagedetailBean {

    /**
     * data : [{"rechargeMoney":40,"rechargeTime":"2016-12-30 18:41:21.0","rechargeType":"支付宝"}]
     * code : SUCCESS
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
         * rechargeMoney : 40
         * rechargeTime : 2016-12-30 18:41:21.0
         * rechargeType : 支付宝
         */

        private int rechargeMoney;
        private String rechargeTime;
        private String rechargeType;

        public int getRechargeMoney() {
            return rechargeMoney;
        }

        public void setRechargeMoney(int rechargeMoney) {
            this.rechargeMoney = rechargeMoney;
        }

        public String getRechargeTime() {
            return rechargeTime;
        }

        public void setRechargeTime(String rechargeTime) {
            this.rechargeTime = rechargeTime;
        }

        public String getRechargeType() {
            return rechargeType;
        }

        public void setRechargeType(String rechargeType) {
            this.rechargeType = rechargeType;
        }
    }
}
