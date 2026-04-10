package com.example.asimplecompose_basedtodoapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.asimplecompose_basedtodoapp.model.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
