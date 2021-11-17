package com.tresfellas.queenbee.ui.register

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.ui.custom.CustomEditText
import kotlinx.android.synthetic.main.view_verify_code.view.*

class VerifyCodeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr), TextWatcher, View.OnFocusChangeListener, CustomEditText.KeyImeChange {

    private var whoHasFocus: Int = 0
    private var editTextList : List<CustomEditText> = listOf()

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_verify_code, this)

        editTextList = listOf(
            view.editText_verify_code1, view.editText_verify_code2, view.editText_verify_code3,
            view.editText_verify_code4, view.editText_verify_code5, view.editText_verify_code6
        )

        for(item in editTextList) {
            item.onFocusChangeListener = this
            item.addTextChangedListener(this)
            item.setKeyImeChangeListener(this)
        }
//        view.editText_verify_code6.onFocusChangeListener = this
//        view.editText_verify_code6.setKeyImeChangeListener(this)
//        view.editText_verify_code6.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(editable: Editable?) {}
//
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if(s.length==6){
//                    return
//                }
//            }
//        })



        editTextList[0].requestFocus()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // Prevent Override
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // Prevent Override
    }

    override fun afterTextChanged(p0: Editable?) {
        val endPosition =
            if(whoHasFocus >= 5) {
                5
            } else {
                whoHasFocus + 1
            }

        setBorderColorAndRequestFocus(editTextList[whoHasFocus], editTextList[endPosition])
    }

    private fun setBorderColorAndRequestFocus(checkingDigit: EditText, focusingDigit: EditText) {
        if (checkingDigit.text.toString().isNotEmpty()) {
            focusingDigit.requestFocus()
            checkingDigit.setBackgroundResource(R.drawable.edittext_border_background_rt)
        } else {
            checkingDigit.setBackgroundResource(R.drawable.edittext_border_background)
        }
    }

    override fun onFocusChange(v: View?, p1: Boolean) {
        whoHasFocus = editTextList.indexOf(v)
    }

    override fun onKeyIme(view: View, keyCode: Int, keyEvent: KeyEvent) {
        if(keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
            onKeyResponder(view)
        }
    }

    private fun onKeyResponder(v: View) {
        val position = editTextList.indexOf(v)

        if(editTextList[position].text.toString() != "") {
            if(position - 1 >= 0) {
                editTextList[position].text?.clear()
                editTextList[position - 1].requestFocus()
            }
        } else {
            if(position - 1 >= 0) {
                editTextList[position - 1].requestFocus()
                editTextList[position - 1].text?.clear()
            }
        }
    }

    fun getCode(): String {
        var result = ""

        for(item in editTextList) {
            result += item.text
        }

        return result
    }

    fun getLastEditText(): CustomEditText{
        return editTextList[5]
    }
}