package com.beijing.angle.rx_coroutine.lifecycle.function;

import static io.reactivex.rxjava3.android.MainThreadDisposable.verifyMainThread;

import android.view.View;

import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/26 16:34
 * @Link: https://github.com/AngleCoding
 */

public  final class ViewDetachesOnSubscribe implements ObservableOnSubscribe<Object> {

    static final Object SIGNAL = new Object();

    final View view;

    public ViewDetachesOnSubscribe(View view) {
        this.view = view;
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
        verifyMainThread();
        EmitterListener listener = new EmitterListener(emitter);
        emitter.setDisposable(listener);
        view.addOnAttachStateChangeListener(listener);

    }

    class EmitterListener extends MainThreadDisposable implements View.OnAttachStateChangeListener {
        final ObservableEmitter<Object> emitter;

        public EmitterListener(ObservableEmitter<Object> emitter) {
            this.emitter = emitter;
        }

        @Override
        public void onViewAttachedToWindow(View view) {
            // Do nothing
        }

        @Override
        public void onViewDetachedFromWindow(View view) {
            emitter.onNext(SIGNAL);
        }

        @Override
        protected void onDispose() {
            view.removeOnAttachStateChangeListener(this);
        }
    }
}
