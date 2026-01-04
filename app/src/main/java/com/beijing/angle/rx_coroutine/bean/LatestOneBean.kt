package com.beijing.angle.rx_coroutine.bean

data class LatestOneBean(
    var msg: String? = null, // 查询成功
    var result: Result? = null,
    var code: String? = null // 200
)

data class Result(
    var createdTime: String? = null, // 2025-12-24 11:50:53
    var updatedTime: String? = null, // 2025-12-24 11:50:53
    var id: String? = null, // 87da989bc92641e79efa2e8a722de6e9
    var status: String? = null, // 1
    var versionCode: String? = null, // 6
    var versionName: String? = null, // v1.0.6
    var url: String? = null, // https://naxx-1311070258.cos.ap-beijing.myqcloud.com/app/b7edc3599945481fb51da46ba22ed207.apk
    var remark: String? = null, // 更新已知问题
    var type: String? = null, // HGZLSYTJ_DJY
    var forceUpdate: String? = null // 1
)