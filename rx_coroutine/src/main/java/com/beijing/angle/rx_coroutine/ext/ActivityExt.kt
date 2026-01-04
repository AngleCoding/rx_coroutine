package com.beijing.angle.rx_coroutine.ext

import android.R
import android.app.Activity.OVERRIDE_TRANSITION_OPEN
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.apply
import kotlin.collections.forEach
import kotlin.jvm.java
import kotlin.ranges.downTo


/**
 * 软键盘的隐藏
 * [view] 事件控件View
 */
fun AppCompatActivity.dismissKeyBoard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}





/**
 * Activity 无动画跳转
 */
inline fun <reified T : AppCompatActivity> AppCompatActivity?.openActivity() =
    this?.startActivity(Intent(this, T::class.java))


/**
 * 携带数据
 */

inline fun <reified T : AppCompatActivity> AppCompatActivity?.openActivity(bundle: Bundle) =
    this?.startActivity(Intent(this, T::class.java).putExtras(bundle))


/**
 * 启动Activity的扩展方法，可在启动时指定自定义动画
 */
inline fun <reified T : AppCompatActivity> AppCompatActivity?.startActivityWithAnimation(
    enterAnim: Int = R.anim.fade_in,
    exitAnim: Int = R.anim.fade_out
) {
    this?.apply {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
        setCustomTransition(enterAnim, exitAnim, this)
    }
}




/**
 * 设置自定义转场动画
 */
fun setCustomTransition(enterAnim: Int, exitAnim: Int, activity: AppCompatActivity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        activity.overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, enterAnim, exitAnim)
    } else {
        @Suppress("DEPRECATION")
        activity.overridePendingTransition(enterAnim, exitAnim)
    }
}

/**
 * context显示toast
 */
fun Context.showToast(tips: String) {
    Toast.makeText(this, tips, Toast.LENGTH_SHORT).show()
}



/**
 * 短信倒计时
 *
 *         countDown(start = {
 *             binding.mBtLO.isEnabled = false
 *         }, end = {
 *             binding.mBtLO.isEnabled = true
 *         }, next = { time ->
 *             binding.mBtLO.text = time.toString()
 *         }, error = { msg ->
 *             binding.mBtLO.text = msg
 *         })
 *
 */
fun AppCompatActivity.countDown(
    timeMillis: Long = 1000,//默认时间间隔 1 秒
    time: Int = 60,//默认时间为 3 秒
    start: (scop: CoroutineScope) -> Unit,
    end: () -> Unit,
    next: (time: Int) -> Unit,
    error: (msg: String?) -> Unit
) {
    lifecycleScope.launch {
        flow {
            (time downTo 0).forEach {
                delay(timeMillis)
                emit(it)
            }
        }.onStart {
            start(this@launch)
        }.onCompletion {
            end()
        }.catch {
            error(it.message ?: "出现未知错误")
        }.collect {
            next(it)
        }
    }
}



