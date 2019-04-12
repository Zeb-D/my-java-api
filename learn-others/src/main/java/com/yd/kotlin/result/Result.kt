package com.yd.kotlin.result

import java.io.Serializable

class Result<T> : Serializable {
    var success: Boolean? = false
    var code: Int? = null
    var msg: String? = null
    var t: Long? = null
    /**
     * 返回的值对象
     */
    var result: T? = null

    object Instant {
        fun <T> newFailInstance(success: Boolean, code: Int, msg: String): Result<T> {
            var reuslt = Result<T>();
            reuslt.success = success;
            reuslt.code = code;
            reuslt.msg = msg
            reuslt.t = System.currentTimeMillis();
            return reuslt;
        }
    }
    companion object {
        fun <T> newFailInstance(success: Boolean, code: Int, msg: String): Result<T> {
            var reuslt = Result<T>();
            reuslt.success = success;
            reuslt.code = code;
            reuslt.msg = msg
            reuslt.t = System.currentTimeMillis();
            return reuslt;
        }
    }
}

fun main() {
    var result = Result.Instant.newFailInstance<String>(false,100,"Laji");
    result = Result.newFailInstance<String>(false,100,"Laji");
    println("result=>$result")
}