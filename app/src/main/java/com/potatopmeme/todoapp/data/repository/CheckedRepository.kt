package com.potatopmeme.todoapp.data.repository

import androidx.room.Query
import com.potatopmeme.todoapp.data.model.Checked
import com.potatopmeme.todoapp.data.model.Todo
import kotlinx.coroutines.flow.Flow

interface CheckedRepository {
    // Room
    suspend fun insertChecked(checked: Checked)

    suspend fun deleteChecked(checked: Checked)

    suspend fun deleteCheckedWithNum(num: Int)

    suspend fun deleteCheckedWithNumAndDate(num: Int, date: String)

    fun getCheckedAll(): Flow<List<Checked>>

    fun getCheckedWithNum(num:Int): List<Checked>

    fun getCheckedWithDate(date:String): List<Checked>

}