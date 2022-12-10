package com.potatopmeme.todoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val num : Int = 0,
    val title : String="",
    val time : String="",

    //0~3
    val repeatType:Int,

    //0 -> none
    val date : String="",

    //1 -> Day of the week
    val sun: Boolean = false,
    val mon: Boolean = false,
    val tue: Boolean = false,
    val wed: Boolean = false,
    val thu: Boolean = false,
    val fri: Boolean = false,
    val sat: Boolean = false,

    val duration: Boolean = false,
    val startDate : String= "",
    val startDateInt : Int= 0,
    val endDate : String = "",
    val endDateInt : Int= 0,

    //2 -> Dates
    val dates : String = "",

    val memo : String=""
)