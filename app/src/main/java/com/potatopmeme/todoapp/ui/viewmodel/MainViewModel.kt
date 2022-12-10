package com.potatopmeme.todoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatopmeme.todoapp.data.model.Checked
import com.potatopmeme.todoapp.data.model.Todo
import com.potatopmeme.todoapp.data.repository.CheckedRepository
import com.potatopmeme.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class MainViewModel(
    private val todoRepository: TodoRepository,
    private val checkedRepository: CheckedRepository,
) : ViewModel() {

    fun saveTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.insertTodo(todo)
    }

    fun updateTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.updateTodo(todo)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.deleteTodo(todo)
        checkedRepository.deleteCheckedWithNum(todo.num)
    }

    fun deleteTodoWithNum(num: Int) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.deleteTodoWithNum(num)
        checkedRepository.deleteCheckedWithNum(num)
    }

    val savedTodo: StateFlow<List<Todo>> = todoRepository.getTodoAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    private val _todoLiveData = MutableLiveData<List<Todo>>()
    val todoLiveData: LiveData<List<Todo>> get() = _todoLiveData
    fun getTodoWithName(num: Int) = viewModelScope.launch(Dispatchers.IO) {
        _todoLiveData.postValue(
            todoRepository.getTodoWithNum(num)
        )
    }

    fun getTodoWithDate(dateStr: String,dateInt: Int,weekType :Int) = viewModelScope.launch(Dispatchers.IO) {
        _todoLiveData.postValue(
            todoRepository.getTodoWithDate(dateStr, dateInt, weekType)
        )
        getCheckedWithData(dateStr)
    }

    private val _checkedLiveData = MutableLiveData<List<Checked>>()
    val checkedLiveData: LiveData<List<Checked>> get() = _checkedLiveData

    fun getCheckedWithData(dateStr:String)= viewModelScope.launch(Dispatchers.IO) {
        _checkedLiveData.postValue(
            checkedRepository.getCheckedWithDate(dateStr)
        )
    }

    fun saveChecked(checked: Checked)= viewModelScope.launch(Dispatchers.IO) {
        checkedRepository.insertChecked(checked)
    }

    fun deleteChecked(checked: Checked)= viewModelScope.launch(Dispatchers.IO) {
        checkedRepository.deleteChecked(checked)
    }
    fun deleteCheckedWith(num: Int,dateStr: String)= viewModelScope.launch(Dispatchers.IO) {
        checkedRepository.deleteCheckedWithNumAndDate(num,dateStr)
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}