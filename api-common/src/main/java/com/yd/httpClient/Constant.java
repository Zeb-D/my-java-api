package com.yd.httpClient;

import com.yd.common.util.properties.Prop;
import com.yd.common.util.properties.Proper;

/**
 * @author Yd on  2018-01-16
 * @Description：
 **/
public class Constant {

    //工具类配置
    public final static String encoding;//编码1
    public final static String fileRenamer;// 文件上传重命名类
    public static final String[] xForwardedSupports;

    //短信发送地址配置（默认值为测试地址）
    public final static String smsUrl;//短信地址


    static {
        Prop constants = null;
        try {
            //该文件部分 需要设置传入 TODO
            constants = Proper.use("web-api.properties");
        } catch (Exception e) {
            System.out.println("读取配置文件错误" + e.toString());
        }
        if (constants == null) {
            encoding = Encoding.UTF_8.toString();
            fileRenamer = null;
            xForwardedSupports = new String[]{"127.0.0.1"};

            smsUrl = "http://b2b2c.sms:10008/jsse";//短信地址
        } else {
            encoding = constants.get("app.encoding", Encoding.UTF_8.name());
            fileRenamer = constants.get("app.fileRenamer");
            String xForwardedSupportsStr = constants.get("app.xForwardedSupports");
            if (xForwardedSupportsStr == null) {
                xForwardedSupports = new String[]{};
            } else {
                xForwardedSupports = xForwardedSupportsStr.split(",");
            }

            smsUrl = constants.get("sms.url", "http://b2b2c.sms:10008/jsse");//短信地址

        }
    }
}
