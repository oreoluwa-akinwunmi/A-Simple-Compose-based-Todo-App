package com.example.asimplecompose_basedtodoapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.asimplecompose_basedtodoapp.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title: String,
    var isCompleted: Boolean = false
)

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    // Observe tasks as a Flow from the DB
    val tasks: Flow<List<Task>> = repository.allTasks

    // Variables to track recently deleted task for the Undo feature
    private var recentlyDeletedTask: Task? = null

    fun addTask(title: String) {
        viewModelScope.launch {
            repository.addTask(Task(title = title))
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isCompleted = !task.isCompleted))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            recentlyDeletedTask = task
        }
    }

    fun undoDelete() {
        viewModelScope.launch {
            recentlyDeletedTask?.let {
                repository.addTask(it)
                recentlyDeletedTask = null
            }
        }
    }

    // Factory needed because ViewModel now has a constructor parameter
    companion object {
        fun factory(repository: TaskRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    TaskViewModel(repository) as T
            }
    }
}
