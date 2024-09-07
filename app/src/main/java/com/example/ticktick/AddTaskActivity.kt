package com.example.ticktick

import android.R
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ticktick.data.RealmDatabase
import com.example.ticktick.databinding.ActivityAddtaskBinding
import com.example.ticktick.model.Task
import io.realm.kotlin.types.RealmInstant
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddtaskBinding
    private lateinit var databaseRepository: RealmDatabase
    private lateinit var sharedPreferences: SharedPreferences
    private var date: String = ""
    private var time: String = ""

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
        val dateTime = getDateTime()
        val dueDate = formatDateTime(dateTime)
        val task = Task(userId, taskName, dueDate)
        databaseRepository.saveTask(task)
    }

    fun setDate(date:String) {
        this.date = date
    }

    fun setTime(time:String) {
        this.time = time
        this.binding.addTaskButton.isEnabled = true;
    }

    fun getDateTime(): String? {
        if (date.isNotEmpty() && time.isNotEmpty()) {
            return this.date + "T" + this.time
        } else {
            return null
        }
    }

    fun showReminder(isChecked: Boolean) {
        if (isChecked){
            binding.fragmentContainerView.visibility = 0;
            binding.fragmentContainerView.visibility = 0;
            binding.addTaskButton.isEnabled = false;
            //hide the keyboard when showing the time set fragments
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.binding.root.windowToken, 0)
        } else {
            binding.fragmentContainerView.visibility = 8;
            binding.fragmentContainerView.visibility = 8;
            binding.addTaskButton.isEnabled = true;
            this.setDate("")
            this.setTime("")
        }
    }

    fun formatDateTime(dateTimeAsString: String?): RealmInstant? {
        if (dateTimeAsString != null) {
            val ldt = LocalDateTime.parse(dateTimeAsString)
            return RealmInstant.from(
                ldt.toEpochSecond(
                    ZoneId.systemDefault().rules.getOffset(
                        Instant.now()
                    )
                ), ldt.nano
            )
        } else return null
    }

}