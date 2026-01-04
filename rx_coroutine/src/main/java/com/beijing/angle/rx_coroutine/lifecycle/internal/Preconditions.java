package com.beijing.angle.rx_coroutine.lifecycle.internal;

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/26 16:22
 * @Link: https://github.com/AngleCoding
 */

public final class Preconditions {

    public static <T> T checkNotNull(T value, String message) {
        if (value == null) {
            throw new NullPointerException(message);
        }
        return value;
    }

    private Preconditions() {
        throw new AssertionError("No instances.");
    }
}
