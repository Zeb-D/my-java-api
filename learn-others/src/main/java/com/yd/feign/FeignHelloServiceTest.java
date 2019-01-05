package com.yd.feign;

import com.yd.entity.User;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * @author Yd on  2018-03-07
 * @description
 **/
public class FeignHelloServiceTest {
    public static void main(String[] args) {
        String context_path = "http://localhost:8080/activiti";
//        ObjectMapper objectMapper = new ObjectMapper()
//                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
//                .configure(SerializationFeature.INDENT_OUTPUT, true)
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FeignHelloService helloService = Feign.builder()
//                .client(RibbonClient.create())//ConfigurationManager.loadPropertiesFromResources("sample-client.properties");
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
//                .client(new OkHttpClient())
                .target(FeignHelloService.class, context_path);

        String r1 = helloService.sayHello("Yd");
        System.out.println(r1);
        User user = new User() {{
            this.setId(123);
            this.setName("Yd");
        }};
        user = helloService.getUser(user);
        System.out.println(user);
    }
}
