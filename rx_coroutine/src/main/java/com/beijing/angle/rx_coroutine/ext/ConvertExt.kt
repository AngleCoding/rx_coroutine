package com.beijing.angle.rx_coroutine.ext

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import com.blankj.utilcode.util.ConvertUtils
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

/**
 * @author 刘红鹏
 * @description:
 * @date: 2026/2/5 15:22
 * @Link: https://github.com/AngleCoding
 */


/**
 * int 与 hexString 互转
 */
fun Any.int2HexString(num: Int): String? {
    try {
        return ConvertUtils.int2HexString(num)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * hexString 与 Int 互转
 */
fun Any.hexString2Int(hexString: String): Int {
    try {
        return ConvertUtils.hexString2Int(hexString)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return -1
}


/**
 *bytes 与 bits 互转
 */
fun Any.bytes2Bits(bytes: ByteArray): String? {
    try {
        return ConvertUtils.bytes2Bits(bytes)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 *bytes 与 bits 互转
 */
fun Any.bits2Bytes(bytes: String): ByteArray? {
    try {
        return ConvertUtils.bits2Bytes(bytes)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 * bytes 与 chars 互转
 */
fun Any.bytes2Chars(bytes: ByteArray): CharArray? {
    try {
        return ConvertUtils.bytes2Chars(bytes)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 * bytes 与 chars 互转
 */
fun Any.bytes2Chars(bytes: CharArray): ByteArray? {
    try {
        return ConvertUtils.chars2Bytes(bytes)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 *bytes 与 hexString 互转
 */
fun Any.bytes2HexString(bytes: ByteArray): String? {
    try {
        return ConvertUtils.bytes2HexString(bytes)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 *bytes 与 hexString 互转
 */
fun Any.hexString2Bytes(hexString: String): ByteArray? {
    try {
        return ConvertUtils.hexString2Bytes(hexString)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 * bytes 与 string 互转
 */
fun Any.bytes2String(bytes: ByteArray): String? {
    try {
        return ConvertUtils.bytes2String(bytes)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 * bytes 与 string 互转
 */
fun Any.string2Bytes(string: String): ByteArray? {
    try {
        return ConvertUtils.string2Bytes(string)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 * bytes 与 JSONObject 互转
 */
fun Any.bytes2JSONObject(bytes: ByteArray): JSONObject? {
    try {
        return ConvertUtils.bytes2JSONObject(bytes)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 * bytes 与 JSONObject 互转
 */
fun Any.jsonObject2Bytes(jsonObject: JSONObject): ByteArray? {
    try {
        return ConvertUtils.jsonObject2Bytes(jsonObject)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 * bytes 与 JSONArray 互转
 */
fun Any.bytes2JSONArray(bytes: ByteArray): JSONArray? {
    try {
        return ConvertUtils.bytes2JSONArray(bytes)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 * bytes 与 JSONArray 互转
 */
fun Any.bytes2JSONArray(jsonArray: JSONArray): ByteArray? {
    try {
        return ConvertUtils.jsonArray2Bytes(jsonArray)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 *inputStream 与 outputStream 互转
 */
fun Any.input2OutputStream(input: InputStream): ByteArrayOutputStream? {
    try {
        return ConvertUtils.input2OutputStream(input)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 *OutputStream 与 ByteArrayInputStream 互转
 */
fun Any.input2OutputStream(output: OutputStream): ByteArrayInputStream? {
    try {
        return ConvertUtils.output2InputStream(output)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 *drawable 与 bitmap 互转
 */
fun Any.drawable2Bitmap(drawable: Drawable): Bitmap? {
    try {
        return ConvertUtils.drawable2Bitmap(drawable)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 *bitmap 与 drawable 互转
 */
fun Any.drawable2Bitmap(bitmap: Bitmap): Drawable? {
    try {
        return ConvertUtils.bitmap2Drawable(bitmap)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 *drawable 与 bytes 互转
 */
fun Any.drawable2Bytes(drawable: Drawable): ByteArray? {
    try {
        return ConvertUtils.drawable2Bytes(drawable)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 *ByteArray 与 Drawable 互转
 */
fun Any.bytes2Drawable(bytes: ByteArray): Drawable? {
    try {
        return ConvertUtils.bytes2Drawable(bytes)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


/**
 *view 转 Bitmap
 */
fun Any.view2Bitmap(view: View): Bitmap? {
    try {
        return ConvertUtils.view2Bitmap(view)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

