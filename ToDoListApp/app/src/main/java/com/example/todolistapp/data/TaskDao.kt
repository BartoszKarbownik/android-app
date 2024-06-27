package com.example.todolistapp.data

import androidx.room.*
import com.example.todolistapp.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("UPDATE tasks SET title = :title, dueDate = :dueDate WHERE id = :id")
    suspend fun updateTask(id: Int, title: String, dueDate: Date?)
}