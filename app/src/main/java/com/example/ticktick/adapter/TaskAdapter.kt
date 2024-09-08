package com.example.ticktick.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ticktick.databinding.TaskItemBinding
import com.example.ticktick.model.Task
import io.realm.kotlin.query.RealmResults

class TaskAdapter(private val tasks: RealmResults<Task>, private val clickListener: TaskClickListener) : RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(from, parent, false)
        return TaskViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindTask(tasks[position])
    }
}