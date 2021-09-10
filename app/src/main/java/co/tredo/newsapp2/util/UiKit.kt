package co.tredo.newsapp2.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import co.tredo.newsapp2.R
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Context.alertPayload(
    title: String? = null,
    message: String? = null,
    @StringRes positiveId: Int = R.string.app_ok,
    @StringRes negativeId: Int? = R.string.app_cancel,
) = suspendCoroutine<AlertAnswer> { continuation ->
    val builder = AlertDialog.Builder(this)
    title?.let(builder::setTitle)
    message?.let(builder::setMessage)
    negativeId?.let {
        builder.setNegativeButton(negativeId) { _, _ -> continuation.resume(AlertAnswer.Cancel) }
    }
    builder
        .setPositiveButton(positiveId) { _, _ -> continuation.resume(AlertAnswer.Ok) }
        .setOnCancelListener { continuation.resume(AlertAnswer.Cancel) }
        .show()
}

sealed class AlertAnswer {
    object Ok : AlertAnswer()
    object Cancel : AlertAnswer()
}