package com.beijing.angle.rx_coroutine.lifecycle.function;

import static com.beijing.angle.rx_coroutine.lifecycle.internal.Preconditions.checkNotNull;

import androidx.annotation.Nullable;

import org.reactivestreams.Publisher;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.CompletableTransformer;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.MaybeTransformer;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.core.SingleTransformer;

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/26 16:21
 * @Link: https://github.com/AngleCoding
 */

public final class LifecycleTransformer <T> implements ObservableTransformer<T, T>,
        FlowableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        CompletableTransformer {

    final Observable<?> observable;


    LifecycleTransformer(Observable<?> observable) {
        checkNotNull(observable, "observable == null");
        this.observable = observable;
    }


    @Override
    public @NonNull CompletableSource apply(@NonNull Completable upstream) {
        return Completable.ambArray(upstream, observable.flatMapCompletable(Functions.CANCEL_COMPLETABLE));
    }

    @Override
    public @NonNull Publisher<T> apply(@NonNull Flowable<T> upstream) {
        return upstream.takeUntil(observable.toFlowable(BackpressureStrategy.LATEST));
    }

    @Override
    public @NonNull MaybeSource<T> apply(@NonNull Maybe<T> upstream) {
        return upstream.takeUntil(observable.firstElement());
    }

    @Override
    public @NonNull ObservableSource<T> apply(@NonNull Observable<T> upstream) {
        return upstream.takeUntil(observable);
    }

    @Override
    public @NonNull SingleSource<T> apply(@NonNull Single<T> upstream) {
        return upstream.takeUntil(observable.firstOrError());
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        LifecycleTransformer<?> that = (LifecycleTransformer<?>) o;
        return observable.equals(that.observable);
    }

    @Override
    public int hashCode() {
        return observable.hashCode();
    }


    @Override
    public String toString() {
        return "LifecycleTransformer{" +
                "observable=" + observable +
                '}';
    }

}
