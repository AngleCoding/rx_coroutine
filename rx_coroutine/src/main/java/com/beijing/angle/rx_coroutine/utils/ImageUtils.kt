package com.beijing.angle.rx_coroutine.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.fragment.app.Fragment
import com.beijing.angle.rx_coroutine.permission.StoragePermissionHelper
import com.beijing.angle.rx_coroutine.permission.StoragePermissionHelper.PermissionCallback
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ImageUtils.ImageType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * @author 刘红鹏
 * @description: 图片工具
 * @date: 2026/2/4 15:11
 * @Link: https://github.com/AngleCoding
 */

object ImageUtils {


    /**
     *  获取bitmap
     *
     *  @param file file转bitmap
     *  @return Bitmap
     *
     *    ImageManageUtils.getAsyncBitmap(file, object : ImageManageListener<Bitmap> {
     *                     override fun onSuccess(data: Bitmap?) {
     *                     }
     *
     *                     override fun onError(errorMessage: String) {
     *                         showToast(errorMessage)
     *                     }
     *
     *                 })
     *
     *
     *
     */
    fun getAsyncBitmap(file: File, listener: ImageManageListener<Bitmap>) {
        CoroutineScope(Dispatchers.Main).launch {
            when (val result = getBitmapAsync(file)) {
                is ImageManageResult.Success -> listener.onSuccess(result.data)
                is ImageManageResult.Error -> listener.onError(result.error)
            }
        }
    }


    private suspend fun getBitmapAsync(file: File): ImageManageResult<Bitmap> =
        withContext(Dispatchers.IO)
        {
            return@withContext try {
                val bitmap = ImageUtils.getBitmap(file)
                ImageManageResult.Success(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                ImageManageResult.Error(e.message ?: "Unknown error")
            }
        }


    /**
     * 缩放图片
     *
     *  @param src 图片
     *  @param newWidth 宽度
     *  @param newHeight 高度
     * @return Bitmap
     *
     *    getScaleAsyncBitmap(
     *                 drawableToBitmap(resources.getDrawable(R.drawable.icon_back)),
     *                 100,
     *                 50,
     *                 object : ImageManageListener<Bitmap> {
     *                     override fun onSuccess(data: Bitmap?) {
     *                         GlideApp.with(this@MainActivity)
     *                             .load(data)
     *                             .into(binding.mImageView)
     *                     }
     *
     *                     override fun onError(errorMessage: String) {
     *                     }
     *
     *                 })
     *
     *
     */
    fun getScaleAsyncBitmap(
        src: Bitmap,
        newWidth: Int,
        newHeight: Int,
        listener: ImageManageListener<Bitmap>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            when (val result = scaleBitmapAsync(src, newWidth, newHeight)) {
                is ImageManageResult.Success -> listener.onSuccess(result.data)
                is ImageManageResult.Error -> listener.onError(result.error)
            }
        }
    }


    private suspend fun scaleBitmapAsync(
        src: Bitmap,
        newWidth: Int,
        newHeight: Int,
    ): ImageManageResult<Bitmap> =
        withContext(Dispatchers.IO)
        {
            return@withContext try {
                val bitmap = ImageUtils.scale(src, newWidth, newHeight)
                ImageManageResult.Success(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                ImageManageResult.Error(e.message ?: "Unknown error")
            }
        }


    /**
     * 裁剪图片
     * @param src 图片
     * @param pixelX 裁剪区域左上角的 X 坐标（水平位置）
     * @param pixelY 裁剪区域左上角的 Y 坐标（垂直位置）
     * @param width 裁剪区域的宽度
     * @param height 裁剪区域的高度
     * @return Bitmap
     *
     *
     *        getClipAsyncBitmap(
     *                 drawableToBitmap(resources.getDrawable(R.drawable.icon_back)), 0, 0, 80, 80,
     *                 object : ImageManageListener<Bitmap> {
     *                     override fun onSuccess(data: Bitmap?) {
     *                         GlideApp.with(this@MainActivity)
     *                             .load(data)
     *                             .into(binding.mImageView)
     *                     }
     *
     *                     override fun onError(errorMessage: String) {
     *                     }
     *
     *                 })
     *
     *
     */

    fun getClipAsyncBitmap(
        src: Bitmap,
        pixelX: Int,
        pixelY: Int,
        width: Int,
        height: Int,
        listener: ImageManageListener<Bitmap>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            when (val result = clipBitmapAsync(src, pixelX, pixelY, width, height)) {
                is ImageManageResult.Success -> listener.onSuccess(result.data)
                is ImageManageResult.Error -> listener.onError(result.error)
            }
        }
    }


