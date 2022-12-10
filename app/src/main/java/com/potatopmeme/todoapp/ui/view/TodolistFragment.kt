package com.potatopmeme.todoapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.potatopmeme.todoapp.R
import com.potatopmeme.todoapp.data.model.Todo
import com.potatopmeme.todoapp.databinding.FragmentTodolistBinding
import com.potatopmeme.todoapp.ui.adapter.TodoListAdapter
import com.potatopmeme.todoapp.ui.viewmodel.MainViewModel
import com.potatopmeme.todoapp.util.collectLatestStateFlow

class TodolistFragment : Fragment() {
    private var _binding: FragmentTodolistBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private lateinit var todoListAdapter: TodoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todolist, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        binding.btnAdd.setOnClickListener{
            startActivity(Intent(activity as MainActivity,AddActivity::class.java))
        }

        collectLatestStateFlow(viewModel.savedTodo){
            todoListAdapter.submitList(it)
            var layoutParams = binding.rvForm.layoutParams
            layoutParams.height = 280*it.size
            binding.rvForm.layoutParams = layoutParams
        }
        
        viewModel.todoLiveData.observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated: $it")
        }

        setupRecyclerView()
        setupTouchHelper(view)
    }

    private fun setupTouchHelper(view: View) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val todo = todoListAdapter.currentList[position]
                viewModel.deleteTodo(todo)
                Snackbar.make(view, "Todo has deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.saveTodo(todo)
                    }
                }.show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvTodo)
        }
    }

    private fun setupRecyclerView() {
        todoListAdapter = TodoListAdapter()
        binding.rvTodo.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            todoListAdapter.setOnItemClickListener( object :TodoListAdapter.OnItemClickListener{
                override fun onItemClick(v: View, todo: Todo, pos: Int) {
                    Log.d(TAG, "onItemClick: $todo")
                    var intent = Intent(context,DetailActivity::class.java)
                    intent.putExtra("num",todo.num)
                    startActivity(intent)
                }
            })
            adapter = todoListAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object{
        private const val TAG = "TodolistFragment"
    }
}