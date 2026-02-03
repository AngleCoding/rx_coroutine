package com.beijing.angle.rx_coroutine.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.beijing.angle.rx_coroutine.base.BaseFragment
import com.beijing.angle.rx_coroutine.databinding.FragmentMyBinding
import com.beijing.angle.rx_coroutine.model.BaseModel
import com.beijing.angle.rx_coroutine.network.vmObserverMain

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/25 15:52
 * @Link: https://github.com/AngleCoding
 */

class MyFragment : BaseFragment<FragmentMyBinding>() {


    private val baseModel: BaseModel by lazy { BaseModel() }


    companion object {
        fun newInstance(): MyFragment {
            val fragment = MyFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


    override fun getViewBinding(inflater: LayoutInflater): FragmentMyBinding {
        return FragmentMyBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    @SuppressLint("CheckResult")
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


    private var scrollChangeListener: ScrollChangeListener? = null


    public fun setScrollChangeListener(scrollChangeListener: ScrollChangeListener?) {
        this.scrollChangeListener = scrollChangeListener
    }


    interface ScrollChangeListener {
        fun onScrollChange(scrollY: Int, oldScrollY: Int)
    }


}