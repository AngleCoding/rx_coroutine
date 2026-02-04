package com.beijing.angle.rx_coroutine.ext

import android.util.Log

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/22 10:27
 * @Link: https://github.com/AngleCoding
 */


inline fun <reified T> T.log(message: String) {
    Log.e(T::class.simpleName, message)
}