package com.potatopmeme.todoapp.ui.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.potatopmeme.todoapp.R
import com.potatopmeme.todoapp.data.model.Checked
import com.potatopmeme.todoapp.data.model.Todo
import com.potatopmeme.todoapp.databinding.FragmentHomeBinding
import com.potatopmeme.todoapp.ui.adapter.TodoHomeListAdapter
import com.potatopmeme.todoapp.ui.adapter.TodoListAdapter
import com.potatopmeme.todoapp.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    val mFormat = SimpleDateFormat("yyyy/M/dd")

    var mNow = System.currentTimeMillis()
    var mDate = Date(mNow)
    var dayNum :Int = 0

    private lateinit var viewModel: MainViewModel

    private lateinit var todoHomeListAdapter: TodoHomeListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val cal = Calendar.getInstance()
        cal.time = mDate
        dayNum = cal.get(Calendar.DAY_OF_WEEK)
        binding.tvDate.text = mFormat.format(mDate)


        viewModel.todoLiveData.observe(viewLifecycleOwner){
            //todoHomeListAdapter.checkedList = viewModel.checkedLiveData.value!!
            todoHomeListAdapter.submitList(it)

        }
        viewModel.checkedLiveData.observe(viewLifecycleOwner){
            val todoList = viewModel.todoLiveData.value
            todoHomeListAdapter.checkedList = it
            todoHomeListAdapter.submitList(todoList)
        }

        viewModel.getTodoWithDate(
            dateStr = binding.tvDate.text.toString(),
            dateInt = binding.tvDate.text.toString().split("/").let {
                var date = it[0].toInt()*10000 + it[1].toInt()*100 + it[2].toInt()
                date
            },
            weekType = dayNum
        )

        binding.btnDate.setOnClickListener {
            val dlg = DateDialog(activity as MainActivity , binding.tvDate.text.toString())
            dlg.setOnOKClickedListener { dateStr, date ->
                binding.tvDate.text = dateStr
                mDate = mFormat.parse(dateStr)
                val cal = Calendar.getInstance()
                cal.time = mDate
                dayNum = cal.get(Calendar.DAY_OF_WEEK)
                viewModel.getTodoWithDate(
                    dateStr = binding.tvDate.text.toString(),
                    dateInt = date,
                    weekType = dayNum
                )
            }
            dlg.show()
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        todoHomeListAdapter = TodoHomeListAdapter(listOf())
        binding.rvChecked.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            todoHomeListAdapter.setOnItemClickListener1( object :TodoHomeListAdapter.OnItemClickListener1{
                override fun onItemClick(v: View, todo: Todo, pos: Int) {
                    val intent = Intent(context as MainActivity,DetailActivity::class.java)
                    intent.putExtra("num",todo.num)
                    startActivity(intent)
                }
            })
            todoHomeListAdapter.setOnItemClickListener2( object :TodoHomeListAdapter.OnItemClickListener2{
                override fun onItemClick(v: View, todo: Todo, checked: ImageView, pos: Int) {
                    Log.d(TAG, "onItemClick: ${checked.isActivated}")
                    checked.isActivated = !checked.isActivated
                    if(checked.isActivated ){
                        viewModel.saveChecked(Checked(todo_num = todo.num, date = binding.tvDate.text.toString()))
                    }else{
                        viewModel.deleteCheckedWith(todo.num, binding.tvDate.text.toString())
                    }
                    viewModel.getTodoWithDate(
                        dateStr = binding.tvDate.text.toString(),
                        dateInt = binding.tvDate.text.toString().split("/").let {
                            var date = it[0].toInt()*10000 + it[1].toInt()*100 + it[2].toInt()
                            date
                        },
                        weekType = dayNum
                    )
                }
            })
            adapter = todoHomeListAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object{
        private const val TAG = "HomeFragment"
    }
}