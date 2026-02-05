package com.beijing.angle.rx_coroutine.ext

import android.os.Handler
import androidx.annotation.IntRange
import androidx.annotation.MainThread
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ThreadUtils.Task
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

/**
 * @author 刘红鹏
 * @description:
 * @date: 2026/2/4 18:38
 * @Link: https://github.com/AngleCoding
 */


/**
 * 判断是否为主线程
 */
fun Any.isMainThread(): Boolean {
    return ThreadUtils.isMainThread()
}


/**
 * 获取主线程 Handler
 */
@MainThread
fun Any.getMainHandler(): Handler {
    return ThreadUtils.getMainHandler()
}


/**
 *  主线程
 *
 *    runOnUiThread(onExecute = {
 *
 *             })
 */
@MainThread
fun Any.runOnUiThread(onExecute: (() -> Unit) = {}) {
    ThreadUtils.runOnUiThread(onExecute)
}

/**
 *延时运行在主线程
 *
 *    binding.mBtClick.click {
 *             runOnUiThreadDelayed(onExecute = {
 *                 Log.e("runOnUiThreadDelayed", "initView: 主线程", )
 *             }, 1000)
 *         }
 *
 */
@MainThread
fun Any.runOnUiThreadDelayed(onExecute: (() -> Unit) = {}, delayMillis: Long) {
    ThreadUtils.runOnUiThreadDelayed(
        onExecute,
        delayMillis
    )
}


/**
 *获取固定线程池
 *
 * 固定线程池（FixedThreadPool）是一种常用的线程池类型，其核心特点是线程数量固定不变。
 * 它适用于需要控制并发执行线程数量的场景，例如网络请求、数据库操作或图片加载等。
 *
 * 特点
 * 1、线程数量固定‌：线程池在创建时就指定了线程数量，这些线程会一直存活，即使处于空闲状态。
 * 2、任务排队‌：当所有线程都在执行任务时，新的任务会被放入一个等待队列中，直到有空闲线程。
 * 3、资源可控‌：通过限制线程数量，可以有效控制资源消耗，避免因线程过多导致系统资源耗尽。
 * 4、适用于稳定负载‌：适合处理负载稳定、并发数可控的场景。
 *
 *
 */
/**
 *
 *     getFixedPool(1,onExecute = {
 *                 runOnUiThread {
 *                     showToast("sss")
 *                 }
 *             })
 */
fun Any.getFixedPool(
    @IntRange(from = 1) size: Int,
    onExecute: (() -> Unit) = {}
): ExecutorService {
    val executor = ThreadUtils.getFixedPool(size)
    executor.execute(onExecute)
    return executor
}


/**
 * 获取单线程池
 *
 * 单线程池（SingleThreadExecutor）是一种非常有用的线程池类型，
 * 它只使用一个工作线程来执行任务，保证所有任务按照指定顺序执行。
 *
 * 主要特点:
 *
 * 1、‌顺序执行‌：所有任务按照提交顺序串行执行，确保了任务执行的顺序性
 * 2、‌线程复用‌：只使用一个线程，避免了频繁创建和销毁线程的开销
 * 3、‌任务队列‌：使用无界队列来缓存等待执行的任务
 *
 * 单线程池特别适用于需要保证任务顺序执行的场景：
 *
 * 1、‌日志写入‌：确保日志按顺序写入文件
 * 2、‌数据库操作‌：避免并发访问数据库导致的问题
 * 3、‌配置文件更新‌：保证配置更新的顺序性
 * 4、‌文件处理‌：按顺序处理文件操作
 *
 *
 *
 *    getSinglePool(onExecute = {
 *                 runOnUiThread(onExecute = {
 *                     showToast("!11")
 *                 })
 *             })
 *
 *
 *       getSinglePool().shutdown()
 *
 *
 */
fun Any.getSinglePool(
    onExecute: (() -> Unit) = {}
): ExecutorService {
    val executor = ThreadUtils.getSinglePool()
    executor.execute(onExecute)
    return executor
}


