package com.beijing.angle.rx_coroutine.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.beijing.angle.rx_coroutine.base.BaseFragment
import com.beijing.angle.rx_coroutine.databinding.FragmentTwoBinding
import com.beijing.angle.rx_coroutine.ext.showToast
import com.beijing.angle.rx_coroutine.model.BaseModel
import com.beijing.angle.rx_coroutine.network.vmObserverMain
import com.jakewharton.rxbinding4.view.focusChanges


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