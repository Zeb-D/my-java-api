package com.yd.java.jvm;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;

/**
 * 概念	MySQL	InfluxDB
 * 数据库（同）	database	database
 * 表（不同）	table	measurement
 * 列（不同）	column	tag(带索引的，非必须)、field(不带索引)、timestemp(唯一主键)
 * -- 查看所有的数据库
 * show databases;
 * -- 使用特定的数据库
 * use database_name;
 * -- 查看所有的measurement
 * show measurements;
 * -- 查询10条数据
 * select * from measurement_name limit 10;
 * -- 数据中的时间字段默认显示的是一个纳秒时间戳，改成可读格式
 * precision rfc3339; -- 之后再查询，时间就是rfc3339标准格式
 * -- 或可以在连接数据库的时候，直接带该参数
 * influx -precision rfc3339
 * -- 查看一个measurement中所有的tag key
 * show tag keys
 * -- 查看一个measurement中所有的field key
 * show field keys
 * -- 查看一个measurement中所有的保存策略(可以有多个，一个标识为default)
 * show retention policies;
 * Created by Zeb灬D on 2017/11/23.
 */
public class CommandTest {

    public static void main(String[] args) {
        InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086", "root", "root");
        String dbName = "test_name";
        String measurement = "test_table";

        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("hostname", "server01")
                .tag("place", "2")
                //.retentionPolicy("default")   //{"error":"retention policy not found: default"}
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();

        long now = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            Point point = Point.measurement(measurement)
                    .time(now + 60000 * i, TimeUnit.MILLISECONDS)
                    .addField("value1", new Float(i))
                    .addField("value2", "feng")
                    .build();
            batchPoints.point(point);
        }


        influxDB.write(batchPoints);
        System.out.println("OK");
    }
}
