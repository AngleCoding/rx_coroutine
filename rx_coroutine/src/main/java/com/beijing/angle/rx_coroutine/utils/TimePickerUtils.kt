package com.beijing.angle.rx_coroutine.utils

import android.annotation.SuppressLint
import android.R
import android.view.Window
import com.bigkoo.pickerview.builder.TimePickerBuilder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * 时间选择：支持年月日时分秒
 *
 *     TimePickerUtils.showTimePicker(
 *                 window, TimeType.SECONDS,
 *                 object : TimeSelectData {
 *                     override fun callback(data: String?) {
 *                         binding.mBtClick.text = data
 *                     }
 *                 }
 *             )
 *
 *
 */
object TimePickerUtils {

    enum class TimeType {
        YEAR,
        MONTH,
        DAY,
        HOURS,
        MINUTES,
        SECONDS,
    }

    /**
     * @param window
     * @param type  YEAR  MONTH
     * @param listener 选中回调
     */
    fun showTimePicker(window: Window, type: TimeType, listener: TimeSelectData?) {
        val startDate = Calendar.getInstance()
        startDate[2017, 0] = 1
        val calendar = Calendar.getInstance()
        calendar.time = Date()

        TimePickerBuilder(window.context) { date, v ->
            when (type) {
                TimeType.YEAR -> {
                    val dateYearStr = getDateYearStr(date)
                    listener?.callback(dateYearStr)
                }

                TimeType.MONTH -> {
                    val dateMonthStr = getDateMonthStr(date)
                    listener?.callback(dateMonthStr)
                }

                TimeType.DAY -> {
                    val dateDayStr = getDateDayStr(date)
                    listener?.callback(dateDayStr)
                }

                TimeType.HOURS -> {
                    val dateHoursStr = getDateHoursStr(date)
                    listener?.callback(dateHoursStr)
                }

                TimeType.MINUTES -> {
                    val dateMinutesStr = getDateMinutesStr(date)
                    listener?.callback(dateMinutesStr)
                }

                TimeType.SECONDS -> {
                    val dateSecondsStr = getDateSecondsStr(date)
                    listener?.callback(dateSecondsStr)
                }
            }
        }.apply {
            when (type) {
                TimeType.YEAR -> {
                    setType(booleanArrayOf(true, false, false, false, false, false))
                }

                TimeType.MONTH -> {
                    setType(booleanArrayOf(true, true, false, false, false, false))
                }

                TimeType.DAY -> {
                    setType(booleanArrayOf(true, true, true, false, false, false))
                }

                TimeType.HOURS -> {
                    setType(booleanArrayOf(true, true, true, true, false, false))
                }

                TimeType.MINUTES -> {
                    setType(booleanArrayOf(true, true, true, true, true, false))
                }

                TimeType.SECONDS -> {
                    setType(booleanArrayOf(true, true, true, true, true, true))
                }
            }

            setCancelText("取消") //取消按钮文字
            setSubmitText("确定") //确认按钮文字
            setSubCalSize(14)
            setContentTextSize(16) //滚轮文字大小
            setTitleSize(14) //标题文字大小
            setTitleText("") //标题文字
            setOutSideCancelable(true) //点击屏幕，点在控件外部范围时，是否取消显示
            isCyclic(false) //是否循环滚动
            setDate(calendar) // 如果不设置的话，默认是系统时间*/
            setLabel("年", "月", "日", "时", "分", "秒") //默认设置为年月日时分秒
            isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            isDialog(false) //是否显示为对话框样式
            setDecorView(window.decorView.findViewById(R.id.content))
            build().show()
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun getDateYearStr(date: Date): String? {
        val formatter = SimpleDateFormat("yyyy")
        return formatter.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDateMonthStr(date: Date): String? {
        val formatter = SimpleDateFormat("yyyy-MM ")
        return formatter.format(date)
    }


    @SuppressLint("SimpleDateFormat")
    private fun getDateDayStr(date: Date): String? {
        val formatter = SimpleDateFormat("yyyy-MM-dd ")
        return formatter.format(date)
    }


    @SuppressLint("SimpleDateFormat")
    private fun getDateHoursStr(date: Date): String? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH")
        return formatter.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDateMinutesStr(date: Date): String? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return formatter.format(date)
    }


    @SuppressLint("SimpleDateFormat")
    private fun getDateSecondsStr(date: Date): String? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return formatter.format(date)
    }


    interface TimeSelectData {
        fun callback(data: String?)
    }


}