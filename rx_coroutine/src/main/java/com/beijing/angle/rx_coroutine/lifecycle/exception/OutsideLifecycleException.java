package com.beijing.angle.rx_coroutine.lifecycle.exception;

import androidx.annotation.Nullable;

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/26 16:25
 * @Link: https://github.com/AngleCoding
 */

public class OutsideLifecycleException extends IllegalStateException{

    public OutsideLifecycleException(@Nullable String detailMessage) {
        super(detailMessage);
    }
}
