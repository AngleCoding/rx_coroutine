package com.beijing.angle.rx_coroutine.main.fragment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.beijing.angle.rx_coroutine.R
import com.beijing.angle.rx_coroutine.base.BaseFragment
import com.beijing.angle.rx_coroutine.databinding.FragmentTwoBinding
import com.beijing.angle.rx_coroutine.ext.click
import com.beijing.angle.rx_coroutine.ext.showToast
import com.beijing.angle.rx_coroutine.model.BaseModel
import com.beijing.angle.rx_coroutine.network.vmObserverMain
import com.beijing.angle.rx_coroutine.utils.ImageDownloader
import com.beijing.angle.rx_coroutine.utils.ImageSaveUtils
import com.beijing.angle.rx_coroutine.utils.ImageSaveUtils.SaveImageListener


/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/25 15:52
 * @Link: https://github.com/AngleCoding
 */

class MyFragment2 : BaseFragment<FragmentTwoBinding>() {


    private val baseModel: BaseModel by lazy { BaseModel() }


    companion object {
        fun newInstance(): MyFragment2 {
            val fragment = MyFragment2()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


    override fun getViewBinding(inflater: LayoutInflater): FragmentTwoBinding {
        return FragmentTwoBinding.inflate(inflater)
    }

    @SuppressLint("CheckResult")
    override fun initView(savedInstanceState: Bundle?) {


    }

    override fun initListener() {
        binding.mButton.click {


        }
    }


    override fun initViewModel() {
        baseModel.getLatestOne()
    }

    override fun iniAgainRequestViewModel() {
        baseModel.getLatestOne()
    }


    override fun initData() {
        baseModel.latestOneBean.vmObserverMain(this, onSuccess = {
        })
    }


}