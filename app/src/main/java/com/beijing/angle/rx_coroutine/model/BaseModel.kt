package com.beijing.angle.rx_coroutine.model

import androidx.lifecycle.MutableLiveData
import com.beijing.angle.rx_coroutine.bean.LatestOneBean
import com.beijing.angle.rx_coroutine.base.BaseViewModel
import com.beijing.angle.rx_coroutine.network.VmLiveData
import com.beijing.angle.rx_coroutine.network.launchVmRequest

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/23 14:44
 * @Link: https://github.com/AngleCoding
 */

class BaseModel : BaseViewModel() {

    val baseRepository: Repository by lazy { Repository() }

    val latestOneBean: VmLiveData<LatestOneBean> = MutableLiveData()

    fun getLatestOne() {
        launchVmRequest({
            baseRepository.getLatestOne()
        }, latestOneBean)
    }

}