package com.beijing.angle.rx_coroutine.ext

import android.annotation.SuppressLint
import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import java.util.concurrent.TimeUnit

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/22 11:35
 * @Link: https://github.com/AngleCoding
 */

/**
 * 按钮监听
 */
@SuppressLint("CheckResult")
fun <T : View> T.click(block: () -> Unit) =
    clicks()
        .throttleFirst(3000, TimeUnit.MILLISECONDS)
        .subscribe {
            block()
        }

