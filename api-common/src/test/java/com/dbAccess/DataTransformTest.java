package com.dbAccess;

import com.yd.dbAccess.CompareUtil;
import com.yd.dbAccess.DBDataExtract;
import com.yd.dbAccess.DBDataLoad;
import com.yd.dbAccess.TableInfo;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 数据迁移测试 原理JDBC
 *
 * @author Yd on  2017-11-29
 * @Description：
 **/
public class DataTransformTest {

    @Test
    public void testExtractAndLoad() {
        //1、先获取 双方的表字段信息；再比较双方 并集
        //2、获取到Oracle 表数据
        //3、将数据插入到MySQL
        DBDataExtract.getConnection("oracle.jdbc.driver.OracleDriver",
                "jdbc:oracle:thin:@106.75.134.252:1521:orac", "serp", "serp#123");
        List<TableInfo> oracleTableInfos = DBDataExtract.getOracleTableInfo("PUB_ORG_ITEM_MASTER");
        List<String> oracleColunms = CompareUtil.getColunms(oracleTableInfos);
        String oracleSql = oracleColunms.toString().substring(1, oracleColunms.toString().length() - 1);
//        System.out.println("oracleSql:"+oracleSql);
        DBDataExtract.getOraclePreparedStatement("PUB_ORG_ITEM_MASTER", oracleSql, 3, 100);
        ResultSet oracleRS = DBDataExtract.getResultSet();


        Connection mysqlCon = DBDataExtract.getConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://139.199.168.194:3306/nerp_goods", "pagoda", "Pagoda@123");
        List<TableInfo> mysqlTableInfos = DBDataExtract.getMysqlTableInfo("goods_org_master");
        List<String> mysqlColunms = CompareUtil.getColunms(mysqlTableInfos);
        String mysqlSql = mysqlColunms.toString().substring(1, mysqlColunms.toString().length() - 1);

//        CompareUtil.containCollection(Arrays.asList(oracleSql.toLowerCase().split(",")), Arrays.asList(mysqlSql.toLowerCase().split(",")));
        Map<String, String> oracleToMysql = CompareUtil.containCollection(oracleColunms, mysqlColunms);
        System.out.println("oracleToMysqlMap:" + oracleToMysql);
//        System.out.println("oracleRS:"+DBDataExtract.resultSetToString(oracleRS, oracleColunms));
        DBDataLoad.DataLoad(oracleToMysql, mysqlCon, oracleRS, "goods_org_master");

        DBDataExtract.close();
    }

    @Test
    public void testGetTableInfo() {
        DBDataExtract.getConnection("oracle.jdbc.driver.OracleDriver",
                "jdbc:oracle:thin:@106.75.134.252:1521:orac", "serp", "serp#123");
        DBDataExtract.getOracleTableInfo("inv_depot_period");
        DBDataExtract.close();
    }

    @Test
    public void testMysqlResult() {
        DBDataExtract.getConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://139.199.168.194:3306/nerp_goods", "pagoda", "Pagoda@123");
        String columns = "GOODSCODE,ORGCODE,ENTERPRISECODE,WAREHOUSECODE,ORDBATCHQTY";
        String[] columnsArray = columns.split(",");
        DBDataExtract.getMysqlPreparedStatement("goods_org_master", columns,
                1, 5);
        ResultSet rs = DBDataExtract.getResultSet();
        System.out.println("rs:" + DBDataExtract.resultSetToString(rs, Arrays.asList(columnsArray)));
        DBDataExtract.close();
    }

    @Test
    public void testOracleResult() {
        DBDataExtract.getConnection("oracle.jdbc.driver.OracleDriver",
                "jdbc:oracle:thin:@106.75.134.252:1521:orac", "serp", "serp#123");
        String columns = "ITEMCODE,ORGCODE,ENTERPRISECODE,WAREHOUSECODE,ORDBATCHQTY";
        String[] columnsArray = columns.split(",");
        DBDataExtract.getOraclePreparedStatement("PUB_ORG_ITEM_MASTER", columns,
                2, 100);
        ResultSet rs = DBDataExtract.getResultSet();
        System.out.println("rs:" + DBDataExtract.resultSetToString(rs, Arrays.asList(columnsArray)));
        DBDataExtract.close();
    }

    @Test
    public void close() {
        DBDataExtract.close();
    }

    @Test
    public void getOracleDataType(){
        DBDataExtract.getConnection("oracle.jdbc.driver.OracleDriver",
                "jdbc:oracle:thin:@106.75.134.252:1521:orac", "serp", "serp#123");
        System.out.println("rs:" + DBDataExtract.getOracleTableInfoDataType());
    }
}
