package com.yd.dbAccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * 数据填充到目标数据库表中
 *
 * @author Yd on  2017-11-30
 * @Description：
 **/
public class DBDataLoad {
    private final static Logger log = LoggerFactory.getLogger(DBDataLoad.class);
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    //先从oracle 中取到数据Rs
    //然后连接MySQL 执行add
    //map 放 Oracle字段-MySQL字段
    public static void DataLoad(Map<String, String> dbColunms, Connection mysqlCon, ResultSet oracleRS, String tableName) {
        System.out.println(dbColunms.values().toString());
        Set<String> set = dbColunms.keySet();
        StringBuffer sb = new StringBuffer();
        sb.append("insert into " + tableName);
        sb.append("(");
        sb.append(dbColunms.values().toString().substring(1, dbColunms.values().toString().length() - 1));//不管是ForEach 还是ToString 得到的拼接还是一样
        sb.append(" ) values");
        int count = sb.length();
//        sb.append();
        try {
            while (oracleRS.next()) {
                sb.append("(");
                for (String colunm : set) {
                    if (oracleRS.getString(colunm) == null) {//null 不需要转换成 字符串
                        sb.append(oracleRS.getString(colunm) + ",");
                    } else {
                        sb.append("'" + oracleRS.getString(colunm) + "'" + ",");
                    }

                }
                sb = new StringBuffer(sb.substring(0, sb.length() - 1));
                sb.append("),");
            }
            if (count != sb.length()) {
                sb = new StringBuffer(sb.substring(0, sb.length() - 1));
            }

            System.out.println("++++MySQL:" + sb);
            // 设置事务为非自动提交
            mysqlCon.setAutoCommit(false);
            PreparedStatement mysqlPS = mysqlCon.prepareStatement(sb.toString());//executeUpdate(sb.toString());
            int ok = mysqlPS.executeUpdate();
            // 提交事务
            mysqlCon.commit();
            System.out.println("insert into OK?=" + ok);
        } catch (SQLException e) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "出错啦" + e.getMessage());
        }

    }
}
