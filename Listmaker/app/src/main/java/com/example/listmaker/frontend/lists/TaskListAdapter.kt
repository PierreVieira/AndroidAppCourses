package com.example.listmaker.frontend.lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.R
import com.example.listmaker.extensions.uniquify

class TaskListAdapter(
    private val taskList: TaskList
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = taskList.tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_list_view_holder, parent, false)
        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as TaskListViewHolder
        holder.taskTextView.text = taskList.tasks[position]
        holder.itemView.setOnClickListener { }
    }

    fun addTask(task: String) {
        val taskToAdd = task.uniquify(taskList.tasks)
        taskList.tasks.add(taskToAdd)
        notifyItemInserted(taskList.tasks.size - 1)
    }

    inner class TaskListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTextView: TextView = itemView.findViewById(R.id.task_text_view)
    }
}
