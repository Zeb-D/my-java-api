package com.yd.poi.bean;

import java.util.Date;

/**
 * Created by Yd on 2018/7/6.
 */
public class PosTip {
    //小票号	收款台号	收款员代码 交易日期	 收款价格  支付方式

    private String number;

    private String tableNum;

    private String code;

    private Date dealDate;

    private Double price;

    private int payMode;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTableNum() {
        return tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getPayMode() {
        return payMode;
    }

    public void setPayMode(int payMode) {
        this.payMode = payMode;
    }
}
