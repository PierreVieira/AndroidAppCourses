package com.example.listmaker.interfaces

import com.example.listmaker.frontend.lists.TaskList

interface TodoListClickListener {
    fun listItemClicked(list: TaskList)
}