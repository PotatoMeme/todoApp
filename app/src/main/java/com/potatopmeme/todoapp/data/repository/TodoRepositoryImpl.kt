package com.potatopmeme.todoapp.data.repository

import com.potatopmeme.todoapp.data.db.TodoDataBase
import com.potatopmeme.todoapp.data.model.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val db: TodoDataBase
): TodoRepository {
    override suspend fun insertTodo(todo: Todo) {
        db.todoDao().insertTodo(todo)
    }

    override suspend fun updateTodo(todo: Todo) {
        db.todoDao().updateTodo(todo)
    }

    override suspend fun deleteBook(todo: Todo) {
        db.todoDao().deleteTodo(todo)
    }

    override fun getTodoAll(): Flow<List<Todo>> {
        return db.todoDao().getTodoAll()
    }

    override fun getTodoWithNum(num: Int): Todo {
        return db.todoDao().getTodoWithNum(num)
    }
}