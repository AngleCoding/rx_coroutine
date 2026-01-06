package com.beijing.angle.rx_coroutine.ext

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/23 11:33
 * @Link: https://github.com/AngleCoding
 */


/**
 * 扩展函数：为Activity添加单个权限请求
 *
 *
 *   if (ContextCompat.checkSelfPermission(
 *                 this,
 *                 Manifest.permission.CAMERA
 *             ) == PackageManager.PERMISSION_GRANTED
 *         ) {
 *             startCamera()
 *         } else {
 *             val lanch =
 *                 registerForSinglePermission(Manifest.permission.CAMERA) { isGranted ->
 *                     {
 *                         if (isGranted) {
 *                             showToast("相机权限申请成功")
 *                         } else {
 *                             showToast("相机权限未打开")
 *                         }
 *                     }
 *
 *                 }
 *
 *             lanch.launch(Manifest.permission.CAMERA)
 *         }
 *
 *
 */

fun AppCompatActivity.registerForSinglePermission(
    permission: String,
    callback: (Boolean) -> Unit
): ActivityResultLauncher<String> {
    return registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        callback(isGranted)
    }
}


// 扩展函数：为FragmentActivity添加单个权限请求
fun FragmentActivity.registerForSinglePermission(
    permission: String,
    callback: (Boolean) -> Unit
): ActivityResultLauncher<String> {
    return registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        callback(isGranted)
    }
}

// 扩展函数：为Fragment添加单个权限请求
fun Fragment.registerForSinglePermission(
    permission: String,
    callback: (Boolean) -> Unit
): ActivityResultLauncher<String> {
    return registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        callback(isGranted)
    }
}


/**
 * 添加多权限请求
 *
 * private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
 *
 *  // 注册权限请求
 *         permissionLauncher = registerForMultiplePermissions { permissions ->
 *             permissions.forEach { (permission, isGranted) ->
 *                 if (isGranted) {
 *                     Toast.makeText(this, "$permission 已授权", Toast.LENGTH_SHORT).show()
 *                 } else {
 *                     Toast.makeText(this, "$permission 未授权", Toast.LENGTH_SHORT).show()
 *                 }
 *             }
 *         }
 *
 *
 *          permissionLauncher.launch(
 *                 arrayOf(
 *                     Manifest.permission.CAMERA,
 *                     Manifest.permission.ACCESS_FINE_LOCATION,
 *                     Manifest.permission.READ_EXTERNAL_STORAGE
 *                 )
 *             )
 *
 */
fun AppCompatActivity.registerForMultiplePermissions(
    callback: (Map<String, Boolean>) -> Unit
): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        callback(permissions)
    }
}


// 扩展函数：为FragmentActivity添加多权限请求
fun FragmentActivity.registerForMultiplePermissions(
    callback: (Map<String, Boolean>) -> Unit
): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        callback(permissions)
    }
}

// 扩展函数：为Fragment添加多权限请求
fun Fragment.registerForMultiplePermissions(
    callback: (Map<String, Boolean>) -> Unit
): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        callback(permissions)
    }
}


