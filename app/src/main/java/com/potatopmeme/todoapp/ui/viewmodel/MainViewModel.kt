package com.potatopmeme.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatopmeme.todoapp.data.model.Todo
import com.potatopmeme.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class MainViewModel(
    private val todoRepository: TodoRepository,
) : ViewModel() {

    fun saveTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.insertTodo(todo)
    }

    fun updateTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.updateTodo(todo)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.deleteBook(todo)
    }

    val savedTodo: StateFlow<List<Todo>> = todoRepository.getTodoAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    fun getTodoWithName(num:Int) = todoRepository.getTodoWithNum(num)

    companion object {
        private const val TAG = "MainViewModel"
    }

}