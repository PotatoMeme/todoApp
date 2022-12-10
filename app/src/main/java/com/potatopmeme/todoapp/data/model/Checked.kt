package com.potatopmeme.todoapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "checked")
data class Checked(
    @PrimaryKey(autoGenerate = true)
    val seq : Int = 0,
    @ColumnInfo(name = "todo_num")
    val todo_num : Int,
    val date : String
)
