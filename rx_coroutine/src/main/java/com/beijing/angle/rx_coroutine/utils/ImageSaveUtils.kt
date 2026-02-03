package com.beijing.angle.rx_coroutine.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.OutputStream

/**
 * @author 刘红鹏
 * @description: 图片保存
 * @date: 2026/2/2 18:04
 * @Link: https://github.com/AngleCoding
 *
 */
/**
 *     ImageSaveUtils(mContext).saveImageAsync(com.github.yuan.picture_take.R.mipmap.back,object :SaveImageListener{
 *                 override fun onSuccess(filePath: String) {
 *                     showToast(filePath)
 *                 }
 *
 *                 override fun onError(errorMessage: String) {
 *                     showToast(errorMessage)
 *                 }
 *
 *             })
 */

class ImageSaveUtils(private val mContext: Context) {

    /**
     * 下载监听接口
     */
    interface SaveImageListener {
        fun onSuccess(filePath: String)
        fun onError(errorMessage: String)
    }

    /**
     * 下载结果密封类
     */
    sealed class ImageSaveResult {
        data class Success(val message: String) : ImageSaveResult()
        data class Error(val errorMessage: String) : ImageSaveResult()
    }


    /**
     * 异步保存本地图片
     * @param resources R.mipmap 资源文件
     * @param listener 监听
     *
     */
    fun saveImageAsync(
        resources: Int,
        listener: SaveImageListener
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            when (val result = saveImage(resources)) {
                is ImageSaveResult.Success -> listener.onSuccess(
                    result.message
                )

                is ImageSaveResult.Error -> listener.onError(result.errorMessage)
            }
        }
    }


    private suspend fun saveImage(
        resources: Int
    ): ImageSaveResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val bitmap = BitmapFactory.decodeResource(mContext.resources, resources)
            if (saveBitmap(mContext, bitmap)) {
                ImageSaveResult.Success("保存到手机相册,请前往查看！")
            } else {
                ImageSaveResult.Error("保存失败")
            }
        } catch (e: Exception) {
            ImageSaveResult.Error(e.message ?: "Unknown error")
        }
    }


    fun saveBitmap(context: Context, bitmap: Bitmap?): Boolean {
        try {
            if (bitmap != null) {
                val addPictureToAlbum =
                    addPictureToAlbum(context, bitmap, "GSY-" + System.currentTimeMillis() + ".jpg")
                bitmap.recycle()
                return addPictureToAlbum
            }
            return false
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }


    /**
     * 保存图片到picture 目录，Android Q适配，最简单的做法就是保存到公共目录，不用SAF存储
     *
     * @param context
     * @param bitmap
     * @param fileName
     */
    fun addPictureToAlbum(context: Context, bitmap: Bitmap, fileName: String?): Boolean {
        try {
            val contentValues = ContentValues()
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            contentValues.put(MediaStore.Images.Media.DESCRIPTION, fileName)
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            val uri: Uri? = context.contentResolver
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            val outputStream: OutputStream? =
                uri?.let { context.contentResolver.openOutputStream(it) }
            outputStream?.let { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
            outputStream?.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }


}