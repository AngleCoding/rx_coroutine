package com.beijing.angle.rx_coroutine.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.beijing.angle.rx_coroutine.lifecycle.event.ActivityEvent
import com.beijing.angle.rx_coroutine.lifecycle.function.LifecycleProvider
import com.beijing.angle.rx_coroutine.lifecycle.function.LifecycleTransformer
import com.beijing.angle.rx_coroutine.lifecycle.function.RxLifecycle
import com.beijing.angle.rx_coroutine.lifecycle.function.RxLifecycleAndroid
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * @author 刘红鹏
 * @description:RxActivity基类 Rxjava生命周期管理
 * @date: 2025/12/26 16:09
 * @Link: https://github.com/AngleCoding
 */

abstract class RxActivity : AppCompatActivity(), LifecycleProvider<ActivityEvent> {

    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()


    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject.hide()
    }

    override fun <T : Any?> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T?> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }


    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
    }


    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }


    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }


    override fun onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }


    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
    }
}