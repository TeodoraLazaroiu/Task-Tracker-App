package com.example.ticktick.task

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ticktick.data.RealmDatabase
import com.example.ticktick.model.Task

class TaskViewModel(private val database: RealmDatabase): ViewModel() {
    var taskList = MutableLiveData<MutableList<Task>>()

    fun addTaskItem(task: Task) {
        val list = taskList.value
        list?.add(task)
        taskList.postValue(list)
    }

    fun addTask(task: Task) {
        val list = taskList.value
        list?.add(task)
        taskList.postValue(list)
    }

    fun completeTask(task: Task) {
        val list = taskList.value
        val listTask = list?.first { it._id == task._id }
        listTask?.completed = true
        taskList.postValue(list)
    }
}