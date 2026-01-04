package com.beijing.angle.rx_coroutine.lifecycle.popupview

import android.content.Context
import com.beijing.angle.rx_coroutine.lifecycle.event.ActivityEvent
import com.beijing.angle.rx_coroutine.lifecycle.function.LifecycleProvider
import com.beijing.angle.rx_coroutine.lifecycle.function.LifecycleTransformer
import com.beijing.angle.rx_coroutine.lifecycle.function.RxLifecycle
import com.beijing.angle.rx_coroutine.lifecycle.function.RxLifecycleAndroid
import com.lxj.xpopup.impl.CenterListPopupView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * @author 刘红鹏
 * @description:在中间的列表对话框
 * @date: 2025/12/26 17:19
 * @Link: https://github.com/AngleCoding
 */

abstract class RxCenterListPopupView(context: Context, bindLayoutId: Int, bindItemLayoutId: Int) :
    CenterListPopupView(context,bindLayoutId,bindItemLayoutId),
    LifecycleProvider<ActivityEvent>  {

    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject.hide()
    }


    override fun <T : Any> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun <T : Any> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject)
    }


    override fun onCreate() {
        super.onCreate()
        lifecycleSubject.onNext(ActivityEvent.CREATE)
    }

    override fun beforeShow() {
        super.beforeShow()
        lifecycleSubject.onNext(ActivityEvent.START)
    }


    override fun onShow() {
        super.onShow()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }


    override fun beforeDismiss() {
        super.beforeDismiss()
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
    }

    override fun onDismiss() {
        super.onDismiss()
        lifecycleSubject.onNext(ActivityEvent.STOP)
    }

    override fun destroy() {
        super.destroy()
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
    }
}