/**
 *
 *    getSinglePool(1,onExecute = {
 *                 runOnUiThread(onExecute = {
 *                     showToast("!11")
 *                 })
 *             })
 *
 */
fun Any.getSinglePool(
    @IntRange(from = 1, to = 10) priority: Int,
    onExecute: (() -> Unit) = {}
): ExecutorService {
    val executor = ThreadUtils.getSinglePool(priority)
    executor.execute(onExecute)
    return executor
}


/**
 *  获取缓冲线程池
 *  缓冲线程池在 Android 开发中特别适用于需要平衡任务处理速度和系统资源消耗的场景，
 *  通过合理的参数配置可以有效提升应用性能和用户体验。
 *
 * 优点
 * ‌任务缓冲机制‌：通过使用有界阻塞队列作为缓冲区，可以有效管理任务的提交和执行，避免任务直接堆积在内存中。
 * ‌资源控制‌：能够控制线程池的最大并发数，避免大量线程抢占系统资源导致的阻塞现象。
 * ‌性能优化‌：通过重用线程池中的线程，避免了频繁创建和销毁线程带来的性能开销。
 * ‌可管理性‌：提供了对线程的简单管理，并支持定时执行和间隔循环执行等功能。
 *
 * 缺点
 * ‌队列限制‌：使用有界队列时，当队列已满且所有线程都在忙时，新提交的任务可能会被拒绝。
 * ‌任务等待‌：当任务数量超过线程池处理能力时，超出的任务会被放入队列等待执行，可能导致响应延迟。
 * ‌配置复杂性‌：需要合理配置线程池参数，包括核心线程数、最大线程数、队列大小等，否则可能影响性能。
 *
 * 使用场景
 *
 * ‌快速响应用户请求‌：在需要快速响应用户请求的业务场景中，可以使用缓冲线程池来并行处理多个任务，缩短总体响应时间。
 * ‌批量任务处理‌：对于需要处理大量任务的场景，如报表统计、数据聚合等，可以通过缓冲线程池来提高吞吐量。
 * ‌控制资源消耗‌：当需要控制并发线程数量，避免因线程过多导致系统资源耗尽时，缓冲线程池是一个理想选择。
 * ‌任务队列管理‌：当需要对任务进行缓冲处理，确保任务有序执行时，可以使用缓冲线程池。
 *
 *
 *      getCachedPool(onExecute = {
 *                   runOnUiThread(onExecute = {
 *                       showToast("!11")
 *                   })
 *               })
 */

fun Any.getCachedPool(
    onExecute: (() -> Unit) = {}
): ExecutorService {
    val executor = ThreadUtils.getCachedPool()
    executor.execute(onExecute)
    return executor
}


fun Any.getCachedPool(
    @IntRange(from = 1, to = 10) priority: Int,
    onExecute: (() -> Unit) = {}
): ExecutorService {
    val executor = ThreadUtils.getCachedPool(priority)
    executor.execute(onExecute)
    return executor
}


/**
 * 获取 IO 线程池
 *
 * 优点
 * ‌资源复用‌：通过重用线程池中的线程，避免了频繁创建和销毁线程带来的性能开销。
 * ‌资源控制‌：能够有效控制线程池的最大并发数，避免大量线程抢占系统资源导致的阻塞现象。
 * ‌任务管理‌：提供对线程的简单管理，支持定时执行以及指定时间间隔循环执行等功能。
 * ‌性能优化‌：避免了每次创建新线程的开销，提高了系统资源的利用率。
 *
 * 缺点
 * ‌配置复杂性‌：需要根据任务类型（CPU密集型、IO密集型）和系统资源合理配置参数（corePoolSize、maxPoolSize、workQueue）。
 * ‌潜在的OOM风险‌：默认创建的无界队列线程池存在内存溢出（OOM）的风险。
 * ‌任务拒绝‌：在极端情况下，如果线程池配置不当，可能导致任务被拒绝。
 *
 * 使用场景
 * ‌网络请求处理‌：Android 中网络请求属于典型的 IO 密集型任务，使用 IO 线程池可以有效管理并发请求，避免因线程过多导致系统资源耗尽。
 * ‌文件读写操作‌：文件的读写操作通常涉及大量 IO 操作，使用线程池可以提高文件操作的效率。
 * ‌数据库操作‌：数据库的读写操作也属于 IO 密集型任务，通过线程池可以更好地控制并发访问。
 * ‌图片加载‌：图片的下载和解码是典型的 IO 操作，使用线程池可以避免阻塞主线程。
 * ‌定时任务执行‌：需要定时或周期性执行的任务，可以利用 IO 线程池的定时执行功能。
 *
 *    getIoPool(onExecute = {
 *
 *             })
 *
 */
