package com.example.ticktick.task

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ticktick.R
import com.example.ticktick.databinding.TaskItemBinding
import com.example.ticktick.model.Task
import io.realm.kotlin.internal.toDuration
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmInstant
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
        val timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        binding.taskName.text = task.taskName

        if (task.dueDate != null) {
            binding.taskDate.text = "Due: " + convertTimeToString(task.dueDate!!)
        }
        else {
            binding.taskDate.text = ""
        }


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

private fun convertTimeToString(realmInstant: RealmInstant): String {
    val instant = Instant.ofEpochSecond(
        realmInstant.epochSeconds,
        realmInstant.nanosecondsOfSecond.toLong()
    )

    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("d MMM, HH:mm")
    return localDateTime.format(formatter)
}