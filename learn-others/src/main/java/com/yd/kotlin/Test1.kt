package com.yd.kotlin

class Test1{

    // 定义属性
    var var_a : Int = 0
    val val_a : Int = 0

    // 初始化
    init {
        var_a = 10
        // val_a = 0 为val类型不能更改。

        println("var_a => $var_a \t val_a => $val_a")
    }
}

fun main(args: Array<String>){
    Test1()
}