fun Any.getIoPool(
    onExecute: (() -> Unit) = {}
): ExecutorService {
    val executor = ThreadUtils.getIoPool()
    executor.execute(onExecute)
    return executor
}


fun Any.getIoPool(
    @IntRange(from = 1, to = 10) priority: Int,
    onExecute: (() -> Unit) = {}
): ExecutorService {
    val executor = ThreadUtils.getIoPool(priority)
    executor.execute(onExecute)
    return executor
}


/**
 * 获取 CPU 线程池
 *
 * 优点
 *
 * ‌资源复用‌：通过重用线程池中的线程，避免了频繁创建和销毁线程带来的性能开销。
 * ‌资源控制‌：能够有效控制线程池的最大并发数，避免大量线程之间因互相抢占系统资源而导致的阻塞现象。
 * ‌性能优化‌：通过重复利用已创建的线程，降低了线程创建和销毁造成的消耗。
 * ‌提高响应速度‌：当任务到达时，任务可以不需要等到线程创建就能立即执行。
 * ‌提高线程可管理性‌：线程是稀缺资源，如果无限制地创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配、调优和监控。
 *
 * 缺点
 *
 * ‌配置复杂性‌：需要合理配置线程池参数，包括核心线程数、最大线程数、队列大小等，否则可能影响性能。
 * ‌潜在的OOM风险‌：默认创建的无界队列线程池存在内存溢出（OOM）的风险。
 * ‌任务等待‌：当任务数量超过线程池处理能力时，超出的任务会被放入队列等待执行，可能导致响应延迟。
 *
 * 使用场景
 *
 * PU密集型任务处理‌：适用于需要大量计算的任务，如数据处理、算法计算等，通过合理配置线程池大小，可以充分利用CPU核心数。
 * ‌批量任务处理‌：对于需要处理大量计算任务的场景，如报表统计、数据聚合等，可以通过CPU线程池来提高吞吐量。
 * ‌控制资源消耗‌：当需要控制并发线程数量，避免因线程过多导致系统资源耗尽时，CPU线程池是一个理想选择。
 * ‌任务队列管理‌：当需要对任务进行缓冲处理，确保任务有序执行时，可以使用CPU线程池。
 *
 *
 *    getCpuPool(onExecute = {
 *                 runOnUiThread(onExecute = {
 *                     showToast("111111")
 *                 })
 *             })
 */
fun Any.getCpuPool(
    onExecute: (() -> Unit) = {}
): ExecutorService {
    val executor = ThreadUtils.getCpuPool()
    executor.execute(onExecute)
    return executor
}


fun Any.getCpuPool(
    @IntRange(from = 1, to = 10) priority: Int,
    onExecute: (() -> Unit) = {}
): ExecutorService {
    val executor = ThreadUtils.getCpuPool(priority)
    executor.execute(onExecute)
    return executor
}


/**
 * 在固定线程池执行任务
 *
 *
 *    executeByFixed(1, object : Task<Boolean>() {
 *                 override fun doInBackground(): Boolean? {
 *                     return false
 *                 }
 *
 *                 override fun onSuccess(result: Boolean?) {
 *                 }
 *
 *                 override fun onCancel() {
 *
 *                 }
 *
 *                 override fun onFail(t: Throwable?) {
 *                 }
 *
 *             })
 *
 *
 */
fun <T> Any.executeByFixed(
    @IntRange(from = 1, to = 10) size: Int,
    task: Task<T>,
) {
    ThreadUtils.executeByFixed<T>(size, task)
}


