package com.yd.common;

import com.yd.common.util.format.BaseFormatEnum;

/**
 * @author Yd on  2018-01-15
 * @Description：
 **/
public class EnumSummry {

    public enum LogOperType {
        ALL(""),

        UPDATE("修改"),

        DELETE("删除"),

        INSERT("新增");

        private String value;

        LogOperType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    public enum YorN implements BaseFormatEnum {
        //yes
        Y("是"),
        //no
        N("否");

        private String value;

        YorN(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String format(String key){
            return YorN.valueOf(key).getValue();
        }

        @Override
        public String toString(){
            return value;
        }
    }

    public enum isEnable {
        //Y-是
        Y("Y", "是"),
        //N-否
        N("N", "否");


        private String key;
        private String value;

        isEnable(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
