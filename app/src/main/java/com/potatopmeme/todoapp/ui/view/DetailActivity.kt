package com.potatopmeme.todoapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.potatopmeme.todoapp.R
import com.potatopmeme.todoapp.data.db.TodoDataBase
import com.potatopmeme.todoapp.data.repository.TodoRepositoryImpl
import com.potatopmeme.todoapp.databinding.ActivityDetailBinding
import com.potatopmeme.todoapp.ui.viewmodel.MainViewModel
import com.potatopmeme.todoapp.ui.viewmodel.MainViewModelProviderFactory

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: MainViewModel

    private var num: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)
        
        num = intent.getIntExtra("num", 0)
        
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.fbDelete.setOnClickListener {
            Snackbar.make(it, "정말로 삭제하시겠습니까?", Snackbar.LENGTH_SHORT).apply {
                setAction("확인") {
                    viewModel.deleteTodoWithNum(num)
                    finish()
                }
            }.show()
        }

        val db = TodoDataBase.getInstance(this)
        val recipeRepositoryImpl = TodoRepositoryImpl(db)
        val factory = MainViewModelProviderFactory(recipeRepositoryImpl)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
       
        viewModel.getTodoWithName(num)
        viewModel.liveData.observe(this) {
            if (it.isEmpty()) return@observe
            val todo = it[0]
            binding.tvTitle.text = todo.title
            if (todo.time.isNotEmpty()) {
                binding.timeForm.setHeightWrap()
                binding.tvTime.text = todo.time
            } else {
                binding.timeForm.setHeight(0)
            }

            when (todo.repeatType) {
                0 -> {
                    binding.tvRepeat.text = "None"
                    binding.dateForm.setHeightWrap()
                    binding.tvDate.text = todo.date
                    binding.weekForm.setHeight(0)
                    binding.durationForm.setHeight(0)
                    binding.datesForm.setHeight(0)
                }
                1 -> {
                    binding.tvRepeat.text = "Day of the week"
                    binding.dateForm.setHeight(0)
                    binding.weekForm.setHeightWrap()

                    binding.tvWeek.text =
                        "${if (todo.sun) "일요일," else ""}${if (todo.mon) "월요일," else ""}${if (todo.tue) "화요일," else ""}${if (todo.wed) "수요일," else ""}${if (todo.thu) "목요일," else ""}${if (todo.fri) "금요일," else ""}${if (todo.sat) "토요일," else ""}"
                            .dropLast(1)

                    if (todo.duration) {
                        binding.durationForm.setHeightWrap()
                        binding.tvStartDate.text = todo.startDate
                        binding.tvEndDate.text = todo.endDate
                    } else {
                        binding.durationForm.setHeight(0)
                    }
                    binding.datesForm.setHeight(0)
                }
                else -> {
                    binding.tvRepeat.text = "Dates"
                    binding.dateForm.setHeight(0)
                    binding.weekForm.setHeight(0)
                    binding.durationForm.setHeight(0)
                    binding.datesForm.setHeightWrap()
                    binding.tvDates.text = todo.dates.replace(" ", "\n")
                }
            }

            binding.tvMemo.text = todo.memo
        }
    }

//    fun setPositiveWeek(card: CardView, tv: TextView) {
//        card.setCardBackgroundColor(colorDarkGray)
//        tv.setTextColor(colorWhite)
//    }
//    fun setNegativeWeek(card: CardView, tv: TextView) {
//        card.setCardBackgroundColor(colorWhite)
//        tv.setTextColor(colorBlack)
//    }

    fun LinearLayout.setHeight(num: Int) {
        var layoutParam = this.layoutParams
        layoutParam.height = num
        this.layoutParams = layoutParam
    }

    fun LinearLayout.setHeightWrap() {
        var layoutParam = this.layoutParams
        layoutParam.height = LinearLayout.LayoutParams.WRAP_CONTENT
        this.layoutParams = layoutParam
    }

    companion object {
//        private val colorDarkGray: Int = Color.parseColor("#444444")
//        private val colorWhite: Int = Color.parseColor("#FFFFFFFF")
//        private val colorBlack: Int = Color.parseColor("#FF000000")
    }
}