    private suspend fun clipBitmapAsync(
        src: Bitmap,
        pixelX: Int,
        pixelY: Int,
        width: Int,
        height: Int,
    ): ImageManageResult<Bitmap> =
        withContext(Dispatchers.IO)
        {
            return@withContext try {
                val bitmap = ImageUtils.clip(src, pixelX, pixelY, width, height)
                ImageManageResult.Success(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                ImageManageResult.Error(e.message ?: "Unknown error")
            }
        }


    /**
     * 保存图片
     *
     * @param src bitmap
     * @param filePath 保存图片路径
     * @param format 图片格式
     * @param quality 图片质量
     * @param recycle 是否回收
     * @param listener
     * @return 图片保存路径
     *
     *
     *     saveAsyncBitmap(
     *                 drawableToBitmap(resources.getDrawable(R.drawable.icon_back)),
     *                 getInternalImageDir().path, CompressFormat.PNG, 100, true, object : ImageManageListener<String> {
     *                     override fun onSuccess(data: String?) {
     *                         data?.let {
     *                             if (true) {
     *                                 showToast("保存成功" + data)
     *                             } else {
     *                                 showToast("保存失败")
     *                             }
     *                         }
     *                     }
     *
     *                     override fun onError(errorMessage: String) {
     *                         showToast("保存异常：" + errorMessage)
     *                     }
     *
     *                 })
     *
     */
    fun saveAsyncBitmap(
        src: Bitmap,
        filePath: String,
        format: CompressFormat,
        quality: Int,
        recycle: Boolean,
        listener: ImageManageListener<String>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            when (val result = saveBitmapAsync(src, filePath, format, quality, recycle)) {
                is ImageManageResult.Success -> listener.onSuccess(result.data)
                is ImageManageResult.Error -> listener.onError(result.error)
            }
        }
    }


    private suspend fun saveBitmapAsync(
        src: Bitmap,
        filePath: String,
        format: CompressFormat,
        quality: Int,
        recycle: Boolean,
    ): ImageManageResult<String> =
        withContext(Dispatchers.IO)
        {
            return@withContext try {
                val bitmap = ImageUtils.save(src, filePath, format, quality, recycle)
                ImageManageResult.Success(filePath)
            } catch (e: Exception) {
                e.printStackTrace()
                ImageManageResult.Error(e.message ?: "Unknown error")
            }
        }


