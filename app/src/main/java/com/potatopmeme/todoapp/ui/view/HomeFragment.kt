package com.potatopmeme.todoapp.ui.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.potatopmeme.todoapp.R
import com.potatopmeme.todoapp.data.model.Checked
import com.potatopmeme.todoapp.data.model.Dates
import com.potatopmeme.todoapp.data.model.Todo
import com.potatopmeme.todoapp.databinding.FragmentHomeBinding
import com.potatopmeme.todoapp.ui.adapter.DatesHomeSelectAdapter
import com.potatopmeme.todoapp.ui.adapter.TodoHomeListAdapter
import com.potatopmeme.todoapp.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

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
    var dayNum: Int = 0

    private lateinit var viewModel: MainViewModel

    private lateinit var todoHomeListAdapter: TodoHomeListAdapter
    private lateinit var datesHomeSelectAdapter: DatesHomeSelectAdapter


    var task = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setupDatesRecyclerView()
        setupTodoRecyclerView()

        val cal = Calendar.getInstance()

        cal.time = mDate
        dayNum = cal.get(Calendar.DAY_OF_WEEK)
        binding.tvDate.text = mFormat.format(mDate)
        val dateInt = binding.tvDate.text.toString().split("/").let {
            var date = it[0].toInt() * 10000 + it[1].toInt() * 100 + it[2].toInt()
            date
        }


        val maxDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        var list = mutableListOf<Dates>()
        var weekType = dayNum + (7 * 5) - dateInt % 100
        for (i in 1..maxDate) {
            list.add(Dates("$i", weekType++, false))
        }
        list[dateInt % 100 - 1].checked = true
        datesHomeSelectAdapter.submitList(list)
        viewModel.getTodoWithDate(
            dateStr = binding.tvDate.text.toString(),
            dateInt = dateInt,
            weekType = dayNum
        )
        //binding.rvDates.scrollToPosition(dateInt % 100 - 1)

        val width = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            80f, this.getResources().getDisplayMetrics()
        ).toInt()

        val width_default = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            30f, this.getResources().getDisplayMetrics()
        ).toInt()

        viewModel.checkedLiveData.observe(viewLifecycleOwner) {
            Log.d(TAG, "$it")
            val todoList = viewModel.todoLiveData.value
            todoHomeListAdapter.checkedList = it
            task = todoList!!.size - it.size
            binding.tvAlive.text = "$task tasks alive"
            var layoutParams = binding.checkedForm.layoutParams
            layoutParams.height = width_default + width * todoList!!.size
            binding.checkedForm.layoutParams = layoutParams
            todoHomeListAdapter.submitList(todoList)
            binding.rvDates.scrollToPosition(
                binding.tvDate.text.toString().split("/")[2].toInt() - 1
            )
        }

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(activity as MainActivity, AddActivity::class.java))
        }

        binding.btnDate.setOnClickListener {
            val dlg = DateDialog(activity as MainActivity, binding.tvDate.text.toString())
            dlg.setOnOKClickedListener { dateStr, date ->
                binding.tvDate.text = dateStr
                mDate = mFormat.parse(dateStr)
                val cal = Calendar.getInstance()
                cal.time = mDate
                dayNum = cal.get(Calendar.DAY_OF_WEEK)
                val maxDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
                var list = mutableListOf<Dates>()
                var weekType = dayNum + (7 * 5) - date % 100
                for (i in 1..maxDate) {
                    list.add(Dates("$i", weekType++, false))
                }
                list[date % 100 - 1].checked = true
                datesHomeSelectAdapter.submitList(list)
                //binding.rvDates.scrollToPosition(date % 100 - 1)

                viewModel.getTodoWithDate(
                    dateStr = binding.tvDate.text.toString(),
                    dateInt = date,
                    weekType = dayNum
                )
            }
            dlg.show()
        }


    }

    private fun setupDatesRecyclerView() {
        datesHomeSelectAdapter = DatesHomeSelectAdapter()
        binding.rvDates.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            datesHomeSelectAdapter.setOnItemClickListener(object :
                DatesHomeSelectAdapter.OnItemClickListener {
                override fun onItemClick(tv: TextView, date: Dates, pos: Int) {
                    var dateStr = binding.tvDate.text.toString()
                    var split = dateStr.split("/")
                    dateStr =
                        "${split[0]}/${split[1]}/${if (pos < 9) "0${pos + 1}" else "${pos + 1}"}"
                    binding.tvDate.text = dateStr
                    mDate = mFormat.parse(dateStr)
                    val cal = Calendar.getInstance()
                    cal.time = mDate
                    dayNum = cal.get(Calendar.DAY_OF_WEEK)

                    val maxDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
                    var list = mutableListOf<Dates>()
                    var weekType = dayNum + (7 * 5) - (pos + 1) % 100
                    for (i in 1..maxDate) {
                        list.add(Dates("$i", weekType++, false))
                    }
                    list[pos].checked = true
                    datesHomeSelectAdapter.submitList(list)

                    viewModel.getTodoWithDate(
                        dateStr = binding.tvDate.text.toString(),
                        dateInt = split[0].toInt() * 10000 + split[1].toInt() * 100 + pos + 1,
                        weekType = dayNum
                    )
                    //binding.rvDates.scrollToPosition((pos+1) % 100 - 1)
                }
            })
            adapter = datesHomeSelectAdapter
        }
    }

    private fun setupTodoRecyclerView() {
        todoHomeListAdapter = TodoHomeListAdapter(listOf())
        binding.rvChecked.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            todoHomeListAdapter.setOnItemClickListener1(object :
                TodoHomeListAdapter.OnItemClickListener1 {
                override fun onItemClick(v: View, todo: Todo, pos: Int) {
                    val intent = Intent(context as MainActivity, DetailActivity::class.java)
                    intent.putExtra("num", todo.num)
                    startActivity(intent)
                }
            })
            todoHomeListAdapter.setOnItemClickListener2(object :
                TodoHomeListAdapter.OnItemClickListener2 {
                override fun onItemClick(v: View, todo: Todo, checked: ImageView, pos: Int) {
                    Log.d(TAG, "onItemClick: ${checked.isActivated}")
                    checked.isActivated = !checked.isActivated
                    if (checked.isActivated) {
                        viewModel.saveChecked(
                            Checked(
                                todo_num = todo.num,
                                date = binding.tvDate.text.toString()
                            )
                        )
                    } else {
                        viewModel.deleteCheckedWith(todo.num, binding.tvDate.text.toString())
                    }
                }
            })
            adapter = todoHomeListAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}