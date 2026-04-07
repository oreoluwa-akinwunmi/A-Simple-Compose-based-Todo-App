package com.example.asimplecompose_basedtodoapp.screens

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object AddTaskScreen: Screen("add_task_screen")
    object CompletedScreen: Screen("completed_screen")
}