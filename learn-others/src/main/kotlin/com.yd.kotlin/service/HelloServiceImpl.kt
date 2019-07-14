package com.yd.kotlin.service

import java.io.Serializable
import java.time.LocalTime
import java.time.temporal.IsoFields
import java.util.*

class HelloServiceImpl : IHelloService, Serializable {
    var nowTime: Date? = null

    override fun sayHello(name: String, time: LocalTime): String {
        val time = nowTime?.toInstant()?.getLong(IsoFields.DAY_OF_QUARTER)
        println("time=>$time")
//        var t:Class<Date> = Date.class;


//        val res = time == null;
        when {
            time == null -> {
                return "name=>$name&time=${Date()}"
            }
            else -> {
                return "name=$name&time=$time"
            }
        }
    }
}

fun main() {
    var result: String = HelloServiceImpl().sayHello("name is yd", LocalTime.MIN)
    println("result=>$result")
}