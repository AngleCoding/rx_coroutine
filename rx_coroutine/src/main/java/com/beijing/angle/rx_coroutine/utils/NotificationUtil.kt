package com.beijing.angle.rx_coroutine.utils

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.beijing.angle.rx_coroutine.permission.NotificationPermissionHelper
import com.beijing.angle.rx_coroutine.permission.NotificationPermissionHelper.PermissionCallback
import com.blankj.utilcode.util.NotificationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author 刘红鹏
 * @description: 通知工具
 * @date: 2026/2/4 17:37
 * @Link: https://github.com/AngleCoding
 */

object NotificationUtil {


    /**
     *
     * 发送通知
     * @param context 上下文对象
     * @param activity
     * @param title 标题栏
     * @param contentText 通知内容
     * @param icon 通知icon
     * @return 发送结果
     *
     *
     *    NotificationUtil.notifyAsync(
     *                 mContext,
     *                 this,
     *                 "测识通知",
     *                 "通知内容",
     *                 com.github.yuan.picture_take.R.mipmap.back,
     *                 object : NotificationListener<Boolean> {
     *                     override fun onSuccess(data: Boolean) {
     *                         if (data) {
     *                             showToast("发送成功")
     *                         }
     *
     *                     }
     *
     *                     override fun onError(errorMessage: String) {
     *                         showToast(errorMessage)
     *                     }
     *
     *                 })
     *
     *
     */
    fun notifyAsync(
        context: Context,
        activity: Activity,
        title: String,
        contentText: String,
        icon: Int,
        listener: NotificationListener<Boolean>
    ) {
        if (NotificationPermissionHelper.checkNotificationPermission(context)) {
            CoroutineScope(Dispatchers.Main).launch {
                when (val result = notify(title, contentText, icon)) {
                    is NotificationResult.Success -> listener.onSuccess(result.data)
                    is NotificationResult.Error -> listener.onError(result.error)
                }
            }
        } else {
            NotificationPermissionHelper.requestNotificationPermission(
                activity,
                object : PermissionCallback {
                    override fun onPermissionGranted() {
                        CoroutineScope(Dispatchers.Main).launch {
                            when (val result = notify(title, contentText, icon)) {
                                is NotificationResult.Success -> listener.onSuccess(result.data)
                                is NotificationResult.Error -> listener.onError(result.error)
                            }
                        }

                    }

                    override fun onPermissionDenied() {
                        listener.onError("通知栏权限未打开")
                    }

                })
        }
    }


    fun notifyAsync(
        context: Context,
        fragment: Fragment,
        title: String,
        contentText: String,
        icon: Int,
        listener: NotificationListener<Boolean>
    ) {
        if (NotificationPermissionHelper.checkNotificationPermission(context)) {
            CoroutineScope(Dispatchers.Main).launch {
                when (val result = notify(title, contentText, icon)) {
                    is NotificationResult.Success -> listener.onSuccess(result.data)
                    is NotificationResult.Error -> listener.onError(result.error)
                }
            }
        } else {
            NotificationPermissionHelper.requestNotificationPermission(
                fragment,
                object : PermissionCallback {
                    override fun onPermissionGranted() {
                        CoroutineScope(Dispatchers.Main).launch {
                            when (val result = notify(title, contentText, icon)) {
                                is NotificationResult.Success -> listener.onSuccess(result.data)
                                is NotificationResult.Error -> listener.onError(result.error)
                            }
                        }

                    }

                    override fun onPermissionDenied() {
                        listener.onError("通知栏权限未打开")
                    }

                })
        }
    }


    private suspend fun notify(
        title: String, contentText: String, icon: Int
    ): NotificationResult<Boolean> =
        withContext(Dispatchers.IO)
        {
            return@withContext try {
                NotificationUtils.notify(1) { consumer ->
                    consumer.apply {
                        setContentTitle(title)
                        setContentText(contentText)
                        setSmallIcon(icon)
                    }
                }
                NotificationResult.Success(true)
            } catch (e: Exception) {
                e.printStackTrace()
                NotificationResult.Error(e.message ?: "Unknown error")
            }
        }


    /**
     * 监听接口
     */
    interface NotificationListener<T> {
        fun onSuccess(data: T)
        fun onError(errorMessage: String)
    }


    /**
     * 结果密封类
     */
    sealed class NotificationResult<out T> {
        data class Success<out T>(val data: T) : NotificationResult<T>()
        data class Error(val error: String) : NotificationResult<Nothing>()
    }


}