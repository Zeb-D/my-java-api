package com.yd.kotlin

fun main(args: Array<String>) {
    println("Hello kotlin")
    var name: String = "My name is Yd"
    val noUpdate: String = name
    name = "update after"

    //立即初始化
    var var_a: Int = 10

    //推导出类型
    var var_b = 5

    //没有初始化的时候，必须声明类型
    var var_c: Float
    var_c = 12.3f
    var_c += 1
    println("var_a => \$var_a")

    println("var_a => $var_a \t var_b => $var_b \t var_a => $var_c")
    //立即初始化
    val val_a: Int = 100

    //推导出类型
    val val_b = 50

    //没有初始化的时候，必须声明类型
    val val_c: Int
    val_c = 1
    // val_c += 1 因为c是常量，所以这句代码是会报错的

    println("val_a => $val_a \t val_b => $val_b \t val_c => $val_c")
}