package com.potatopmeme.todoapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.potatopmeme.todoapp.data.model.Checked
import com.potatopmeme.todoapp.data.model.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDataBase : RoomDatabase(){
    abstract fun todoDao() : TodoDao

    companion object{
        @Volatile
        private var INSTANCE: TodoDataBase? = null

        private fun buildDatabase(context: Context): TodoDataBase =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDataBase::class.java,
                "my-todos"
            ).build()

        fun getInstance(context: Context): TodoDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
    }
}