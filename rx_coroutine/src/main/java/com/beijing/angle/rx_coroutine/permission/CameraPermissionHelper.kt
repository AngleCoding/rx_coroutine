package com.beijing.angle.rx_coroutine.permission

import android.Manifest
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.UtilsTransActivity

/**
 * 相机权限
 *
 *    <uses-feature
 *         android:name="android.hardware.camera"
 *         android:required="false" />
 *
 *     <uses-permission android:name="android.permission.ACCESS_BACKGROUND_CAMERA" />
 *     <uses-permission android:name="android.permission.CAMERA" />
 *
 *     if (!CameraPermissionHelper.checkCameraPermission()) {
 *                 CameraPermissionHelper.requestCameraPermission(object : PermissionCallback {
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
 *
 */
object CameraPermissionHelper {


    /**
     * 检查相机权限是否已经授予
     * @return Boolean 是否拥有相机权限
     */
    fun checkCameraPermission(): Boolean {
        return PermissionUtils.isGranted(Manifest.permission.CAMERA)
    }


    /**
     * @param callback 权限申请回调接口
     */
    fun requestCameraPermission(callback: PermissionCallback) {
        PermissionUtils.permission(Manifest.permission.CAMERA)
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