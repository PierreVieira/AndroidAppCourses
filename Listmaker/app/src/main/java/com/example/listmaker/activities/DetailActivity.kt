package com.example.listmaker.activities

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listmaker.databinding.ActivityDetailBinding
import com.example.listmaker.frontend.dialog.DialogCreateTask
import com.example.listmaker.frontend.lists.TaskList
import com.example.listmaker.frontend.lists.TaskListAdapter
import com.example.listmaker.interfaces.CreateListener

class DetailActivity : BaseRecyclerActivitiy() {

    private lateinit var list: TaskList
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = intent.getParcelableExtra(MainActivity.ITENT_LIST_KEY)!!
        title = list.name
        setupFloatActionButton()
        setupRecyclerView()
    }

    override fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration =  DividerItemDecoration(this, layoutManager.orientation)
        binding.taskListRecyclerView.apply {
            this.layoutManager = layoutManager
            adapter = createAdapter()
            addItemDecoration(dividerItemDecoration)
        }
    }

    override fun setupFloatActionButton() {
        binding.addTaskButton.setOnClickListener { showAddTaskDialog() }
    }

    private fun showAddTaskDialog() {
        val myDialog = DialogCreateTask(this, object : CreateListener {
            override fun created(argument: Any) {
                val task = argument as String
                (binding.taskListRecyclerView.adapter as TaskListAdapter).addTask(task)
            }
        })
        myDialog.create().show()
    }

    override fun createAdapter() = TaskListAdapter(list)
}