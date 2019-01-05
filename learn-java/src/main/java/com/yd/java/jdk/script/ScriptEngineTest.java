package com.yd.java.jdk.script;

import javax.script.*;

/**
 * javax.script 脚本<p>
 * 始于JDK1.6，一般的用途主要是能解析通用的表达式就好，比如X >= 1（X作为参数传入）这样的表达式，也能利用js的函数语法，创造一个就像java的函数一样存在于内存中随时可以被调用的函数，更可以将js中的对象直接转换成java对象。
 * <p>cript包下最主要的是ScriptEngineManager、ScriptEngine、CompiledScript和Bindings 4个类或接口。
 * <p>
 *     ScriptEngineManager是一个工厂的集合，可以通过name或tag的方式获取某个脚本的工厂并生成一个此脚本的ScriptEngine，
 *     目前只有javascript的工厂。通过工厂函数得到了ScriptEngine之后，就可以用这个对象来解析脚本字符串了，
 *     直接调用Object obj = ScriptEngine.eval(String script)即可，返回的obj为表达式的值，比如true、false或int值。
 * </p>
 * <p>
 *     CompiledScript可以将ScriptEngine解析一段脚本的结果存起来，方便多次调用。
 *     只要将ScriptEngine用Compilable接口强制转换后，调用compile(String script)就返回了一个CompiledScript对象，
 *     要用的时候每次调用一下CompiledScript.eval()即可，一般适合用于js函数的使用。
 * </p>
 * <p>
 *     Bindings的概念算稍微复杂点，我的理解Bindings是用来存放数据的容器。
 *     它有3个层级，为Global级、Engine级和Local级，前2者通过ScriptEngine.getBindings()获得，是唯一的对象，
 *     而Local Binding由ScriptEngine.createBindings()获得，很好理解，每次都产生一个新的。Global对应到工厂，Engine对应到ScriptEngine，
 *     向这2者里面加入任何数据或者编译后的脚本执行对象，在每一份新生成的Local Binding里面都会存在。
 * </p>
 * <p>
 *     ScriptContext的概念，这个可能很少用到吧，它是用来连接ScriptEngine和Bindings的工具。
 * </p>
 * @author Yd on  2018-09-05
 * @description
 **/
public class ScriptEngineTest {
    public static void main(String[] args) {
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
            Compilable compilable = (Compilable) engine;
            Bindings bindings = engine.createBindings(); // Local级别的Binding
            String script = "function add(op1,op2){return op1+op2} add(a, b)"; // 定义函数并调用
            CompiledScript JSFunction = compilable.compile(script); // 解析编译脚本函数
            bindings.put("a", 1);
            bindings.put("b", 2); // 通过Bindings加入参数
            Object result = JSFunction.eval(bindings);
            System.out.println(result); // 调用缓存着的脚本函数对象，Bindings作为参数容器传入
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
