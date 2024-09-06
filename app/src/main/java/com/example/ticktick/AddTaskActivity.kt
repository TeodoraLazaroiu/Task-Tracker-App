package com.example.ticktick

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ticktick.data.RealmDatabase
import com.example.ticktick.databinding.ActivityAddtaskBinding
import com.example.ticktick.model.Task
import io.realm.kotlin.types.RealmInstant
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddtaskBinding
    private lateinit var databaseRepository: RealmDatabase
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var date: String
    private lateinit var time: String

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

        binding.reminderCheckbox.setOnCheckedChangeListener {
            buttonView, isChecked ->
                showReminder(isChecked)
        }
    }

    fun createTask() {
        var taskName = binding.addTaskName.text.toString()
        val userId = sharedPreferences.getString("user_id", "DEFAULT")?:"DEFAULT";
        //TODO() convert date to realminstant
        val dueDate = RealmInstant.now()
        val task = Task(userId, taskName, dueDate)
        databaseRepository.saveTask(task)
    }

    fun setDate(date:String) {
        this.date = date
    }

    fun setTime(time:String) {
        this.time = time
    }

    fun getDateTime():String{
        return this.date+"T"+this.time
    }

    fun showReminder(isChecked: Boolean) {
        if (isChecked){
            binding.fragmentContainerView.visibility = 0;
            binding.fragmentContainerView.visibility = 0;
        } else {
            binding.fragmentContainerView.visibility = 8;
            binding.fragmentContainerView.visibility = 8;
        }
    }

    fun formatDateTime(dateTimeAsString: String):LocalDateTime {
        return LocalDateTime.parse(dateTimeAsString)
    }
}