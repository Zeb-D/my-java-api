package com.yd.java.jvm;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;

/**
 * Created by Zeb灬D on 2017/11/23.
 */
public class CommandTest {

    public static void main(String[] args) {
        InfluxDB influxDB = InfluxDBFactory.connect("http://192.168.0.1:8086", "root", "root");
        String dbName = "test_name";
        String measurement = "test_table";

        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("hostname", "server02")
                .tag("place", "2")
                .retentionPolicy("default")
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
