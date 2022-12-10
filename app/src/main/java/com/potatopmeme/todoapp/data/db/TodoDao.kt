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
    fun getTodoWithNum(num: Int): List<Todo>

    @Query("SELECT * FROM todo WHERE date LIKE :dateStr OR (repeatType = 1  AND sun AND ( (NOT duration) OR (:dateInt BETWEEN startDateInt AND endDateInt))) OR (repeatType =2  And dates Like :dateStr)")
    fun getTodoWithDataSun(
        dateStr: String,
        dateInt: Int,
    ): List<Todo>

    @Query("SELECT * FROM todo WHERE date LIKE :dateStr OR (repeatType = 1  AND mon AND ( (NOT duration) OR (:dateInt BETWEEN startDateInt AND endDateInt))) OR (repeatType =2  And dates Like :dateStr)")
    fun getTodoWithDataMon(
        dateStr: String,
        dateInt: Int,
    ): List<Todo>

    @Query("SELECT * FROM todo WHERE date LIKE :dateStr OR (repeatType = 1  AND tue AND ( (NOT duration) OR (:dateInt BETWEEN startDateInt AND endDateInt))) OR (repeatType =2  And dates Like :dateStr)")
    fun getTodoWithDataTue(
        dateStr: String,
        dateInt: Int,
    ): List<Todo>

    @Query("SELECT * FROM todo WHERE date LIKE :dateStr OR (repeatType = 1  AND wed AND ( (NOT duration) OR (:dateInt BETWEEN startDateInt AND endDateInt))) OR (repeatType =2  And dates Like :dateStr)")
    fun getTodoWithDataWed(
        dateStr: String,
        dateInt: Int,
    ): List<Todo>

    @Query("SELECT * FROM todo WHERE date LIKE :dateStr OR (repeatType = 1  AND thu AND ( (NOT duration) OR (:dateInt BETWEEN startDateInt AND endDateInt))) OR (repeatType =2  And dates Like :dateStr)")
    fun getTodoWithDataThu(
        dateStr: String,
        dateInt: Int,
    ): List<Todo>

    @Query("SELECT * FROM todo WHERE date LIKE :dateStr OR (repeatType = 1  AND fri AND ( (NOT duration) OR (:dateInt BETWEEN startDateInt AND endDateInt))) OR (repeatType =2  And dates Like :dateStr)")
    fun getTodoWithDataFri(
        dateStr: String,
        dateInt: Int,
    ): List<Todo>

    @Query("SELECT * FROM todo WHERE date LIKE :dateStr OR (repeatType = 1  AND sat AND ( (NOT duration) OR (:dateInt BETWEEN startDateInt AND endDateInt))) OR (repeatType =2  And dates Like :dateStr)")
    fun getTodoWithDataSat(
        dateStr: String,
        dateInt: Int,
    ): List<Todo>

}
