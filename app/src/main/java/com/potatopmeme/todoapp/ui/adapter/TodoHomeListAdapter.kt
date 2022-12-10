package com.potatopmeme.todoapp.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.potatopmeme.todoapp.data.model.Checked
import com.potatopmeme.todoapp.data.model.Todo
import com.potatopmeme.todoapp.databinding.ItemHomeTodoBinding
import com.potatopmeme.todoapp.databinding.ItemHomeTodoTimeBinding
import com.potatopmeme.todoapp.databinding.ItemTodoBinding
import com.potatopmeme.todoapp.databinding.ItemTodoTimeBinding

class TodoHomeListAdapter( var checkedList : List<Checked>) : ListAdapter<Todo, RecyclerView.ViewHolder>(TodoDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> TodoHomeViewHolder(
                ItemHomeTodoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> TodoHomeTimeViewHolder(
                ItemHomeTodoTimeBinding.inflate(
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
            true -> (holder as TodoHomeViewHolder).bind(detail!!)
            false -> (holder as TodoHomeTimeViewHolder).bind(detail!!)
        }
    }

    interface OnItemClickListener1 {
        fun onItemClick(v: View, todo: Todo , pos: Int)
    }

    interface OnItemClickListener2 {
        fun onItemClick(v: View, todo: Todo , checked : ImageView , pos: Int)
    }

    private var listener1: OnItemClickListener1? = null
    private var listener2: OnItemClickListener2? = null

    fun setOnItemClickListener1(listener: OnItemClickListener1) {
        this.listener1 = listener
    }
    fun setOnItemClickListener2(listener: OnItemClickListener2) {
        this.listener2 = listener
    }

    inner class TodoHomeViewHolder(
        private val binding: ItemHomeTodoBinding
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
            }

            if(checkedList.any { it.todo_num == todo.num }){
                binding.btnCheck.isActivated = true
            }

            val pos = absoluteAdapterPosition
            itemView.setOnClickListener {
                Log.d(TAG, "bind: $pos")
            }
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener1?.onItemClick(it, todo, pos)
                }
            }
            if (pos != RecyclerView.NO_POSITION) {
                binding.btnCheck.setOnClickListener {
                    listener2?.onItemClick(it, todo,binding.btnCheck, pos)
                }
            }
        }
    }

    inner class TodoHomeTimeViewHolder(
        private val binding: ItemHomeTodoTimeBinding
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
            }

            if(checkedList.any { it.todo_num == todo.num }){
                binding.btnCheck.isActivated = true
            }

            val pos = absoluteAdapterPosition
            itemView.setOnClickListener {
                Log.d(TAG, "bind: $pos")
            }
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener1?.onItemClick(it, todo, pos)
                }
            }
            if (pos != RecyclerView.NO_POSITION) {
                binding.btnCheck.setOnClickListener {
                    listener2?.onItemClick(it, todo,binding.btnCheck, pos)
                }
            }
        }
    }

    companion object {
        private const val TAG = "TodoHomeListAdapter"
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