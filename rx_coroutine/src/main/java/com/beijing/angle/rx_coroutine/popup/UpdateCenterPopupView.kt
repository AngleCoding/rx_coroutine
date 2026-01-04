package com.beijing.angle.rx_coroutine.popup

import android.annotation.SuppressLint
import android.content.Context
import com.beijing.angle.rx_coroutine.R
import com.beijing.angle.rx_coroutine.databinding.PopupUpdateBinding
import com.beijing.angle.rx_coroutine.ext.click
import com.beijing.angle.rx_coroutine.lifecycle.popupview.RxCenterPopupView

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/26 9:57
 * @Link: https://github.com/AngleCoding
 */

@SuppressLint("ViewConstructor")
class UpdateCenterPopupView constructor(
    context: Context,
    val name: String?,
    val des: String?,
    val updateClickListener: UpdateClickListener?
) :
    RxCenterPopupView(context) {

    private lateinit var mPopupUpdateBinding: PopupUpdateBinding

    override fun onCreate() {
        super.onCreate()

        mPopupUpdateBinding = PopupUpdateBinding.bind(contentView)

        name?.let { mPopupUpdateBinding.versionName.text = it }
        des?.let { mPopupUpdateBinding.remark.text = it }

        initOnClickListener()
    }

    private fun initOnClickListener() {
        mPopupUpdateBinding.update.click {
            updateClickListener?.update()
        }


        mPopupUpdateBinding.cancel.click {
            updateClickListener?.finish()
        }

    }


    override fun getImplLayoutId(): Int {
        return R.layout.popup_update
    }


    interface UpdateClickListener {
        fun update()
        fun finish()
    }
}