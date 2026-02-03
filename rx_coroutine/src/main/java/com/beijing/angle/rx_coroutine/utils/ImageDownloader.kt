package com.beijing.angle.rx_coroutine.utils

import androidx.fragment.app.Fragment
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.beijing.angle.rx_coroutine.utils.StoragePermissionHelper.PermissionCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @author 刘红鹏
 * @description: 图片下载工具
 * @date: 2026/2/2 15:46
 * @Link: https://github.com/AngleCoding
 */
/**
 *
 *
 *    ImageDownloader().downloadImageActivityAsync("url", this, object
 *                 : DownloadListener {
 *                 override fun onSuccess(filePath: String, uri: Uri) {
 *                     showToast(filePath)
 *                 }
 *
 *                 override fun onError(errorMessage: String) {
 *                     showToast(errorMessage)
 *                 }
 *
 *             })
 *
 */
class ImageDownloader() {

    /**
     * 异步下载图片带回调
     * @param imageUrl 图片URL
     * @param listener 下载监听器
     */
    fun downloadImageActivityAsync(
        imageUrl: String,
        activity: AppCompatActivity,
        listener: DownloadListener
    ) {

        if (StoragePermissionHelper.checkStoragePermission()) {
            CoroutineScope(Dispatchers.Main).launch {
                when (val result = downloadImage(imageUrl)) {
                    is DownloadResult.Success -> listener.onSuccess(
                        result.filePath,
                        result.uri
                    )

                    is DownloadResult.Error -> listener.onError(result.errorMessage)
                }
            }
        } else {
            StoragePermissionHelper.requestStorageActivityPermission(
                activity,
                object : PermissionCallback {
                    override fun onPermissionGranted() {
                        CoroutineScope(Dispatchers.Main).launch {
                            when (val result = downloadImage(imageUrl)) {
                                is DownloadResult.Success -> listener.onSuccess(
                                    result.filePath,
                                    result.uri
                                )

                                is DownloadResult.Error -> listener.onError(result.errorMessage)
                            }
                        }
                    }

                    override fun onPermissionDenied() {
                        listener.onError("保存失败，存储权限未打开")
                    }

                })
        }

    }


    /**
     * 异步下载图片带回调
     * @param imageUrl 图片URL
     * @param listener 下载监听器
     */
    fun downloadImageFragmentAsync(
        imageUrl: String,
        fragment: Fragment,
        listener: DownloadListener
    ) {

        if (StoragePermissionHelper.checkStoragePermission()) {
            CoroutineScope(Dispatchers.Main).launch {
                when (val result = downloadImage(imageUrl)) {
                    is DownloadResult.Success -> listener.onSuccess(
                        result.filePath,
                        result.uri
                    )

                    is DownloadResult.Error -> listener.onError(result.errorMessage)
                }
            }
        } else {
            StoragePermissionHelper.requestStorageFragmentPermission(
                fragment,
                object : PermissionCallback {
                    override fun onPermissionGranted() {
                        CoroutineScope(Dispatchers.Main).launch {
                            when (val result = downloadImage(imageUrl)) {
                                is DownloadResult.Success -> listener.onSuccess(
                                    result.filePath,
                                    result.uri
                                )

                                is DownloadResult.Error -> listener.onError(result.errorMessage)
                            }
                        }
                    }

                    override fun onPermissionDenied() {
                        listener.onError("保存失败，存储权限未打开")
                    }

                })
        }

    }


    /**
     * 下载图片并保存到本地
     * @param imageUrl 图片URL
     * @param fileName 文件名（可选，默认生成时间戳文件名）
     * @param directory 保存目录（可选，默认Download目录）
     * @return 下载结果
     */
    private suspend fun downloadImage(
        imageUrl: String,
        fileName: String? = null,
        directory: File? = null
    ): DownloadResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.requestMethod = "GET"

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                return@withContext DownloadResult.Error("HTTP Error: ${connection.responseCode}")
            }

            val inputStream = BufferedInputStream(connection.inputStream)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            if (bitmap == null) {
                inputStream.close()
                return@withContext DownloadResult.Error("Failed to decode image")
            }

            val finalFileName = fileName ?: generateFileName(imageUrl)
            val saveDirectory = directory ?: getDownloadDirectory()
            val file = File(saveDirectory, finalFileName)

            if (!saveDirectory.exists()) {
                saveDirectory.mkdirs()
            }

            val outputStream = FileOutputStream(file)
            val format = when {
                finalFileName.endsWith(".png", ignoreCase = true) -> Bitmap.CompressFormat.PNG
                finalFileName.endsWith(".jpg", ignoreCase = true) ||
                        finalFileName.endsWith(
                            ".jpeg",
                            ignoreCase = true
                        ) -> Bitmap.CompressFormat.JPEG

                else -> Bitmap.CompressFormat.PNG
            }

            bitmap.compress(format, 90, outputStream)
            outputStream.flush()
            outputStream.close()
            inputStream.close()
            connection.disconnect()

            DownloadResult.Success(file.absolutePath, Uri.fromFile(file))
        } catch (e: Exception) {
            DownloadResult.Error(e.message ?: "Unknown error")
        }
    }


    /**
     * 获取下载目录
     */
    private fun getDownloadDirectory(): File {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    }

    /**
     * 生成文件名
     */
    private fun generateFileName(url: String): String {
        val extension = getImageExtension(url)
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return "image_$timestamp$extension"
    }


    /**
     * 获取图片扩展名
     */
    private fun getImageExtension(url: String): String {
        return when {
            url.contains(".png", ignoreCase = true) -> ".png"
            url.contains(".jpg", ignoreCase = true) -> ".jpg"
            url.contains(".jpeg", ignoreCase = true) -> ".jpeg"
            else -> ".png"
        }
    }


    /**
     * 下载结果密封类
     */
    sealed class DownloadResult {
        data class Success(val filePath: String, val uri: Uri) : DownloadResult()
        data class Error(val errorMessage: String) : DownloadResult()
    }


    /**
     * 下载监听接口
     */
    interface DownloadListener {
        fun onSuccess(filePath: String, uri: Uri)
        fun onError(errorMessage: String)
    }


}