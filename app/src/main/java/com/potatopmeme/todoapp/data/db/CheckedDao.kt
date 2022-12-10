package com.potatopmeme.todoapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.potatopmeme.todoapp.data.model.Checked
import com.potatopmeme.todoapp.data.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckedDao {
    @Insert
    suspend fun insertChecked(checked: Checked)

    @Delete
    suspend fun deleteChecked(checked: Checked)

    @Query("DELETE FROM checked WHERE todo_num = :num AND date = :date")
    suspend fun deleteCheckedWithNumAndDate(num: Int, date: String)

    @Query("SELECT * FROM checked")
    fun getCheckedAll(): Flow<List<Checked>>

    @Query("SELECT * FROM checked WHERE todo_num =:num")
    fun getCheckedWithNum(num: Int): List<Checked>

    @Query("SELECT * FROM checked WHERE date =:date")
    fun getCheckedWithDate(date: String): List<Checked>
}
