package com.oliverbotello.eha_remoteconfig.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat

fun showMessage(context: Context, message: String, long: Boolean = false, tag: String = GLOBAL_TAG) {
    showLog(message, tag)
    showToast(context, message, long)
}

fun showLog(message: String, tag:  String = GLOBAL_TAG) {
    Log.e(tag, message)
}

fun showToast(context: Context, message: String, long: Boolean = false) {
   Toast.makeText(context, message, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}