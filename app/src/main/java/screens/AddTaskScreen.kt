package screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.asimplecompose_basedtodoapp.TaskViewModel

@Composable
fun AddTaskScreen(
    navController: NavController,
    viewModel: TaskViewModel
) {
    var taskEntered by remember { mutableStateOf("") }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TextField(
                value = taskEntered,
                onValueChange = { taskEntered = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter task") }
            )

            Spacer(modifier = Modifier.size(12.dp))

            Button(
                onClick = {
                    viewModel.addTask(taskEntered)
                    taskEntered = ""
                    navController.popBackStack()
                }
            ) {
                Text("Add Task")
            }
        }
    }
}