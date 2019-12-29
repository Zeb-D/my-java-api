package com.yd.disruptor.test;

import lombok.Data;

/**
 * Created by Zeb灬D on 2017/10/7
 * Description:
 */
@Data
public class TestEvent {
    private int value;

    public static void main(String[] args) {
        String url = "{\"data\":{\"point_code\":\"LOG60313\",\"error_code\":\"900001\",\"server_ip\":\"127.0.0.1\",\"server_name\":\"localhost\",\"notice_time\":\"2017-11-07 09:08:17\",\"content\":{\"info\":\"voice:1122,1122\\nweixin:12344,12344,2233\\nrtx:14754,14754\\nsms:1122,1122\\nemail:2289472333@qq.com,2289472333@qq.com,xx@gmail.com\\nappName:esearch-search\\n\\\\u544a\\\\u8b66\\\\u57df=esearch\\n\\\\u544a\\\\u8b66App=esearch-search\\n\\\\u544a\\\\u8b66\\\\u76ee\\\\u6807=realTime*\\n\\\\u544a\\\\u8b66\\\\u63cf\\\\u8ff0=\\\\u5b9e\\\\u65f6\\\\u544a\\\\u8b66\\\\u6d4b\\\\u8bd5byZeb灬D\\n\\\\u544a\\\\u8b66\\\\u65f6\\\\u95f4=2017-11-07 09:08:17\\n\\\\u70b9\\\\u51fb\\\\u67e5\\\\u770b\\\\u8be6\\\\u60c5 : http:\\\\/\\\\/localhost:8090\\\\/elog\\\\/api\\\\/alarm\\\\/8aa184725f94043f015f9405530f0003.do\"},\"level\":2,\"is_test\":0},\"token\":\"186d082a07e934a975bf2b2f0e284712\",\"encode\":0}";
        String result = url.replaceAll("\\\\u", "\\u");
        result = result.replaceAll("\\\\/", "\\/");
        System.out.println(result);

    }
}