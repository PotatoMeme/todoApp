package com.potatopmeme.todoapp.data.repository

import com.potatopmeme.todoapp.data.db.TodoDataBase
import com.potatopmeme.todoapp.data.model.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val db: TodoDataBase
) : TodoRepository {
    override suspend fun insertTodo(todo: Todo) {
        db.todoDao().insertTodo(todo)
    }

    override suspend fun updateTodo(todo: Todo) {
        db.todoDao().updateTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        db.todoDao().deleteTodo(todo)
    }

    override suspend fun deleteTodoWithNum(num: Int) {
        db.todoDao().deleteTodoWithNum(num)
    }

    override fun getTodoAll(): Flow<List<Todo>> {
        return db.todoDao().getTodoAll()
    }

    override fun getTodoWithNum(num: Int): List<Todo> {
        return db.todoDao().getTodoWithNum(num)
    }

    override fun getTodoWithDate(dateStr: String, dateInt: Int, weekType: Int): List<Todo> {
        return when (weekType) {
            1-> db.todoDao().getTodoWithDataSun("%$dateStr%", dateInt)
            2 -> db.todoDao().getTodoWithDataMon("%$dateStr%", dateInt)
            3 -> db.todoDao().getTodoWithDataTue("%$dateStr%", dateInt)
            4 -> db.todoDao().getTodoWithDataWed("%$dateStr%", dateInt)
            5 -> db.todoDao().getTodoWithDataThu("%$dateStr%", dateInt)
            6 -> db.todoDao().getTodoWithDataFri("%$dateStr%", dateInt)
            else -> db.todoDao().getTodoWithDataSat("%$dateStr%", dateInt)
        }
    }
}