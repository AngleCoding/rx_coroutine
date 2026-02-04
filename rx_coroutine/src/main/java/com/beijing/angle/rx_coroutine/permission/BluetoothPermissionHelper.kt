package com.beijing.angle.rx_coroutine.permission

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.UtilsTransActivity


/**
 * 蓝牙权限
 *
 *     <uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
 *     <uses-permission android:name="android.permission.BLUETOOTH_SCAN" />
 *     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 *
 *     if (!BluetoothPermissionHelper.checkBluetoothPermission(mContext)){
 *                 BluetoothPermissionHelper.requestBluetoothPermission(object :PermissionCallback{
 *                     override fun onPermissionGranted() {
 *
 *                     }
 *
 *                     override fun onPermissionDenied() {
 *                     }
 *
 *                 })
 *             }
 */
object BluetoothPermissionHelper {


    const val BLUETOOTH_PERMISSION_REQUEST_CODE = 5001
    const val ENABLE_BLUETOOTH_REQUEST_CODE = 5002


    /**
     * 检查蓝牙权限是否已经授予
     * @param context 上下文
     * @return Boolean 是否拥有蓝牙权限
     */
    fun checkBluetoothPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12及以上版本需要新的蓝牙权限
            PermissionUtils.isGranted(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN
            )
        } else
            PermissionUtils.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)
    }


    /**
     * 申请蓝牙权限
     * @param callback 权限申请回调接口
     */
    fun requestBluetoothPermission(callback: PermissionCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12及以上版本申请新蓝牙权限
            PermissionUtils.permission(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN
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
        } else
            // Android 6.0至Android 11版本申请位置权限
            PermissionUtils.permission(Manifest.permission.ACCESS_FINE_LOCATION)
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
     * 启用蓝牙功能（Activity中调用）
     * @param activity Activity实例
     * @return Boolean 是否成功发起启用蓝牙请求
     */
    fun enableBluetooth(activity: Activity): Boolean {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            return false // 设备不支持蓝牙
        }

        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH_REQUEST_CODE)
            return true
        }
        return true // 蓝牙已启用
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
        if (requestCode == BLUETOOTH_PERMISSION_REQUEST_CODE) {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    // Android 12及以上版本检查新蓝牙权限
                    if (permissions.isNotEmpty() && (
                                permissions.contains(Manifest.permission.BLUETOOTH_CONNECT) ||
                                        permissions.contains(Manifest.permission.BLUETOOTH_SCAN)
                                )
                    ) {
                        if (grantResults.isNotEmpty() && grantResults.all { it == android.content.pm.PackageManager.PERMISSION_GRANTED }) {
                            callback.onPermissionGranted()
                        } else {
                            callback.onPermissionDenied()
                        }
                    }
                }

                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    // Android 6.0至Android 11版本检查位置权限
                    if (permissions.isNotEmpty() && permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                            callback.onPermissionGranted()
                        } else {
                            callback.onPermissionDenied()
                        }
                    }
                }
            }
            return true
        }
        return false
    }


    /**
     * 处理活动结果
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param callback 权限回调接口
     * @return Boolean 是否处理了该结果
     */
    fun handleActivityResult(
        requestCode: Int,
        resultCode: Int,
        callback: PermissionCallback?
    ): Boolean {
        if (requestCode == ENABLE_BLUETOOTH_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                callback?.onPermissionGranted()
            } else {
                callback?.onPermissionDenied()
            }
            return true
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