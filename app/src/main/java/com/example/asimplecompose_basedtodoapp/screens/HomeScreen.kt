package com.example.asimplecompose_basedtodoapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.asimplecompose_basedtodoapp.model.EmptyState
import com.example.asimplecompose_basedtodoapp.model.TaskViewModel
import com.example.asimplecompose_basedtodoapp.model.TodoItemRow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: TaskViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val tasks by viewModel.tasks.collectAsState(initial = emptyList())
    val incompleteTasks = tasks.filter { !it.isCompleted }

    val message = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.get<String>("message")

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it)

            // Clears it after showing
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<String>("message")
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "My Tasks")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate(Screen.AddTaskScreen.route) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Completed tasks button
            TextButton(
                onClick = { navController.navigate(Screen.CompletedScreen.route) }) {
                Text("View Completed Tasks")
            }
            if (incompleteTasks.isEmpty()) {
                EmptyState(isCompletedScreen = false)
            } else {
                // Task List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(incompleteTasks) { task ->
                        TodoItemRow(
                            task = task,
                            onDelete = {
                                viewModel.deleteTask(task)
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Task deleted",
                                        actionLabel = "Undo",
                                        duration = SnackbarDuration.Short
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.undoDelete()
                                    }
                                }
                            },
                            onToggle = { viewModel.toggleTask(task) }
                        )
                    }
                }
            }


        }

    }
}
