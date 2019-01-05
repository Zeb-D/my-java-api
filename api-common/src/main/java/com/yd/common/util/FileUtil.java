package com.yd.common.util;

import java.io.File;

/**
 * @author Yd on  2018-02-07
 * @description
 **/
public class FileUtil {

    /**
     * 判断该File是否想要查找的文件
     *
     * @param file
     * @param fileNameLike
     * @return true| false
     */
    public static Boolean assertIsFileByLike(File file, String fileNameLike) {
        if (file != null) {
            if (file.isDirectory()) {
                return false;
            }
            if (file.isFile()) {
                return isContains(file.getName(), fileNameLike);
            }
        }

        return false;
    }

    /**
     * 搜索该路径下的文件列表
     *
     * @param fileRootPath
     * @return
     */
    public static File[] searchFile(String fileRootPath) {
        File root = new File(fileRootPath);
        if (root.isDirectory()) {
            return root.listFiles();
        } else if (root.isFile()) {
            return new File[]{root};
        }
        throw new RuntimeException("this filePath " + fileRootPath + " is not exist");
    }

    public static Boolean isContains(String s1, String s2) {
        return isContains(s1, s2, true);
    }

    public static Boolean isContains(String s1, String s2, Boolean neglect) {//忽略大小写
        if (neglect) {
            s1 = s1.toUpperCase();
            s2 = s2.toUpperCase();
            return s1.indexOf(s2) > -1;
        } else {
            return s1.indexOf(s2) > -1;
        }

    }

}