    /**
     * 保存图片到相册
     *
     *    save2AlbumAsyncBitmap(this,drawableToBitmap(resources.getDrawable(R.drawable.icon_error)),
     *                 CompressFormat.PNG, 100, false, object : ImageManageListener<Boolean> {
     *                     override fun onSuccess(data: Boolean?) {
     *                         data?.let {
     *                             if (it) {
     *                                 showToast("保存成功")
     *                             }
     *                         }
     *                     }
     *
     *                     override fun onError(errorMessage: String) {
     *                     }
     *
     *                 })
     *
     */
    fun save2AlbumAsyncBitmap(
        activity: Activity,
        src: Bitmap,
        format: CompressFormat,
        quality: Int,
        recycle: Boolean,
        listener: ImageManageListener<Boolean>
    ) {
        if (StoragePermissionHelper.checkStoragePermission()) {
            CoroutineScope(Dispatchers.Main).launch {
                when (val result = save2AlbumAsync(src, format, quality, recycle)) {
                    is ImageManageResult.Success -> listener.onSuccess(result.data)
                    is ImageManageResult.Error -> listener.onError(result.error)
                }
            }
        } else {
            StoragePermissionHelper.requestStorageActivityPermission(
                activity,
                object : PermissionCallback {
                    override fun onPermissionGranted() {
                        CoroutineScope(Dispatchers.Main).launch {
                            when (val result = save2AlbumAsync(src, format, quality, recycle)) {
                                is ImageManageResult.Success -> listener.onSuccess(result.data)
                                is ImageManageResult.Error -> listener.onError(result.error)
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
     * 保存图片到相册
     *
     *    save2AlbumAsyncBitmap(this,drawableToBitmap(resources.getDrawable(R.drawable.icon_error)),
     *                 CompressFormat.PNG, 100, false, object : ImageManageListener<Boolean> {
     *                     override fun onSuccess(data: Boolean?) {
     *                         data?.let {
     *                             if (it) {
     *                                 showToast("保存成功")
     *                             }
     *                         }
     *                     }
     *
     *                     override fun onError(errorMessage: String) {
     *                     }
     *
     *                 })
     *
     *
     */
    fun save2AlbumAsyncBitmap(
        fragment: Fragment,
        src: Bitmap,
        format: CompressFormat,
        quality: Int,
        recycle: Boolean,
        listener: ImageManageListener<Boolean>
    ) {
        if (StoragePermissionHelper.checkStoragePermission()) {
            CoroutineScope(Dispatchers.Main).launch {
                when (val result = save2AlbumAsync(src, format, quality, recycle)) {
                    is ImageManageResult.Success -> listener.onSuccess(result.data)
                    is ImageManageResult.Error -> listener.onError(result.error)
                }
            }
        } else {
            StoragePermissionHelper.requestStorageFragmentPermission(
                fragment,
                object : PermissionCallback {
                    override fun onPermissionGranted() {
                        CoroutineScope(Dispatchers.Main).launch {
                            when (val result = save2AlbumAsync(src, format, quality, recycle)) {
                                is ImageManageResult.Success -> listener.onSuccess(result.data)
                                is ImageManageResult.Error -> listener.onError(result.error)
                            }
                        }
                    }

                    override fun onPermissionDenied() {
                        listener.onError("保存失败，存储权限未打开")
                    }

                })
        }
    }


    private suspend fun save2AlbumAsync(
        src: Bitmap,
        format: CompressFormat,
        quality: Int,
        recycle: Boolean,
    ): ImageManageResult<Boolean> =
        withContext(Dispatchers.IO)
        {
            return@withContext try {
                val bitmap = ImageUtils.save2Album(src, format, quality, recycle)
                if (bitmap != null) {
                    ImageManageResult.Success(true)
                } else {
                    ImageManageResult.Success(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ImageManageResult.Error(e.message ?: "Unknown error")
            }
        }


    /**
     * 获取图片类型
     * @param filePath 图片路径
     * @return 图片格式
     *
     */

    fun getImageType(filePath: String, listener: ImageManageListener<ImageType>) {
        CoroutineScope(Dispatchers.Main).launch {
            when (val result = getImageTypeAsync(filePath)) {
                is ImageManageResult.Success -> listener.onSuccess(result.data)
                is ImageManageResult.Error -> listener.onError(result.error)
            }
        }
    }


    private suspend fun getImageTypeAsync(
        filePath: String,
    ): ImageManageResult<ImageType> =
        withContext(Dispatchers.IO)
        {
            return@withContext try {
                val imageType = ImageUtils.getImageType(filePath)
                ImageManageResult.Success(imageType)
            } catch (e: Exception) {
                e.printStackTrace()
                ImageManageResult.Error(e.message ?: "Unknown error")
            }
        }


    /**
     * 压缩图片-缩放
     * @param src Bitmap
     * @param newWidth 图片宽度
     * @param newHeight 图片高度
     * @return Bitmap
     *
     *
     *    compressScaleAsync(drawableToBitmap(resources.getDrawable(R.drawable.icon_error)),
     *                 100,
     *                 100,
     *                 object : ImageManageListener<Bitmap> {
     *                     override fun onSuccess(data: Bitmap?) {
     *                         GlideApp.with(this@MainActivity)
     *                             .load(data)
     *                             .into(binding.mImageView)
     *                     }
     *
     *                     override fun onError(errorMessage: String) {
     *                     }
     *
     *                 })
     *
     *
     *
     */
    fun compressScaleAsync(
        src: Bitmap,
        newWidth: Int,
        newHeight: Int,
        listener: ImageManageListener<Bitmap>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            when (val result = compressByScaleAsync(src, newWidth, newHeight)) {
                is ImageManageResult.Success -> listener.onSuccess(result.data)
                is ImageManageResult.Error -> listener.onError(result.error)
            }
        }
    }


    private suspend fun compressByScaleAsync(
        src: Bitmap,
        newWidth: Int,
        newHeight: Int,
    ): ImageManageResult<Bitmap> =
        withContext(Dispatchers.IO)
        {
            return@withContext try {
                val imageType = ImageUtils.compressByScale(src, newWidth, newHeight)
                ImageManageResult.Success(imageType)
            } catch (e: Exception) {
                e.printStackTrace()
                ImageManageResult.Error(e.message ?: "Unknown error")
            }
        }


    /**
     * 压缩图片-质量
     * @param src Bitmap
     * @param newWidth 图片宽度
     * @param newHeight 图片高度
     * @return Bitmap
     *
     *
     *    compressByQualityAsync(
     *                 drawableToBitmap(resources.getDrawable(R.drawable.icon_error)),
     *                 50,
     *                 false,
     *                 object : ImageManageListener<Bitmap> {
     *                     override fun onSuccess(data: Bitmap?) {
     *                         GlideApp.with(this@MainActivity)
     *                             .load(data)
     *                             .into(binding.mImageView)
     *                     }
     *
     *                     override fun onError(errorMessage: String) {
     *                     }
     *
     *                 })
     *
     *
     *
     *
     */
    fun compressByQualityAsync(
        src: Bitmap,
        @IntRange(from = 0, to = 100) quality: Int,
        recycle: Boolean,
        listener: ImageManageListener<Bitmap>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            when (val result = qualityAsync(src, quality, recycle)) {
                is ImageManageResult.Success -> listener.onSuccess(result.data)
                is ImageManageResult.Error -> listener.onError(result.error)
            }
        }
    }


    private suspend fun qualityAsync(
        src: Bitmap,
        @IntRange(from = 0, to = 100) quality: Int,
        recycle: Boolean,
    ): ImageManageResult<Bitmap> =
        withContext(Dispatchers.IO)
        {
            return@withContext try {
                val byteArray = ImageUtils.compressByQuality(src, quality, recycle)
                val bitmap = ImageUtils.bytes2Bitmap(byteArray)
                ImageManageResult.Success(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                ImageManageResult.Error(e.message ?: "Unknown error")
            }
        }


    /**
     * 添加文字水印
     * @param src 图片
     * @param content 内容
     * @param textSize 文字大小
     * @param color 文字颜色
     * @param pixelX  内容x坐标
     * @param pixelY 内容y坐标
     * @return Bitmap
     *
     *
     *    addTextWatermarkAsync(
     *                 drawableToBitmap(resources.getDrawable(R.drawable.icon_error)),
     *                 "添加水印",
     *                 20,
     *                 Color.RED,
     *                 drawableToBitmap(resources.getDrawable(R.drawable.icon_error)).width / 2.toFloat(),
     *                 drawableToBitmap(resources.getDrawable(R.drawable.icon_error)).height / 2.toFloat(),
     *                 object : ImageManageListener<Bitmap> {
     *                     override fun onSuccess(data: Bitmap?) {
     *                         GlideApp.with(this@MainActivity)
     *                             .load(data)
     *                             .into(binding.mImageView)
     *                     }
     *
     *                     override fun onError(errorMessage: String) {
     *                     }
     *
     *                 })
     *
     *
     */
    fun addTextWatermarkAsync(
        src: Bitmap,
        content: String,
        textSize: Int,
        @ColorInt color: Int,
        pixelX: Float,
        pixelY: Float,
        listener: ImageManageListener<Bitmap>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            when (val result = addTextWatermark(src, content, textSize, color, pixelX, pixelY)) {
                is ImageManageResult.Success -> listener.onSuccess(result.data)
                is ImageManageResult.Error -> listener.onError(result.error)
            }
        }
    }


    private suspend fun addTextWatermark(
        src: Bitmap,
        content: String,
        textSize: Int,
        @ColorInt color: Int,
        pixelX: Float,
        pixelY: Float,
    ): ImageManageResult<Bitmap> =
        withContext(Dispatchers.IO)
        {
            return@withContext try {
                val bitmap =
                    ImageUtils.addTextWatermark(src, content, textSize, color, pixelX, pixelY)
                ImageManageResult.Success(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                ImageManageResult.Error(e.message ?: "Unknown error")
            }
        }


    /**
     * 监听接口
     */
    interface ImageManageListener<T> {
        fun onSuccess(data: T?)
        fun onError(errorMessage: String)
    }


    /**
     * 结果密封类
     */
    sealed class ImageManageResult<out T> {
        data class Success<out T>(val data: T?) : ImageManageResult<T>()
        data class Error(val error: String) : ImageManageResult<Nothing>()
    }

}