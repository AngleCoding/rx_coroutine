package com.beijing.angle.rx_coroutine.ext

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.beijing.angle.rx_coroutine.R

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/29 9:56
 * @Link: https://github.com/AngleCoding
 */


@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
fun Context.showRichTextNotification(
    channelId: String,
    channelName: String,
    title: String,
    text: String,
    largeIconResId: Int,
    bigText: String,
    intent: PendingIntent
) {
    // 创建通知渠道（Android 8.0+）
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            lightColor = Color.BLUE
            enableLights(true)
        }
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }

    // 构建富文本通知
    val notification = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(R.drawable.icon_back)
        .setContentTitle(title)
        .setContentText(text)
        .setLargeIcon(BitmapFactory.decodeResource(resources, largeIconResId))
        .setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
        .setContentIntent(intent)
        .setAutoCancel(true)
        .build()

    // 发送通知
    NotificationManagerCompat.from(this).notify(1, notification)
}


@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
fun Context.showBigPictureNotification(
    channelId: String,
    channelName: String,
    title: String,
    text: String,
    largeIconResId: Int,
    bigPictureResId: Int,
    intent: PendingIntent
) {
    // 创建通知渠道
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT
        )
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }

    // 构建大图通知
    val notification = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(R.drawable.icon_back)
        .setContentTitle(title)
        .setContentText(text)
        .setLargeIcon(BitmapFactory.decodeResource(resources, largeIconResId))
        .setStyle(
            NotificationCompat.BigPictureStyle()
                .bigPicture(BitmapFactory.decodeResource(resources, bigPictureResId))
        )
        .setContentIntent(intent)
        .setAutoCancel(true)
        .build()

    // 发送通知
    NotificationManagerCompat.from(this).notify(2, notification)
}