/**
 * 在固定线程池延时执行任务
 *
 *    executeByFixedWithDelay(1, object : Task<Boolean>() {
 *                 override fun doInBackground(): Boolean? {
 *                     return false
 *                 }
 *
 *                 override fun onSuccess(result: Boolean) {
 *                     if (result) {
 *                         showToast("下载成功")
 *                     } else {
 *                         showToast("下载失败")
 *                     }
 *                 }
 *
 *                 override fun onCancel() {
 *
 *                 }
 *
 *                 override fun onFail(t: Throwable?) {
 *                 }
 *
 *             }, 3000, TimeUnit.MILLISECONDS)
 *
 */
fun <T> Any.executeByFixedWithDelay(
    @IntRange(from = 1, to = 10) size: Int,
    task: Task<T>,
    delay: Long,
    unit: TimeUnit
) {
    ThreadUtils.executeByFixedWithDelay<T>(size, task, delay, unit)
}


/**
 * 在固定线程池按固定频率执行任务
 *
 *    executeByFixedAtFixRate(1, object : Task<Boolean>() {
 *                 override fun doInBackground(): Boolean? {
 *                     return false
 *                 }
 *
 *                 override fun onSuccess(result: Boolean) {
 *                     if (result) {
 *                         showToast("下载成功")
 *                     } else {
 *                         showToast("下载失败")
 *                     }
 *                 }
 *
 *                 override fun onCancel() {
 *
 *                 }
 *
 *                 override fun onFail(t: Throwable?) {
 *                 }
 *
 *             }, 5000, TimeUnit.MILLISECONDS)
 *
 */
fun <T> Any.executeByFixedAtFixRate(
    @IntRange(from = 1, to = 10) size: Int,
    task: Task<T>,
    delay: Long,
    unit: TimeUnit
) {
    ThreadUtils.executeByFixedAtFixRate<T>(size, task, delay, unit)
}


/**
 * 在单线程池延时执行任务
 *
 *
 *    executeBySingleWithDelay(object : Task<Boolean>() {
 *                 override fun doInBackground(): Boolean? {
 *                     return false
 *                 }
 *
 *                 override fun onSuccess(result: Boolean) {
 *                     if (result) {
 *                         showToast("下载成功")
 *                     } else {
 *                         showToast("下载失败")
 *                     }
 *                 }
 *
 *                 override fun onCancel() {
 *
 *                 }
 *
 *                 override fun onFail(t: Throwable?) {
 *                 }
 *
 *             }, 1000, TimeUnit.MILLISECONDS, 1)
 *
 */
fun <T> Any.executeBySingleWithDelay(
    task: Task<T>,
    delay: Long,
    unit: TimeUnit,
    @IntRange(from = 1, to = 10) priority: Int
) {
    ThreadUtils.executeBySingleWithDelay<T>(task, delay, unit, priority)
}


/**
 * 在单线程池按固定频率执行任务
 *
 *    executeBySingleAtFixRate(object : Task<Boolean>() {
 *                 override fun doInBackground(): Boolean? {
 *                     return false
 *                 }
 *
 *                 override fun onSuccess(result: Boolean) {
 *                     if (result) {
 *                         showToast("下载成功")
 *                     } else {
 *                         showToast("下载失败")
 *                     }
 *                 }
 *
 *                 override fun onCancel() {
 *
 *                 }
 *
 *                 override fun onFail(t: Throwable?) {
 *                 }
 *
 *             }, 2000, TimeUnit.MILLISECONDS, 1)
 */
fun <T> Any.executeBySingleAtFixRate(
    task: Task<T>,
    delay: Long,
    unit: TimeUnit,
    @IntRange(from = 1, to = 10) priority: Int
) {
    ThreadUtils.executeBySingleAtFixRate<T>(task, delay, unit, priority)
}


