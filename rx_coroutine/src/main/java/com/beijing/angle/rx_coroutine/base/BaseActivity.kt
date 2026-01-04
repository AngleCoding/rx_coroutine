package com.beijing.angle.rx_coroutine.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.viewbinding.ViewBinding
import com.beijing.angle.rx_coroutine.R
import com.beijing.angle.rx_coroutine.databinding.ActivityBaseBinding
import com.beijing.angle.rx_coroutine.ext.click
import com.beijing.angle.rx_coroutine.lifecycle.RxActivity
import com.beijing.angle.rx_coroutine.utils.AppManager
import me.jessyan.autosize.internal.CustomAdapt

/**
 * @author 刘红鹏
 * @description:所有Activity基类
 * @date: 2025/12/19 17:47
 * @Link: https://github.com/AngleCoding
 */

abstract class BaseActivity<VB : ViewBinding> : RxActivity(),
    CustomAdapt, IBaseUIView {


    private lateinit var baseBinding: ActivityBaseBinding

    protected lateinit var binding: VB

    protected lateinit var mContext: Context

    //当前页面状态
    private var myBaseViewStatus =
        EBaseViewStatus.SUCCESS //页面显示状态 SUCCESS, LOADING, ERROR  分别表示正常、加载中、失败

    private var loadingView: View? = null //加载中布局
    private var errorView: View? = null //错误布局
    private var uploadStubView: View? = null //错误布局


    /**
     * 绑定布局
     */
    abstract fun getViewBinding(inflater: LayoutInflater): VB


    /**
     * 初始化准备工作
     */
    abstract fun initView(bundle: Bundle?)

    /**
     * 设置监听
     */
    abstract fun initListener()

    /**
     * 请求网络
     */
    abstract fun initViewModel()

    /**
     * 重新请求
     */
    abstract fun iniAgainRequestViewModel()

    /**
     * 数据
     */
    abstract fun initData()


    /**
     * 是否需要显示标题栏
     * 默认不显示
     */
    open fun isToolbarVisibility(): Boolean {
        return false
    }


    /**
     * 设置标题栏内容
     */
    open fun setTitleName(titleName: String) {
        baseBinding.toolbarTitle.text = titleName
    }

    /**
     * 设置标题栏右侧图片
     */
    open fun setRightIcon(@DrawableRes resId: Int) {
        baseBinding.rightIcon.setImageResource(resId)
    }

    /**
     * 设置标题栏右边文字内容
     */
    open fun setToolbarSubtitle(toolbarSubtitle: String) {
        baseBinding.toolbarSubtitle.text = toolbarSubtitle
    }

    /**
     * 设置标题栏右边文字内容
     */
    open fun setToolbarSubtitle(@ColorInt color: Int) {
        baseBinding.toolbarSubtitle.setTextColor(color)
    }

    /**
     * 设置标题栏返回图片
     */
    open fun setBackIcon(@DrawableRes resId: Int) {
        baseBinding.back.setImageResource(resId)
    }

    open fun getBundleExtras(extras: Bundle?) {} //接收bundle数据


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)
        this.mContext = applicationContext
        enableEdgeToEdge()
        initialization(savedInstanceState)
    }


    private fun initialization(bundle: Bundle?) {
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        if (isToolbarVisibility()) {
            baseBinding.titleBar.visibility = VISIBLE
        } else {
            baseBinding.titleBar.visibility = GONE
        }
        setContentView(baseBinding.root)
        binding = getViewBinding(layoutInflater)
        baseBinding.back.click { finish() }
        baseBinding.contentLayout.addView(binding.root)
        intent.extras?.let { getBundleExtras(it) }
        initView(bundle)
        initListener()
        initViewModel()
        initData()
    }




    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.removeActivity(this)

    }


    override fun isBaseOnWidth(): Boolean {
        return true
    }

    override fun getSizeInDp(): Float {
        return 375F
    }


    override fun getBaseViewStatus(): EBaseViewStatus? {
        return myBaseViewStatus
    }

    override fun setBaseViewStatus(baseViewStatus: EBaseViewStatus?) {
        when (baseViewStatus) {
            EBaseViewStatus.LOADING -> {
                showLoadingLayout()
            }

            EBaseViewStatus.SUCCESS -> {
                showSuccessLayout()
            }

            EBaseViewStatus.ERROR -> {
                showErrorLayout("")
            }

            EBaseViewStatus.UPLOAD -> {
                showUploadLayout("")
            }

            else -> {
                showErrorLayout("")
            }
        }
    }


    /**
     * 显示加载中界面
     */
    override fun showLoadingLayout() {
        loadingView ?: let {
            loadingView = baseBinding.loadingStub.inflate()
        }

        //显示界面
        baseBinding.contentLayout.visibility = GONE
        loadingView?.visibility = VISIBLE
        errorView?.visibility = GONE
        uploadStubView?.visibility = GONE

        //更改状态
        myBaseViewStatus = EBaseViewStatus.LOADING
    }


    override fun showUploadLayout(loadingTxt: String?) {
        uploadStubView ?: let {
            uploadStubView = baseBinding.uploadStub.inflate()
        }


        uploadStubView?.findViewById<TextView>(R.id.mTvContent)?.apply {
            text = loadingTxt
        }

        //显示界面
        baseBinding.contentLayout.visibility = VISIBLE
        uploadStubView?.visibility = VISIBLE
        loadingView?.visibility = GONE
        errorView?.visibility = GONE


        //更改状态
        myBaseViewStatus = EBaseViewStatus.UPLOAD

    }

    /**
     * 显示子布局,隐藏加载中和错误布局
     */
    override fun showSuccessLayout() {
        //显示界面
        baseBinding.contentLayout.visibility = VISIBLE
        loadingView?.visibility = GONE
        errorView?.visibility = GONE
        uploadStubView?.visibility = GONE

        //更改状态
        myBaseViewStatus = EBaseViewStatus.SUCCESS
    }


    /**
     * 显示错误布局,隐藏子布局
     *
     * @param error
     */
    override fun showErrorLayout(loadingTxt: String?) {
        errorView ?: let {
            errorView = baseBinding.errorStub.inflate()
        }

        //显示界面
        baseBinding.contentLayout.visibility = GONE
        loadingView?.visibility = GONE
        errorView?.visibility = VISIBLE
        uploadStubView?.visibility = GONE


        errorView?.findViewById<TextView>(R.id.mTvContent)?.apply {
            text = loadingTxt
        }

        errorView?.findViewById<TextView>(R.id.mBtRetry)?.apply {
            this.click {
                iniAgainRequestViewModel()
            }
        }

        //更改状态
        myBaseViewStatus = EBaseViewStatus.ERROR
    }


}