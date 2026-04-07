package com.example.asimplecompose_basedtodoapp.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Task(
    val id: Int,
    val title: String,
    var isCompleted: Boolean = false
)

class TaskViewModel : ViewModel() {
    // This holds the master list of tasks
    var tasks = mutableStateListOf<Task>()
        private set

    // Variables to track recently deleted task for the Undo feature
    private var recentlyDeletedTask: Task? = null
    private var recentlyDeletedIndex: Int? = null

    fun addTask(title: String) {
        tasks.add(Task(id = tasks.size, title = title))
    }

    fun toggleTask(task: Task) {
        val index = tasks.indexOf(task)
        if (index != -1) {
            tasks[index] = task.copy(isCompleted = !task.isCompleted)
        }
    }

    fun deleteTask(task: Task) {
        val index = tasks.indexOf(task)
        if (index != -1) {
            recentlyDeletedTask = task
            recentlyDeletedIndex = index
            tasks.removeAt(index)
        }
    }

    fun undoDelete() {
        val task = recentlyDeletedTask
        val index = recentlyDeletedIndex
        if (task != null && index != null) {
            tasks.add(index, task)
            recentlyDeletedTask = null
            recentlyDeletedIndex = null
        }
    }
}