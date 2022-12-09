package com.potatopmeme.todoapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.potatopmeme.todoapp.R
import com.potatopmeme.todoapp.data.model.Dates
import com.potatopmeme.todoapp.databinding.ActivityAddBinding
import com.potatopmeme.todoapp.ui.adapter.DatesSelectAdapter

class AddActivity : AppCompatActivity() {
    private var _binding: ActivityAddBinding? = null
    private val binding get() = _binding!!

    private var arrWeek = arrayOf(false, false, false, false, false, false, false)

    private lateinit var datesSelectAdapter: DatesSelectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =
            DataBindingUtil.setContentView(this, R.layout.activity_add)

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }
        binding.btnSave.setOnClickListener {
            finish()
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
    var a = 0
    private fun setupDatesLayout(appCompatActivity: AppCompatActivity ) {
        datesSelectAdapter = DatesSelectAdapter()
        binding.rvDates.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            datesSelectAdapter.setOnItemClickListener( object : DatesSelectAdapter.OnItemClickListener{
                override fun onItemClick(tv: TextView, date: Dates, pos: Int) {
                    val dlg = DateDialog(appCompatActivity)
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

        binding.fbDatesPlus.setOnClickListener {
            list.add(Dates(""))
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
        binding.btnStartDate.setOnClickListener {
            Log.d(TAG, "setupDurationLayout: btnStartDate clicked")
            val dlg = DateDialog(this)
            dlg.setOnOKClickedListener { dateStr, date ->
                binding.tvStartDate.text = dateStr
                startDate = date
            }
            dlg.show()
        }
        binding.btnEndDate.setOnClickListener {
            Log.d(TAG, "setupDurationLayout: btnEndDate clicked")
            val dlg = DateDialog(this)
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
            val dlg = DateDialog(this)
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
            val dlg = TimeDialog(this)
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
        val sort_list = listOf("None", "Day of the week", "Duration", "Dates")
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
                        setDurationLayoutHeight(0)
                        setDatesLayoutHeight(0)
                    }
                    2 -> {
                        setDateLayoutHeight(0)
                        setWeekLayoutHeight(0)
                        setDurationLayoutHeight(LinearLayout.LayoutParams.WRAP_CONTENT)
                        setDatesLayoutHeight(0)
                    }
                    3 -> {
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

    private fun setDatesLayoutHeight(height: Int) {
        var layoutParams = binding.datesLayout.layoutParams
        layoutParams.height = height
        binding.datesLayout.layoutParams = layoutParams
    }

    companion object {
        private const val TAG = "AddActivity"
    }
}