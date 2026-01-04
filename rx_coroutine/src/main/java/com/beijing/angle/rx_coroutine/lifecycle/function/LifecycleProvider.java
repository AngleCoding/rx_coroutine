package com.beijing.angle.rx_coroutine.lifecycle.function;

import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/26 16:17
 * @Link: https://github.com/AngleCoding
 */

public interface LifecycleProvider<E> {

    @NonNull
    @CheckReturnValue
    Observable<E> lifecycle();

    /**
     * Binds a source until a specific event occurs.
     *
     * @param event the event that triggers unsubscription
     * @return a reusable {@link LifecycleTransformer} which unsubscribes when the event triggers.
     */
    @NonNull
    @CheckReturnValue
    <T> LifecycleTransformer<T> bindUntilEvent(@NonNull E event);

    /**
     * Binds a source until the next reasonable event occurs.
     *
     * @return a reusable {@link LifecycleTransformer} which unsubscribes at the correct time.
     */
    @NonNull
    @CheckReturnValue
    <T> LifecycleTransformer<T> bindToLifecycle();
}
