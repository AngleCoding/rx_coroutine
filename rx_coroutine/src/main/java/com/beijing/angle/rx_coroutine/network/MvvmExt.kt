package com.beijing.angle.rx_coroutine.network

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.beijing.angle.rx_coroutine.base.BaseActivity
import com.beijing.angle.rx_coroutine.base.BaseApp
import com.beijing.angle.rx_coroutine.base.BaseFragment
import com.beijing.angle.rx_coroutine.base.BaseViewModel
import com.beijing.angle.rx_coroutine.base.EBaseViewStatus
import com.beijing.angle.rx_coroutine.ext.showToast
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import java.lang.reflect.ParameterizedType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * @author 刘红鹏
 * @description:
 * @date: 2025/12/22 15:11
 * @Link: https://github.com/AngleCoding
 */


/**
 * BaseViewModel开启协程扩展
 */
fun <T> BaseViewModel.launchVmRequest(
    request: suspend () -> BaseResponse<T>, viewState: VmLiveData<T>
) {
    viewModelScope.launch {
        runCatching {
            viewState.value = VmState.Loading
            request()
        }.onSuccess {
            viewState.paresVmResult(it)
        }.onFailure {
            viewState.paresVmException(it)
        }
    }
}


/**
 * 网络错误提示
 */
fun Throwable?.parseErrorString(): String {
    return when (this) {
        is SocketTimeoutException -> "连接超时,请稍后再试"
        is TimeoutException -> "连接超时,请稍后再试"
        is TimeoutCancellationException -> "连接超时,请稍后再试"
        is ConnectException -> "网络不给力，请稍候重试！"
        is UnknownHostException -> "网络不给力，请稍候重试！"
        is HttpStatusCodeException -> "Http状态码异常：${this.statusCode}"
        is JsonSyntaxException -> "数据解析失败,请检查数据是否正确"
        is ParseException -> "数据不正确"
        else -> "请求失败，请稍后再试"
    }
}


