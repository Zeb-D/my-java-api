package com.yd.kotlin.service

import java.time.LocalTime


class Hello : AbsHelloService {
    constructor()

    override fun setName(name: String) {

    }

    override fun sayHello(name: String, time: LocalTime): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

fun main() {
    val hello :IHelloService = Hello()
    println("kotlin.NotImplementedError: An operation is not implemented: not implemented")
    hello.sayHello("name is yd", LocalTime.MAX)
}