package com.beijing.angle.rx_coroutine.permission

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.UtilsTransActivity

/**
 * 申请存储权限
 *
 *     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 *     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *
 *      if (!StoragePermissionHelper.checkStoragePermission(mContext)) {
 *            StoragePermissionHelper.requestStoragePermission(this, object : PermissionCallback {
 *                     override fun onPermissionGranted() {
 *                     }
 *
 *                     override fun onPermissionDenied() {
 *                     }
 *
 *                 })
 *
 *       }
 */
object StoragePermissionHelper {

    public const val MANAGE_ALL_FILES_REQUEST_CODE = 2001

    /**
     * 申请存储权限
     * @param activity Activity上下文
     * @param callback 权限申请回调
     */
    fun requestStorageActivityPermission(activity: Activity, callback: PermissionCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11及以上版本
            if (Environment.isExternalStorageManager()) {
                callback.onPermissionGranted()
            } else {
                // 请求所有文件访问权限
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                val uri = Uri.fromParts("package", activity.packageName, null)
                intent.data = uri
                activity.startActivityForResult(
                    intent,
                    MANAGE_ALL_FILES_REQUEST_CODE
                )
            }
        } else {
            // Android 11以下版本
            PermissionUtils.permission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
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
    }

    /**
     * 申请存储权限 (Fragment版本)
     * @param fragment Fragment上下文
     * @param callback 权限申请回调
     */
    fun requestStorageFragmentPermission(fragment: Fragment, callback: PermissionCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11及以上版本
            if (Environment.isExternalStorageManager()) {
                callback.onPermissionGranted()
            } else {
                // 请求所有文件访问权限
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                val uri = Uri.fromParts("package", fragment.requireContext().packageName, null)
                intent.data = uri
                fragment.startActivityForResult(intent, MANAGE_ALL_FILES_REQUEST_CODE)
            }
        } else {
            // Android 11以下版本
            PermissionUtils.permission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
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
    }


    /**
     * 检查是否已获得存储权限
     * @return 是否有权限
     */
    fun checkStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            PermissionUtils.isGranted(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    /**
     * 打开应用设置页面
     * @param activity Activity上下文
     */
    fun openAppSettings(activity: Activity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
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