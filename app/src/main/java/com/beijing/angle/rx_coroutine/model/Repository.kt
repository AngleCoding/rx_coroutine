package com.beijing.angle.rx_coroutine.model

import com.beijing.angle.rx_coroutine.App
import com.beijing.angle.rx_coroutine.bean.LatestOneBean
import com.beijing.angle.rx_coroutine.network.BaseResponse
import com.beijing.angle.rx_coroutine.rxhttp.RxHttp
import com.blankj.utilcode.util.DeviceUtils
import rxhttp.retry
import rxhttp.toAwait
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/23 14:45
 * @Link: https://github.com/AngleCoding
 */

class Repository {

    suspend fun getLatestOne(): BaseResponse<LatestOneBean> {
        val bean = RxHttp.Companion.get(App.Companion.BASE_URL + "/version/getLatestOne")
            .addQuery("type", "HGZLSYTJ_DJY")
            .setHeader("deviceCode", DeviceUtils.getUniqueDeviceId())
            .toAwait<LatestOneBean>()
            .retry(2, 1000) {
                it is ConnectException
                it is SocketTimeoutException
            }
            .await()
        return BaseResponse(bean.msg, bean, bean.code)
    }


}