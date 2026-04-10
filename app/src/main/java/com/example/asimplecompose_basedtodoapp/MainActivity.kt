package com.example.asimplecompose_basedtodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room

class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext, TaskDatabase::class.java, "tasks.db"
        ).build()
    }
    private val repository by lazy { TaskRepository(database.taskDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation(repository = repository)
        }
    }
}
