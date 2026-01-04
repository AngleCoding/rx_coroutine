package com.beijing.angle.coroutine_live_model.exception

import com.beijing.angle.rx_coroutine.network.parseErrorString


/**
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */
class AppException : Exception {
    var errorMsg: String

    constructor(error: String?) : super() {
        errorMsg = error ?: parseError(null)
    }

    constructor(throwable: Throwable?) : super() {
        errorMsg = parseError(throwable)
    }

    private fun parseError(throwable: Throwable?): String {
        return throwable.parseErrorString()
    }

}