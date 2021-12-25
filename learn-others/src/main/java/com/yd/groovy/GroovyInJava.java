package com.yd.groovy;

import com.google.common.collect.Maps;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

public class GroovyInJava {

    public static void main(String[] args) throws IOException, ResourceException, ScriptException, IllegalAccessException, InstantiationException, InterruptedException {

//		String[] roots = new String[] { "src/main/resources/" };
//		//通过指定的roots来初始化GroovyScriptEngine
//		GroovyScriptEngine gse = new GroovyScriptEngine(roots);
//		GroovyObject groovyObject = (GroovyObject) gse.loadScriptByName("TestScript.groovy").newInstance();
//
//		GroovyObject groovyObject2 = (GroovyObject) gse.loadScriptByName("TestScript.groovy").newInstance();
//
//		Map<String, Object> logMap = Maps.newHashMap();
//
//		logMap.put("responseTime", "200");
//		logMap.put("status", "running");
//
//		Boolean result = (Boolean) groovyObject.invokeMethod("output", logMap);
//		System.out.println(result);
//
//		Thread.sleep(Integer.MAX_VALUE);
//		logMap.put("responseTime","-1");
//		Boolean result2 = (Boolean) groovyObject.invokeMethod("output", logMap);
//		System.out.println(result2);
//
//		Boolean result3 = (Boolean) groovyObject.invokeMethod("output", logMap3);
//		System.out.println(result3);

        Map<String, Object> logMap3 = Maps.newHashMap();

        logMap3.put("responseTime", 1001);
        logMap3.put("status", "running");

        Binding binding = new Binding();
        binding.setVariable("str", logMap3);

        GroovyShell shell = new GroovyShell(binding);

        String script = "str.responseTime> 100 && str.status == \"running\"";
        // redirect output:
        PrintStream oldOut = System.out;
        Object value;
        while (true) {
            try {
                value = shell.evaluate(script);
                System.out.println(value);

                logMap3.put("responseTime", "1");
                String scriptTemp = "Long.parseLong(str.responseTime) > 0 && str.status == \"running\"";
                value = shell.evaluate(scriptTemp);
                System.out.println(value);
            } finally {

            }
        }
    }
}
