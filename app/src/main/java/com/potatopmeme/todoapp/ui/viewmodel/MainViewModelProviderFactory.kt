package com.potatopmeme.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.potatopmeme.todoapp.data.repository.CheckedRepository
import com.potatopmeme.todoapp.data.repository.TodoRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelProviderFactory(
    private val todoRepository: TodoRepository,
    private val checkedRepository: CheckedRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(todoRepository,checkedRepository) as T
        }
        throw java.lang.IllegalArgumentException("ViewModel class not found")
    }
}