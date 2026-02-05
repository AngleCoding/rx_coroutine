package com.beijing.angle.rx_coroutine.network

import android.util.Log
import com.beijing.angle.coroutine_live_model.exception.AppException
import com.beijing.angle.rx_coroutine.base.BaseApp
import com.google.gson.JsonSyntaxException
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser

/**
 * @author AnglePenCoding
 * Created by on 2023/2/17 0017
 * @website https://github.com/AnglePengCoding
 */


/**
 * 处理返回值
 *
 * @param result 请求结果
 */
fun <T> VmLiveData<T>.paresVmResult(result: BaseResponse<T>) {
    try {
        if (result.code == BaseApp.instance.getResultSuccessCode()) {
            value =    VmState.Success(result.result)
        } else if (result.code == BaseApp.instance.getTokenFailureCode()) {
            value =      VmState.TokenFailure
        } else {
            value =   VmState.Error(AppException(result.msg))
        }
    } catch (e: Exception) {
        value =  VmState.Error(AppException(e.message))
    }
}




/**
 * 异常转换异常处理
 */
fun <T> VmLiveData<T>.paresVmException(e: Throwable) {
    this.value = VmState.Error(AppException(e))
}



