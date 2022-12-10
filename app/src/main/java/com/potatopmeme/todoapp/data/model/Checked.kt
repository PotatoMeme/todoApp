package com.potatopmeme.todoapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "checked",
        foreignKeys = arrayOf(
        ForeignKey(
            entity = Todo::class,
            parentColumns = arrayOf("num"),
            childColumns = arrayOf("todo_num"),
            onDelete = ForeignKey.CASCADE
        )
        )
)
data class Checked(
    @PrimaryKey(autoGenerate = true)
    val seq : Int = 0,

    val todo_num : Int,
    val date : String
)
