package com.potatopmeme.todoapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.potatopmeme.todoapp.R
import com.potatopmeme.todoapp.databinding.FragmentSettingsBinding
import com.potatopmeme.todoapp.databinding.FragmentTodolistBinding

class TodolistFragment : Fragment() {
    private var _binding: FragmentTodolistBinding? = null
    private val binding get() = _binding!!

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
        binding.btnAdd.setOnClickListener{
            startActivity(Intent(activity as MainActivity,AddActivity::class.java))
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}