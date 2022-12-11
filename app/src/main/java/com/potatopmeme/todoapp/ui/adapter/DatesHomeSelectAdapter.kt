package com.potatopmeme.todoapp.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.potatopmeme.todoapp.data.model.Dates
import com.potatopmeme.todoapp.databinding.ItemDatesBinding
import com.potatopmeme.todoapp.databinding.ItemHomeDatesBinding


class DatesHomeSelectAdapter :
    ListAdapter<Dates, DatesHomeSelectAdapter.DatesHomeViewHolder>(DatesDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatesHomeViewHolder {
        return DatesHomeViewHolder(
            ItemHomeDatesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DatesHomeViewHolder, position: Int) {
        val detail = getItem(position)
        holder.bind(detail!!)
    }

    interface OnItemClickListener {
        fun onItemClick(tv: TextView, date: Dates, pos: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class DatesHomeViewHolder(
        private val binding: ItemHomeDatesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Dates) {
            with(binding) {
                dateItem = date
            }

            binding.tvDate.text=date.date

            binding.tvWeek.text = when(date.weekType%7+1){
                1 -> "sun"
                2 -> "mon"
                3 -> "tue"
                4 -> "wed"
                5 -> "thu"
                6 -> "fri"
                else -> "sat"
            }

            if (date.checked) {
                setPositiveWeek(binding.card,binding.tvWeek,binding.tvDate)
            } else {
                setNegativeWeek(binding.card,binding.tvWeek,binding.tvDate)
            }

            val pos = absoluteAdapterPosition
            itemView.setOnClickListener {
                Log.d(TAG, "bind: $pos")
            }
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(binding.tvDate, date, pos)
                }
            }
        }
    }

    fun setPositiveWeek(card: CardView, tv1: TextView, tv2: TextView) {
        card.setCardBackgroundColor(colorDarkGray)
        tv1.setTextColor(colorWhite)
        tv2.setTextColor(colorWhite)
    }
    fun setNegativeWeek(card: CardView, tv1: TextView, tv2: TextView) {
        card.setCardBackgroundColor(colorWhite)
        tv1.setTextColor(colorBlack)
        tv2.setTextColor(colorBlack)
    }

    companion object {
        private val colorDarkGray: Int = Color.parseColor("#444444")
        private val colorWhite: Int = Color.parseColor("#FFFFFFFF")
        private val colorBlack: Int = Color.parseColor("#FF000000")

        private const val TAG = "DatesSelectAdapter"
        private val DatesDiffCallback = object : DiffUtil.ItemCallback<Dates>() {
            override fun areItemsTheSame(oldItem: Dates, newItem: Dates): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Dates, newItem: Dates): Boolean {
                return oldItem == newItem
            }
        }

    }
}