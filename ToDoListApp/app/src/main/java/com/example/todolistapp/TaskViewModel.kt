package com.example.todolistapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.data.TaskDao
import com.example.todolistapp.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        viewModelScope.launch {
            taskDao.getAllTasks().collect {
                _tasks.value = it
            }
        }
    }

    fun addTask(title: String, dueDate: Date?) {
        viewModelScope.launch {
            taskDao.insertTask(Task(title = title, dueDate = dueDate))
        }
    }

    //nie implementowane - klikanie rozwija zadanie, lepiej usunąć i dodać nowe
    fun updateTask(task: Task, newTitle: String, newDueDate: Date?) {
        viewModelScope.launch {
            taskDao.updateTask(task.id, newTitle, newDueDate)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }
}