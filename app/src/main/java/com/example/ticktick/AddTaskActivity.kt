package com.example.ticktick

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ticktick.data.RealmDatabase
import com.example.ticktick.databinding.ActivityAddtaskBinding
import com.example.ticktick.model.Task

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddtaskBinding
    private lateinit var databaseRepository: RealmDatabase
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddtaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseRepository = RealmDatabase()
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)

        var addTaskButton = binding.addTaskButton
        addTaskButton.setOnClickListener {
            createTask()
        }
    }

    fun createTask() {
        var taskName = binding.addTaskName.text.toString()
        val userId = sharedPreferences.getString("user_id", "DEFAULT")?:"DEFAULT";
        val task = Task(userId, taskName)
        databaseRepository.saveTask(task)
    }
}