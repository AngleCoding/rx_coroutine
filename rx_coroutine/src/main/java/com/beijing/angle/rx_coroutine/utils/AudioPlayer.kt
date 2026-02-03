package com.beijing.angle.rx_coroutine.utils

import android.content.Context
import android.media.MediaPlayer

/**
 * @author 刘红鹏
 * @description:
 * @date: 2026/2/3 16:38
 * @Link: https://github.com/AngleCoding
 */

class AudioPlayer(val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    /**
     * 播放本地音频文件
     */
    fun playLocalAudio(filePath: String) {
        try {
            releaseMediaPlayer()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(filePath)
                prepareAsync()
                setOnPreparedListener { start() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 播放Assets中的音频文件
     */
    fun playAssetAudio(assetPath: String) {
        try {
            releaseMediaPlayer()
            mediaPlayer = MediaPlayer().apply {
                val assetFileDescriptor = context.assets.openFd(assetPath)
                setDataSource(
                    assetFileDescriptor.fileDescriptor,
                    assetFileDescriptor.startOffset,
                    assetFileDescriptor.length
                )
                prepareAsync()
                setOnPreparedListener { start() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 播放网络音频文件
     */
    fun playOnlineAudio(url: String) {
        try {
            releaseMediaPlayer()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(url)
                prepareAsync()
                setOnPreparedListener { start() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 暂停播放
     */
    fun pause() {
        mediaPlayer?.pause()
    }

    /**
     * 继续播放
     */
    fun resume() {
        mediaPlayer?.start()
    }

    /**
     * 停止播放
     */
    fun stop() {
        releaseMediaPlayer()
    }

    /**
     * 设置播放进度
     */
    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    /**
     * 获取当前播放位置
     */
    fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    /**
     * 获取音频总时长
     */
    fun getDuration(): Int {
        return mediaPlayer?.duration ?: 0
    }

    /**
     * 是否正在播放
     */
    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying == true
    }

    /**
     * 释放媒体播放器资源
     */
    private fun releaseMediaPlayer() {
        mediaPlayer?.apply {
            if (isPlaying) stop()
            release()
        }
        mediaPlayer = null
    }
}