package com.example.ticktick.task

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ticktick.R
import com.example.ticktick.databinding.TaskItemBinding
import com.example.ticktick.model.Task
import io.realm.kotlin.query.RealmResults

class TaskAdapter(private val tasks: RealmResults<Task>, private val clickListener: TaskClickListener) : RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(from, parent, false)
        return TaskViewHolder(parent.context, binding, clickListener)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindTask(tasks[position])
    }
}

class TaskViewHolder(private val context: Context, private val binding: TaskItemBinding, private val clickListener: TaskClickListener) : RecyclerView.ViewHolder(binding.root) {
    fun bindTask(task: Task) {
        binding.taskName.text =  task.taskName

        if (task.completed) {
            binding.taskName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        val checkbox = if(task.completed) R.drawable.checked_box else R.drawable.unchecked_box
        binding.taskCheckbox.setImageResource(checkbox)

        binding.taskCheckbox.setOnClickListener {
            clickListener.completeTask(task)
        }
    }
}

interface TaskClickListener {
    fun completeTask(task: Task)
}