/**
 * 获取vm clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}


/**
 * 跟下面的方法类似,只是调用形式上有所区别
 * 这种vmResult要提前定义好
 * 下面vmResult: VmResult<T>.() -> Unit可以直接写在参数里面
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserver(owner: LifecycleOwner, vmResult: VmResult<T>) {
    observe(owner) {
        when (it) {
            is VmState.Loading -> {
                vmResult.onAppLoading()
            }

            is VmState.Success -> {
                it.data?.let {
                    vmResult.onAppSuccess(it)
                    vmResult.onAppComplete()
                } ?: BaseApp.instance.showToast("无数据")

            }

            is VmState.Error -> {
                vmResult.onAppError(it.error)
                vmResult.onAppComplete()
            }

            is VmState.TokenFailure -> BaseApp.instance.startLoginActivity()
        }
    }
}


/**
 * 重写所有回调方法
 * onAppLoading
 * onAppSuccess
 * onAppError
 * onAppComplete
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserver(owner: LifecycleOwner, vmResult: VmResult<T>.() -> Unit) {
    val result = VmResult<T>();result.vmResult();observe(owner) {
        when (it) {
            is VmState.Loading -> {
                result.onAppLoading()
            }

            is VmState.Success -> {
                it.data?.let {
                    result.onAppSuccess(it)
                    result.onAppComplete()
                } ?: BaseApp.instance.showToast("无数据")
            }

            is VmState.Error -> {
                result.onAppError(it.error)
                result.onAppComplete()
            }

            is VmState.TokenFailure -> BaseApp.instance.startLoginActivity()
        }
    }
}


//---------------------------------------Activity  start ------------------------------------
/**
 * 默认不带loading的网络请求
 * 第一个参数继承自BaseActivity
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverDefault(
    activity: BaseActivity<*>, tips: Boolean? = true, crossinline onSuccess: ((T) -> Unit)
) {
    observe(activity) {
        when (it) {
            is VmState.Loading -> {
            }

            is VmState.Success -> {
                it.data?.let {
                    onSuccess(it)
                } ?: activity.showToast("无数据")

            }

            is VmState.Error -> {
                if (null != tips && tips) activity.showToast(it.error.errorMsg)
            }

            VmState.TokenFailure -> BaseApp.instance.startLoginActivity()
        }
    }
}


/**
 * 默认不带loading的网络请求
 * 第一个参数继承自BaseActivity
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 * 第四个参数 不论成功还是失败都会回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverDefault(
    activity: BaseActivity<*>,
    tips: Boolean? = true,
    crossinline onSuccess: ((T) -> Unit),
    crossinline onComplete: (() -> Unit) = {}
) {
    observe(activity) {
        when (it) {
            is VmState.Loading -> {
            }

            is VmState.Success -> {
                it.data?.let {
                    onSuccess(it)
                    activity.showSuccessLayout()
                    onComplete()
                } ?: activity.showErrorLayout("无数据")

            }

            is VmState.Error -> {
                if (null != tips && tips) activity.showToast(it.error.errorMsg)
                onComplete()
            }

            is VmState.TokenFailure -> BaseApp.instance.startLoginActivity()
        }
    }
}


/**
 * 主网络请求 适用于页面必须请求网络后才显示的页面,页面的初始状态isShowLoading设置为true
 * 第一个参数继承自BaseActivity
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverMain(
    activity: BaseActivity<*>,
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(activity) {
        when (it) {
            is VmState.Loading -> {
                activity.setBaseViewStatus(EBaseViewStatus.LOADING)
                activity.showLoadingLayout()

            }

            is VmState.Success -> {
                it.data?.let {
                    onSuccess(it)
                    activity.showSuccessLayout()
                } ?: activity.showErrorLayout("无数据")
            }

            is VmState.Error -> {
                activity.setBaseViewStatus(EBaseViewStatus.ERROR)
                activity.showErrorLayout(it.error.errorMsg)
            }

            VmState.TokenFailure -> BaseApp.instance.startLoginActivity()
        }
    }
}


/**
 * 主网络请求 适用于页面必须请求网络后才显示的页面,页面的初始状态isShowLoading设置为true
 * 第一个参数继承自BaseActivity
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 * 第四个参数 不论成功还是失败都会回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverMain(
    activity: BaseActivity<*>,
    crossinline onSuccess: ((T) -> Unit),
    crossinline onComplete: (() -> Unit) = {}
) {
    observe(activity) {
        when (it) {
            is VmState.Loading -> {
                activity.setBaseViewStatus(EBaseViewStatus.LOADING)
                activity.showLoadingLayout()
            }

            is VmState.Success -> {
                it.data?.let {
                    onSuccess(it)
                    activity.showSuccessLayout()
                    onComplete()
                } ?: activity.showErrorLayout("无数据")

            }

            is VmState.Error -> {
                activity.setBaseViewStatus(EBaseViewStatus.ERROR)
                activity.showErrorLayout(it.error.errorMsg)
                onComplete()
            }

            VmState.TokenFailure -> BaseApp.instance.startLoginActivity()
        }
    }
}


//---------------------------------------Activity  end ------------------------------------


//---------------------------------------fragment  start ------------------------------------


/**
 * 带loading的网络请求
 * 第一个参数继承自BaseFragment
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverLoading(
    fragment: BaseFragment<*>,
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(fragment) {
        when (it) {
            is VmState.Loading -> {
                fragment.setBaseViewStatus(EBaseViewStatus.LOADING)
                fragment.showLoadingLayout()
            }

            is VmState.Success -> {
                it.data?.let {
                    onSuccess(it)
                } ?: fragment.showErrorLayout("无数据")

            }

            is VmState.Error -> {
                fragment.setBaseViewStatus(EBaseViewStatus.ERROR)
                fragment.showErrorLayout(it.error.errorMsg)
            }

            VmState.TokenFailure -> BaseApp.instance.startLoginActivity()
        }
    }
}


/**
 * 带loading的网络请求
 * 第一个参数继承自BaseFragment
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 * 第四个参数 不论成功还是失败都会回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverLoading(
    fragment: BaseFragment<*>,
    crossinline onSuccess: ((T) -> Unit),
    crossinline onComplete: (() -> Unit) = {}
) {
    observe(fragment) {
        when (it) {
            is VmState.Loading -> {
                fragment.setBaseViewStatus(EBaseViewStatus.LOADING)
                fragment.showLoadingLayout()
            }

            is VmState.Success -> {
                it.data?.let {
                    onSuccess(it)
                    fragment.showSuccessLayout()
                    onComplete()
                } ?: fragment.showErrorLayout("无数据")

            }

            is VmState.Error -> {
                fragment.setBaseViewStatus(EBaseViewStatus.ERROR)
                fragment.showErrorLayout(it.error.errorMsg)
                onComplete()
            }

            VmState.TokenFailure -> BaseApp.instance.startLoginActivity()
        }
    }
}


/**
 * 不带loading的网络请求
 * 第一个参数继承自BaseFragment
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 * 第四个参数 不论成功还是失败都会回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverDefault(
    fragment: BaseFragment<*>,
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(fragment) {
        when (it) {
            is VmState.Loading -> {
            }

            is VmState.Success -> {
                it.data?.let {
                    onSuccess(it)
                } ?: fragment.context?.showToast("无数据")

            }

            is VmState.Error -> {
                fragment.context?.showToast("无数据")
            }

            VmState.TokenFailure -> BaseApp.instance.startLoginActivity()
        }
    }
}


/**
 * 不带loading的网络请求
 * 第一个参数继承自BaseFragment
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 * 第四个参数 不论成功还是失败都会回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverDefault(
    fragment: BaseFragment<*>,
    crossinline onSuccess: ((T) -> Unit),
    crossinline onComplete: (() -> Unit) = {}
) {
    observe(fragment) {
        when (it) {
            is VmState.Loading -> {
            }

            is VmState.Success -> {
                it.data?.let {
                    onSuccess(it)
                    onComplete()
                } ?: fragment.context?.showToast("无数据")

            }

            is VmState.Error -> {
                fragment.context?.showToast(it.error.errorMsg)
                onComplete()
            }

            VmState.TokenFailure -> BaseApp.instance.startLoginActivity()
        }
    }
}


/**
 * 主网络请求 适用于页面必须请求网络后才显示的页面,页面的初始状态isShowLoading设置为true
 * 第一个参数继承自BaseFragment
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverMain(
    fragment: BaseFragment<*>,
    crossinline onSuccess: ((T) -> Unit)
) {
    observe(fragment) {
        when (it) {
            is VmState.Loading -> {
                fragment.setBaseViewStatus(EBaseViewStatus.LOADING)
                fragment.showLoadingLayout()

            }

            is VmState.Success -> {
                it.data?.let {
                    onSuccess(it)
                    fragment.showSuccessLayout()
                } ?: fragment.showErrorLayout("无数据")

            }

            is VmState.Error -> {
                fragment.setBaseViewStatus(EBaseViewStatus.ERROR)
                fragment.showErrorLayout(it.error.errorMsg)
            }

            VmState.TokenFailure -> BaseApp.instance.startLoginActivity()
        }
    }
}


/**
 * 主网络请求 适用于页面必须请求网络后才显示的页面,页面的初始状态isShowLoading设置为true
 * 第一个参数继承自BaseFragment
 * 第二个参数 是否toast提示错误
 * 第三个参数 成功回调
 * 第四个参数 不论成功还是失败都会回调
 */
@MainThread
inline fun <T> VmLiveData<T>.vmObserverMain(
    fragment: BaseFragment<*>,
    crossinline onSuccess: ((T) -> Unit),
    crossinline onComplete: (() -> Unit) = {}
) {
    observe(fragment) {
        when (it) {
            is VmState.Loading -> {
                fragment.setBaseViewStatus(EBaseViewStatus.LOADING)
                fragment.showLoadingLayout()
            }

            is VmState.Success -> {
                it.data?.let {
                    onSuccess(it)
                    fragment.showSuccessLayout()
                    onComplete()
                } ?: fragment.showErrorLayout("无数据")

            }

            is VmState.Error -> {
                fragment.setBaseViewStatus(EBaseViewStatus.ERROR)
                fragment.showErrorLayout(it.error.errorMsg)
                onComplete()
            }

            VmState.TokenFailure -> BaseApp.instance.startLoginActivity()
        }
    }
}

//---------------------------------------fragment  end ------------------------------------


