package com.example.customappcompatbutton

import android.app.ActionBar
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.webview.R

class CustomButton : AppCompatButton {

    constructor(context: Context?) : super(context!!)

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    )

    init {
        setBackgroundResource(R.drawable.button_shape)
        setTypeface(typeface, Typeface.BOLD)

        this.layoutParams = LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 100)
    }
}