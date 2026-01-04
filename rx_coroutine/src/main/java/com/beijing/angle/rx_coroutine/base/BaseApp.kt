package com.beijing.angle.rx_coroutine.base

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.Utils
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cache.CacheMode
import java.io.File

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/22 10:11
 * @Link: https://github.com/AngleCoding
 */

open class BaseApp : Application() {

    /**
     * 成功码默认为200
     */
    open fun getResultSuccessCode(): String {
        return "200"
    }

    /**
     * token失效默认为500
     */
    open fun getTokenFailureCode(): String {
        return "500"
    }


    /**
     * 缓存模式
     *
     * ONLY_NETWORK  该模式下，仅请求网络，不处理缓存；这是RxHttp默认的缓存模式
     *
     * ONLY_CACHE 该模式下，仅读取缓存，读取成功，直接返回；否则直接抛出异常
     *
     * NETWORK_SUCCESS_WRITE_CACHE  该模式下，直接请求网络，若请求成功，则写入缓存并返回；否则直接返回
     *
     * READ_CACHE_FAILED_REQUEST_NETWORK 该模式下，先读取缓存，读取成功，直接返回；否则将请求网络(请求成功，写入缓存)
     *
     * REQUEST_NETWORK_FAILED_READ_CACHE  该模式下，先请求网络，请求成功，写入缓存并返回；否则读取缓存
     *
     */
    open fun getCacheMode(): CacheMode {
        return CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
    }

    /**
     * token失效  跳转的登录页面
     */
    open fun getLoginActivity(): AppCompatActivity? {
        return null
    }


    /**
     * 是否开启网络日志
     */
    open fun isRxHttpDebug(): Boolean {
        return true
    }


    companion object {
        val instance: BaseApp by lazy { BaseApp() } // 默认线程安全
    }

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        initAutoSize()
        initRxHttp()
    }

    private fun initRxHttp() {
        RxHttpPlugins
            .init(RxHttpPlugins.getOkHttpClient())
            .setDebug(isRxHttpDebug())
            .setCache(
                File(externalCacheDir, "RxHttpCache"),
                10 * 1024 * 1024,
                getCacheMode(),
                60 * 1000
            ) // 配置缓存

    }


    private fun initAutoSize() {
        AutoSize.initCompatMultiProcess(this)
        AutoSizeConfig.getInstance().isBaseOnWidth = true
        AutoSizeConfig.getInstance().isCustomFragment = true
    }


    fun startLoginActivity() {
        getLoginActivity()?.let {
            startActivity(Intent(this, it::class.java))
        } ?: throw RuntimeException("loginActivity  is  null")
    }


}