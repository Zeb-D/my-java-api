package com.yd.kotlin.service

import java.time.LocalTime

interface IHelloService {
    fun  sayHello(name:String,time:LocalTime):String
}