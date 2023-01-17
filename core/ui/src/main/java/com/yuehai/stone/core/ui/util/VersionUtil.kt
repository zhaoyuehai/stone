package com.yuehai.stone.core.ui.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

@Suppress("DEPRECATION")
fun Context.getVersionCode(): Long {
    val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getPackageInfo(
            packageName, PackageManager.PackageInfoFlags.of(0)
        )
    } else {
        packageManager.getPackageInfo(packageName, 0)
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) packageInfo.longVersionCode else packageInfo.versionCode.toLong()
}

fun Context.getVersionName(): String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    packageManager.getPackageInfo(
        packageName, PackageManager.PackageInfoFlags.of(0)
    ).versionName
} else {
    @Suppress("DEPRECATION") packageManager.getPackageInfo(packageName, 0).versionName
}