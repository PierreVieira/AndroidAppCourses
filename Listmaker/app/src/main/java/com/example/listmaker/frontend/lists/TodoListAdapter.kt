package com.example.listmaker.frontend.lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.R
import com.example.listmaker.interfaces.TodoListClickListener
import com.example.listmaker.extensions.uniquify

class TodoListAdapter(
    private val lists: MutableList<TaskList>,
    private val clickListener: TodoListClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = lists.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_list_view_holder, parent, false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as TodoListViewHolder
        holder.listPositionTextView.text = (position + 1).toString()
        holder.listTitleTextView.text = lists[position].name
        holder.itemView.setOnClickListener { clickListener.listItemClicked(lists[position]) }
    }

    fun addList(list: TaskList) {
        val titlesString = lists.map { it.name }
        list.name = list.name.uniquify(titlesString)
        lists.add(list)
        notifyItemInserted(lists.size - 1)
    }

    inner class TodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listPositionTextView: TextView = itemView.findViewById(R.id.itemNumber)
        val listTitleTextView: TextView = itemView.findViewById(R.id.task_title)
    }
}
