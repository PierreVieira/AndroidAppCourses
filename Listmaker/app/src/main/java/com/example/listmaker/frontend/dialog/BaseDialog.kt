package com.example.listmaker.frontend.dialog

import android.app.AlertDialog
import android.content.Context

abstract class BaseDialog(context: Context) : AlertDialog.Builder(context) {
    protected val editText = MyEditText(context)
}