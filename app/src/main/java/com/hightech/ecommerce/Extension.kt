package com.hightech.ecommerce

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

fun logi(msg: String?) = Log.d("HIGHTECTH_LOG_INFO", msg)

fun Context.toast(msg: String?) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
fun Context.longToast(msg: String?) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
fun Fragment.toast(msg: String?) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
fun View.toast(msg: String?) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

fun ViewGroup.layoutInflater(@LayoutRes layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}

fun Context.getStatusBarHeight(): Int {
    var statusBarHeight = 0
    val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        statusBarHeight = resources.getDimensionPixelSize(resourceId)
    }
    return statusBarHeight
}