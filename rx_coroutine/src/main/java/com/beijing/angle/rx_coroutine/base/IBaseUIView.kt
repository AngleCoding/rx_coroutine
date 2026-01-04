package com.beijing.angle.rx_coroutine.base

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/24 16:17
 * @Link: https://github.com/AngleCoding
 */

interface IBaseUIView {

    /** 获取当前布局状态,方便在callBack中统一处理  */
    fun getBaseViewStatus(): EBaseViewStatus?

    /** 手动设置布局状态,一般情况不需要  */
    fun setBaseViewStatus(baseViewStatus: EBaseViewStatus?)

    /** 显示成功布局  */
    fun showSuccessLayout()

    /** 显示加载中布局  */
    fun showLoadingLayout()


    /** 显示上传布局  */
    fun showUploadLayout(loadingTxt: String? = "加载中...")

    /** 显示失败布局  */
    fun showErrorLayout(errorMsg: String?)




}