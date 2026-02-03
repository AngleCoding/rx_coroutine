package com.beijing.angle.rx_coroutine.ext

import android.widget.EditText
import com.blankj.utilcode.util.RegexUtils
import java.util.regex.Pattern

/**
 * @author 刘红鹏
 * @description: 
 * @date: 2026/2/3 16:43
 * @Link: https://github.com/AngleCoding
*/

private const val PHONE_REGEX = "^1[3-9]\\d{9}$"


private fun isPhoneValid(phone: String?): Boolean {
    return Pattern.matches(PHONE_REGEX, phone)
}


//验证手机号
fun EditText.isPhone(): Boolean {
    return isPhoneValid(this.text.toString())
}


//验证身份证
fun EditText.isIDCard18Exact(): Boolean {
    return RegexUtils.isIDCard18Exact(this.text.toString())
}