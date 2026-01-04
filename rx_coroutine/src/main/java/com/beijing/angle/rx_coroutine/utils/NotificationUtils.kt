package com.beijing.angle.rx_coroutine.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


/**
 * 通知栏工具
 */
object NotificationUtils {


    @SuppressLint("MissingPermission")
    fun showNotification(context: Context, appIcon: Int, title: String?, content: String) {
        val channelId = "channel_id"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, "Channel", NotificationManager.IMPORTANCE_DEFAULT)
            NotificationManagerCompat.from(context).createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(appIcon)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        NotificationManagerCompat.from(context).notify(1, builder.build())
    }

}