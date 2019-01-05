package com.yd.dbAccess;

import lombok.Data;

/**
 * 数据库表信息
 * <p>
 * MySQL数据库系统表 from information.colums where table_name =''
 * Oracle DB: FROM user_tab_cols WHERE table_name=
 * </p>
 *
 * @author Yd on  2017-11-29
 * @Description：
 **/
@Data
public class TableInfo {
    private String columnName;//字段名
    private String dataType;//数据类型
    private String characterMaxLength;//字段长度
    private String columnNameDefault;//默认字段名
    private String nullable;//是否为空
    private Integer ordinalPosition;//字段对应下标

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCharacterMaxLength() {
        return characterMaxLength;
    }

    public void setCharacterMaxLength(String characterMaxLength) {
        this.characterMaxLength = characterMaxLength;
    }

    public String getColumnNameDefault() {
        return columnNameDefault;
    }

    public void setColumnNameDefault(String columnNameDefault) {
        this.columnNameDefault = columnNameDefault;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public Integer getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(Integer ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }
}
