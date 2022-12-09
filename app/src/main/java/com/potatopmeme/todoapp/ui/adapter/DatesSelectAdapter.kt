package com.potatopmeme.todoapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.potatopmeme.todoapp.data.model.Dates
import com.potatopmeme.todoapp.databinding.ItemDatesBinding


class DatesSelectAdapter : ListAdapter<Dates, DatesSelectAdapter.DatesViewHolder>(DatesDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatesViewHolder {
        return DatesViewHolder(
            ItemDatesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DatesViewHolder, position: Int) {
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

    inner class DatesViewHolder(
        private val binding: ItemDatesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Dates) {
            with(binding){
                dates = date
            }

            val pos = absoluteAdapterPosition
            binding.btnDate.setOnClickListener {
                Log.d(TAG, "bind: $pos")
            }
            if (pos != RecyclerView.NO_POSITION) {
                binding.btnDate.setOnClickListener {
                    listener?.onItemClick(binding.tvDate, date, pos)
                }
            }
        }
    }

    companion object {
        private const val TAG = "DatesSelectAdapter"
        private val DatesDiffCallback = object : DiffUtil.ItemCallback<Dates>() {
            override fun areItemsTheSame(oldItem: Dates, newItem: Dates): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Dates, newItem:Dates): Boolean {
                return oldItem == newItem
            }
        }

    }
}