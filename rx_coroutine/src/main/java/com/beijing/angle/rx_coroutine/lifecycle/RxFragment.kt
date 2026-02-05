package com.beijing.angle.rx_coroutine.lifecycle

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.beijing.angle.rx_coroutine.lifecycle.event.FragmentEvent
import com.beijing.angle.rx_coroutine.lifecycle.function.LifecycleProvider
import com.beijing.angle.rx_coroutine.lifecycle.function.LifecycleTransformer
import com.beijing.angle.rx_coroutine.lifecycle.function.RxLifecycle
import com.beijing.angle.rx_coroutine.lifecycle.function.RxLifecycleAndroid
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * @author 刘红鹏
 * @description: Fragment基类 Rxjava生命周期管理
 * @date: 2025/12/26 16:39
 * @Link: https://github.com/AngleCoding
 */

open class RxFragment : Fragment(), LifecycleProvider<FragmentEvent> {

    private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()


    override fun lifecycle(): Observable<FragmentEvent> {
        return lifecycleSubject.hide()

    }

    override fun <T : Any?> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleSubject.onNext(FragmentEvent.ATTACH)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
    }

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(FragmentEvent.START)
    }


    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(FragmentEvent.RESUME)
    }

    override fun onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE)
        super.onPause()
    }


    override fun onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP)
        super.onStop()
    }

    override fun onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
        super.onDestroyView()
    }


    override fun onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY)
        super.onDestroy()
    }

    override fun onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH)
        super.onDetach()
    }

}