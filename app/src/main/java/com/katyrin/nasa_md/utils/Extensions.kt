package com.katyrin.nasa_md.utils

import android.animation.Animator
import android.app.Activity
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.katyrin.nasa_md.R
import java.text.SimpleDateFormat
import java.util.*

private const val ROTATION_ANIMATED_AMOUNT = 500f
private const val ROTATION_DURATION = 1500L
private const val DATE_FORMAT = "yyyy-MM-dd"

fun View.startRotateImage(onAnimationEnd: () -> Unit) {
    animate()
        .rotationBy(ROTATION_ANIMATED_AMOUNT)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .setDuration(ROTATION_DURATION)
        .setListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                onAnimationEnd()
            }

            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })
}

fun ViewGroup.beginDelayedTransition() {
    TransitionManager.beginDelayedTransition(
        this,
        TransitionSet()
            .addTransition(ChangeBounds())
            .addTransition(ChangeImageTransform())
    )
}

fun Fragment.toast(string: String?) {
    Toast.makeText(context, string, Toast.LENGTH_LONG).apply {
        show()
    }
}

fun Int.previousDay(): String {
    val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, -this)
    return formatter.format(cal.time)
}

@Suppress("IMPLICIT_CAST_TO_ANY")
fun ImageView.setImageFromUri(uri: String?, placeholder: Int = R.drawable.ic_no_photo_vector) {
    val glideUrl = if (uri.isNullOrEmpty()) placeholder else GlideUrl(uri)

    Glide.with(context)
        .load(glideUrl)
        .placeholder(placeholder)
        .error(R.drawable.ic_no_photo_vector)
        .into(this)
}

@RequiresApi(Build.VERSION_CODES.M)
fun Activity.getResIdFromAttribute(): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(android.R.attr.colorAccent, typedValue, true)
    val resourceId = typedValue.resourceId
    return if (resourceId != 0) getColor(resourceId)
    else getColor(typedValue.data)
}

fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}