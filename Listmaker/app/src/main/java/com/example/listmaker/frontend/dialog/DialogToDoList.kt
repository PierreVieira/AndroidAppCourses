package com.example.listmaker.frontend.dialog

import android.content.Context
import com.example.listmaker.R
import com.example.listmaker.frontend.lists.TaskList
import com.example.listmaker.interfaces.CreateListener

class DialogToDoList(context: Context, createListListener: CreateListener) : BaseDialog(context) {

    init {
        setTitle(context.resources.getString(R.string.dialog_todo_title))
        setView(editText)
        setPositiveButton(R.string.positive_button_title) { dialog, _ ->
            var listName = editText.text.toString()
            if (listName.isBlank()) {
                listName = "New Task"
            }
            val list = TaskList(listName)
            createListListener.created(list)
            dialog.dismiss()
        }
    }
}