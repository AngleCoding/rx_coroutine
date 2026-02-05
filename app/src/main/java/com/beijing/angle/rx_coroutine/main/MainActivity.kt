package com.beijing.angle.rx_coroutine.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.beijing.angle.rx_coroutine.R
import com.beijing.angle.rx_coroutine.base.BaseActivity
import com.beijing.angle.rx_coroutine.databinding.ActivityMainBinding
import com.beijing.angle.rx_coroutine.ext.cancel
import com.beijing.angle.rx_coroutine.ext.click
import com.beijing.angle.rx_coroutine.ext.dp2px
import com.beijing.angle.rx_coroutine.ext.executeByCached
import com.beijing.angle.rx_coroutine.ext.executeByCachedAtFixRate
import com.beijing.angle.rx_coroutine.ext.executeByCachedWithDelay
import com.beijing.angle.rx_coroutine.ext.executeByCpu
import com.beijing.angle.rx_coroutine.ext.executeByCpuAtFixRate
import com.beijing.angle.rx_coroutine.ext.executeByCpuWithDelay
import com.beijing.angle.rx_coroutine.ext.executeByFixed
import com.beijing.angle.rx_coroutine.ext.executeByFixedAtFixRate
import com.beijing.angle.rx_coroutine.ext.executeByFixedWithDelay
import com.beijing.angle.rx_coroutine.ext.executeByIo
import com.beijing.angle.rx_coroutine.ext.executeByIoAtFixRate
import com.beijing.angle.rx_coroutine.ext.executeByIoWithDelay
import com.beijing.angle.rx_coroutine.ext.executeBySingleAtFixRate
import com.beijing.angle.rx_coroutine.ext.executeBySingleWithDelay
import com.beijing.angle.rx_coroutine.ext.getCachedPool
import com.beijing.angle.rx_coroutine.ext.getCpuPool
import com.beijing.angle.rx_coroutine.ext.getFixedPool
import com.beijing.angle.rx_coroutine.ext.getIoPool
import com.beijing.angle.rx_coroutine.ext.getScreenWidth
import com.beijing.angle.rx_coroutine.ext.getSinglePool
import com.beijing.angle.rx_coroutine.ext.hexString2Int
import com.beijing.angle.rx_coroutine.ext.int2HexString
import com.beijing.angle.rx_coroutine.ext.isMainThread
import com.beijing.angle.rx_coroutine.ext.runOnUiThread
import com.beijing.angle.rx_coroutine.ext.runOnUiThreadDelayed
import com.beijing.angle.rx_coroutine.ext.showToast
import com.beijing.angle.rx_coroutine.utils.ImageUtils.ImageManageListener
import com.beijing.angle.rx_coroutine.utils.ImageUtils.addTextWatermarkAsync
import com.beijing.angle.rx_coroutine.utils.ImageUtils.compressByQualityAsync
import com.beijing.angle.rx_coroutine.utils.NotificationUtil
import com.beijing.angle.rx_coroutine.utils.NotificationUtil.NotificationListener
import com.blankj.utilcode.util.ThreadUtils.Task
import com.blankj.utilcode.util.ToastUtils
import com.github.yuan.picture_take.utils.GlideApp
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit


class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun isToolbarVisibility(): Boolean {
        return true
    }


    override fun initView(bundle: Bundle?) {
    }


    override fun initListener() {

        binding.mBtClick.click {
            binding.mBtClick.setText(hexString2Int("2"))
        }

    }

    override fun initViewModel() {
    }

    override fun iniAgainRequestViewModel() {

    }

    override fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()
    }


}