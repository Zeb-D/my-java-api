package com.yd.java.jdk.io;

import java.io.File;

/**
 * 各种方式 获取文件路径
 *
 * @author Yd on  2018-06-20
 * @description System.getProperty()参数大全
 * # java.version                                Java Runtime Environment version
 * # java.vendor                                Java Runtime Environment vendor
 * # java.vendor.url                           Java vendor URL
 * # java.home                                Java installation directory
 * # java.vm.specification.version   Java Virtual Machine specification version
 * # java.vm.specification.vendor    Java Virtual Machine specification vendor
 * # java.vm.specification.name      Java Virtual Machine specification name
 * # java.vm.version                        Java Virtual Machine implementation version
 * # java.vm.vendor                        Java Virtual Machine implementation vendor
 * # java.vm.name                        Java Virtual Machine implementation name
 * # java.specification.version        Java Runtime Environment specification version
 * # java.specification.vendor         Java Runtime Environment specification vendor
 * # java.specification.name           Java Runtime Environment specification name
 * # java.class.version                    Java class format version number
 * # java.class.path                      Java class path
 * # java.library.path                 List of paths to search when loading libraries
 * # java.io.tmpdir                       Default temp file path
 * # java.compiler                       Name of JIT compiler to use
 * # java.ext.dirs                       Path of extension directory or directories
 * # os.name                              Operating system name
 * # os.arch                                  Operating system architecture
 * # os.version                       Operating system version
 * # file.separator                         File separator ("/" on UNIX)
 * # path.separator                  Path separator (":" on UNIX)
 * # line.separator                       Line separator ("/n" on UNIX)
 * # user.name                        User’s account name
 * # user.home                              User’s home directory
 * # user.dir                               User’s current working directory
 **/
public class FilePathTest {
    public static void main(String[] args) {
        //利用System.getProperty()函数获取当前路径
        System.out.println(System.getProperty("user.dir"));

        //File.getCanonicalPath()和File.getAbsolutePath()大约只是对于new File(".")和new File("..")两种路径有所区别。
        File directory = new File(".");//设定为当前文件夹
        try{
            System.out.println(directory.getCanonicalPath());//获取标准的路径
            System.out.println(directory.getAbsolutePath());//获取绝对路径
        }catch(Exception e){}

        //在类中取得路径 /代表 类的绝对路径 "" 代表当前类路径
        System.out.println(FilePathTest.class.getResource("").getPath());
    }
}
