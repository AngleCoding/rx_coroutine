package com.beijing.angle.rx_coroutine.lifecycle.popupview

import android.content.Context
import com.beijing.angle.rx_coroutine.lifecycle.event.ActivityEvent
import com.beijing.angle.rx_coroutine.lifecycle.function.LifecycleProvider
import com.beijing.angle.rx_coroutine.lifecycle.function.LifecycleTransformer
import com.beijing.angle.rx_coroutine.lifecycle.function.RxLifecycle
import com.beijing.angle.rx_coroutine.lifecycle.function.RxLifecycleAndroid
import com.lxj.xpopup.core.HorizontalAttachPopupView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * @author 刘红鹏
 * @description:水平方向的依附于某个View或者某个点的弹窗，
 * 可以轻松实现微信朋友圈点赞的弹窗效果。
 * 支持通popupPosition()方法手动指定想要出现在目标的左边还是右边，
 * 但是对Top和Bottom则不生效
 * @date: 2025/12/26 17:20
 * @Link: https://github.com/AngleCoding
 */

abstract class RxHorizontalAttachPopupView(context: Context) : HorizontalAttachPopupView(context),
    LifecycleProvider<ActivityEvent> {


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