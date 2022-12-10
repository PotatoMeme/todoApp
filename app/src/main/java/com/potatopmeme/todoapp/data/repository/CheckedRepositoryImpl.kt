package com.potatopmeme.todoapp.data.repository

import com.potatopmeme.todoapp.data.db.CheckedDataBase
import com.potatopmeme.todoapp.data.model.Checked
import kotlinx.coroutines.flow.Flow

class CheckedRepositoryImpl(
    private val db: CheckedDataBase
) : CheckedRepository {
    override suspend fun insertChecked(checked: Checked) {
        db.checkedDao().insertChecked(checked)
    }

    override suspend fun deleteChecked(checked: Checked) {
        db.checkedDao().deleteChecked(checked)
    }

    override suspend fun deleteCheckedWithNum(num: Int) {
        db.checkedDao().deleteCheckedWithNum(num)
    }

    override suspend fun deleteCheckedWithNumAndDate(num: Int, date: String) {
        db.checkedDao().deleteCheckedWithNumAndDate(num, date)
    }

    override fun getCheckedAll(): Flow<List<Checked>> {
        return db.checkedDao().getCheckedAll()
    }

    override fun getCheckedWithNum(num: Int): List<Checked> {
        return db.checkedDao().getCheckedWithNum(num)
    }

    override fun getCheckedWithDate(date: String): List<Checked> {
        return db.checkedDao().getCheckedWithDate(date)
    }

}