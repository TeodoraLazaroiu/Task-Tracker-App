package com.example.ticktick.adapter

import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ticktick.databinding.TaskItemBinding
import com.example.ticktick.model.Task
import com.example.ticktick.utils.Helper

class TaskViewHolder(private val binding: TaskItemBinding, private val clickListener: TaskClickListener) : RecyclerView.ViewHolder(binding.root) {
    private val helper = Helper()
    fun bindTask(task: Task) {
        binding.taskName.text = task.taskName

        if (task.dueDate != null) {
            binding.taskDate.text = buildString {
                append("Due: ")
                append(helper.realmInstantToDateString(task.dueDate!!))
            }
        }
        else {
            binding.taskDate.text = ""
        }

        if (task.completed) {
            binding.taskName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.taskDate.visibility = View.GONE
            binding.taskDelete.visibility = View.VISIBLE
        }

        binding.taskCheckbox.isChecked = task.completed
        binding.taskCheckbox.setOnClickListener {
            clickListener.completeTask(task)
        }

        binding.taskDelete.setOnClickListener {
            clickListener.deleteTask(task)
        }
    }
}