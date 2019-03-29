package com.yd.java.encrypt.util;

public class OSUtil {
	
	/**
	 * 判断当前操作系统是不是window
	 */
	public static boolean isWindows() {
		boolean flag = false;
		if (System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
			flag = true;
		}
		return flag;
	}
}
