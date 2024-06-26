package com.example.todolistapp

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolistapp.TaskViewModel
import com.example.todolistapp.model.Task
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*

@Composable
fun TaskListScreen(navController: NavController, viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addTask") }) {
                Icon(painter = painterResource(id = android.R.drawable.ic_input_add), contentDescription = "Add Task")
            }
        }
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            items(tasks) { task ->
                TaskItem(task = task, onDelete = { viewModel.deleteTask(task) })
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onDelete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.title,
                modifier = Modifier.weight(1f),
                maxLines = if (expanded) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = onDelete) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_delete),
                    contentDescription = "Delete"
                )
            }
        }
    }
}