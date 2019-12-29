package com.yd.groovy;

import com.google.common.collect.Maps;
import groovy.lang.Binding;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import lombok.Data;
import org.codehaus.groovy.control.CompilationFailedException;

import java.util.Map;

/**
 * Created by Zeb灬D on 2017/12/23
 * Description:
 */
public class TestGroovy {
    public static void main(String[] args) {

        /* key 业务id */
//        Map<String,Rule> ruleMap = Maps.newHashMap();
//        ruleMap.put("1",new Rule("Long.parseLong(str.responseTime) > 100 && str.status == \"running\""));
//        ruleMap.put("2",new Rule("Long.parseLong(str.responseTime) > 10 && str.status == \"running\""));

//        Map<String, Object> logMap = Maps.newHashMap();
//		logMap.put("responseTime", 10.1);
//        logMap.put("status", "running");
//        logMap.put("x", "heldlo");
//
//        Rule rule = new Rule("logMap.responseTime == 10.1 && logMap.status == 'running' && !logMap.x.contains('ll')");
//        Object value;
//        value = rule.getShell().cal(logMap);
//        System.out.println(value);
//
//        Map<String, Object> logMap1 = Maps.newHashMap();
//        logMap1.put("responseTime", 10);
//        logMap1.put("status", "running");
//        logMap1.put("x", "runningll");
//
//        value = rule.getShell().cal(logMap1);
//        System.out.println(value);

        Rule r = new Rule("!logMap.fieldKey.contains('fieldKey-1')");
        Map<String, Object> map = Maps.newHashMap();
        map.put("fieldKey", "fieldKey-1dasdf");
        map.put("fieldKey-2", "fieldKey-2");
        Object value = r.getShell().cal(map);
        System.out.println(value);
    }

    @Data
    static class Rule{
        private String md5;
        private MyShell shell;

        Rule(String script){
            md5 = script;
            shell = new MyShell(script);
        }
    }

    static class MyShell extends GroovyShell{

        private Script script;
        private String ruleStr;

        MyShell(String ruleStr){
            super(new Binding());
            this.ruleStr = ruleStr;
            evaluate(ruleStr);
        }

        public Object cal(Map<String, Object> logMap){
            this.getContext().setVariable("logMap",logMap);
            return script.run();
        }

        public Object evaluate(GroovyCodeSource codeSource) throws CompilationFailedException {
            Script script = parse(codeSource);
            this.script = script;
            return null;
        }
//        protected synchronized String generateScriptName() {
//            return "Script" + scriptMd5 + ".groovy";
//        }
    }
}
