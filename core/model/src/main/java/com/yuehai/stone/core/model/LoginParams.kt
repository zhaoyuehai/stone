package com.yuehai.stone.core.model

data class LoginParams(
    val phoneNumber: String? = null,//手机号
    val phoneCode: String? = null,//手机验证码
    val username: String? = null,//用户名6~20位
    val password: String? = null,//密码8~12位
    val type: String = "2",//登录类型 1：用户名登录 2：App登录 3：地图标注平台登录
    val loginSource: String = "1"//登录来源 1：调度系统管理员（Web） 2：点检人员（APP） 3：地图标注人员
)