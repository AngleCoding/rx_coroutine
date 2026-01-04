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


// 扩展函数：为Activity添加单个权限请求
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


// 扩展函数：为FragmentActivity添加多权限请求
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


