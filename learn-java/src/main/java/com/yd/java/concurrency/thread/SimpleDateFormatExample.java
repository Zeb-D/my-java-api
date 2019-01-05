package com.yd.java.concurrency.thread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yd on  2018-01-22
 * @Description：
 **/
public class SimpleDateFormatExample {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public String getFormat(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        return dateFormat.format(date);
    }

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void getFormat() {
        synchronized (SIMPLE_DATE_FORMAT) {
            SIMPLE_DATE_FORMAT.format(new Date());
        }
    }

    private static final ThreadLocal DATE_FORMATTER = new ThreadLocal() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
}
