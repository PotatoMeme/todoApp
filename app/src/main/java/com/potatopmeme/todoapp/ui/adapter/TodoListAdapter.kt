package com.potatopmeme.todoapp.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.potatopmeme.todoapp.data.model.Todo
import com.potatopmeme.todoapp.databinding.ItemTodoBinding
import com.potatopmeme.todoapp.databinding.ItemTodoTimeBinding

class TodoListAdapter : ListAdapter<Todo, RecyclerView.ViewHolder>(TodoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> TodoViewHolder(
                ItemTodoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> TodoTimeViewHolder(
                ItemTodoTimeBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).time.isNullOrBlank()) 0 else 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val detail = getItem(position)
        when (getItem(position).time.isNullOrBlank()) {
            true -> (holder as TodoViewHolder).bind(detail!!)
            false -> (holder as TodoTimeViewHolder).bind(detail!!)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, todo: Todo, pos: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class TodoViewHolder(
        private val binding: ItemTodoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            with(binding) {
                todoItem = todo
                cardView.setCardBackgroundColor(
                    Color.parseColor(
                        when (todo.repeatType) {
                            0 -> "#1FE9B2"
                            1 -> "#FCB050"
                            else -> "#FD5D5D"
                        }
                    )
                )
                tvSecond.text = when (todo.repeatType) {
                    0 -> "${todo.date}"
                    1 -> "${if (todo.sun) "일" else ""}${if (todo.mon) "월" else ""}${if (todo.tue) "화" else ""}${if (todo.wed) "수" else ""}${if (todo.thu) "목" else ""}${if (todo.fri) "금" else ""}${if (todo.sat) "토" else ""}"
                    else -> "${todo.dates.split(" ")[0]} +"
                }
            }
            val pos = absoluteAdapterPosition
            itemView.setOnClickListener {
                Log.d(TAG, "bind: $pos")
            }
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(it, todo, pos)
                }
            }
        }
    }

    inner class TodoTimeViewHolder(
        private val binding: ItemTodoTimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            with(binding) {
                todoItem = todo
                cardView.setCardBackgroundColor(
                    Color.parseColor(
                        when (todo.repeatType) {
                            0 -> "#1FE9B2"
                            1 -> "#FCB050"
                            else -> "#FD5D5D"
                        }
                    )
                )
                tvSecond.text = when (todo.repeatType) {
                    0 -> "${todo.date}"
                    1 -> "${if (todo.sun) "일" else ""}${if (todo.mon) "월" else ""}${if (todo.tue) "화" else ""}${if (todo.wed) "수" else ""}${if (todo.thu) "목" else ""}${if (todo.fri) "금" else ""}${if (todo.sat) "토" else ""}"
                    else -> "${todo.dates.split(" ")[0]} +"
                }
            }
            val pos = absoluteAdapterPosition
            itemView.setOnClickListener {
                Log.d(TAG, "bind: $pos")
            }
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(it, todo, pos)
                }
            }
        }
    }

    companion object {
        private const val TAG = "DatesSelectAdapter"
        private val TodoDiffCallback = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }
        }

    }
}