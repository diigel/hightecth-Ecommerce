package com.hightech.ecommerce.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.iid.FirebaseInstanceId
import com.hightech.ecommerce.R
import java.util.*
import java.util.regex.Pattern
import kotlin.coroutines.coroutineContext

const val EMAIL_REGEX = "(?:[a-zA-Z0-9!#\$%&'*+\\/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#\$%&'*+\\/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
fun isEmailValid(email: String?) = Pattern.matches(EMAIL_REGEX, email)

fun logi(msg: String) = Log.d("HIGHTECTH_LOG_INFO", msg)

fun Context.toast(msg: String?) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
fun Context.longToast(msg: String?) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
fun Fragment.toast(msg: String?) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
fun View.toast(msg: String?) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

fun ViewGroup.layoutInflater(@LayoutRes layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}

fun ImageView.loadImage(
    url: String? = null,
    @DrawableRes drawableRes: Int? = null,
    resizeTo: Int? = null
) {
    if (resizeTo != null) {
        if (drawableRes != null) {
            Glide.with(context)
                .load(drawableRes)
                .apply(RequestOptions().override(resizeTo, resizeTo))
                .into(this)
        } else {
            Glide.with(context)
                .load(url)
                .apply(RequestOptions().override(resizeTo, resizeTo))
                .into(this)
        }
    } else {
        if (drawableRes != null) {
            Glide.with(context)
                .load(drawableRes)
                .into(this)
        } else {
            Glide.with(context)
                .load(url)
                .into(this)
        }
    }
}

fun changePrefix(text: Editable) {
    if (text.length >= 3) {
        val get3First = text.toString().substring(0, 3)
        if (get3First == "+62") {
            val change = SpannableStringBuilder(text.toString().replaceFirst(get3First, "0"))
            text.replace(0, text.length, change.trim())
        }
    }
    if (text.length >= 2) {
        val get2First = text.toString().substring(0, 2)
        if (get2First == "62") {
            val change = SpannableStringBuilder(text.toString().replaceFirst(get2First, "0"))
            text.replace(0, text.length, change.trim())
        }
    }
    if (text.isNotEmpty()) {
        val get1First = text.toString().substring(0, 1)
        if (get1First != "0") {
            val change = SpannableStringBuilder(text.toString().replaceFirst(get1First, "0"))
            text.replace(0, text.length, change.trim())
        }
    }
}

fun EditText.textWacther(using62 : Boolean = false, result: (String) -> Unit) {
    this.addTextChangedListener {
        doOnTextChanged { text, _, _, _ ->

            Handler(Looper.getMainLooper()).postDelayed({
                if (using62){
                    changePrefix(this.editableText)
                }
                if (text?.length ?: 0 > 2) {
                    val textResult = text.toString()
                    result.invoke(textResult)
                }
            }, 800)
        }
    }
}

fun Context.loaderDialog(): AlertDialog {
    val builderProgress = AlertDialog.Builder(this)
        .setView(LayoutInflater.from(this).inflate(R.layout.loader_layout, null))
        .setCancelable(false)

    val dialogProgress = builderProgress.create()
    dialogProgress.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    return dialogProgress
}

@SuppressLint("HardwareIds")
fun getDeviceUniqueId(context: Context): String =
    Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

fun getToken(token: (String) -> Unit) {
    FirebaseInstanceId.getInstance()
        .instanceId
        .addOnSuccessListener { instanceIdResult ->
            token(instanceIdResult.token)
        }
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

@SuppressLint("SetTextI18n")
fun Context.showDialogInfo(
    message: String? = null,
    buttonText: String? = "Tutup",
    image: String? = null,
    title : String? = null,
    dialogResult: (() -> Unit)? = null
) {
    val sheetView = LayoutInflater.from(this).inflate(R.layout.dialog_info, null)
    val dialog = MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
        customView(view = sheetView)
        cornerRadius(50f)
    }

    val textTitle = dialog.findViewById<TextView>(R.id.text_title)
    val text = dialog.findViewById<TextView>(R.id.text_info)
    text?.text = message
    textTitle?.text = title

    val imageInfo = dialog.findViewById<ImageView>(R.id.image_info)
    val btnCancel = dialog.findViewById<Button>(R.id.button_dismiss)

    when {
        image != null -> {
            imageInfo?.loadImage(image)
        }
        else -> {
            imageInfo?.loadImage(drawableRes = R.drawable.image_info)
            imageInfo?.layoutParams?.width = 120.dp
            imageInfo?.layoutParams?.height = 120.dp
        }
    }

    btnCancel?.text = buttonText
    btnCancel?.setOnClickListener {
        dialog.dismiss()
        dialogResult?.invoke()
    }

    if (!dialog.isShowing) {

        dialog.show()
    }
}

fun <T> MutableList<T>.removeDuplicatesItem(): MutableList<T> {
    val set = LinkedHashSet<T>()
    set.addAll(this)
    this.clear()
    this.addAll(set)
    return this
}