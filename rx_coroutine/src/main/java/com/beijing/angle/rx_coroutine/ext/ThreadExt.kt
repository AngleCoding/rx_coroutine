package com.beijing.angle.rx_coroutine.ext

import com.blankj.utilcode.util.ThreadUtils

/**
 * @author 刘红鹏
 * @description:
 * @date: 2026/2/4 18:38
 * @Link: https://github.com/AngleCoding
 */


/**
 * 判断是否为主线程
 */
fun Any.isMainThread(): Boolean {
    return ThreadUtils.isMainThread()
}


