package com.yd.kotlin.lazy

class LazyFiled {
    val lazyValue: String by lazy {
        println("computed!")
        "Hello"
    }

    var name: String? = "name is yd"

    private lateinit var age: String

}

fun main() {
    val lazy1: LazyFiled = LazyFiled()
    println("lazy=>$lazy1")
    var age:Int = -102
    age = age.ushr(1)
    println("age=>$age")
}