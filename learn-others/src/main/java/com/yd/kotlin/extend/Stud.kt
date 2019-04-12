package com.yd.kotlin.extend

open class Stud : Person {
    var age: Int? = null

    constructor() {
        super.name = "name is yd"
    }
}

fun main() {
    val s = Stud()
    println("age=>${s.age}:name=>${s.name}")
}