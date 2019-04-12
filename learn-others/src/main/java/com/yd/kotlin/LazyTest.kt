package com.yd.kotlin

fun main() {
    val name: Array<String> by lazy {
        arrayOf(
            "aaa", "bbb", "ccc"
        )
    }

    print(name)
}