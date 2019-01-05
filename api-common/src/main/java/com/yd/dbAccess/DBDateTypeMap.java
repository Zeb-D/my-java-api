package com.yd.dbAccess;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Yd on  2018-01-17
 * @Description：
 **/
public class DBDateTypeMap {
    private static final Map<String,String> oracleMapper = new HashMap<String, String>();

    static {
//            PL/SQL NATURAL, BLOB, PL/SQL PLS INTEGER, DECIMAL, RAW, TIME WITH TZ, SIGNED BINARY INTEGER(32), TABLE, UROWID, INTERVAL YEAR TO MONTH, NAMED COLLECTION, TIMESTAMP, PL/SQL STRING, INTEGER, PL/SQL REF CURSOR, SIGNED BINARY INTEGER(8), UNSIGNED BINARY INTEGER(32), NUMBER, CANONICAL, PL/SQL LONG, OID, UNSIGNED BINARY INTEGER(16), VARCHAR2, INTERVAL DAY TO SECOND, DATE, REAL, VARCHAR, TIMESTAMP WITH LOCAL TZ, BINARY_DOUBLE, PL/SQL POSITIVEN, PL/SQL BINARY INTEGER, PL/SQL COLLECTION, FLOAT, NAMED OBJECT, BINARY ROWID, UNSIGNED BINARY INTEGER(8), CHAR, PL/SQL NATURALN, DOUBLE PRECISION, PL/SQL BOOLEAN, VARYING ARRAY, TIMESTAMP WITH TZ, OCTET, PL/SQL POSITIVE, LOB POINTER, PL/SQL ROWID, CLOB, CONTIGUOUS ARRAY, PL/SQL RECORD, TIME, CFILE, REF, SIGNED BINARY INTEGER(16), PL/SQL LONG RAW, BINARY_FLOAT, POINTER, SMALLINT, BFILE
    }

    public static Map<String,String> addDataType(Set<String> dataTypeSet){
        Iterator<String> iterator = dataTypeSet.iterator();
        while (iterator.hasNext()){
            String dataType = iterator.next();
            switch (dataType.trim()){
                case "DECIMAL":
                    oracleMapper.put(dataType.trim(),BigDecimal.class.getSimpleName());break;
                case "CHAR":
                    oracleMapper.put(dataType.trim(),Character.class.getSimpleName());break;
                case "VARCHAR2":
                case "VARCHAR":
                    oracleMapper.put(dataType.trim(),String.class.getSimpleName());break;
                case "TIMESTAMP":
                case "DATE":
                case "TIME":
                    oracleMapper.put(dataType.trim(),Date.class.getSimpleName());break;
                case "INTEGER":
                case "SIGNED BINARY INTEGER(8)":
                case "UNSIGNED BINARY INTEGER(32)":
                    oracleMapper.put(dataType.trim(),Date.class.getSimpleName());break;
                case "FLOAT":
                    oracleMapper.put(dataType.trim(),Float.class.getSimpleName());break;
                case "NUMBER":
                    oracleMapper.put(dataType.trim(),Double.class.getSimpleName());break;
                case "PL/SQL NATURAL":
                case "BLOB":
                case "PL/SQL PLS INTEGER":
                case "RAW":
                case "TIME WITH TZ":
                case "SIGNED BINARY INTEGER(32)":
                case "TABLE":
                case "UROWID":
                case "YEAR TO MONTH":
                case "INTERVAL":
                case "NAMED COLLECTION":
                case "PL/SQL STRING":
                case "PL/SQL REF CURSOR":
                case "CANONICAL":
                case "PL/SQL LONG":
                case "OID":
                case "UNSIGNED BINARY INTEGER(16)":
                case "INTERVAL DAY TO SECOND":
                case "REAL":
                case "TIMESTAMP WITH LOCAL TZ":
                case "BINARY_DOUBLE":
                case "PL/SQL POSITIVEN":
                case "PL/SQL BINARY INTEGER":
                case "PL/SQL COLLECTION":
                case "NAMED OBJECT":
                case "BINARY ROWID":
                case "UNSIGNED BINARY INTEGER(8)":
                case "PL/SQL NATURALN":
                case "DOUBLE PRECISION":
                case "PL/SQL BOOLEAN":
                case "VARYING ARRAY":
                case "TIMESTAMP WITH TZ":
                case "OCTET":
                case "PL/SQL POSITIVE":
                case "LOB POINTER":
                case "PL/SQL ROWID":
                case "CLOB":
                case "CONTIGUOUS ARRAY":
                case "PL/SQL RECORD":
                case "CFILE":
                case "BFILE":
                case "REF":
                case "SIGNED BINARY INTEGER(16)":
                case "PL/SQL LONG RAW":
                case "BINARY_FLOAT":
                case "POINTER":
                case "SMALLINT":
                    oracleMapper.put(dataType.trim(),UnknownDataType.class.getSimpleName());break;
                default:
                    oracleMapper.put(dataType.trim(),UnknownDataType.class.getSimpleName());
            }
        }
        return oracleMapper;
    }

}
