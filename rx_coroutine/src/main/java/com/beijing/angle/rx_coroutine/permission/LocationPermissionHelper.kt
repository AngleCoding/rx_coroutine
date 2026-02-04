package com.beijing.angle.rx_coroutine.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.UtilsTransActivity


/**
 * 位置权限
 *
 *     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 *     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 *
 *    if (!LocationPermissionHelper.checkLocationPermission()) {
 *                 LocationPermissionHelper.requestLocationPermission(object :
 *                     LocationPermissionHelper.PermissionCallback {
 *                     override fun onPermissionGranted() {
 *                     }
 *
 *                     override fun onPermissionDenied() {
 *                     }
 *
 *                 })
 *             }
 */
object LocationPermissionHelper {

    const val LOCATION_PERMISSION_REQUEST_CODE = 4001


    /**
     * 检查位置权限是否已经授予
     * @return Boolean 是否拥有位置权限
     */
    fun checkLocationPermission(): Boolean {
        return PermissionUtils.isGranted(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }


    /**
     * @param callback 权限申请回调接口
     */
    fun requestLocationPermission(callback: PermissionCallback) {
        PermissionUtils.permission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
            .rationale(object : PermissionUtils.OnRationaleListener {
                override fun rationale(
                    activity: UtilsTransActivity,
                    shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest
                ) {
                    shouldRequest.again(true)
                }

            })
            .callback(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    callback.onPermissionGranted()
                }

                override fun onDenied() {
                    callback.onPermissionDenied()
                }
            })
            .request()
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
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (permissions.isNotEmpty() && (
                        permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION) ||
                                permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)
                        )
            ) {
                if (grantResults.isNotEmpty() && grantResults.all { it == android.content.pm.PackageManager.PERMISSION_GRANTED }) {
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
     * 打开应用设置页面
     * @param context 上下文
     */
    fun openAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }


    /**
     * 权限申请回调接口
     */
    interface PermissionCallback {
        /**
         * 当权限被授予时调用
         */
        fun onPermissionGranted()

        /**
         * 当权限被拒绝时调用
         */
        fun onPermissionDenied()
    }

}