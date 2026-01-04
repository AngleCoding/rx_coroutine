package com.beijing.angle.rx_coroutine.network

data class BaseResponse<T>(
    var msg: String? = null,
    var result: T? = null,
    var code: String? = null
)