/**
 *  在缓冲线程池执行任务
 *
 *    executeByCached(object : Task<Boolean>() {
 *                 override fun doInBackground(): Boolean? {
 *                     return false
 *                 }
 *
 *                 override fun onSuccess(result: Boolean) {
 *                     if (result) {
 *                         showToast("下载成功")
 *                     } else {
 *                         showToast("下载失败")
 *                     }
 *                 }
 *
 *                 override fun onCancel() {
 *
 *                 }
 *
 *                 override fun onFail(t: Throwable?) {
 *                 }
 *
 *             },  1)
 *
 */

fun <T> Any.executeByCached(
    task: Task<T>,
    @IntRange(from = 1, to = 10) priority: Int
) {
    ThreadUtils.executeByCached<T>(task, priority)
}


/**
 * 在缓冲线程池延时执行任务
 *
 *    executeByCachedWithDelay(object : Task<Boolean>() {
 *                 override fun doInBackground(): Boolean? {
 *                     return false
 *                 }
 *
 *                 override fun onSuccess(result: Boolean) {
 *                     if (result) {
 *                         showToast("下载成功")
 *                     } else {
 *                         showToast("下载失败")
 *                     }
 *                 }
 *
 *                 override fun onCancel() {
 *
 *                 }
 *
 *                 override fun onFail(t: Throwable?) {
 *                 }
 *
 *             }, 2000, TimeUnit.MILLISECONDS, 1)
 *
 *
 */
fun <T> Any.executeByCachedWithDelay(
    task: Task<T>,
    delay: Long,
    unit: TimeUnit,
    @IntRange(from = 1, to = 10) priority: Int
) {
    ThreadUtils.executeByCachedWithDelay<T>(task, delay, unit, priority)
}


/**
 * 在缓冲线程池按固定频率执行任务
 *
 *
 *    executeByCachedAtFixRate(object : Task<Boolean>() {
 *                 override fun doInBackground(): Boolean? {
 *                     return false
 *                 }
 *
 *                 override fun onSuccess(result: Boolean) {
 *                     if (result) {
 *                         showToast("下载成功")
 *                     } else {
 *                         showToast("下载失败")
 *                     }
 *                 }
 *
 *                 override fun onCancel() {
 *
 *                 }
 *
 *                 override fun onFail(t: Throwable?) {
 *                 }
 *
 *             }, 2000, TimeUnit.MILLISECONDS, 1)
 */
fun <T> Any.executeByCachedAtFixRate(
    task: Task<T>,
    delay: Long,
    unit: TimeUnit,
    @IntRange(from = 1, to = 10) priority: Int
) {
    ThreadUtils.executeByCachedAtFixRate<T>(task, delay, unit, priority)
}


/**
 *在 IO 线程池执行任务
 *
 *     executeByIo(object : Task<Boolean>() {
 *                 override fun doInBackground(): Boolean? {
 *                     return false
 *                 }
 *
 *                 override fun onSuccess(result: Boolean) {
 *                     if (result) {
 *                         showToast("下载成功")
 *                     } else {
 *                         showToast("下载失败")
 *                     }
 *                 }
 *
 *                 override fun onCancel() {
 *
 *                 }
 *
 *                 override fun onFail(t: Throwable?) {
 *                 }
 *
 *             }, 1)
 */
fun <T> Any.executeByIo(
    task: Task<T>,
    @IntRange(from = 1, to = 10) priority: Int
) {
    ThreadUtils.executeByIo<T>(task, priority)
}


/**
 * 在 IO 线程池延时执行任务
 *
 *    executeByIoWithDelay(object : Task<Boolean>() {
 *                 override fun doInBackground(): Boolean? {
 *                     return false
 *                 }
 *
 *                 override fun onSuccess(result: Boolean) {
 *                     if (result) {
 *                         showToast("下载成功")
 *                     } else {
 *                         showToast("下载失败")
 *                     }
 *                 }
 *
 *                 override fun onCancel() {
 *
 *                 }
 *
 *                 override fun onFail(t: Throwable?) {
 *                 }
 *
 *             }, 2000, TimeUnit.MILLISECONDS, 1)
 */
