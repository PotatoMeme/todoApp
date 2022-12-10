package com.potatopmeme.todoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        todoRepository.deleteTodo(todo)
    }

    fun deleteTodoWithNum(num: Int) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.deleteTodoWithNum(num)
    }

    val savedTodo: StateFlow<List<Todo>> = todoRepository.getTodoAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    private val _liveData = MutableLiveData<List<Todo>>()
    val liveData: LiveData<List<Todo>> get() = _liveData
    fun getTodoWithName(num: Int) = viewModelScope.launch(Dispatchers.IO) {
        _liveData.postValue(
            todoRepository.getTodoWithNum(num)
        )
    }

    fun getTodoWithDate(dateStr: String,dateInt: Int,weekType :Int) = viewModelScope.launch(Dispatchers.IO) {
        _liveData.postValue(
            todoRepository.getTodoWithDate(dateStr, dateInt, weekType)
        )
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}