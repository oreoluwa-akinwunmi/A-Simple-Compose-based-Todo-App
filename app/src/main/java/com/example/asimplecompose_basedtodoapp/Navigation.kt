package com.example.asimplecompose_basedtodoapp

import screens.AddTaskScreen
import screens.CompletedScreen
import screens.HomeScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    // By creating the ViewModel here, it will be shared across all screens in the NavHost
    val taskViewModel: TaskViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController, viewModel = taskViewModel)
        }
        composable(route = Screen.AddTaskScreen.route) {
            AddTaskScreen(navController = navController, viewModel = taskViewModel)
        }
        composable(route = Screen.CompletedScreen.route) {
            CompletedScreen(navController = navController, viewModel = taskViewModel)
        }
    }
}