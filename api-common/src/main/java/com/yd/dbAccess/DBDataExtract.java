package com.yd.dbAccess;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据表 数据JDBC抓取
 *
 * @author Yd on  2017-11-29
 * @Description：
 **/
public class DBDataExtract {
    private final static Logger log = LoggerFactory.getLogger(DBDataExtract.class);
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    public static Connection getConnection(String className, String url, String userName, String passWord) {
        try {
            Class.forName(className);
            connection = DriverManager.getConnection(url, userName, passWord);
            if (connection == null) {
                System.out.println("\n connection is null ");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "出错啦" + e.getMessage());
        }
        return connection;
    }

    public static PreparedStatement getMysqlPreparedStatement(String tableName, String columns, Integer pageNo, Integer pageSize) {
        String sql = "select " + columns +
                " from " + tableName +
                " limit " + (pageNo - 1) * pageSize + " , " + pageSize;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "出错啦" + e.getMessage());
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public static PreparedStatement getOraclePreparedStatement(String tableName, String columns, Integer pageNo, Integer pageSize) {
        String sql = "SELECT " + columns + " FROM " +
                "( " +
                "SELECT A.*, ROWNUM RN " +
                "FROM (SELECT * FROM " + tableName + " ) A " +
                "WHERE ROWNUM <= " + pageNo * pageSize
                + " ) " +
                "WHERE RN >= " + ((pageNo - 1) * pageSize + 1);
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "出错啦" + e.getMessage());
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public static ResultSet getResultSet() {
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + "出错啦" + e.getMessage());
            e.printStackTrace();
        }
        return resultSet;
    }

    public static List<TableInfo> getMysqlTableInfo(String tableName) {

        List<TableInfo> list = new ArrayList<TableInfo>();
        try {
            String sql = "select column_name,data_type,character_maximum_length,COLUMN_DEFAULT,ORDINAL_POSITION,is_nullable from information_schema.columns where table_name = ?";
//        String sql = "SELECT * from PUB_ORG_ITEM_MASTER where ITEMCODE='171932'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tableName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                TableInfo info = new TableInfo();
                info.setColumnName(rs.getString(1));
                info.setDataType(rs.getString(2));
                info.setCharacterMaxLength(rs.getString(3));
                info.setColumnNameDefault(rs.getString(4));
                info.setOrdinalPosition(rs.getInt(5));
                info.setNullable(rs.getString(6));
                list.add(info);
            }
//            System.out.println(list);
        } catch (SQLException e) {
            System.out.println("获取MySQL中表" + tableName + "信息出错啦");
            e.printStackTrace();
        }

        return list;
    }

    public static List<TableInfo> getOracleTableInfo(String tableName) {
        List<TableInfo> list = new ArrayList<TableInfo>();
        try {
            String sql = "select column_name,data_type,data_length,DATA_DEFAULT,column_id,NULLABLE FROM user_tab_cols WHERE table_name= ?";
//        String sql = "SELECT * from PUB_ORG_ITEM_MASTER where ITEMCODE='171932'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tableName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                TableInfo info = new TableInfo();
                info.setColumnName(rs.getString(1));
                info.setDataType(rs.getString(2));
                info.setCharacterMaxLength(rs.getString(3));
                info.setColumnNameDefault(rs.getString(4));
                info.setOrdinalPosition(rs.getInt(5));
                info.setNullable(rs.getString(6));
                list.add(info);
            }
//            System.out.println(list);
        } catch (SQLException e) {
            System.out.println("获取Oracle中表" + tableName + "信息出错啦");
            e.printStackTrace();
        }

        return list;
    }

    public static Set<String> getOracleTableInfoDataType() {
        Set<String> dataTypeSet = new HashSet<String>();
        try {
            String sql = "SELECT * FROM DBA_TYPES WHERE OWNER IS NULL";
//        String sql = "SELECT * from PUB_ORG_ITEM_MASTER where ITEMCODE='171932'";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                dataTypeSet.add(rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println("获取Oracle中数据类型出错啦");
            e.printStackTrace();
        }

        return dataTypeSet;
    }

    /**
     * @param cloums <t>—需要查找的字段集 不包含select,以逗号分隔</t>
     * @param table  <t>需要查找的表</t>
     * @return
     */
    public static PreparedStatement getPreparedStatement(String cloums, String table) {
        try {
            preparedStatement = connection.prepareStatement("SELECT  " + cloums + " FROM  " + table);
            ResultSetMetaData resultSetMetaData = preparedStatement.getMetaData();
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return preparedStatement;
    }

    //对结果集 进行输出
    public static StringBuffer resultSetToString(ResultSet rs, List<String> colunms) {
        StringBuffer sb = new StringBuffer("[");
        try {
            while (rs.next()) {
                sb.append("{");
                for (int i = 0; i < colunms.size(); i++) {
                    sb.append(colunms.get(i)).append("=").append(rs.getString(colunms.get(i))).append(",");
                }
                sb = new StringBuffer(sb.substring(0, sb.length() - 1));
                sb.append("},");
            }
            sb = new StringBuffer(sb.substring(0, sb.length() - 1));
            sb.append("]");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb;
    }

    /**
     * 关闭JDBC链接
     */
    public static void close() {
        try {

        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(DBDataLoad.class.getName() + "-" + Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}
