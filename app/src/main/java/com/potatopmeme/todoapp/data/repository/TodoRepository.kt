package com.potatopmeme.todoapp.data.repository

import com.potatopmeme.todoapp.data.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    // Room
    suspend fun insertTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun deleteTodoWithNum(num: Int)

    fun getTodoAll(): Flow<List<Todo>>

    fun getTodoWithNum(num: Int): List<Todo>
}