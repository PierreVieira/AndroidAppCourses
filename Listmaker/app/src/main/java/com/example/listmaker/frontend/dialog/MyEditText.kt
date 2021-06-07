package com.example.listmaker.frontend.dialog

import android.content.Context
import android.text.InputType

class MyEditText(context: Context) : androidx.appcompat.widget.AppCompatEditText(context) {

    init {
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
    }
}