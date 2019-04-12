package com.yd.kotlin

class Test2 {

    // 声明可空变量

    var var_a: Int? = 0
    val val_a: Int? = null

    init {
        var_a = 10
        // val_a = 0 为val类型不能更改。

        println("var_a => $var_a \t val_a => $val_a")
    }
}

fun main() {
    Test2()
}