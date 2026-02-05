package com.beijing.angle.rx_coroutine.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import com.beijing.angle.rx_coroutine.R
import com.beijing.angle.rx_coroutine.base.BaseActivity
import com.beijing.angle.rx_coroutine.databinding.ActivityMainBinding
import com.beijing.angle.rx_coroutine.ext.click
import com.beijing.angle.rx_coroutine.ext.dp2px
import com.beijing.angle.rx_coroutine.ext.getScreenWidth
import com.beijing.angle.rx_coroutine.ext.isMainThread
import com.beijing.angle.rx_coroutine.ext.showToast
import com.beijing.angle.rx_coroutine.utils.ImageUtils.ImageManageListener
import com.beijing.angle.rx_coroutine.utils.ImageUtils.addTextWatermarkAsync
import com.beijing.angle.rx_coroutine.utils.ImageUtils.compressByQualityAsync
import com.beijing.angle.rx_coroutine.utils.NotificationUtil
import com.beijing.angle.rx_coroutine.utils.NotificationUtil.NotificationListener
import com.github.yuan.picture_take.utils.GlideApp
import java.io.File
import java.io.FileOutputStream


class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun isToolbarVisibility(): Boolean {
        return true
    }


    override fun initView(bundle: Bundle?) {

        binding.mBtClick.click {
            if (isMainThread()) {
                showToast("主线程")
            } else {
                showToast("bus主线程")
            }
        }

    }


    override fun initListener() {

    }

    override fun initViewModel() {
    }

    override fun iniAgainRequestViewModel() {

    }

    override fun initData() {

    }


}