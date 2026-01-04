package com.beijing.angle.rx_coroutine

import com.beijing.angle.rx_coroutine.base.BaseApp
import rxhttp.wrapper.annotation.DefaultDomain
import rxhttp.wrapper.cache.CacheMode
import rxhttp.wrapper.cache.CacheMode.ONLY_NETWORK

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/22 11:13
 * @Link: https://github.com/AngleCoding
 */

class App : BaseApp() {


    companion object {
        @DefaultDomain
        const val BASE_URL: String = "http://hgz.app.creditmoa.com"
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun isRxHttpDebug(): Boolean {
        return true
    }

    override fun getResultSuccessCode(): String {
        return "200"
    }

    override fun getCacheMode(): CacheMode {
        return ONLY_NETWORK
    }



}