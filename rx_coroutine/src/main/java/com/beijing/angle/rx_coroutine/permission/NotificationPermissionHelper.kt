package com.beijing.angle.rx_coroutine.permission

import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.core.net.toUri

/**
 * 通知权限
 *
 *
 *    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
 *
 *        if (!NotificationPermissionHelper.checkNotificationPermission(mContext)) {
 *                 NotificationPermissionHelper.requestNotificationPermission(this, object :
 *                     NotificationPermissionHelper.PermissionCallback {
 *                     override fun onPermissionGranted() {
 *                         showToast("权限声请成功")
 *                     }
 *
 *                     override fun onPermissionDenied() {
 *                         showToast("权限声请失败")
 *                     }
 *
 *                 })
 *             }
 */
object NotificationPermissionHelper {


    const val NOTIFICATION_PERMISSION_REQUEST_CODE = 3001


    /**
     * 检查通知权限是否已经授予
     * @param context 上下文
     * @return Boolean 是否拥有通知权限
     */
    fun checkNotificationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13及以上版本需要单独申请通知权限
            context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == android.content.pm.PackageManager.PERMISSION_GRANTED
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Android 8.0及以上版本检查通知渠道是否启用
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.areNotificationsEnabled()
        } else {
            // 低版本默认启用通知
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        }
    }



    /**
     * 申请通知权限（Activity中调用）
     * @param activity Activity实例
     * @param callback 权限申请回调接口
     */
    fun requestNotificationPermission(activity: Activity, callback: PermissionCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13及以上版本需要运行时权限
            androidx.core.app.ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                NOTIFICATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // 低版本直接检查通知设置
            if (checkNotificationPermission(activity)) {
                callback.onPermissionGranted()
            } else {
                // 引导用户到通知设置页面
                openNotificationSettings(activity)
                callback.onPermissionDenied()
            }
        }
    }


    /**
     * 申请通知权限（Fragment中调用）
     * @param fragment Fragment实例
     * @param callback 权限申请回调接口
     */
    fun requestNotificationPermission(fragment: Fragment, callback: PermissionCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13及以上版本需要运行时权限
            fragment.requestPermissions(
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                NOTIFICATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // 低版本直接检查通知设置
            if (checkNotificationPermission(fragment.requireContext())) {
                callback.onPermissionGranted()
            } else {
                // 引导用户到通知设置页面
                openNotificationSettings(fragment.requireContext())
                callback.onPermissionDenied()
            }
        }
    }


    /**
     * 处理权限请求结果
     * @param requestCode 请求码
     * @param permissions 权限数组
     * @param grantResults 结果数组
     * @param callback 权限回调接口
     * @return Boolean 是否处理了该结果
     */
    fun handleRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        callback: PermissionCallback
    ): Boolean {
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (permissions.isNotEmpty() && permissions[0] == Manifest.permission.POST_NOTIFICATIONS) {
                if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    callback.onPermissionGranted()
                } else {
                    callback.onPermissionDenied()
                }
                return true
            }
        }
        return false
    }


    /**
     * 打开通知设置页面
     * @param context 上下文
     */
    private fun openNotificationSettings(context: Context) {
        val intent = Intent().apply {
            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            // 兼容旧版本
            val fallbackIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = "package:${context.packageName}".toUri()
            }
            context.startActivity(fallbackIntent)
        }
    }



    /**
     * 权限申请回调接口
     */
    interface PermissionCallback {
        /**
         * 权限被授予
         */
        fun onPermissionGranted()

        /**
         * 权限被拒绝
         */
        fun onPermissionDenied()
    }
}