fun <T> Any.executeByIoWithDelay(
    task: Task<T>,
    delay: Long,
    unit: TimeUnit,
    @IntRange(from = 1, to = 10) priority: Int
) {
    ThreadUtils.executeByIoWithDelay<T>(task, delay, unit, priority)
}


/**
 * 在 IO 线程池按固定频率执行任务
 *
 *    executeByIoAtFixRate(object : Task<Boolean>() {
 *                 override fun doInBackground(): Boolean? {
 *                     return false
 *                 }
 *
 *                 override fun onSuccess(result: Boolean) {
 *                     if (result) {
 *                         showToast("下载成功")
 *                     } else {
 *                         showToast("下载失败")
 *                     }
 *                 }
 *
 *                 override fun onCancel() {
 *
 *                 }
 *
 *                 override fun onFail(t: Throwable?) {
 *                 }
 *
 *             }, 2000, TimeUnit.MILLISECONDS, 1)
 */
fun <T> Any.executeByIoAtFixRate(
    task: Task<T>,
    delay: Long,
    unit: TimeUnit,
    @IntRange(from = 1, to = 10) priority: Int
) {
    ThreadUtils.executeByIoAtFixRate<T>(task, delay, unit, priority)
}


/**
 * 在 CPU 线程池执行任务
 *
 *       executeByCpu(object : Task<Boolean>() {
 *                 override fun doInBackground(): Boolean? {
 *                     return false
 *                 }
 *
 *                 override fun onSuccess(result: Boolean) {
 *                     if (result) {
 *                         showToast("下载成功")
 *                     } else {
 *                         showToast("下载失败")
 *                     }
 *                 }
 *
 *                 override fun onCancel() {
 *
 *                 }
 *
 *                 override fun onFail(t: Throwable?) {
 *                 }
 *
 *             }, 1)
 *
 */
fun <T> Any.executeByCpu(
    task: Task<T>,
    @IntRange(from = 1, to = 10) priority: Int
) {
    ThreadUtils.executeByCpu<T>(task, priority)
}


/**
 * 在 CPU 线程池延时执行任务
 *
 *    executeByCpuWithDelay(object : Task<Boolean>() {
 *                 override fun doInBackground(): Boolean? {
 *                     return false
 *                 }
 *
 *                 override fun onSuccess(result: Boolean) {
 *                     if (result) {
 *                         showToast("下载成功")
 *                     } else {
 *                         showToast("下载失败")
 *                     }
 *                 }
 *
 *                 override fun onCancel() {
 *
 *                 }
 *
 *                 override fun onFail(t: Throwable?) {
 *                 }
 *
 *             }, 2000, TimeUnit.MILLISECONDS, 1)
 *
 */

fun <T> Any.executeByCpuWithDelay(
    task: Task<T>,
    delay: Long,
    unit: TimeUnit,
    @IntRange(from = 1, to = 10) priority: Int
) {
    ThreadUtils.executeByCpuWithDelay<T>(task, delay, unit, priority)
}


/**
 * 在 CPU 线程池按固定频率执行任务
 *
 *
 *    object task: Task<Boolean>() {
 *         override fun doInBackground(): Boolean? {
 *             return false
 *         }
 *
 *         override fun onSuccess(result: Boolean) {
 *             if (result) {
 *                 ToastUtils.showShort("下载成功")
 *             } else {
 *                 ToastUtils.showShort("下载失败")
 *             }
 *         }
 *
 *         override fun onCancel() {
 *
 *         }
 *
 *         override fun onFail(t: Throwable?) {
 *         }
 *
 *     }
 *
 *
 *
 *        executeByCpuAtFixRate(task, 5000, TimeUnit.MILLISECONDS, 1)
 *
 *
 *        cancel(task)
 *
 */
fun <T> Any.executeByCpuAtFixRate(
    task: Task<T>,
    delay: Long,
    unit: TimeUnit,
    @IntRange(from = 1, to = 10) priority: Int
) {
    ThreadUtils.executeByCpuAtFixRate<T>(task, delay, unit, priority)
}



fun  <T> Any.cancel( task : Task<T>) {
    ThreadUtils.cancel(task)
}
