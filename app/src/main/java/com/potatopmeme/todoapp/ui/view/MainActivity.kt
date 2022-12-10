package com.potatopmeme.todoapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.potatopmeme.todoapp.R
import com.potatopmeme.todoapp.data.db.CheckedDataBase
import com.potatopmeme.todoapp.data.db.TodoDataBase
import com.potatopmeme.todoapp.data.model.Checked
import com.potatopmeme.todoapp.data.repository.CheckedRepositoryImpl
import com.potatopmeme.todoapp.data.repository.TodoRepositoryImpl
import com.potatopmeme.todoapp.databinding.ActivityMainBinding
import com.potatopmeme.todoapp.ui.viewmodel.MainViewModel
import com.potatopmeme.todoapp.ui.viewmodel.MainViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: MainViewModel

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val todo_db = TodoDataBase.getInstance(this)
        val recipeRepositoryImpl = TodoRepositoryImpl(todo_db)
        val checked_db = CheckedDataBase.getInstance(this)
        val checkedRepositoryImpl = CheckedRepositoryImpl(checked_db)

        val factory = MainViewModelProviderFactory(recipeRepositoryImpl,checkedRepositoryImpl)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]


        setupJetpackNavigation()
    }

    private fun setupJetpackNavigation() {
        val host = supportFragmentManager
            .findFragmentById(R.id.todo_nav_host_fragment) as NavHostFragment? ?: return
        navController = host.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}