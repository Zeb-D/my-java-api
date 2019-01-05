package com.yd.java.jdk.nio;


import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Path接口是Java NIO2 的一部分，是对Java6 和Java7的 NIO的更新。
 * Java的Path接口在Java7 中被添加到Java NIO，位于java.nio.file包中， 其全路径是java.nio.file.Path。
 *
 * 一个Path实例代表了一个文件系统中的路径。一个路径可以指向一个文件或者一个文件夹。
 * 一个路径可以是绝对路径或者是相对路径。
 * 绝对路径是从根路径开始的全路径，相对路径是一个相对其他路径的文件或文件夹路径。
 * @author Yd on 2018-06-20
 * @description
 *
 * 在很多地方java.nio.file.Path接口和java.io.File类是相似的，但是它们有几个主要的不同。 在很多类中，你可以使用Path 接口替换 file 类使用。
 */
public class PathTest {

    //Paths.get(“c:\data\myfile.txt”)方法的调用，这个方法是创建一个Path实例。
    //Paths.get() 方法是一个创建Path实例的工厂方法。
    //使用Paths.get(basePath, relativePath)方法创建一个相对路径的实例。
    public static Path pathCreated(String fileName){
        return Paths.get(fileName);

        //String path = "d:\\data\\projects\\a-project\\..\\another-project";
        //Path parentDir2 = Paths.get(path); -->d:\data\projects\another-project

        //Path path1 = Paths.get("d:\\data\\projects", ".\\a-project"); --> d:\data\projects\a-project
    }
}
