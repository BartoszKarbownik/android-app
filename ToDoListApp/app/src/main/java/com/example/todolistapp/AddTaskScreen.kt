package com.example.todolistapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import androidx.navigation.NavController
import com.example.todolistapp.model.Task
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddTaskScreen(navController: NavController, viewModel: TaskViewModel) {
    var taskTitle by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf<Date?>(null) }
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            label = { Text("Task Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (dueDate != null) SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dueDate!!) else "Select Due Date",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    // Here we would show a date picker. For simplicity, we'll just set a random date.
                    dueDate = Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * Random().nextInt(30)))
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (taskTitle.isNotEmpty()) {
                    viewModel.addTask(taskTitle, dueDate)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Task")
        }
    }
}