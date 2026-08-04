package com.beijing.angle.rx_coroutine.utils

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * @author 刘红鹏
 * @description:
 * @date: 2026/8/4 11:15
 * @Link: https://github.com/AngleCoding
 */



/**
 * 挂起函数：异步下载 PDF
 * 调用此函数时，它会在 IO 线程执行，并返回文件路径或抛出异常
 *
 *
 *
 *
 *    lifecycleScope.launch {
 *                 try {
 *
 *                     val data = fetchDataFromNetwork()
 *
 *                 } catch (e: Exception) {
 *                     tvResult.text = "错误: ${e.message}"
 *                 }
 *             }
 */
suspend fun downloadPdfAsync(
    url: String,
    fileName: String,
    context: Context
): String {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        val response = client.newCall(request).execute()

        if (!response.isSuccessful) {
            throw IOException("Server error: ${response.code}")
        }

        val inputStream = response.body?.byteStream()
            ?: throw IOException("Response body is null")

        val dir = context.getExternalFilesDir(android.os.Environment.DIRECTORY_DOWNLOADS)
            ?: throw IOException("External files directory is null")

        if (!dir.exists()) dir.mkdirs()

        val destFile = File(dir, fileName)

        // 使用 use 自动关闭流
        inputStream.use { input ->
            FileOutputStream(destFile).use { output ->
                val buffer = ByteArray(1024)
                var len: Int
                while (input.read(buffer).also { len = it } != -1) {
                    output.write(buffer, 0, len)
                }
                output.flush()
            }
        }

        destFile.absolutePath
    }
}