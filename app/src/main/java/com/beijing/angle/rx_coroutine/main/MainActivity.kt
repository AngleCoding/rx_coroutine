package com.beijing.angle.rx_coroutine.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.beijing.angle.rx_coroutine.adapter.FragmentAdapter
import com.beijing.angle.rx_coroutine.base.BaseActivity
import com.beijing.angle.rx_coroutine.databinding.ActivityMainBinding
import com.beijing.angle.rx_coroutine.ext.log
import com.beijing.angle.rx_coroutine.ext.showToast
import com.beijing.angle.rx_coroutine.main.fragment.MyFragment
import com.beijing.angle.rx_coroutine.main.fragment.MyFragment.ScrollChangeListener
import com.beijing.angle.rx_coroutine.main.fragment.MyFragment2
import com.flyco.tablayout.utils.FragmentChangeManager
import com.jakewharton.rxbinding4.viewpager.pageSelections
import kotlin.math.abs


class MainActivity : BaseActivity<ActivityMainBinding>(), ScrollChangeListener {


    private val mFragmentList: ArrayList<Fragment> = ArrayList()

    override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun isToolbarVisibility(): Boolean {
        return true
    }


    @SuppressLint("CheckResult")
    @androidx.annotation.RequiresPermission(android.Manifest.permission.POST_NOTIFICATIONS)
    override fun initView(bundle: Bundle?) {

        val titles = arrayOf(
            "推荐", "娱乐", "科技", "科技", "科技",
            "科技", "科技", "科技", "科技", "科技"
        )
        val fragment = MyFragment.newInstance()
        val fragment2 = MyFragment2.newInstance()
        val fragment3 = MyFragment.newInstance()
        val fragment4 = MyFragment2.newInstance()
        val fragment5 = MyFragment.newInstance()
        val fragment6 = MyFragment2.newInstance()
        val fragment7 = MyFragment.newInstance()
        val fragment8 = MyFragment.newInstance()
        val fragment9 = MyFragment.newInstance()
        val fragment10 = MyFragment.newInstance()

        mFragmentList.add(fragment)
        mFragmentList.add(fragment2)
        mFragmentList.add(fragment3)
        mFragmentList.add(fragment4)
        mFragmentList.add(fragment5)
        mFragmentList.add(fragment6)
        mFragmentList.add(fragment7)
        mFragmentList.add(fragment8)
        mFragmentList.add(fragment9)
        mFragmentList.add(fragment10)


        binding.mViewPager.adapter = FragmentAdapter(supportFragmentManager, mFragmentList)
        binding.mViewPager.offscreenPageLimit = mFragmentList.size
        binding.tab.setViewPager(binding.mViewPager, titles)
        binding.tab.onPageSelected(0)

        fragment.setScrollChangeListener(this)

    }


    override fun initListener() {

    }

    override fun initViewModel() {
    }

    override fun iniAgainRequestViewModel() {

    }

    override fun initData() {

    }


    override fun onScrollChange(scrollY: Int, oldScrollY: Int) {
    }


}