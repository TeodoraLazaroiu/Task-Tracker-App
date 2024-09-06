package com.example.ticktick

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ticktick.data.RealmDatabase
import com.example.ticktick.databinding.ActivityAddtaskBinding
import com.example.ticktick.model.Task

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddtaskBinding
    private lateinit var databaseRepository: RealmDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddtaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseRepository = RealmDatabase()

        var addTaskButton = binding.addTaskButton
        addTaskButton.setOnClickListener {
            createTask()
        }
    }

    fun createTask() {
        var taskName = binding.addTaskName.text.toString()
        val userId = intent.extras?.getString("uid")
        val task = Task(userId, taskName)
        databaseRepository.saveTask(task)
    }
}