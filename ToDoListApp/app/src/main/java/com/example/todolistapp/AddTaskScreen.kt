package com.example.todolistapp

import android.app.DatePickerDialog
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

    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                dueDate = calendar.time
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            label = { Text("Tytuł zadania") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (dueDate != null) dateFormatter.format(dueDate!!) else "Wybierz datę dla zadania",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    datePickerDialog.show()
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
            Text("Zapisz zadanie")
        }
    }
}