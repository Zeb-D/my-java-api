package com.yd.java.jvm;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Zeb灬D on 2018/1/17
 * Description:
 */
public class InfluxdbTest {

    public static InfluxDB influxDB;
    public static String dbName = "appXX";
    public static String measurementName = "test-url";
    public static String server = "http://127.0.0.1:8086";

    public static void main(String[] args) {
        initInfluxDB();

//        for (int i = 0 ; i < Integer.MAX_VALUE; i ++){
//            int minCount = 3;
//            int maxCount = 10;
//            int minSum = 50;
//            int maxSum = 100;
//
//            String[] provinces = {"jiangxi","guangdong","yunnan"};
//            String[][] cities = {{"nanchang","ganzhou"},{"shenzhen","guangzhou"},{"guilin","kunming"}};
//            int provinceIndex = 0 + (int)(Math.random() * (2-0+1));
//            int cityIndex = 0 + (int)(Math.random() * (1-0+1));
//
//            int randomCount = minCount + (int)(Math.random() * (maxCount-minCount+1));
//            int randomSum = minSum + (int)(Math.random() * (maxSum-minSum+1));
//
//            Point point = Point.measurement(measurementName)
//                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
//                    .tag("country","CN")
//                    .tag("province",provinces[provinceIndex])
//                    .tag("city",cities[provinceIndex][cityIndex])
//                    .addField("COUNT", randomCount)
//                    .addField("SUM", randomSum)
//                    .build();
//            influxDB.setDatabase(dbName);
//            influxDB.setRetentionPolicy("rp-7d-test");
//            influxDB.write(point);
//            System.err.println(point);
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }


//        for (int i = 0 ; i < Integer.MAX_VALUE; i ++){
//            int minCount = 3;
//            int maxCount = 10;
//            int minSum = 50;
//            int maxSum = 100;
//
//            String[] countries = {"CN","USA","UK","JPAN","FRANCE","INDIA"};
//
//            int countryIndex = 0 + (int)(Math.random() * (5-0+1));
//
//            int randomCount = minCount + (int)(Math.random() * (maxCount-minCount+1));
//            int randomSum = minSum + (int)(Math.random() * (maxSum-minSum+1));
//
//            Point point = Point.measurement(measurementName)
//                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
//                    .tag("country",countries[countryIndex])
//                    .addField("COUNT", randomCount)
//                    .addField("SUM", randomSum)
//                    .build();
//            influxDB.setDatabase(dbName);
//            influxDB.setRetentionPolicy("rp-7d-test");
//            influxDB.write(point);
//            System.err.println(point);
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        long time1 = 1519457100000L;
        Point point = Point.measurement(measurementName)
                .time(time1, TimeUnit.MILLISECONDS)
                .tag("url", "www.2.com")
                .addField("count", 1)
                .addField("urlField", "www.2.com")
                .build();
        Point point2 = Point.measurement(measurementName)
                .time(time1, TimeUnit.MILLISECONDS)
                .tag("url", "www.3.com")
                .addField("count", 5)
                .addField("urlField", "www.3.com")
                .build();
        Point point3 = Point.measurement(measurementName)
                .time(time1, TimeUnit.MILLISECONDS)
                .tag("url", "www.4.com")    //  唯一索引
                .addField("count", 9)
                .addField("urlField", "www.4.com")
                .build();
        createRetentionPolicy(dbName);
        influxDB.setDatabase(dbName);
        influxDB.write(point);
        influxDB.write(point2);
        influxDB.write(point3);

        String command = " select * from  \"test-url\""; // + measurementName;
        QueryResult results = query(command, dbName);

        if (results.getResults() == null) {
            return;
        }
        System.out.println(results.getResults());
        for (QueryResult.Result result : results.getResults()) {

            List<QueryResult.Series> series = result.getSeries();
            for (QueryResult.Series serie : series) {
                System.out.println(serie.getColumns());
                System.out.println(serie.getValues());
            }
        }
    }

    public static void initInfluxDB() {
        influxDB = InfluxDBFactory.connect(server);

        List<String> databases = influxDB.describeDatabases();
        if (!databases.contains(dbName)) {
            influxDB.createDatabase(dbName);
            //defalut 策略名 /database 数据库名/ 30d 数据保存时限30天/ 1  副本个数为1/ 结尾DEFAULT 表示 设为默认的策略
            influxDB.createRetentionPolicy("rp-1d-test", dbName, "1d", "30m", 2, true);
        }
        //log.info("influxDB init finshed! server:[{}],DATABASE:[{}]",server,DATABASE);
    }

    /**
     * 设置数据保存策略
     * defalut 策略名 /database 数据库名/ 30d 数据保存时限30天/ 1  副本个数为1/ 结尾DEFAULT 表示 设为默认的策略
     */
    public static void createRetentionPolicy(String database) {
        String command = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT",
                "defalut", database, "30d", 1);
        query(command, database);
    }

    /**
     * 查询
     *
     * @param command 查询语句
     * @return
     */
    public static QueryResult query(String command, String database) {
        return influxDB.query(new Query(command, database));
    }

    /**
     * 插入
     *
     * @param measurement 表
     * @param tags        标签
     * @param fields      字段
     */
    public void insert(String database, String measurement, Map<String, String> tags, Map<String, Object> fields) {
        Point.Builder builder = Point.measurement(measurement);
        builder.tag(tags);
        builder.fields(fields);

        influxDB.write(database, "", builder.build());
    }

    /**
     * 批量写入测点
     *
     * @param batchPoints
     */
    public static void batchInsert(BatchPoints batchPoints) {
        influxDB.write(batchPoints);
        // influxDB.enableGzip();
        // influxDB.enableBatch(2000,100,TimeUnit.MILLISECONDS);
        // influxDB.disableGzip();
        // influxDB.disableBatch();
    }

    /**
     * 删除
     *
     * @param command 删除语句
     * @return 返回错误信息
     */
    public String deleteMeasurementData(String command, String database) {
        QueryResult result = influxDB.query(new Query(command, database));
        return result.getError();
    }
}