package com.potatopmeme.todoapp.data.repository

import com.potatopmeme.todoapp.data.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    // Room
    suspend fun insertTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    suspend fun deleteBook(todo: Todo)

    fun getTodoAll(): Flow<List<Todo>>

    fun getTodoWithNum(num: Int): Todo
}