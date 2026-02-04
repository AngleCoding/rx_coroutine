package com.beijing.angle.rx_coroutine.ext

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils

/**
 * @author 刘红鹏
 * @description:
 * @date: 2026/2/4 18:23
 * @Link: https://github.com/AngleCoding
 */


/**
 *  获取屏幕的宽度（单位：px）
 */
fun Context.getScreenWidth(): Int {
    return ScreenUtils.getScreenWidth()
}


/**
 *  获取屏幕的高度（单位：px）
 */
fun Context.getScreenHeight(): Int {
    return ScreenUtils.getScreenHeight()
}


/**
 *  获取应用屏幕的宽度（单位：px）
 */
fun Context.getAppScreenWidth(): Int {
    return ScreenUtils.getAppScreenWidth()
}


/**
 *  获取应用屏幕的高度（单位：px）
 */
fun Context.getAppScreenHeight(): Int {
    return ScreenUtils.getAppScreenHeight()
}


/**
 *  获取屏幕密度
 */
fun Context.getScreenDensity(): Float {
    return ScreenUtils.getScreenDensity()
}

/**
 *   获取屏幕密度 DPI
 */
fun Context.getScreenDensityDpi(): Int {
    return ScreenUtils.getScreenDensityDpi()
}

/**
 *    设置屏幕为全屏
 */
fun Context.getScreenDensityDpi(activity: Activity) {
    ScreenUtils.setFullScreen(activity)
}

/**
 *    设置屏幕为非全屏
 */
fun Context.setNonFullScreen(activity: Activity) {
    ScreenUtils.setNonFullScreen(activity)
}

/**
 *  切换屏幕为全屏与否状态
 */
fun Context.toggleFullScreen(activity: Activity) {
    ScreenUtils.toggleFullScreen(activity)
}


/**
 *  判断屏幕是否为全屏
 */
fun Context.isFullScreen(activity: Activity): Boolean {
    return ScreenUtils.isFullScreen(activity)
}


/**
 *  设置屏幕为竖屏
 */
fun Context.setPortrait(activity: Activity) {
    ScreenUtils.setPortrait(activity)
}


/**
 *  判断是否横屏
 */
fun Context.isLandscape(): Boolean {
    return ScreenUtils.isLandscape()
}

/**
 *  判断是否竖屏
 */
fun Context.isPortrait(): Boolean {
    return ScreenUtils.isPortrait()
}


/**
 * 获取屏幕旋转角度
 *
 * @return ROTATION_0  ROTATION_90  ROTATION_180 ROTATION_270
 *
 */
fun Context.getScreenRotation(activity: Activity): Int {
    return ScreenUtils.getScreenRotation(activity)
}


/**
 *  截屏
 */
fun Context.screenShot(activity: Activity): Bitmap {
    return ScreenUtils.screenShot(activity)
}


/**
 *  截屏
 */
fun Context.screenShot(activity: Activity, isDeleteStatusBar: Boolean): Bitmap {
    return ScreenUtils.screenShot(activity, isDeleteStatusBar)
}


/**
 *  判断是否锁屏
 */
fun Context.isScreenLock(): Boolean {
    return ScreenUtils.isScreenLock()
}


/**
 * dp 与 px 转换
 */
fun Any.dp2px(dpValue: Float): Int {
    return SizeUtils.dp2px(dpValue)
}


/**
 * dp 与 px 转换
 */
fun Any.px2dp(dpValue: Float): Int {
    return SizeUtils.px2dp(dpValue)
}


/**
 * sp 与 px 转换
 */
fun Any.sp2px(dpValue: Float): Int {
    return SizeUtils.sp2px(dpValue)
}

/**
 * sp 与 px 转换
 */
fun Any.px2sp(dpValue: Float): Int {
    return SizeUtils.px2sp(dpValue)
}


