package com.beijing.angle.rx_coroutine.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.beijing.angle.rx_coroutine.R
import com.beijing.angle.rx_coroutine.databinding.FragmentBaseBinding
import com.beijing.angle.rx_coroutine.ext.click
import com.beijing.angle.rx_coroutine.lifecycle.RxFragment

/**
 * @author 刘红鹏
 * @description:所有Fragment基类
 * @date: 2025/12/19 17:49
 * @Link: https://github.com/AngleCoding
 *
 *
 *
 *     private val mFragmentList: ArrayList<Fragment> = ArrayList()
 *
 *      val titles = arrayOf(
 *             "推荐"
 *         )
 *         val fragment = MyFragment.newInstance()
 *
 *
 *          mFragmentList.add(fragment)
 *
 *            binding.mViewPager.adapter = FragmentAdapter(supportFragmentManager, mFragmentList)
 *         binding.mViewPager.offscreenPageLimit = mFragmentList.size
 *         binding.tab.setViewPager(binding.mViewPager, titles)
 *         binding.tab.onPageSelected(0)
 *
 *
 *
 *     companion object {
 *         fun newInstance(): MyFragment {
 *             val fragment = MyFragment()
 *             val args = Bundle()
 *             fragment.arguments = args
 *             return fragment
 *         }
 *     }
 *
 *
 *  <LinearLayout
 *         android:id="@+id/contentLayout"
 *         android:orientation="vertical"
 *         android:layout_marginLeft="@dimen/dp_15"
 *         android:layout_marginRight="@dimen/dp_15"
 *         android:layout_width="match_parent"
 *         android:layout_height="match_parent">
 *
 *         <com.flyco.tablayout.SlidingTabLayout
 *             android:id="@+id/tab"
 *             tl:tl_textBold="SELECT"
 *             android:layout_width="match_parent"
 *             android:layout_height="@dimen/dp_50"
 *             tl:tl_indicator_width="30dp"
 *             tl:tl_indicator_height="@dimen/dp_7"
 *             tl:tl_tab_padding="20dp"
 *             tl:tl_indicator_bounce_enable="true"
 *             tl:tl_indicator_anim_duration="5000"
 *             tl:tl_tab_space_equal="false"
 *             tl:tl_indicator_style="NORMAL"
 *             tl:tl_indicator_corner_radius="@dimen/dp_10"
 *             tl:tl_textSelectColor="#2E81FF"
 *             tl:tl_indicator_color="#2E81FF"
 *             tl:tl_textUnselectColor="#8CA0CD"
 *             tl:tl_textsize="18dp"
 *
 *             />
 *
 *         <com.beijing.angle.rx_coroutine.widget.NoScrollViewPager
 *             android:id="@+id/mViewPager"
 *             android:layout_width="match_parent"
 *             android:layout_height="match_parent" />
 *
 *     </LinearLayout>
 *
 *
 *
 */

abstract class BaseFragment<VB : ViewBinding> : RxFragment(), IBaseUIView {



    private lateinit var baseBinding: FragmentBaseBinding


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
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)


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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        baseBinding = FragmentBaseBinding.inflate(inflater, container, false) //用布局填充器填充布局
        binding = getViewBinding(inflater)
        baseBinding.contentLayout.addView(binding.root)
        return baseBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        if (isAdded && isVisible && !isHidden) {
            initViewModel()
        }
        initData()
        initListener()
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
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
        errorView?.visibility = VISIBLE
        baseBinding.contentLayout.visibility = GONE
        loadingView?.visibility = GONE
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