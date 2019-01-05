package com.yd.dbAccess;

import com.sun.istack.NotNull;

import java.io.*;
import java.util.List;

/**
 * @author Yd on  2018-01-18
 * @Description：
 **/
public class CreateEntity {

    public static final String DB_Oracle_NOT_NULL = "N";//not null
    public static final String DB_MySql_NOT_NULL = "NO";//not null

    public static final String File_SuffixName = ".java";//文件后缀名
    public static final String File_WorkSpace_Dir = System.getProperty("user.dir");
    public static final String File_Project_Dir = File_WorkSpace_Dir + "\\operate-Log";
    public static final String File_Path = File_Project_Dir + "\\src\\main\\java";
    public static final String File_Target_Package = "/com/yd/entity/auto/";


    public static void main(String[] args) {
        String fileName = "GoodsOrgMaster";
        File file = createFile(fileName);
        writeFileContent(file,"abc");
        writeFileContent(file,"abc1");

    }

    public static void createEntityByOracle(@NotNull String tableName,@NotNull List<TableInfo> tableInfos) {


    }

    public static File createFile(@NotNull String tableName) {
        File packagePath = new File(File_Path + File_Target_Package);
        packagePath.delete();
        if (!packagePath.exists()) {
            System.out.println("创建package情况：" + packagePath.mkdirs());
        } else {
            packagePath.delete();
            System.out.println("存在目录：" + packagePath.getAbsolutePath());
        }

        File file = new File(File_Path + File_Target_Package + tableName + File_SuffixName);
        file.delete();
        try {
            if (!file.exists()) {
                System.out.println("创建file情况：" + file.createNewFile());
            } else {
                System.out.println("存在目录：" + file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    public static void writeFileContent(File file, String content) {
        if (file == null || !file.exists()) {
            throw new RuntimeException("the file is not exists!");
        }
        FileOutputStream fos = null;
        try {
            FileWriter fileWritter = new FileWriter(file.getName(),true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(content);
            bufferWritter.flush();
            fileWritter.flush();
            bufferWritter.close();
            fileWritter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
