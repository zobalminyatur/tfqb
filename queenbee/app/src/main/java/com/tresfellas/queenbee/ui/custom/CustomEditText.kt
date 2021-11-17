package com.tresfellas.queenbee.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class CustomEditText : AppCompatEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var mListener: KeyImeChange? = null

    fun setKeyImeChangeListener(listener: KeyImeChange) {
        this.mListener = listener
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        mListener?.onKeyIme(this, event.keyCode, event)

        return super.dispatchKeyEvent(event)
    }

    interface KeyImeChange {
        fun onKeyIme(view: View, keyCode: Int, keyEvent: KeyEvent)
    }
}