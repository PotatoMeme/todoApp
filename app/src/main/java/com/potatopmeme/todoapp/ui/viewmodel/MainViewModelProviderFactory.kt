package com.potatopmeme.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.potatopmeme.todoapp.data.repository.TodoRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelProviderFactory(
    private val todoRepository: TodoRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(todoRepository) as T
        }
        throw java.lang.IllegalArgumentException("ViewModel class not found")
    }
}