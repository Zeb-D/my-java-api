package com.yd.kotlin.service

open abstract class AbsHelloService : IHelloService {
    abstract fun setName(name: String);
}