package com.example.listmaker.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listmaker.*
import com.example.listmaker.frontend.dialog.DialogToDoList
import com.example.listmaker.databinding.ActivityMainBinding
import com.example.listmaker.frontend.lists.TaskList
import com.example.listmaker.frontend.lists.TodoListAdapter
import com.example.listmaker.interfaces.CreateListener
import com.example.listmaker.interfaces.TodoListClickListener
import com.example.listmaker.persistence.DataManager

class MainActivity : BaseRecyclerActivitiy() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataManager: DataManager
    private lateinit var lists: MutableList<TaskList>

    companion object {
        const val ITENT_LIST_KEY = "intent_list_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        dataManager = DataManager(this)
        lists = dataManager.readAllLists()
        setupRecyclerView()
        setupFloatActionButton()
    }

    override fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.content.todoListRecyclerView.apply {
            this.layoutManager = layoutManager
            adapter = createAdapter()
            addItemDecoration(dividerItemDecoration)
        }
    }

    override fun setupFloatActionButton() {
        binding.addTodoButton.setOnClickListener { showTodoListDialog() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun createAdapter() = TodoListAdapter(lists, object : TodoListClickListener {
        override fun listItemClicked(list: TaskList) {
            showTaskListItems(list)
        }
    })

    private fun showTodoListDialog() {
        val myDialog = DialogToDoList(this, object : CreateListener {
            override fun created(argument: Any) {
                val list = argument as TaskList
                (binding.content.todoListRecyclerView.adapter as TodoListAdapter).addList(list)
                dataManager.saveList(list)
                showTaskListItems(list)
            }
        })
        myDialog.create().show()
    }

    private fun showTaskListItems(list: TaskList) {
        val taskListIntent = Intent(this, DetailActivity::class.java)
        taskListIntent.putExtra(ITENT_LIST_KEY, list)
        startActivity(taskListIntent)
    }
}