package com.yd.poi.bean;

/**
 * Created by Yd on 2018/7/10.
 */
public enum PayMode {
    /**  */
    ali(0, "支付宝"),

    /** 增加 */
    wx(1, "微信");

    private Integer value;
    private String description;

    PayMode(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
