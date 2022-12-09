package com.potatopmeme.todoapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.potatopmeme.todoapp.R
import com.potatopmeme.todoapp.data.db.TodoDataBase
import com.potatopmeme.todoapp.data.model.Dates
import com.potatopmeme.todoapp.data.model.Todo
import com.potatopmeme.todoapp.data.repository.TodoRepositoryImpl
import com.potatopmeme.todoapp.databinding.ActivityAddBinding
import com.potatopmeme.todoapp.ui.adapter.DatesSelectAdapter
import com.potatopmeme.todoapp.ui.viewmodel.MainViewModel
import com.potatopmeme.todoapp.ui.viewmodel.MainViewModelProviderFactory

class AddActivity : AppCompatActivity() {
    private var _binding: ActivityAddBinding? = null
    private val binding get() = _binding!!

    private var arrWeek = arrayOf(false, false, false, false, false, false, false)

    lateinit var viewModel: MainViewModel

    private lateinit var datesSelectAdapter: DatesSelectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =
            DataBindingUtil.setContentView(this, R.layout.activity_add)
        
        
        val db = TodoDataBase.getInstance(this)
        val recipeRepositoryImpl = TodoRepositoryImpl(db)
        val factory = MainViewModelProviderFactory(recipeRepositoryImpl)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]


        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }
        binding.btnSave.setOnClickListener {
            val pos = binding.spRepeat.selectedItemPosition
            if (binding.etTitle.text.toString().isNullOrBlank()) {
                Toast.makeText(this, "제목을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else if (pos == 0 && binding.tvDate.text.isNullOrEmpty()) {
                Toast.makeText(this, "날짜를 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else if (pos == 1 && !arrWeek.any { it }) {
                Toast.makeText(this, "주중 하나라도 선택해 주세요", Toast.LENGTH_SHORT).show()
            } else if (pos == 1 && binding.scDuration.isChecked && (binding.tvStartDate.text.toString()
                    .isNullOrEmpty() || binding.tvEndDate.text.toString().isNullOrEmpty())
            ) {
                Toast.makeText(this, "기간의 날짜를 모두 선택해 주세요", Toast.LENGTH_SHORT).show()
            } else if (pos == 1 && binding.scDuration.isChecked && (startDate!! > endDate!!)) {
                Toast.makeText(this, "기간의 시작날짜가 종료날짜보다 큽니다. 다시 선택해 주세요", Toast.LENGTH_SHORT).show()
            } else if (pos == 2 && list.size == 0) {
                Toast.makeText(this, "날짜가 하나도 없습니다. 추가해 주세요", Toast.LENGTH_SHORT).show()
            } else if (pos == 2 && list.any { it.date.isNullOrEmpty() }) {
                Toast.makeText(this, "날짜가 비어있는것이 있습니다. 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else {
                when (pos) {
                    0 -> {
                        viewModel.saveTodo(
                            Todo(
                                title = binding.etTitle.text.toString(),
                                time = binding.tvTime.text.toString(),
                                repeatType = pos,
                                date = binding.tvDate.text.toString(),
                                memo = binding.etMemo.text.toString()
                            )
                        )
                    }
                    1 -> {
                        viewModel.saveTodo(
                            if(binding.scDuration.isSelected){
                                Todo(
                                    title = binding.etTitle.text.toString(),
                                    time = binding.tvTime.text.toString(),
                                    repeatType = pos,
                                    sun = arrWeek[0],
                                    mon = arrWeek[1],
                                    tue = arrWeek[2],
                                    wed = arrWeek[3],
                                    thu = arrWeek[4],
                                    fri = arrWeek[5],
                                    sat = arrWeek[6],
                                    duration = true,
                                    memo = binding.etMemo.text.toString()
                                )
                            }else{
                                Todo(
                                    title = binding.etTitle.text.toString(),
                                    time = binding.tvTime.text.toString(),
                                    repeatType = pos,
                                    sun = arrWeek[0],
                                    mon = arrWeek[1],
                                    tue = arrWeek[2],
                                    wed = arrWeek[3],
                                    thu = arrWeek[4],
                                    fri = arrWeek[5],
                                    sat = arrWeek[6],
                                    duration = false,
                                    startDate = binding.tvStartDate.text.toString(),
                                    endDate = binding.tvEndDate.text.toString(),
                                    memo = binding.etMemo.text.toString()
                                )
                            }
                        )
                    }
                    2->{
                        val dates = list.sortedBy {
                            it.date
                        }.let {
                            var str = StringBuilder()
                            it.forEach { date ->
                                str.append(date).append(" ")
                            }
                            str.toString()
                        }
                        viewModel.saveTodo(
                            Todo(
                                title = binding.etTitle.text.toString(),
                                time = binding.tvTime.text.toString(),
                                repeatType = pos,
                                dates = dates,
                                memo = binding.etMemo.text.toString()
                            )
                        )
                    }
                }
                finish()
            }


        }
        setupTimeLayout()
        setupSpinner()
        setupDateLayout()
        setupWeekLayout()
        setupDurationLayout()
        setupDatesLayout(this)
        setFirstLayoutSet()
    }

    var list = mutableListOf<Dates>()
    private fun setupDatesLayout(appCompatActivity: AppCompatActivity) {
        datesSelectAdapter = DatesSelectAdapter()
        binding.rvDates.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            datesSelectAdapter.setOnItemClickListener(object :
                DatesSelectAdapter.OnItemClickListener {
                override fun onItemClick(tv: TextView, date: Dates, pos: Int) {
                    val dlg = DateDialog(appCompatActivity, date.date)
                    dlg.setOnOKClickedListener { dateStr, date ->
                        list[pos].date = dateStr
                        tv.text = dateStr
                        Log.d(TAG, "onItemClick: $list")
                        datesSelectAdapter.submitList(list.toList())
                    }
                    dlg.show()
                    Log.d(TAG, "onItemClick: $list")
                }
            })
            adapter = datesSelectAdapter
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                list.removeAt(position)
                var layoutParams = binding.rvForm.layoutParams
                layoutParams.height -= 230
                binding.rvForm.layoutParams = layoutParams
                datesSelectAdapter.submitList(list)
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvDates)
        }

        binding.fbDatesPlus.setOnClickListener {
            list.add(Dates(if (list.size > 0) list[list.size - 1].date else ""))
            Log.d(TAG, "setupDatesLayout: $list")
            var layoutParams = binding.rvForm.layoutParams
            layoutParams.height += 230
            binding.rvForm.layoutParams = layoutParams
            datesSelectAdapter.submitList(list.toList())
        }
    }

    var startDate: Int? = null
    var endDate: Int? = null
    private fun setupDurationLayout() {
        binding.scDuration.setOnCheckedChangeListener { _, b ->
            //Log.d(TAG, "setupDurationLayout: ${binding.scDuration.isChecked}")
            when (b) {
                true -> setDurationFormHeight(LinearLayout.LayoutParams.WRAP_CONTENT)
                false -> setDurationFormHeight(0)
            }
        }
        binding.btnStartDate.setOnClickListener {
            Log.d(TAG, "setupDurationLayout: btnStartDate clicked")
            val dlg = DateDialog(this, binding.tvStartDate.text.toString())
            dlg.setOnOKClickedListener { dateStr, date ->
                binding.tvStartDate.text = dateStr
                startDate = date
            }
            dlg.show()
        }
        binding.btnEndDate.setOnClickListener {
            Log.d(TAG, "setupDurationLayout: btnEndDate clicked")
            val dlg = DateDialog(this, binding.tvEndDate.text.toString())
            dlg.setOnOKClickedListener { dateStr, date ->
                binding.tvEndDate.text = dateStr
                endDate = date
            }
            dlg.show()
        }

    }

    var date: Int? = null
    private fun setupDateLayout() {

        binding.btnDate.setOnClickListener {
            Log.d(TAG, "setupDateLayout: btnDate clicked")
            val dlg = DateDialog(this, binding.tvDate.text.toString())
            dlg.setOnOKClickedListener { dateStr, date ->
                binding.tvDate.text = dateStr
                this.date = date
            }
            dlg.show()
        }
    }

    private fun setupTimeLayout() {
        binding.scTime.setOnCheckedChangeListener { _, b ->
            when (b) {
                true -> setTimeLayoutHeight(LinearLayout.LayoutParams.WRAP_CONTENT)
                false -> setTimeLayoutHeight(0)
            }
        }

        binding.btnTime.setOnClickListener {
            Log.d(TAG, "setupTimeLayout: btnTime clicked")
            val dlg = TimeDialog(this, binding.tvTime.text.toString())
            dlg.setOnOKClickedListener { time ->
                binding.tvTime.text = time
            }
            dlg.show()
        }
    }

    private fun setFirstLayoutSet() {
        setTimeLayoutHeight(0)
        setDateLayoutHeight(LinearLayout.LayoutParams.WRAP_CONTENT)
        setWeekLayoutHeight(0)
        setDurationLayoutHeight(0)
        setDatesLayoutHeight(0)
    }

    private fun setupWeekLayout() {
        val colorDarkGray: Int = ContextCompat.getColor(this, R.color.base_dark_gray)
        val colorWhite: Int = ContextCompat.getColor(this, R.color.white)
        val colorBlack: Int = ContextCompat.getColor(this, R.color.black)

        binding.btnSun.setOnClickListener {
            if (arrWeek[0]) {
                binding.btnSun.setCardBackgroundColor(colorWhite)
                binding.tvSun.setTextColor(colorBlack)
                arrWeek[0] = false
            } else {
                binding.btnSun.setCardBackgroundColor(colorDarkGray)
                binding.tvSun.setTextColor(colorWhite)
                arrWeek[0] = true
            }
        }

        binding.btnMon.setOnClickListener {
            if (arrWeek[1]) {
                binding.btnMon.setCardBackgroundColor(colorWhite)
                binding.tvMon.setTextColor(colorBlack)
                arrWeek[1] = false
            } else {
                binding.btnMon.setCardBackgroundColor(colorDarkGray)
                binding.tvMon.setTextColor(colorWhite)
                arrWeek[1] = true
            }
        }

        binding.btnTue.setOnClickListener {
            if (arrWeek[2]) {
                binding.btnTue.setCardBackgroundColor(colorWhite)
                binding.tvTue.setTextColor(colorBlack)
                arrWeek[2] = false
            } else {
                binding.btnTue.setCardBackgroundColor(colorDarkGray)
                binding.tvTue.setTextColor(colorWhite)
                arrWeek[2] = true
            }
        }

        binding.btnWed.setOnClickListener {
            if (arrWeek[3]) {
                binding.btnWed.setCardBackgroundColor(colorWhite)
                binding.tvWed.setTextColor(colorBlack)
                arrWeek[3] = false
            } else {
                binding.btnWed.setCardBackgroundColor(colorDarkGray)
                binding.tvWed.setTextColor(colorWhite)
                arrWeek[3] = true
            }
        }

        binding.btnThu.setOnClickListener {
            if (arrWeek[4]) {
                binding.btnThu.setCardBackgroundColor(colorWhite)
                binding.tvThu.setTextColor(colorBlack)
                arrWeek[4] = false
            } else {
                binding.btnThu.setCardBackgroundColor(colorDarkGray)
                binding.tvThu.setTextColor(colorWhite)
                arrWeek[4] = true
            }
        }

        binding.btnFri.setOnClickListener {
            if (arrWeek[5]) {
                binding.btnFri.setCardBackgroundColor(colorWhite)
                binding.tvFri.setTextColor(colorBlack)
                arrWeek[5] = false
            } else {
                binding.btnFri.setCardBackgroundColor(colorDarkGray)
                binding.tvFri.setTextColor(colorWhite)
                arrWeek[5] = true
            }
        }

        binding.btnSat.setOnClickListener {
            if (arrWeek[6]) {
                binding.btnSat.setCardBackgroundColor(colorWhite)
                binding.tvSat.setTextColor(colorBlack)
                arrWeek[6] = false
            } else {
                binding.btnSat.setCardBackgroundColor(colorDarkGray)
                binding.tvSat.setTextColor(colorWhite)
                arrWeek[6] = true
            }
        }
    }

    private fun setupSpinner() {
        val sort_list = listOf("None", "Day of the week", "Dates")
        val adapter_spinner =
            ArrayAdapter(this, R.layout.itme_dropdown, sort_list)
        binding.spRepeat.setAdapter(adapter_spinner)
        binding.spRepeat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d(TAG, "onItemSelected: $position")
                when (position) {
                    0 -> {
                        setDateLayoutHeight(LinearLayout.LayoutParams.WRAP_CONTENT)
                        setWeekLayoutHeight(0)
                        setDurationLayoutHeight(0)
                        setDatesLayoutHeight(0)
                    }
                    1 -> {
                        setDateLayoutHeight(0)
                        setWeekLayoutHeight(LinearLayout.LayoutParams.WRAP_CONTENT)
                        setDurationLayoutHeight(LinearLayout.LayoutParams.WRAP_CONTENT)
                        setDatesLayoutHeight(0)
                    }
                    2 -> {
                        setDateLayoutHeight(0)
                        setWeekLayoutHeight(0)
                        setDurationLayoutHeight(0)
                        setDatesLayoutHeight(LinearLayout.LayoutParams.WRAP_CONTENT)
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

    private fun setTimeLayoutHeight(height: Int) {
        var layoutParams = binding.timeLayout.layoutParams
        layoutParams.height = height
        binding.timeLayout.layoutParams = layoutParams
    }

    private fun setDateLayoutHeight(height: Int) {
        var layoutParams = binding.dateLayout.layoutParams
        layoutParams.height = height
        binding.dateLayout.layoutParams = layoutParams
    }

    private fun setWeekLayoutHeight(height: Int) {
        var layoutParams = binding.weekLayout.layoutParams
        layoutParams.height = height
        binding.weekLayout.layoutParams = layoutParams
    }

    private fun setDurationLayoutHeight(height: Int) {
        var layoutParams = binding.durationLayout.layoutParams
        layoutParams.height = height
        binding.durationLayout.layoutParams = layoutParams
    }

    private fun setDurationFormHeight(height: Int) {
        var layoutParams = binding.durationForm.layoutParams
        layoutParams.height = height
        binding.durationForm.layoutParams = layoutParams
    }


    private fun setDatesLayoutHeight(height: Int) {
        var layoutParams = binding.datesLayout.layoutParams
        layoutParams.height = height
        binding.datesLayout.layoutParams = layoutParams
    }

    companion object {
        private const val TAG = "AddActivity"
    }
}