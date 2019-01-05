package com.yd.java.jdk.io;

import com.yd.java.jdk.Constant;

import java.io.File;
import java.io.FileFilter;

/**
 * FIle类可以让你访问底层文件系统:
 * 检测文件是否存在
 * 读取文件长度
 * 重命名或移动文件
 * 删除文件
 * 检测某个路径是文件还是目录
 * 读取目录中的文件列表
 *
 * @author Yd on 2018-06-20
 * 注意：File只能访问文件以及文件系统的元数据。
 * 如果你想读写文件内容，需要使用FileInputStream、FileOutputStream或者RandomAccessFile。
 * 如果你正在使用Java NIO，并且想使用完整的NIO解决方案，你会使用到java.nio.FileChannel(否则你也可以使用File)。
 */
public class FileTest {

    public static void main(String[] args) {
        //创建File
        File file = new File(Constant.FILENAME);

        boolean fileExists = file.exists();

        long length = file.length();

        boolean success = file.renameTo(new File(Constant.FILENAME+".bak"));

        boolean isDirectory = file.isDirectory();
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return false;
            }
        });

        String[] fileNames = file.list();

        File[] files = file.listFiles();

        //返回布尔值表明是否成功删除文件，同样也会有相同的操作失败原因
        boolean success1 = new File(Constant.FILENAME+".bak").delete();

    }

}
