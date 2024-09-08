package com.example.ticktick.adapter

import com.example.ticktick.model.Task

interface TaskClickListener {
    fun completeTask(task: Task)
    fun deleteTask(task: Task)
}