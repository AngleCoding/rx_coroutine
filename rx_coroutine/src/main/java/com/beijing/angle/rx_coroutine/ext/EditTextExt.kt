package com.beijing.angle.rx_coroutine.ext

import android.widget.EditText
import com.blankj.utilcode.util.RegexUtils

/**
 * @author 刘红鹏
 * @description:
 * @date: 2026/2/3 16:43
 * @Link: https://github.com/AngleCoding
 */


/**
 * 简单验证手机号
 */
fun EditText.isMobileSimple(): Boolean {
    return RegexUtils.isMobileSimple(this.text.toString())
}


/**
 * 精确验证手机号
 */
fun EditText.isMobileExact(): Boolean {
    return RegexUtils.isMobileExact(this.text.toString())
}


/**
 * 验证电话号码
 */
fun EditText.isTel(): Boolean {
    return RegexUtils.isTel(this.text.toString())
}


/**
 * 验证身份证号码 15 位
 */
fun EditText.isIDCard15(): Boolean {
    return RegexUtils.isIDCard15(this.text.toString())
}


/**
 *  简单验证身份证号码 18 位
 */
fun EditText.isIDCard18(): Boolean {
    return RegexUtils.isIDCard18(this.text.toString())
}


/**
 * 精确验证身份证号码 18 位
 */
fun EditText.isIDCard18Exact(): Boolean {
    return RegexUtils.isIDCard18Exact(this.text.toString())
}


/**
 * 验证邮箱
 */
fun EditText.isEmail(): Boolean {
    return RegexUtils.isEmail(this.text.toString())
}


/**
 * 验证 URL
 */
fun EditText.isURL(): Boolean {
    return RegexUtils.isURL(this.text.toString())
}


/**
 * 验证汉字
 */
fun EditText.isZh(): Boolean {
    return RegexUtils.isZh(this.text.toString())
}


/**
 * 验证用户名
 */
fun EditText.isUsername(): Boolean {
    return RegexUtils.isUsername(this.text.toString())
}


/**
 * 验证 yyyy-MM-dd 格式的日期校验，已考虑平闰年
 */
fun EditText.isDate(): Boolean {
    return RegexUtils.isDate(this.text.toString())
}


/**
 * 验证 IP 地址
 */
fun EditText.isIP(): Boolean {
    return RegexUtils.isIP(this.text.toString())
}


/**
 * 判断是否匹配正则
 */
fun EditText.isMatch(regex: String): Boolean {
    return RegexUtils.isMatch(regex, this.text.toString())
}


/**
 * 获取正则匹配的部分
 */
fun EditText.getMatches(regex: String): List<String> {
    return RegexUtils.getMatches(regex, this.text.toString())
}


/**
 * 获取正则匹配分组
 */
fun EditText.getSplits(regex: String): kotlin.Array<String> {
    return RegexUtils.getSplits(regex, this.text.toString())
}

/**
 * 替换正则匹配的第一部分
 */
fun EditText.getReplaceFirst(regex: String, replacement: String): String {
    return RegexUtils.getReplaceFirst(this.text.toString(), regex, replacement)
}

/**
 * 替换所有正则匹配的部分
 */
fun EditText.getReplaceAll(regex: String, replacement: String): String {
    return RegexUtils.getReplaceAll(this.text.toString(), regex, replacement)
}
