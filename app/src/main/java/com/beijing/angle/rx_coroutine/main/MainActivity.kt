package com.beijing.angle.rx_coroutine.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.beijing.angle.rx_coroutine.base.BaseActivity
import com.beijing.angle.rx_coroutine.databinding.ActivityMainBinding
import com.beijing.angle.rx_coroutine.ext.click
import com.beijing.angle.rx_coroutine.ext.hexString2Int
import com.beijing.angle.rx_coroutine.model.BaseModel
import com.beijing.angle.rx_coroutine.network.vmObserverMain


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val baseModel: BaseModel by lazy { BaseModel() }


    override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun isToolbarVisibility(): Boolean {
        return true
    }


    override fun initView(bundle: Bundle?) {
    }


    @SuppressLint("CheckResult")
    override fun initListener() {

        binding.mBtClick.click {
            baseModel.getLatestOne()
        }

    }

    override fun initViewModel() {
    }

    override fun iniAgainRequestViewModel() {

    }

    override fun initData() {
        baseModel.latestOneBean.vmObserverMain(this, onSuccess = {
        })
    }


}