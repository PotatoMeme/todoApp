package com.potatopmeme.todoapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.potatopmeme.todoapp.data.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert
    suspend fun insertTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("DELETE FROM todo WHERE num = :num")
    suspend fun deleteTodoWithNum(num: Int)

    @Query("SELECT * FROM todo")
    fun getTodoAll(): Flow<List<Todo>>


    @Query("SELECT * FROM todo WHERE num = :num")
    fun getTodoWithNum(num : Int): List<Todo>
}
