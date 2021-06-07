package com.example.listmaker.frontend.dialog

import android.content.Context
import com.example.listmaker.R
import com.example.listmaker.interfaces.CreateListener

class DialogCreateTask(context: Context, createTaskListener: CreateListener) : BaseDialog(context) {
    init {
        setTitle(context.resources.getString(R.string.dialog_task_title))
        setView(editText)
        setPositiveButton(R.string.positive_button_title) { dialog, _ ->
            var taskName = editText.text.toString()
            if (taskName.isBlank()) {
                taskName = "New Item Task"
            }
            createTaskListener.created(taskName)
            dialog.dismiss()
        }
    }
}