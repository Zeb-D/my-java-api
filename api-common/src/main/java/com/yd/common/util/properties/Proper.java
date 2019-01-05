package com.yd.common.util.properties;

import com.yd.httpClient.Constant;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yd on  2018-01-16
 * @Description：Proper. Proper can load properties file from CLASSPATH or File object.
 **/
public class Proper {

    private static final Map<String, Prop> map = new ConcurrentHashMap<String, Prop>();
    private static Prop prop = null;

    private Proper() {
    }

    /**
     * Using the properties file. It will loading the properties file if not loading.
     *
     * @see #use(String, String)
     */
    public static Prop use(String fileName) {
        return use(fileName, Constant.encoding);
    }

    /**
     * Using the properties file. It will loading the properties file if not loading.
     * <p/>
     * Example:<br>
     * Proper.use("config.txt", "UTF-8");<br>
     * Proper.use("other_config.txt", "UTF-8");<br><br>
     * String userName = Proper.get("userName");<br>
     * String password = Proper.get("password");<br><br>
     * <p/>
     * userName = Proper.use("other_config.txt").get("userName");<br>
     * password = Proper.use("other_config.txt").get("password");<br><br>
     * <p/>
     * Proper.use("com/jfinal/config_in_sub_directory_of_classpath.txt");
     *
     * @param fileName the properties file's name in classpath or the sub directory of classpath
     * @param encoding the encoding
     */
    public static Prop use(String fileName, String encoding) {
        Prop result = map.get(fileName);
        if (result == null) {
            result = new Prop(fileName, encoding);
            map.put(fileName, result);
            if (Proper.prop == null)
                Proper.prop = result;
        }
        return result;
    }

    /**
     * Using the properties file bye File object. It will loading the properties file if not loading.
     *
     * @see #use(File, String)
     */
    public static Prop use(File file) {
        return use(file, Constant.encoding);
    }

    /**
     * Using the properties file bye File object. It will loading the properties file if not loading.
     * <p/>
     * Example:<br>
     * Proper.use(new File("/var/config/my_config.txt"), "UTF-8");<br>
     * Strig userName = Proper.use("my_config.txt").get("userName");
     *
     * @param file     the properties File object
     * @param encoding the encoding
     */
    public static Prop use(File file, String encoding) {
        Prop result = map.get(file.getName());
        if (result == null) {
            result = new Prop(file, encoding);
            map.put(file.getName(), result);
            if (Proper.prop == null)
                Proper.prop = result;
        }
        return result;
    }

    public static Prop useless(String fileName) {
        Prop previous = map.remove(fileName);
        if (Proper.prop == previous)
            Proper.prop = null;
        return previous;
    }

    public static void clear() {
        prop = null;
        map.clear();
    }

    public static Prop getProp() {
        if (prop == null)
            throw new IllegalStateException("Load propties file by invoking Proper.use(String fileName) method first.");
        return prop;
    }

    public static Prop getProp(String fileName) {
        return map.get(fileName);
    }

    public static String get(String key) {
        return getProp().get(key);
    }

    public static String get(String key, String defaultValue) {
        return getProp().get(key, defaultValue);
    }

    public static Integer getInt(String key) {
        return getProp().getInt(key);
    }

    public static Integer getInt(String key, Integer defaultValue) {
        return getProp().getInt(key, defaultValue);
    }

    public static Long getLong(String key) {
        return getProp().getLong(key);
    }

    public static Long getLong(String key, Long defaultValue) {
        return getProp().getLong(key, defaultValue);
    }

    public static Boolean getBoolean(String key) {
        return getProp().getBoolean(key);
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {
        return getProp().getBoolean(key, defaultValue);
    }

    public static boolean containsKey(String key) {
        return getProp().containsKey(key);
    }
}
