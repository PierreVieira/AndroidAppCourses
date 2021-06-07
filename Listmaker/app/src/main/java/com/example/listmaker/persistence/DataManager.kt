package com.example.listmaker.persistence

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.listmaker.frontend.lists.TaskList

class DataManager(activity: Activity) {

    private val sharedPreferences: SharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)

    fun saveList(taskList: TaskList) {
        with(sharedPreferences.edit()) {
            putStringSet(taskList.name, taskList.tasks.toSet())
            apply()
        }
    }

    fun readAllLists() : MutableList<TaskList> {
        val contents = sharedPreferences.all
        val taskLists = mutableListOf<TaskList>()
        contents.forEach {
            val listAttribute = mutableListOf<String>()
            listAttribute.addAll(it.value as Collection<String>)
            val list = TaskList(it.key, listAttribute)
            taskLists.add(list)
        }
        return taskLists.asReversed()
    }

}