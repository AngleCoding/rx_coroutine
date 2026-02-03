package com.beijing.angle.rx_coroutine.utils

import com.beijing.angle.rx_coroutine.lifecycle.RxActivity
import java.lang.ref.WeakReference

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/19 17:51
 * @Link: https://github.com/AngleCoding
 */

class AppManager {

    /**
     * WeakReference add AppCompatActivity
     */
    private val activities = mutableListOf<WeakReference<RxActivity>>()


    companion object {
        val instance: AppManager by lazy { AppManager() } // 默认线程安全
    }

    public fun addActivity(activity: RxActivity) {
        activities.add(WeakReference(activity))
    }

    fun removeActivity(activity: RxActivity) {
        activities.removeAll { it.get() == activity }
    }

    fun clear() {
        activities.clear()
    }

}