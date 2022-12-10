package com.potatopmeme.todoapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.potatopmeme.todoapp.data.model.Checked
import com.potatopmeme.todoapp.data.model.Todo

@Database(entities = [Checked::class], version = 1, exportSchema = false)
abstract class CheckedDataBase : RoomDatabase(){
    abstract fun checkedDao() : CheckedDao

    companion object{
        @Volatile
        private var INSTANCE: CheckedDataBase? = null

        private fun buildDatabase(context: Context): CheckedDataBase =
            Room.databaseBuilder(
                context.applicationContext,
                CheckedDataBase::class.java,
                "my-checkes"
            ).build()

        fun getInstance(context: Context): CheckedDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
    }
}