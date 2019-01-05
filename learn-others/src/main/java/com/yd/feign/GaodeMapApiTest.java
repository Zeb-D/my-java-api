package com.yd.feign;

import feign.Feign;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yd on  2018-04-26
 * @description
 **/
public class GaodeMapApiTest {

    public static void main(String[] args) {
        String contextPath = "http://restapi.amap.com";
        MapApi api = Feign.builder()
//                .client(RibbonClient.create())//ConfigurationManager.loadPropertiesFromResources("sample-client.properties");
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .client(new OkHttpClient())
                .target(MapApi.class, contextPath);

        String key = "89e51c7379bc5544035285a938555498";
        String subdistrict = "3";
        //todo 待测试，返回数据 没磨合
        Result result = api.getCityBy(subdistrict, key);

        System.out.println("\n api:" + result);
    }


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    interface MapApi {
        @RequestLine("GET /v3/config/district?subdistrict={subdistrict}&key={key}")
        Result getCityBy(@Param(value = "subdistrict") String subdistrict, @Param(value = "key") String key);
    }

   public static class Result {
        String status;
        String info;
        String infocode;
        String count;
        Suggestion suggestion;
        List<Districts> districts;

       public String getStatus() {
           return status;
       }

       public void setStatus(String status) {
           this.status = status;
       }

       public String getInfo() {
           return info;
       }

       public void setInfo(String info) {
           this.info = info;
       }

       public String getInfocode() {
           return infocode;
       }

       public void setInfocode(String infocode) {
           this.infocode = infocode;
       }

       public String getCount() {
           return count;
       }

       public void setCount(String count) {
           this.count = count;
       }

       public Suggestion getSuggestion() {
           return suggestion;
       }

       public void setSuggestion(Suggestion suggestion) {
           this.suggestion = suggestion;
       }

       public List<Districts> getDistricts() {
           return districts;
       }

       public void setDistricts(List<Districts> districts) {
           this.districts = districts;
       }
   }

    public static class Suggestion {
        List<String> keywords;
        List<String> cities;

        public List<String> getKeywords() {
            return keywords;
        }

        public void setKeywords(List<String> keywords) {
            this.keywords = keywords;
        }

        public List<String> getCities() {
            return cities;
        }

        public void setCities(List<String> cities) {
            this.cities = cities;
        }
    }

    public static class Districts {
        List<String> citycode;
        String adcode;
        String name;
        String center;
        String level;
        List<DistrictsLow> districts = new ArrayList<DistrictsLow>();

        public List<String> getCitycode() {
            return citycode;
        }

        public void setCitycode(List<String> citycode) {
            this.citycode = citycode;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCenter() {
            return center;
        }

        public void setCenter(String center) {
            this.center = center;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<DistrictsLow> getDistricts() {
            return districts;
        }

        public void setDistricts(List<DistrictsLow> districts) {
            this.districts = districts;
        }
    }

    public static class DistrictsLow {
        String citycode;
        String adcode;
        String name;
        String center;
        String level;
        List<DistrictsLow> districts = new ArrayList<DistrictsLow>();

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCenter() {
            return center;
        }

        public void setCenter(String center) {
            this.center = center;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<DistrictsLow> getDistricts() {
            return districts;
        }

        public void setDistricts(List<DistrictsLow> districts) {
            this.districts = districts;
        }
    }
}
