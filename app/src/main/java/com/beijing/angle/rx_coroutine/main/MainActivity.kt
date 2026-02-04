package com.beijing.angle.rx_coroutine.main

import android.os.Bundle
import android.view.LayoutInflater
import com.beijing.angle.rx_coroutine.base.BaseActivity
import com.beijing.angle.rx_coroutine.databinding.ActivityMainBinding
import com.beijing.angle.rx_coroutine.ext.click
import com.beijing.angle.rx_coroutine.permission.BluetoothPermissionHelper
import com.beijing.angle.rx_coroutine.permission.BluetoothPermissionHelper.PermissionCallback


class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun isToolbarVisibility(): Boolean {
        return true
    }


    override fun initView(bundle: Bundle?) {

        binding.mBtClick.click {
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